package com.cynefin.videostream.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoService {

    @Value("${video.file-path}")
    private String videoPath;

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

    public ResponseEntity<Resource> streamVideo(String range) {
        File videoFile = new File(videoPath);
        long videoLength = videoFile.length();

        try {
            RandomAccessFile file = new RandomAccessFile(videoFile, "r");
            long start = 0, end = videoLength - 1;

            if (range != null) {
                HttpRange httpRange = HttpRange.parseRanges(range).get(0);
                start = httpRange.getRangeStart(videoLength);
                end = httpRange.getRangeEnd(videoLength);
            }

            long contentLength = end - start + 1;

            byte[] videoBytes = new byte[(int) contentLength];
            file.seek(start);
            file.readFully(videoBytes);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + videoLength)
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(new org.springframework.core.io.ByteArrayResource(videoBytes));

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