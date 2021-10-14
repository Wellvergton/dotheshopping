package io.github.wellvergton.dotheshopping.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wellvergton.dotheshopping.data.UserDetailData;
import io.github.wellvergton.dotheshopping.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final String TOKEN_SECRET;
  private final int TOKEN_EXPIRATION_TIME;

  public JWTAuthenticationFilter(
    AuthenticationManager authenticationManager, String token_secret, int token_expiration_time
  ) {
    this.authenticationManager = authenticationManager;
    TOKEN_SECRET = token_secret;
    TOKEN_EXPIRATION_TIME = token_expiration_time;
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request, HttpServletResponse response
  ) throws AuthenticationException {
    try {
      User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        user.getEmail(), user.getPassword(), new ArrayList<>()
      ));
    } catch (IOException e) {
      throw new RuntimeException("Authentication failure", e);
    }
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult
  ) throws IOException, ServletException {
    UserDetailData userDetailData = (UserDetailData) authResult.getPrincipal();
    String token = JWT.create()
      .withSubject(userDetailData.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(TOKEN_SECRET));

    response.getWriter().write(token);
    response.getWriter().flush();
  }
}
