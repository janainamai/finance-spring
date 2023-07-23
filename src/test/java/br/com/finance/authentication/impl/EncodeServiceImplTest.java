package br.com.finance.authentication.impl;

import br.com.finance.authentication.services.impl.EncodeServiceImpl;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should encrypt the received password")
    void testEncode() {
        String password = "password123";

        String encodedPassword = service.encode(password);
        assertThat(encodedPassword).isNotNull();
    }

}