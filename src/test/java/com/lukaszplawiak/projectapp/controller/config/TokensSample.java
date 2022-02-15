package com.lukaszplawiak.projectapp.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TokensSample {

    @Bean
    public String validTokenWithRole_User() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5rb3dhbHNraUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDI1MDJ9.WgaOZZXBY2lwcYuYNXD9RPIyTN5gOZ1FIIj4BsVvF58";
    }

    @Bean
    public String validTokenWithRole_Manager() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNrZXltb3VzZUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDI1MzZ9.fJVJVtTu9MAa2UM3xbkvW_CVmGOUL6AmH98nHDe6b04";
    }

    @Bean
    public String validTokenWithRole_Admin() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJhbHRyaXZpQGdtYWlsLmNvbSIsImV4cCI6MTY0NDk0MjU1OH0.rSK1cqegityI_1eZ1qVwhXMWRdvKypFG8JngfDUepss";
    }

    @Bean
    public String validTokenWithRole_Super_Admin() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25hbGl6YUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDI1ODJ9.qqne2wrrbbh8G04Uky4GyvWOHMuGPyF-sk5LO2tX0Jc";
    }

    @Bean
    public String unValidTokenWithRole_User() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5rb3dhbHNraUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDE1MDl9.Zr8q8Ik7leiO3mRuQiIzeLGwNz8iQ2pBKlEBi3NQ56Q";
    }

    @Bean
    public String unValidTokenWithRole_Manager() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNrZXltb3VzZUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDE0ODh9.qoZo5wUBzSdWU0KYPFVqT7V-Q0K4b-lvNg3SXFwgHkQ";
    }

    @Bean
    public String unValidTokenWithRole_Admin() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJhbHRyaXZpQGdtYWlsLmNvbSIsImV4cCI6MTY0NDk0MTQ2Nn0.1OKmgNl2KyIxtcv2AbzwTeEAYtdoRFv_6-38dqYVj9Q";
    }

    @Bean
    public String unValidTokenWithRole_Super_Admin() {
        return "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25hbGl6YUBnbWFpbC5jb20iLCJleHAiOjE2NDQ5NDE0Mzl9.rolQDWltoR0zg-vdN-tVrtY9HRt7dE2vze58A0G-fwo";
    }
}
