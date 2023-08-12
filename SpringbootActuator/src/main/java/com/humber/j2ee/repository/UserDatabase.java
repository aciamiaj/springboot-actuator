package com.humber.j2ee.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.humber.j2ee.model.User;

@Repository
public class UserDatabase {
	
	public List<User> getAllUsers() {
		return Stream.of(
				new User(101, "John", "john@gmail.com", "inactive"),
				new User(102, "Adam", "adam@gmail.com", "active"),
				new User(103, "Taylor", "taylor@gmail.com", "inactive"),
				new User(104, "Katy", "katy@gmail.com", "active"),
				new User(105, "Justin", "justin@gmail.com", "active"))
				.collect(Collectors.toList());
	}
	
	public long getUserStatusCountByStatus(String status) {
		return getAllUsers().stream().filter(user -> user.getStatus().equals(status)).count();
		
	}

}
