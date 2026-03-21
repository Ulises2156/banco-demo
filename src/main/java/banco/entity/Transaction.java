package banco.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")

public class Transaction{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(optional = false)
@JoinColumn(name = "account_id")
private Account account;

@Column(nullable = false, precision = 19, scale = 4)
private BigDecimal amount;

@Column(nullable = false, length = 20)
private String type;  //DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT

@Column(nullable = false, updatable = false)
private Instant createdAt = Instant.now();

public Transaction(Account account, BigDecimal amount, String type){
    this.account = account;
    this.amount = amount;
    this.type = type;

}
 // Los getters
    public Long getId(){
    return id;
    }
    public Account getAccount(){
    return account;
 }
    public BigDecimal getAmount(){
    return amount;
 }
 public String getType(){
    return type;
 }
 public  Instant getCreatedAt(){
    return createdAt;
 }
}