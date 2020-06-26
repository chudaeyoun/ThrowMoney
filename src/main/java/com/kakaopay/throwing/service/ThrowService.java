package com.kakaopay.throwing.service;

import com.kakaopay.throwing.common.ParserUtils;
import com.kakaopay.throwing.domain.Distribution;
import com.kakaopay.throwing.domain.Throw;
import com.kakaopay.throwing.repository.DistributionRepository;
import com.kakaopay.throwing.repository.ThrowRepository;
import com.kakaopay.throwing.vo.ThrowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ThrowService {
    private final ThrowRepository throwRepository;
    private final DistributionRepository distributionRepository;

    public ThrowDTO makeToken(ThrowDTO throwDTO) {
        throwDTO.setToken("TTT");
        return throwDTO;
    }

    public void saveThrow(ThrowDTO throwDTO, HttpServletRequest request) {
        throwRepository.save(
                Throw.builder()
                        .token(makeToken())
                        .user(ParserUtils.getUserHeader(request))
                        .room(ParserUtils.getUserHeader(request))
                        .count(throwDTO.getCount())
                        .money(throwDTO.getMoney())
                        .build());
    }

    private String makeToken() {
        String token = "";

        return token;
    }
}
