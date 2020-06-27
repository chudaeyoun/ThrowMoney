package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    Distribution findFirstByTokenAndMoney(String token, long money);
    Distribution findByTokenOne(String token);

    boolean findByTokenByRoomByAndUser(String token, String room, int user);
    boolean findByTokenByRoomByAndCreateBy(String token, String room, int user);

    Distribution findFirstByTokenAndRoomAndUserAndyNOne(String token, String room, int user, String yn);

    Distribution findByCreatedAtLessThanOne(String token, String room, int timeGap);
}
