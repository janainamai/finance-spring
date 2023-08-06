package br.com.finance.finance.services.interfaces;

import br.com.finance.finance.services.dto.TransactionDto;

public interface TransactionService {

    TransactionDto getById(String id);

    void create(TransactionDto dto);

}
