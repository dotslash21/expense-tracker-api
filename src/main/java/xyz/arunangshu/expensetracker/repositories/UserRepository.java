package xyz.arunangshu.expensetracker.repositories;

import xyz.arunangshu.expensetracker.domain.User;
import xyz.arunangshu.expensetracker.exceptions.EtAuthException;

public interface UserRepository {

  Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

  User findByEmailAndPassword(String email, String password) throws EtAuthException;

  Integer getCountByEmail(String email);

  User findById(Integer userId);
}
