package banco.controller;

import banco.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }
    @PostMapping("/register")
    public  ResponseEntity<?> register(@RequestBody Map<String, String> body){

        String username = body.get("username");
        String password = body.get("password");

        return  ResponseEntity.ok(service.register(username, password));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){

        String username = body.get("username");
        String password = body.get("password");

       String token = service.login(username, password);

        return ResponseEntity.ok(Map.of("token", token));
        }
    }

