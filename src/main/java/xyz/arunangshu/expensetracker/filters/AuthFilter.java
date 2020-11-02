package xyz.arunangshu.expensetracker.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;
import xyz.arunangshu.expensetracker.Constants;

public class AuthFilter extends GenericFilterBean {

  private final JwtParser jwtParser;

  public AuthFilter() {
    SecretKey secretKey = Keys
        .hmacShaKeyFor(Constants.API_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    String authHeader = httpServletRequest.getHeader("Authorization");
    if (authHeader != null) {
      String[] authHeaderArr = authHeader.split("Bearer ");

      if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
        String token = authHeaderArr[1];

        try {
          Claims claims = jwtParser.parseClaimsJws(token).getBody();
          httpServletRequest
              .setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
        } catch (Exception e) {
          httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token");
          return;
        }
      } else {
        httpServletResponse
            .sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
        return;
      }
    } else {
      httpServletResponse
          .sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be present");
      return;
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
