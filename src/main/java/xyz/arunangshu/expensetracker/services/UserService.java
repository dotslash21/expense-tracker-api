package xyz.arunangshu.expensetracker.services;

import xyz.arunangshu.expensetracker.domain.User;
import xyz.arunangshu.expensetracker.exceptions.EtAuthException;

public interface UserService {

  User validateUser(String email, String password) throws EtAuthException;

  User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}
