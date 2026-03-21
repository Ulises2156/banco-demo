package banco.controller;

import banco.dto.TransactionDTO;
import banco.entity.Transaction;
import banco.service.TransactionService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account/{accountId}/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController (TransactionService service){
            this.service = service;

    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> history(@PathVariable Long accountId){
        return ResponseEntity.ok(service.findByAccount(accountId));
    }

}

