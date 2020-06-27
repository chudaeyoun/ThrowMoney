package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    Distribution findFirstByTokenAndMoney(String token, long money);
    Distribution findFirstByToken(String token);

    boolean existsByTokenAndCreateUser(String token, int user);

    List<Distribution> findByToken(String token);
}
