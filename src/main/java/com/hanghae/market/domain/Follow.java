package com.hanghae.market.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue
    private Long id;
}
