package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.Throw;
import org.springframework.data.repository.CrudRepository;

public interface ThrowRepository extends CrudRepository<Throw, Long> {
}
