package io.github.wellvergton.dotheshopping.data;

import io.github.wellvergton.dotheshopping.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDetailData implements UserDetails {
  private final User user;

  public UserDetailData(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return this.getUser().orElse(new User()).getPassword();
  }

  @Override
  public String getUsername() {
    return this.getUser().orElse(new User()).getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private Optional<User> getUser() {
    return Optional.ofNullable(user);
  }
}
