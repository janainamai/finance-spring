package br.com.finance.finance.domain.converters;

import br.com.finance.finance.domain.dto.BankAccountOutput;
import br.com.finance.finance.domain.entities.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BankAccountConverterTest {

    @Test
    void testConvertAllWhenNotEmpty() {
        BankAccount blueAccount = new BankAccount();
        blueAccount.setId(UUID.randomUUID());
        blueAccount.setName("Bank account blue");
        blueAccount.setDescription("Description of blue bank account");
        blueAccount.setTotalBalance(BigDecimal.valueOf(250.65));
        blueAccount.setActive(true);

        BankAccount greenAccount = new BankAccount();
        greenAccount.setId(UUID.randomUUID());
        greenAccount.setName("Bank account green");
        greenAccount.setDescription("Description of green bank account");
        greenAccount.setTotalBalance(BigDecimal.valueOf(-100.65));
        greenAccount.setActive(false);

        List<BankAccountOutput> output = BankAccounOutputConverter.convertAll(List.of(blueAccount, greenAccount));
        BankAccountOutput blueOutput = new BankAccountOutput();
        blueOutput.setName(blueAccount.getName());
        blueOutput.setDescription(blueAccount.getDescription());
        blueOutput.setActive(blueAccount.isActive());
        blueOutput.setTotalBalance(blueAccount.getTotalBalance());

        BankAccountOutput greenOutput = new BankAccountOutput();
        greenOutput.setName(greenAccount.getName());
        greenOutput.setDescription(greenAccount.getDescription());
        greenOutput.setActive(greenAccount.isActive());
        greenOutput.setTotalBalance(greenAccount.getTotalBalance());

        assertThat(output).containsAll(List.of(blueOutput, greenOutput));
    }

    @Test
    void testConvertAllWhenEmpty() {
        List<BankAccountOutput> output = BankAccounOutputConverter.convertAll(Collections.emptyList());

        assertThat(output).isEmpty();
    }

}