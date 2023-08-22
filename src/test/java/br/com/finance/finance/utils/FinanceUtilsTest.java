package br.com.finance.finance.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FinanceUtilsTest {

    @Test
    @DisplayName("Should convert the string into a UUID")
    void testStringToUUID() {
        String id = "e1947397-2b3a-4bc8-aa84-dee2dd0b14cb";

        UUID uuid = FinanceUtils.stringToUuidOrThrowException(id);

        assertThat(uuid).hasToString(id);
    }

}