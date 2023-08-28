package br.com.finance.finance.services.interfaces;

import br.com.finance.finance.services.dto.FilterTransactionDto;
import br.com.finance.finance.services.dto.TransactionDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> getAll();

    TransactionDto getById(String id);

    void create(TransactionDto dto);

    Page<TransactionDto> getAllByFilters(FilterTransactionDto dto);

    void deleteById(String id);

}
