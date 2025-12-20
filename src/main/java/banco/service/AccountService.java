package banco.service;

import banco.dto.AccountDTO;
import banco.entity.Account;
import banco.exception.ResourceNotFoundException;
import banco.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo){
        this.repo = repo;
    }

    public List<AccountDTO> findAll(){
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());

    }
    public AccountDTO findById(Long id){
            Account acc = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found " + id));
            return toDTO(acc);
    }
    @Transactional
    public AccountDTO create (AccountDTO dto){
        //intentar generar el número de cueta
        String acctNum = dto.getAccountNumber();
        if (acctNum == null || acctNum.isBlank()) {
            acctNum = "ACCT-" + System.currentTimeMillis();
        }
        if (repo.existsByAccountNumber(acctNum)){
            throw new IllegalArgumentException("Account number already exists");
        }
        Account account = new Account(acctNum, dto.getOwnerName(), dto.getCurrency(), dto.getBalance() == null ? BigDecimal.ZERO : dto.getBalance());
        Account saved = repo.save(account);
        return toDTO(saved);
    }

    @Transactional
    public AccountDTO update (Long id, AccountDTO dto){
        Account acc = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account not found: " +id));
        acc.setOwnerName(dto.getOwnerName());
        acc.setCurrency(dto.getCurrency());
        if(dto.getBalance() != null) acc.setBalance(dto.getBalance());
        Account saved = repo.save(acc);
        return toDTO(saved);
    }
    @Transactional
    public void delete(Long id){
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Account not found: " +id);
        repo.deleteById(id);
    }
    private AccountDTO toDTO (Account acc){
        AccountDTO dto = new AccountDTO();
        dto.setId(acc.getId());
        dto.setAccountNumber(acc.getAccountNumber());
        dto.setOwnerName(acc.getOwnerName());
        dto.setCurrency(acc.getCurrency());
        dto.setBalance(acc.getBalance());
        return dto;
    }

}