package io.github.wellvergton.dotheshopping.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidateFilter extends BasicAuthenticationFilter {
  public static final String HEADER_ATTRIBUTE = "Authorization";
  public static final String PREFIX = "Bearer ";
  private final String TOKEN_SECRET;

  public JWTValidateFilter(AuthenticationManager authenticationManager, String token_secret) {
    super(authenticationManager);
    TOKEN_SECRET = token_secret;
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
    String user = JWT.require(Algorithm.HMAC512(TOKEN_SECRET))
      .build()
      .verify(token)
      .getSubject();

    if (user == null) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain chain
  ) throws IOException, ServletException {
    String attribute = request.getHeader(HEADER_ATTRIBUTE);

    if (attribute == null) {
      chain.doFilter(request, response);;

      return;
    }

    if (!attribute.startsWith(PREFIX)) {
      chain.doFilter(request, response);

      return;
    }

    String token = attribute.replace(PREFIX, "");
    UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(request, response);
  }
}
