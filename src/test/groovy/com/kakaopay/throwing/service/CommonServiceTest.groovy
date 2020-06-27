package com.kakaopay.throwing.service

import com.kakaopay.throwing.domain.User
import com.kakaopay.throwing.repository.UserRepository
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CommonServiceTest extends Specification {
    @Autowired
    CommonService commonService
    @Autowired
    UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
        userRepository.saveAll(makeUserList())
    }

    def "addUserMoney test"() {
        when:
        commonService.addUserMoney(USER, MONEY)

        then:
        userRepository.findByUser(USER).getMoney() == RES

        where:
        USER | MONEY  | RES
        1    | -10000 | 90000
        2    | 10000  | 110000
    }

    def makeUserList() {
        List<User> userList = Lists.newArrayList()
        userList.add(User.builder().user(1).money(100000).build())
        userList.add(User.builder().user(2).money(100000).build())

        return userList
    }
}