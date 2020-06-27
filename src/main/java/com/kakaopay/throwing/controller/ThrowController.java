package com.kakaopay.throwing.controller;

import com.kakaopay.throwing.service.ThrowService;
import com.kakaopay.throwing.vo.ThrowDTO;
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
public class ThrowController {
    private final ThrowService throwService;

    @PostMapping("/throwing")
    public ResponseEntity<ThrowDTO> throwMoney(@RequestBody ThrowDTO throwDTO, HttpServletRequest request) {
        log.info("throwing Request parameter : {}", throwDTO.toString());
        return ResponseEntity.ok(throwService.getThrow(throwDTO, request));
    }
}
