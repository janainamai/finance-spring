package br.com.finance.finance.repositories;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query("SELECT t FROM transaction t " +
            "LEFT JOIN bank_account b on b.id = t.bankAccount.id " +
            "WHERE " +
                "t.eventDate BETWEEN :startDate AND :endDate " +
                "AND t.deleted = false " +
                "AND b.id = :bankAccountId " +
                "AND (:transactionType IS NULL or t.transactionType = :transactionType)")
    Page<TransactionEntity> findAllByFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("bankAccountId") UUID bankAccountId,
            @Param("transactionType") EnumTransactionType transactionType,
            Pageable pageable);

    void deleteByBankAccount(BankAccountEntity bankAccount);
}
