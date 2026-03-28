package banco.service;

import banco.entity.User;
import banco.repository.UserRepository;
import banco.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo,
                       BCryptPasswordEncoder encoder,
                       JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String register(String username, String password) {

        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Usuario ya existe");
        }

        String encodedPassword = encoder.encode(password);

        String role = "ROLE_USER";
        if (username.equals("admin")){
           role = "ROLE_ADMIN";
        }
        User user = new User(username, encodedPassword, role);

        userRepo.save(user);

        return "Usuario creado";
    }

    public String login(String username, String password) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password incorrecta");
        }
        return jwtService.generateToken(username);
    }
}
