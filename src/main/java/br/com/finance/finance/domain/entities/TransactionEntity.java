package br.com.finance.finance.domain.entities;

import br.com.finance.finance.domain.enums.EnumTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private LocalDate eventDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "event_time", nullable = false)
    private LocalTime eventTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccountEntity bankAccount;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_on", nullable = false)
    private LocalDateTime deletedOn;

}
