package com.hanghae.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.market.dto.SignupReqeustDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Embedded
    private Adderss address;

    @Column
    private String myself;

    @Column(columnDefinition = "TEXT")
    private String profile_img;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Follow> requestUsers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "followUser")
    private List<Follow> responseUsers = new ArrayList<>();


    @Column(nullable = true)
    private String kakaoId;

    //일반회원 reqequstDto
    @Builder
    public User(String username , String password,String email,String myself,String city,String street) {
        this.username = username;

        this.password = password;

        this.email = email;

        this.myself = myself;

        this.address = Adderss.builder()
                .city(city)
                .street(street)
                .build();

        this.role = UserRole.ROLE_USER;
    }

    // Kakao 회원가입 Dto
    public User(String username,String password,String email,String kakaoId) {
        this.username = username;

        this.password = password;

        this.email = email;

        this.myself = "test";

        this.address = new Adderss("test","test");
        this.role = UserRole.ROLE_USER;
        this.kakaoId = kakaoId;
    }
}
