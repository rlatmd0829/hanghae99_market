package com.hanghae.market.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginErrorHandlerController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Map handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Map result = new HashMap();
        result.put("message","loginfail");
        return result;
    }

}
