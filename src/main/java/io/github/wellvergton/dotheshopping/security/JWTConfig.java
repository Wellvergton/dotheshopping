package io.github.wellvergton.dotheshopping.security;

import io.github.wellvergton.dotheshopping.service.UserDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailImpl userService;
  private final PasswordEncoder encoder;
  @Value("${security.enable-csrf}")
  private boolean csrfEnabled;
  @Value("${token.secret}")
  public String TOKEN_SECRET;
  @Value("${token.expiration-time}")
  public int TOKEN_EXPIRATION_TIME;

  public JWTConfig(UserDetailImpl userService, PasswordEncoder encoder) {
    this.userService = userService;
    this.encoder = encoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(encoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (csrfEnabled) {
      http.csrf().disable();
    }

    JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(
      authenticationManager(), TOKEN_SECRET, TOKEN_EXPIRATION_TIME
    );
    JWTValidateFilter validateFilter = new JWTValidateFilter(authenticationManager(), TOKEN_SECRET);

    http.authorizeRequests()
      .antMatchers(HttpMethod.POST, "/signin", "/signup").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(authenticationFilter)
      .addFilter(validateFilter)
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
