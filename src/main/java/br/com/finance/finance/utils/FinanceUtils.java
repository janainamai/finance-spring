package br.com.finance.finance.utils;

import br.com.finance.authentication.infra.exception.BadRequestException;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class FinanceUtils {

    public static UUID stringToUuidOrThrowException(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new BadRequestException("Invalid id");
        }
    }

    public static UUID stringToUuidOrNull(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            return null;
        }
    }

}
