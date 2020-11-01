package xyz.arunangshu.expensetracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import xyz.arunangshu.expensetracker.domain.User;
import xyz.arunangshu.expensetracker.exceptions.EtAuthException;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private static final String SQL_CREATE = "INSERT INTO "
      + "ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) "
      + "VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
  private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
  private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, "
      + "PASSWORD FROM ET_USERS WHERE USER_ID = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Integer create(String firstName, String lastName, String email, String password)
      throws EtAuthException {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE,
            Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        return  preparedStatement;
      }, keyHolder);

      return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("USER_ID");
    } catch (Exception e) {
      throw new EtAuthException("Invalid details, Failed to create account");
    }
  }

  @Override
  public User findByEmailAndPassword(String email, String password) throws EtAuthException {
    return null;
  }

  @Override
  public Integer getCountByEmail(String email) {
    return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
  }

  @Override
  public User findById(Integer userId) {
    return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
  }

  private final RowMapper<User> userRowMapper = ((resultSet, rowNum) -> new User(
      resultSet.getInt("USER_ID"),
      resultSet.getString("FIRST_NAME"),
      resultSet.getString("LAST_NAME"),
      resultSet.getString("EMAIL"),
      resultSet.getString("PASSWORD")
  ));
}
