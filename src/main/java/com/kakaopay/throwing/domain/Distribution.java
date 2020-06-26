package com.kakaopay.throwing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "distribution")
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Distribution extends BaseEntity {
    @Column(name = "token", length = 3)
    private String token;

    @Column(name = "money")
    private long money;

    @Column(name = "y_n")
    private String yN;
}
