package com.hanghae.market.model;

import com.hanghae.market.dto.SignupReqeustDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

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


    public User(SignupReqeustDto reqeustDto) {

        this.username = reqeustDto.getUsername();
        this.password = reqeustDto.getPassword();
        this.email = reqeustDto.getEmail();
        this.myself = reqeustDto.getMyself();
        this.address = new Adderss(reqeustDto.getCity(), reqeustDto.getStreet());
        this.role = UserRole.ROLE_USER;
    }
}
