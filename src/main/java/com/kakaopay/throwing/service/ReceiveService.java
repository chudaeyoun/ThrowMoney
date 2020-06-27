package com.kakaopay.throwing.service;

import com.kakaopay.throwing.domain.Distribution;
import com.kakaopay.throwing.repository.DistributionRepository;
import com.kakaopay.throwing.type.HeaderNames;
import com.kakaopay.throwing.vo.ReceiveDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveService {
    private final CommonService commonService;

    private final DistributionRepository distributionRepository;

    @Transactional
    public ReceiveDTO getMoney(ReceiveDTO receiveDTO, HttpServletRequest request) throws Exception {
        setReceiveDTO(receiveDTO, request);

        updateDistribute(receiveDTO);
        commonService.addUserMoney(receiveDTO.getUser(), receiveDTO.getMoney());

        return receiveDTO;
    }

    private void setReceiveDTO(ReceiveDTO receiveDTO, HttpServletRequest request) {
        receiveDTO.setMoney(receiveDTO.getMoney());
        receiveDTO.setUser(Integer.parseInt(request.getHeader(HeaderNames.USER.getName())));
        receiveDTO.setRoom(request.getHeader(HeaderNames.ROOM.getName()));
    }


    private void updateDistribute(ReceiveDTO receiveDTO) throws Exception {
        checkValidation(getDistribute(receiveDTO.getToken()), receiveDTO);

        Distribution distribution = getOneDistribute(receiveDTO.getToken());
        distribution.setUser(receiveDTO.getUser());
        distribution.setUse("Y");

        receiveDTO.setMoney(distribution.getMoney());

        distributionRepository.save(distribution);
    }

    private Distribution getOneDistribute(String token) {
        return distributionRepository.findFirstByTokenAndUse(token, "N");
    }

    private List<Distribution> getDistribute(String token) {
        return distributionRepository.findByToken(token);
    }

    private void checkValidation(List<Distribution> distributionList, ReceiveDTO receiveDTO) throws Exception {
        if (distributionList == null || distributionList.size() == 0) {
            throw new Exception("유효한 토큰이 아닙니다.");
        }

        if (!distributionList.get(0).getRoom().equals(receiveDTO.getRoom())) {
            throw new Exception("다른 방의 뿌리기입니다.");
        }

        if (distributionList.get(0).getCreateUser() == receiveDTO.getUser()) {
            throw new Exception("본인의 뿌리기는 받을 수 없습니다.");
        }

        if (distributionList.stream().anyMatch(it -> it.getUser() == receiveDTO.getUser())) {
            throw new Exception("두 번 받을 수 없습니다.");
        }

        if (distributionList.stream().noneMatch(it -> it.getUse().equals("N"))) {
            throw new Exception("다 분배되어 받을 수 없습니다.");
        }

        if (distributionList.get(0).getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new Exception("뿌린지 10분이 지나 받을 수 없습니다.");
        }
    }
}