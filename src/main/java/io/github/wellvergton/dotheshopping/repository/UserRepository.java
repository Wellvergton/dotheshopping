package io.github.wellvergton.dotheshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wellvergton.dotheshopping.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  public Optional<User> findByEmail(String email);
}
