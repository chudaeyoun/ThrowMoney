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
import java.time.LocalDate;
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
        searchDTO.setRoom(request.getHeader(HeaderNames.ROOM.getName()));
        return searchThrowingInfo(searchDTO);
    }

    private SearchDTO searchThrowingInfo(SearchDTO searchDTO) throws Exception {
        List<Distribution> distributionList = getDistribute(searchDTO.getToken());
        checkValidation(distributionList, searchDTO);
        return setThrowingInfo(distributionList, searchDTO.getToken());
    }

    private List<Distribution> getDistribute(String token) {
        return distributionRepository.findByToken(token);
    }

    private void checkValidation(List<Distribution> distributionList, SearchDTO searchDTO) throws Exception {
        if (distributionList == null || distributionList.size() == 0) {
            throw new Exception("유효한 토큰이 아닙니다.");
        }

        if (distributionList.get(0).getCreateUser() == searchDTO.getUser()) {
            throw new Exception("본인의 토큰이 아닙니다.");
        }

        if (distributionList.get(0).getCreatedAt().plusDays(7).toLocalDate().isBefore(LocalDate.now())) {
            throw new Exception("뿌린지 7일 지나 조회 할 수 없습니다.");
        }
    }

    private SearchDTO setThrowingInfo(List<Distribution> distributionList, String token) {
        return SearchDTO.builder()
                .createAt(distributionList.get(0).getCreatedAt())
                .throwingMoney(throwRepository.findByToken(token).getMoney())
                .receivedMoney(getReceivedMoney(distributionList))
                .receiveDTOList(getReceiveDTOList(distributionList))
                .build();
    }

    private long getReceivedMoney(List<Distribution> distributionList) {
        return distributionList.stream()
                .filter(it -> it.getUse().equals("Y"))
                .map(Distribution::getMoney)
                .reduce(0L, Long::sum);
    }

    private List<ReceiveDTO> getReceiveDTOList(List<Distribution> distributionList) {
        return distributionList.stream()
                .map(it -> ReceiveDTO.builder()
                        .money(it.getMoney())
                        .user(it.getUser())
                        .build())
                .collect(Collectors.toList());
    }
}