package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    Distribution findFirstByTokenAndUse(String token, String use);

    List<Distribution> findByToken(String token);
}
