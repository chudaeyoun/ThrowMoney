package com.kakaopay.throwing.service

import com.google.common.collect.Sets
import com.kakaopay.throwing.common.ThrowUtils
import com.kakaopay.throwing.repository.ThrowRepository
import com.kakaopay.throwing.vo.ThrowDTO
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ThrowServiceTest extends Specification {
    def "getUniqueToken test"() {
        given:
        ThrowRepository throwRepository = Stub()
        ThrowDTO throwDTO = Stub()
        Set<String> tokenSet = Sets.newHashSet()

        when:
        for (int i = 0; i < LOOP; i++) {
            while (true) {
                String token = ThrowUtils.makeToken(3)
                if (!throwRepository.existsByToken(token)) {
                    throwDTO.setToken(token)
                    throwRepository.save(ThrowDTO.convertToEntity(throwDTO))
                    tokenSet.add(token)
                    break
                }
            }
        }

        then:
        tokenSet.size() == SIZE

        where:
        LOOP | SIZE
        10   | 10
        100  | 100
    }
}