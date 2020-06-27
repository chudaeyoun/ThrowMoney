package com.kakaopay.throwing.service;

import com.kakaopay.throwing.domain.Distribution;
import com.kakaopay.throwing.repository.DistributionRepository;
import com.kakaopay.throwing.repository.ThrowRepository;
import com.kakaopay.throwing.type.HeaderNames;
import com.kakaopay.throwing.vo.ReceiveDTO;
import com.kakaopay.throwing.vo.SearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final ThrowRepository throwRepository;
    private final DistributionRepository distributionRepository;

    public SearchDTO getThrowingInfo(SearchDTO searchDTO, HttpServletRequest request) throws Exception {
        searchDTO.setUser(Integer.parseInt(request.getHeader(HeaderNames.USER.getName())));
        return searchThrowingInfo(searchDTO);
    }

    private SearchDTO searchThrowingInfo(SearchDTO searchDTO) throws Exception {
        if (checkInvalidToken(searchDTO.getToken())) {
            throw new Exception("유효한 토큰이 아닙니다.");
        }

        if (!checkSelf(searchDTO.getToken(), searchDTO.getUser())) {
            throw new Exception("본인 토큰이 아닙니다.");
        }

        if (checkTime(searchDTO.getToken(), searchDTO.getUser())) {
            throw new Exception("뿌린지 7일 지난 토큰입니다.");
        }

        return setThrowingInfo(searchDTO.getToken());
    }

    private boolean checkInvalidToken(String token) {
        return distributionRepository.findFirstByToken(token) == null;
    }

    private boolean checkSelf(String token, int user) {
        return distributionRepository.findByTokenAndCreateBy(token, user);
    }

    private boolean checkTime(String token, int user) {
        return false;
    }

    private SearchDTO setThrowingInfo(String token) {
        List<Distribution> distributionList = distributionRepository.findByToken(token);

        return SearchDTO.builder()
                .createAt(distributionList.get(0).getCreatedAt())
                .throwingMoney(throwRepository.findByToken(token).getMoney())
                .receivedMoney(
                        distributionList.stream()
                                .filter(it -> it.getYN().equals("Y"))
                                .map(Distribution::getMoney)
                                .reduce(0L, Long::sum))
                .receiveDTOList(distributionList.stream()
                        .map(it -> ReceiveDTO.builder()
                                .money(it.getMoney())
                                .user(it.getUser())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}