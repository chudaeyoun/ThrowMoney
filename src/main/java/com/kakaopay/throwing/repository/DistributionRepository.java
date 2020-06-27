package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    Distribution findFirstByTokenAndMoney(String token, long money);
    Distribution findFirstByToken(String token);

    boolean findByTokenAndCreateBy(String token, int user);
    boolean findByTokenAndRoomAndUser(String token, String room, int user);
    boolean findByTokenAndRoomAndCreateBy(String token, String room, int user);

    Distribution findFirstByTokenAndRoomAndUserAndYN(String token, String room, int user, String yN);

    Distribution findFirstByCreatedAtLessThan(String token, String room, int timeGap);

    List<Distribution> findByToken(String token);
}
