package com.kakaopay.throwing.service

import com.kakaopay.throwing.domain.Distribution
import com.kakaopay.throwing.repository.DistributionRepository
import com.kakaopay.throwing.vo.SearchDTO
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class SearchServiceTest extends Specification {
    @Autowired
    SearchService searchService
    @Autowired
    DistributionRepository distributionRepository

    def setup() {
        distributionRepository.deleteAll()
    }

    def "checkValidation test"() {
        given:
        SearchDTO searchDTO = makeSearchDTO()

        expect:
        String errorMsg = ""
        try {
            searchService.checkValidation(CASE, searchDTO)
        }
        catch (Exception e) {
            errorMsg = e.message
        }

        errorMsg == ERROR

        where:
        CASE                        | ERROR
        null                        | "유효한 토큰이 아닙니다."
        makeNotSelfDistribution()   | "본인의 토큰이 아닙니다."
        makeAfter7dayDistribution() | "뿌린지 7일 지나 조회 할 수 없습니다."
    }

    def makeSearchDTO() {
        return SearchDTO.builder()
                .user(1)
                .build()
    }

    def makeNotSelfDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        distributionList.add(Distribution.builder()
                .token("AAA")
                .money(3000)
                .user(0)
                .room("A")
                .use("N")
                .createUser(2)
                .build())

        return distributionList
    }

    def makeAfter7dayDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        Distribution distribution = makeDistribution()
        distribution.setCreatedAt(LocalDateTime.now().minusDays(8))

        distributionList.add(distribution)

        return distributionList
    }

    def makeDistribution() {
        return Distribution.builder()
                .token("AAA")
                .money(3000)
                .user(0)
                .room("A")
                .use("N")
                .createUser(1)
                .build()
    }
}