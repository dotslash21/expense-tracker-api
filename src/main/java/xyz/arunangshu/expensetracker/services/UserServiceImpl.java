package xyz.arunangshu.expensetracker.services;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.arunangshu.expensetracker.domain.User;
import xyz.arunangshu.expensetracker.exceptions.EtAuthException;
import xyz.arunangshu.expensetracker.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User validateUser(String email, String password) throws EtAuthException {
    if (email != null) email = email.toLowerCase();

    return userRepository.findByEmailAndPassword(email, password);
  }

  @Override
  public User registerUser(String firstName, String lastName, String email, String password)
      throws EtAuthException {
    if (email != null) email = email.toLowerCase();

    if (!isValidEmail(email))
      throw new EtAuthException("Invalid email format");

    Integer count = userRepository.getCountByEmail(email);
    if (count > 0)
      throw new EtAuthException("Email already in use");

    Integer userId = userRepository.create(firstName, lastName, email, password);
    return userRepository.findById(userId);
  }

  boolean isValidEmail(String email) {
    Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
    return emailPattern.matcher(email).matches();
  }
}
