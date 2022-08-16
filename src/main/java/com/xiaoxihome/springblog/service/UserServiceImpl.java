package com.xiaoxihome.springblog.service;

import com.xiaoxihome.springblog.domain.Role;
import com.xiaoxihome.springblog.domain.User;
import com.xiaoxihome.springblog.repo.RoleRepo;
import com.xiaoxihome.springblog.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepo userRepo;
  private final RoleRepo roleRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("username %s not found", username));
    } else {
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      user.getRoles().forEach(role -> {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
      });
      return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        authorities
      );
    }
  }

  @Override
  public User saveUser(User user) {
    log.info("Saving new user {} to the database", user.getUsername());
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepo.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    User user = userRepo.findByUsername(username);
    Role role = roleRepo.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String username) {
    return userRepo.findByUsername(username);
  }

  @Override
  public List<User> getUsers() {
    return userRepo.findAll();
  }
}
