package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    Distribution findFirstByTokenAndMoney(String token, long money);
    Distribution findFirstByToken(String token);

    boolean existsByTokenAndCreateUser(String token, int user);
    boolean existsByTokenAndRoomAndUser(String token, String room, int user);
    boolean existsByTokenAndRoomAndCreateUser(String token, String room, int createUser);

    Distribution findFirstByTokenAndRoomAndUserAndUse(String token, String room, int user, String use);

//    Distribution findFirstByCreatedAtLessThan(String token, String room, int timeGap);

    List<Distribution> findByToken(String token);
}
