package com.xiaoxihome.springblog.service;

import com.xiaoxihome.springblog.domain.Role;
import com.xiaoxihome.springblog.domain.User;

import java.util.List;

public interface UserService {
  User saveUser(User user);
  Role saveRole(Role role);

  void addRoleToUser(String username, String roleName);
  User getUser(String username);
  List<User> getUsers();
}
