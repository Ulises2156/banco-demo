package banco.controller;

import banco.dto.AccountDTO;
import banco.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController (AccountService service) {this.service = service; }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO dto) {
        AccountDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/accounts/" + created.getId())).body(created);

    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @Valid @RequestBody AccountDTO dto) {
        return ResponseEntity.ok(service.update(id,dto));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount){
        return ResponseEntity.ok(service.depoist(id, amount));
    }
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount){

        return ResponseEntity.ok(service.withdraw(id, amount));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam BigDecimal amount) {

        service.transfer(fromId, toId, amount);
        return ResponseEntity.ok("Transferencia realizada correctamente");
    }

}