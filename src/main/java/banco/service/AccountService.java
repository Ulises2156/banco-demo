package banco.service;

import banco.dto.AccountDTO;
import banco.entity.Account;
import banco.exception.ResourceNotFoundException;
import banco.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import banco.repository.TransactionRepository;
import banco.entity.Transaction;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class AccountService {

    private final AccountRepository repo;
    private final TransactionRepository transactionRepo;

    public AccountService(AccountRepository repo, TransactionRepository transactionRepo)
    {
        this.repo = repo;
        this.transactionRepo = transactionRepo;
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
       if (dto.getOwnerName() != null){
           acc.setOwnerName(dto.getOwnerName());
       }
       if (dto.getCurrency() != null){
           acc.setCurrency(dto.getCurrency());
       }
       if (dto.getBalance() != null){
           acc.setBalance(dto.getBalance());
       }
        return toDTO(repo.save(acc));
    }
    @Transactional
    public void delete(Long id){
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Account not found: " +id);
        repo.deleteById(id);

    }
    @Transactional
    public AccountDTO depoist(Long id, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
            Account acc = repo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));

            acc.setBalance(acc.getBalance().add(amount));

            Transaction tx = new Transaction(acc, amount, "DEPOIST");
            transactionRepo.save(tx);

            return toDTO(repo.save(acc));
        }
       @Transactional
       public AccountDTO withdraw(Long id, BigDecimal amount){

        if(amount == null || amount.compareTo(BigDecimal.ZERO   ) <= 0 ){
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        Account acc = repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found: "));

        if(acc.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Fondos insuficientes");

        }
        acc.setBalance(acc.getBalance().subtract(amount));

        Transaction tx = new Transaction(acc, amount, "WITHDRAW");
        transactionRepo.save(tx);
        return toDTO(repo.save(acc));
       }
    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount){

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (fromId.equals(toId)){
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }
        Account from = repo.findById(fromId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta origen no encontrada: " + fromId));
        Account to = repo.findById(toId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta destino no encontrada: " + toId));
        if (from.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Fondos insuficientes");
        }
        from.setBalance(from.getBalance().subtract(amount));

        to.setBalance(to.getBalance().add(amount));

        //Transaciones
        Transaction txOut = new Transaction(from, amount, "TRANSFER_OUT");
        Transaction txIn = new Transaction(to,amount, "TRANSFER_IN");

        transactionRepo.save(txOut);
        transactionRepo.save(txIn);

        repo.save(from);
        repo.save(to);
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