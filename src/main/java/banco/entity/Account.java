package banco.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name= "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Column(name ="owner_name", nullable = false, length = 150)
    private String ownerName;

    @Column(name ="currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "balance", nullable = false, precision = 19,  scale = 4)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAT = Instant.now();

    @Version
    private Long version;

    @ManyToOne(optional = false)
    @JoinColumn(name ="user_id")
    private User user;

    public Account(){}

    public Account(String accountNumber, String ownerName, String currency, BigDecimal balance){
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.currency = currency;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public Long getId() {return id;}
    public void setId (Long id) {this.id = id;}

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {this.ownerName = ownerName;}


    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {this.currency = currency;}

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Instant createdAT) {
        this.createdAT = createdAT;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

