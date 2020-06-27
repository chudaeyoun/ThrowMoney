package com.kakaopay.throwing.service

import com.kakaopay.throwing.domain.Distribution
import com.kakaopay.throwing.repository.DistributionRepository
import com.kakaopay.throwing.vo.ReceiveDTO
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class ReceiveServiceTest extends Specification {
    @Autowired
    ReceiveService receiveService
    @Autowired
    DistributionRepository distributionRepository

    def setup() {
        distributionRepository.deleteAll()
    }

    def "checkValidation test"() {
        given:
        ReceiveDTO receiveDTO = makeReceiveDTO()

        expect:
        String errorMsg = ""
        try {
            receiveService.checkValidation(CASE, receiveDTO)
        }
        catch (Exception e) {
            errorMsg = e.message
        }

        errorMsg == ERROR

        where:
        CASE                         | ERROR
        null                         | "유효한 토큰이 아닙니다."
        makeOtherRoomDistribution()  | "다른 방의 뿌리기입니다."
        makeSelfDistribution()       | "본인의 뿌리기는 받을 수 없습니다."
        makeTwiceDistribution()      | "두 번 받을 수 없습니다."
        makeZeroDistribution()       | "다 분배되어 받을 수 없습니다."
        makeAfter10MinDistribution() | "뿌린지 10분이 지나 받을 수 없습니다."

    }

    def makeReceiveDTO() {
        return ReceiveDTO.builder()
                .user(1)
                .room("A")
                .build()
    }

    def makeOtherRoomDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        distributionList.add(Distribution.builder()
                .token("AAA")
                .money(3000)
                .user(0)
                .room("B")
                .use("N")
                .build())

        return distributionList
    }

    def makeSelfDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        Distribution distribution = makeDistribution()
        distribution.setCreateUser(1)

        distributionList.add(distribution)

        return distributionList
    }

    def makeTwiceDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        Distribution distribution = makeDistribution()
        distribution.setUser(1)

        distributionList.add(distribution)

        return distributionList
    }

    def makeZeroDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        Distribution distribution = makeDistribution()
        distribution.setUser(2)
        distribution.setUse("Y")

        distributionList.add(distribution)

        return distributionList
    }

    def makeAfter10MinDistribution() {
        List<Distribution> distributionList = Lists.newArrayList()

        Distribution distribution = makeDistribution()
        distribution.setUser(2)
        distribution.setCreatedAt(LocalDateTime.now().minusMinutes(10))

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
                .createUser(2)
                .build()
    }
}