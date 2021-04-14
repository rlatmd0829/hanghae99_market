package com.hanghae.market.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.config.jwt.JwtProperties;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//사용자가 jwt토큰을 보내면 토큰이 유효한지 확인
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요한 주소요청이 있을때 해당 필터 작동.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 요청 시도");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        //header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");

        // 토큰 만료됬을때 error잡기위해 try catch
        try{
            String username =
                    JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
            // JWT토큰 서명이 정상적으로 됨 (토큰 인증 ok)
            if(username != null){
                User user = userRepository.findByUsername(username);
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                //임의로 token발급 -> username이 null이 아닌 경우라는 건 존재하는 회원이기떄문에.
                System.out.println(principalDetails.getAuthorities());
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

                //강제로 시큐리티 세션에 접근하여 authentication객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request,response);
        }catch (Exception e){

            JSONObject json = new JSONObject();
            json.put("message", "tokenExpired");
            PrintWriter out = response.getWriter();
            out.print(json);

        }

    }
}
