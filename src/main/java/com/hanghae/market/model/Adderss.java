package com.hanghae.market.model;

import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@RequiredArgsConstructor
public class Adderss {
    private String city;
    private String street;

    public Adderss(String city, String street) {
        this.city = city;
        this.street = street;
    }
}
