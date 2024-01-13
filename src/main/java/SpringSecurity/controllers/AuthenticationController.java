package SpringSecurity.controllers;

import SpringSecurity.DTOS.AuthenticationDTO;
import SpringSecurity.DTOS.RegisterDTO;
import SpringSecurity.entities.User;
import SpringSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Validated AuthenticationDTO data)  {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if (userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), data.password(), data.role());

        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
