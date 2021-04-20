package com.hanghae.market.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;


@Getter
@RequiredArgsConstructor
@Embeddable
public class Adderss {
    private String city;
    private String street;

    @Builder
    public Adderss(String city, String street) {
        this.city = city;
        this.street = street;
    }
}
