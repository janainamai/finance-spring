package br.com.finance.finance.domain.converters;

import br.com.finance.finance.domain.dto.BankAccountOutput;
import br.com.finance.finance.domain.entities.BankAccount;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BankAccounOutputConverter {

    public static List<BankAccountOutput> convertAll(List<BankAccount> accounts) {

        return accounts.stream().map(account -> {

            BankAccountOutput output = new BankAccountOutput();
            output.setName(account.getName());
            output.setDescription(account.getDescription());
            output.setActive(account.isActive());
            output.setTotalBalance(account.getTotalBalance());

            return output;

        }).collect(Collectors.toList());

    }
}
