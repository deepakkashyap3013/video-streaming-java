package com.cynefin.videostream.controller;

import com.cynefin.videostream.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public ResponseEntity<Resource> getHomePage() {
        return videoService.getHomePage();
    }

    @GetMapping("/video")
    public ResponseEntity<Resource> streamVideo(@RequestHeader(value = "Range", required = false) String range) {
        return videoService.streamVideo(range);
    }

    @GetMapping("/video/info")
    public ResponseEntity<Object> getVideoInformation() {
        return videoService.getVideoInformation();
    }
}