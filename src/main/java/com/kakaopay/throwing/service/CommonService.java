package com.kakaopay.throwing.service;

import com.kakaopay.throwing.domain.User;
import com.kakaopay.throwing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final UserRepository userRepository;

    public void minusUserMoney(int user, long money) {
        User userInfo = userRepository.findByUser(user);
        userInfo.setMoney(userInfo.getMoney() - money);

        userRepository.save(userInfo);
    }

    public void plusUserMoney(int user, long money) {
        User userInfo = userRepository.findByUser(user);
        userInfo.setMoney(userInfo.getMoney() + money);

        userRepository.save(userInfo);
    }
}