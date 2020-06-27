package com.kakaopay.throwing.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "distribution")
@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Distribution extends BaseEntity {
    @Column(name = "token", length = 3)
    private String token;

    @Column(name = "money")
    private long money;

    @Column(name = "user")
    private int user;

    @Column(name = "room")
    private String room;

    @Column(name = "y_n")
    private String yN;

    @Column(name = "create_by")
    private int createBy;
}
