package com.humber.j2ee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humber.j2ee.model.User;
import com.humber.j2ee.repository.UserDatabase;

@RestController
public class UserController {
	
	@Autowired
	private UserDatabase database;

	@GetMapping("/loadUsers")
	public List<User> getUsers(){
		return database.getAllUsers();
	}
}
