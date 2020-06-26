package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

public interface DistributionRepository extends CrudRepository<Distribution, Long> {
}
