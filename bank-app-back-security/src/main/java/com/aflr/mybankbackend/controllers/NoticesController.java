package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Notice;
import com.aflr.mybankbackend.repositories.NoticeRepository;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class NoticesController {

    private NoticeRepository noticeRepository;

    public NoticesController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {
        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if (!notices.isEmpty()) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS.SECONDS))
                    .body(notices);
        }
        return ResponseEntity.noContent().build();
    }

}
