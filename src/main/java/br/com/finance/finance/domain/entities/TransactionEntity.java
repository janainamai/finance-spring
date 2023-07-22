package br.com.finance.finance.domain.entities;

import br.com.finance.authentication.domain.enums.EnumTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity(name = "transaction")
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private EnumTransactionType transactionType;

    @Temporal(TemporalType.DATE)
    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "event_time", nullable = false)
    private Date eventTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccountEntity bankAccount;

}
