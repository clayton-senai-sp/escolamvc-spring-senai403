package br.senai.sp.escolamvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index(){
        return "login/login";
    }

    @GetMapping("/error")
    public String loginError(){
        return "login/login-error";
    }

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        //perform audit action
        return "/";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        //perform audit action
        return "login";
    }

    @PostMapping("/error")
    public String error() {
        //perform audit action
        return "/login/login-error";
    }
}
