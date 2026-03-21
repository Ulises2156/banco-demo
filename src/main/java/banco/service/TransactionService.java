package banco.service;

import banco.dto.TransactionDTO;
import banco.entity.Transaction;
import banco.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo){
        this.repo = repo;
    }
    public List<TransactionDTO> findByAccount(Long accountId){
        return repo.findByAccountIdOrderByCreatedAtDesc(accountId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    private TransactionDTO toDTO(Transaction tx){
        TransactionDTO dto = new TransactionDTO();
        dto.setId(tx.getId());
        dto.setAmount(tx.getAmount());
        dto.setType(tx.getType());
        dto.setCreatedAt( tx.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return dto;
    }
}