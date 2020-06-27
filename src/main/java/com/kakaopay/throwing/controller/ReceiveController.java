package com.kakaopay.throwing.controller;

import com.kakaopay.throwing.service.ReceiveService;
import com.kakaopay.throwing.vo.ReceiveDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReceiveController {
    private final ReceiveService receiveService;

    @PostMapping("/receive")
    public ResponseEntity<ReceiveDTO> receiveMoney(@RequestBody ReceiveDTO receiveDTO, HttpServletRequest request) throws Exception {
        log.info("receive Request parameter : {}", receiveDTO.toString());
        return ResponseEntity.ok(receiveService.getMoney(receiveDTO, request));
    }
}
