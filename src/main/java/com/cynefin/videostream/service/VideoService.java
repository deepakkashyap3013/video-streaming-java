package com.cynefin.videostream.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoService {

    @Value("${video.file-path}")
    private String videoPath;

    //Maximum chunk size (1MB = 1024 * 1024 bytes)
    private static final long MAX_CHUNK_SIZE = 1024 * 1024;

    private final ResourceLoader resourceLoader;

    public VideoService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ResponseEntity<Resource> getHomePage() {
        Resource resource = resourceLoader.getResource("classpath:/static/index.html");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/html")
                .body(resource);
    }

    public ResponseEntity<Resource> streamVideo(String rangeHeader) {
        try {
            File videoFile = new File(videoPath);
            long fileSize = videoFile.length();

            long startPosition = 0;
            long endPosition = Math.min(MAX_CHUNK_SIZE - 1, fileSize - 1);

            if (rangeHeader != null) {
                // Parse range header
                List<HttpRange> ranges = HttpRange.parseRanges(rangeHeader);
                if (!ranges.isEmpty()) {
                    HttpRange range = ranges.get(0);
                    startPosition = range.getRangeStart(fileSize);
                    long requestedEnd = range.getRangeEnd(fileSize);
                    // Limit the chunk size
                    endPosition = Math.min(startPosition + MAX_CHUNK_SIZE - 1, requestedEnd);
                }
            }

            // Calculate content length
            long contentLength = endPosition - startPosition + 1;

            // Create input stream for the specified range
            InputStream inputStream = Files.newInputStream(videoFile.toPath());
            inputStream.skip(startPosition);

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
            headers.add(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d",
                    startPosition, endPosition, fileSize));

            // Create InputStreamResource that will be streamed to the client
            InputStreamResource resource = new InputStreamResource(inputStream) {
                @Override
                public long contentLength() throws IOException {
                    return contentLength;
                }
            };

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Object> getVideoInformation() {
        File videoFile = new File(videoPath);

        Map<String, Object> videoInfo = new HashMap<>();
        videoInfo.put("name", videoFile.getName());
        videoInfo.put("size", videoFile.length());
        videoInfo.put("type", "video/mp4");

        return ResponseEntity.ok(videoInfo);
    }
}