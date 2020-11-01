package xyz.arunangshu.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

  private Integer userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
