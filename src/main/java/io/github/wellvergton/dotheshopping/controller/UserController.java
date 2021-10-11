package io.github.wellvergton.dotheshopping.controller;

import io.github.wellvergton.dotheshopping.model.User;
import io.github.wellvergton.dotheshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder encoder;

  @PostMapping(path = "/signup")
  public ResponseEntity<User> signUp(@RequestBody User user) {
    user.setPassword(encoder.encode(user.getPassword()));

    return ResponseEntity.ok(userRepository.save(user));
  }

  @GetMapping(path = "/signin")
  public ResponseEntity<Boolean> signIn(@RequestBody User user) {
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    User userOnDb = userOptional.get();
    boolean isValid = encoder.matches(user.getPassword(), userOnDb.getPassword());

    HttpStatus status = isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

    return ResponseEntity.status(status).body(isValid);
  }
}
