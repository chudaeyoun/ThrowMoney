package com.kakaopay.throwing.service;

import com.kakaopay.throwing.common.HeaderNames;
import com.kakaopay.throwing.common.ThrowUtils;
import com.kakaopay.throwing.domain.Distribution;
import com.kakaopay.throwing.domain.User;
import com.kakaopay.throwing.repository.DistributionRepository;
import com.kakaopay.throwing.repository.ThrowRepository;
import com.kakaopay.throwing.repository.UserRepository;
import com.kakaopay.throwing.vo.ThrowDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThrowService {
    private final UserRepository userRepository;
    private final ThrowRepository throwRepository;
    private final DistributionRepository distributionRepository;

    private static final int tokenLength = 3;

    @Transactional
    public ThrowDTO getThrow(ThrowDTO throwDTO, HttpServletRequest request) {
        setThrowDTO(throwDTO, request);

        saveThrow(throwDTO);
        saveDistribute(throwDTO);
        updateUserMoney(throwDTO);

        return throwDTO;
    }

    private void setThrowDTO(ThrowDTO throwDTO, HttpServletRequest request) {
        throwDTO.setToken(getUniqueToken());
        throwDTO.setUser(Integer.parseInt(request.getHeader(HeaderNames.USER.getName())));
        throwDTO.setRoom(request.getHeader(HeaderNames.ROOM.getName()));
    }

    private String getUniqueToken() {
        String token = "";

        do {
            token = ThrowUtils.makeToken(tokenLength);
        } while (isExistToken(token));

        return token;
    }

    private void saveThrow(ThrowDTO throwDTO) {
        throwRepository.save(ThrowDTO.convertToEntity(throwDTO));
    }

    private void saveDistribute(ThrowDTO throwDTO) {
        ThrowUtils.distributeMoney(throwDTO.getMoney(), throwDTO.getCount())
                .stream()
                .map(it -> makeDistribution(throwDTO.getToken(), it))
                .forEach(distributionRepository::save);
    }

    private void updateUserMoney(ThrowDTO throwDTO) {
        User user = userRepository.findByUser(throwDTO.getUser());
        user.setMoney(user.getMoney() - throwDTO.getMoney());

        userRepository.save(user);
    }

    private Distribution makeDistribution(String token, long money) {
        return Distribution.builder()
                .token(token)
                .money(money)
                .yN("N")
                .build();
    }

    private boolean isExistToken(String token) {
        return throwRepository.existsByToken(token);
    }
}