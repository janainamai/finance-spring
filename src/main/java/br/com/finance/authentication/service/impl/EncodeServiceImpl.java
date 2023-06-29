package br.com.finance.authentication.service.impl;

import br.com.finance.authentication.service.EncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncodeServiceImpl implements EncoderService {

    @Override
    public BCryptPasswordEncoder getEnconder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return getEnconder().encode(password);
    }

}
