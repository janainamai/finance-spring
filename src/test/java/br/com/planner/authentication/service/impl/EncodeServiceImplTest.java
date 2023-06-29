package br.com.planner.authentication.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EncodeServiceImplTest {

    @InjectMocks
    private EncodeServiceImpl service;

    @Test
    void testEncode() {
        String password = "password123";

        String encodedPassword = service.encode(password);
        assertThat(encodedPassword).isNotNull();
    }

}