package banco.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AccountDTO {

    private Long id;
    private String accountNumber;

    @NotBlank (message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Currency is required")
    @Size(
            min = 3,
            max = 3,
            message = "Currency must be exactly 3 characters"
    )
    private String currency;
    @NotNull(message = "Balance is required")
     @DecimalMin(
             value = "0.00",
             inclusive = true,
             message = "Balance must be zero or positive"
     )

    @NotNull
    private BigDecimal balance;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
