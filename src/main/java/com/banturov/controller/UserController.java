package com.banturov.controller;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.entity.User;
import com.banturov.repository.UserRepository;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String userName = "root";
	private static String password = "root";

	private static Logger log = Logger.getLogger(UserController.class.getName());
	
	@GetMapping(path = "/login")
	public ResponseEntity<Boolean> loginUser(@RequestBody User user) {
		boolean status = UserRepository.checkUser(url, userName, password, user);
		if (status)
			return new ResponseEntity<>(true, HttpStatus.OK);
		else
			return new ResponseEntity<>(false, HttpStatus.CONFLICT);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody User user) {
		boolean status = UserRepository.addUser(url, userName, password, user);
		if (status)
			return new ResponseEntity<>(true, HttpStatus.OK);
		else
			return new ResponseEntity<>(false, HttpStatus.CONFLICT);
	}
}
