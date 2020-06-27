package com.kakaopay.throwing.repository;

import com.kakaopay.throwing.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUser(int user);
}
