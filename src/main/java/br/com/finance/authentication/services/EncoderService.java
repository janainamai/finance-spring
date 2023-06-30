package br.com.finance.authentication.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface EncoderService {

    BCryptPasswordEncoder getEnconder();

    String encode(String password);

}
