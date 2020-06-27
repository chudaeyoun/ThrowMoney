package com.kakaopay.throwing.service;

import com.kakaopay.throwing.domain.Distribution;
import com.kakaopay.throwing.repository.DistributionRepository;
import com.kakaopay.throwing.repository.ThrowRepository;
import com.kakaopay.throwing.repository.UserRepository;
import com.kakaopay.throwing.type.HeaderNames;
import com.kakaopay.throwing.vo.ReceiveDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveService {
    private final CommonService commonService;

    private final UserRepository userRepository;
    private final ThrowRepository throwRepository;
    private final DistributionRepository distributionRepository;

    @Transactional
    public ReceiveDTO getMoney(ReceiveDTO receiveDTO, HttpServletRequest request) throws Exception {
        setReceiveDTO(receiveDTO, request);

        setDistribute(receiveDTO);
        updateDistribute(receiveDTO);
        commonService.plusUserMoney(receiveDTO.getUser(), receiveDTO.getMoney());

        return receiveDTO;
    }

    private void updateDistribute(ReceiveDTO receiveDTO) {
        Distribution distribution =
                distributionRepository.findFirstByTokenAndMoney(receiveDTO.getToken(), receiveDTO.getMoney());

        distribution.setUser(receiveDTO.getUser());
        distribution.setYN("Y");

        distributionRepository.save(distribution);
    }

    private void setReceiveDTO(ReceiveDTO receiveDTO, HttpServletRequest request) {
        receiveDTO.setMoney(receiveDTO.getMoney());
        receiveDTO.setUser(Integer.parseInt(request.getHeader(HeaderNames.USER.getName())));
        receiveDTO.setRoom(request.getHeader(HeaderNames.ROOM.getName()));
    }

    private void setDistribute(ReceiveDTO receiveDTO) throws Exception {
        receiveDTO.setMoney(
                getDistribute(receiveDTO.getToken(), receiveDTO.getRoom(), receiveDTO.getUser()).getMoney());

        if (checkReceivedOne(receiveDTO.getToken(), receiveDTO.getRoom(), receiveDTO.getUser())) {
            throw new Exception("이 뿌리기에서 한 번 받았습니다.");
        }

        if (checkReceivedSelf(receiveDTO.getToken(), receiveDTO.getRoom(), receiveDTO.getUser())) {
            throw new Exception("본인의 뿌리기는 받을 수 없습니다.");
        }

        if (checkRoom(receiveDTO.getToken(), receiveDTO.getRoom())) {
            throw new Exception("다른 방의 뿌리기입니다.");
        }

//        if (checkTime(receiveDTO.getToken(), receiveDTO.getRoom())) {
//            throw new Exception("10분이 넘어 받을 수 없습니다.");
//        }
    }

    private Distribution getDistribute(String token, String room, int user) {
        return distributionRepository.findFirstByTokenAndRoomAndUserAndyNOne(token, room, user, "N");
    }

    private boolean checkReceivedOne(String token, String room, int user) {
        return distributionRepository.findByTokenByRoomByAndUser(token, room, user);
    }

    private boolean checkReceivedSelf(String token, String room, int user) {
        Distribution distribution = distributionRepository.findByTokenOne(token);
        return distributionRepository.findByTokenByRoomByAndCreateBy(token, room, distribution.getCreateBy());
    }

    private boolean checkRoom(String token, String room) {
        return distributionRepository.findByTokenOne(token).getRoom().equals(room);
    }

    private boolean checkTime(String token, String room) {
        return distributionRepository.findByCreatedAtLessThanOne(token, room, 10) != null;
    }
}