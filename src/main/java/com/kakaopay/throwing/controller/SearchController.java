package com.kakaopay.throwing.controller;

import com.kakaopay.throwing.service.SearchService;
import com.kakaopay.throwing.vo.SearchDTO;
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
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/search")
    public ResponseEntity<SearchDTO> searchThrowing(@RequestBody SearchDTO searchDTO, HttpServletRequest request) throws Exception {
        log.info("search Request parameter : {}", searchDTO.toString());
        return ResponseEntity.ok(searchService.getThrowingInfo(searchDTO, request));
    }
}
