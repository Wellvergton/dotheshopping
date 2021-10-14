package io.github.wellvergton.dotheshopping.service;

import io.github.wellvergton.dotheshopping.data.UserDetailData;
import io.github.wellvergton.dotheshopping.model.User;
import io.github.wellvergton.dotheshopping.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User " + email + " not found");
    }

    return new UserDetailData(user.orElse(new User()));
  }
}
