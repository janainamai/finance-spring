package br.com.finance.finance.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "bank_account")
@Table(name = "bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "total_balance", nullable = false)
    private BigDecimal totalBalance;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

}
