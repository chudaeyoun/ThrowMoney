package com.kakaopay.throwing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(name = "user")
    private long user;

    @Column(name = "money")
    private long money;
}
