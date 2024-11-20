package com.example.BancoSimples.Model.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtil {

    public static String enconder(String senha){
        BCryptPasswordEncoder senhaEnconder = new BCryptPasswordEncoder();
        return senhaEnconder.encode(senha);
    }
}
