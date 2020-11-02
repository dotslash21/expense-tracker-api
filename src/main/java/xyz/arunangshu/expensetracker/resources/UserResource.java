package xyz.arunangshu.expensetracker.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arunangshu.expensetracker.Constants;
import xyz.arunangshu.expensetracker.domain.User;
import xyz.arunangshu.expensetracker.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");

    User user = userService.validateUser(email, password);

    return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
    String firstName = (String) userMap.get("firstName");
    String lastName = (String) userMap.get("lastName");
    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");

    User user = userService.registerUser(firstName, lastName, email, password);

    return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
  }

  private Map<String, String> generateJWT(User user) {
    long timestamp = System.currentTimeMillis();
    SecretKey secretKey = Keys.hmacShaKeyFor(Constants.API_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    String token = Jwts.builder()
        .setIssuedAt(new Date(timestamp))
        .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
        .claim("userId", user.getUserId())
        .claim("email", user.getEmail())
        .claim("firstName", user.getFirstName())
        .claim("lastName", user.getLastName())
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();

    Map<String, String> map = new HashMap<>();
    map.put("token", token);
    return map;
  }
}
