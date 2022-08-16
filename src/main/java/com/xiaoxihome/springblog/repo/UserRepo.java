package com.xiaoxihome.springblog.repo;

import com.xiaoxihome.springblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
