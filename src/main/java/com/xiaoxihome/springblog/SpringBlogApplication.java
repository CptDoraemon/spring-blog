package com.xiaoxihome.springblog;

import com.xiaoxihome.springblog.domain.Role;
import com.xiaoxihome.springblog.domain.User;
import com.xiaoxihome.springblog.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));

			userService.saveUser(new User(null, "Aaa", "aaa@test.com", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Bbb", "bbb@test.com", "1234", new ArrayList<>()));

			userService.addRoleToUser("Aaa", "USER");
			userService.addRoleToUser("Bbb", "USER");

			userService.addRoleToUser("Aaa", "ADMIN");
		};
	}

}
