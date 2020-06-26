package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
