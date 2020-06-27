package com.kakaopay.throwing.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "throw")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Throw extends BaseEntity {
    @Column(name = "token", length = 3)
    private String token;

    @Column(name = "user")
    private int user;

    @Column(name = "room")
    private String room;

    @Column(name = "money")
    private long money;

    @Column(name = "count")
    private int count;
}
