package com.hanghae.market.config;

import com.hanghae.market.config.jwt.JwtAuthenticationEntryPoint;
import com.hanghae.market.config.jwt.JwtAuthorizationFilter;
import com.hanghae.market.config.jwt.JwtAuthenticationFilter;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity//시큐리티 활성화
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    /* 비밀번호 암호화 */
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 한글 인코딩
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);


        http.csrf().disable();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //session을 사용하지않겠다 .

                //jwt와 cors 관련 filter
                .and()
                    .addFilter(corsFilter)
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                //권한 설정
                .authorizeRequests()
                    .antMatchers("/h2-console/**" ).permitAll()
                    .antMatchers("/user/**").permitAll()
                    //.antMatchers("/boards").access("hasRole('ROLE_USER') ")
                    .antMatchers("/boards/**").permitAll()
                    .antMatchers("/kakao/**").permitAll()
                    .antMatchers("/api/chat/newChat").permitAll()
                    .antMatchers("/chat/send").permitAll()
                    .antMatchers("/api/chat/**").permitAll()
                    .anyRequest().permitAll();

        //h2-console 허용을 위한 코드 *배포시 삭제 *
        http.headers().frameOptions().disable();
    }

}
