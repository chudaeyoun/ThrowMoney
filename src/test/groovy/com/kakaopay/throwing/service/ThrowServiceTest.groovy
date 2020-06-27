package com.kakaopay.throwing.service

import com.kakaopay.throwing.domain.Throw
import com.kakaopay.throwing.repository.ThrowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ThrowServiceTest extends Specification {
    @Autowired
    ThrowService throwService
    @Autowired
    ThrowRepository throwRepository

    def setup() {
        throwRepository.deleteAll()
    }

    def "getUniqueToken test"() {
        expect:
        for (int i = 0; i < LOOP; i++) {
            throwRepository.save(
                    Throw.builder()
                            .token(throwService.getUniqueToken())
                            .build())
        }

        throwRepository.count() == SIZE

        where:
        LOOP | SIZE
        10   | 10
        30   | 30
    }
}