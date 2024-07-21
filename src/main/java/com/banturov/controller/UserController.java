package com.banturov.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.configuration.PropertiesConfig;
import com.banturov.entity.User;
import com.banturov.jwt.JwtConverter;
import com.banturov.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User controller", description = "Allows register and authorize a user")
@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class.getName());

	private static String url = null;
	private static String userName = null;
	private static String password = null;

	static {
		url = PropertiesConfig.get("db.url");
		userName = PropertiesConfig.get("db.login");
		password = PropertiesConfig.get("db.password");
	}
	@Autowired
	private UserRepository rep;

	@Autowired
	private JwtConverter jwt;
	
	@Operation(summary = "Login user", description = "Allow login user")
	@GetMapping(path = "/login")
	public ResponseEntity<String> loginUser(
			@RequestBody @Parameter(required = true, description = "An entity with data for user login") User user) {
		String login = null;
		try {
			login = rep.checkUser(url, userName, password, user);
			if (login.length() != 0) {
				log.log(Level.INFO, "User  " + user.getLogin() + " logged in");
				return new ResponseEntity<>(jwt.jwtMaker(login), HttpStatus.OK);
			} else
				return new ResponseEntity<>("No such user", HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Operation(summary = "Register user", description = "Allow register user")
	@PostMapping(path = "/register")
	public ResponseEntity<String> registerUser(
			@RequestBody @Parameter(description = "An entity with data for user registration", required = true) User user) {
		boolean status;
		try {
			status = rep.addUser(url, userName, password, user);
			if (status) {
				log.log(Level.INFO, "New user created");
				return new ResponseEntity<>("true", HttpStatus.CREATED);
			} else
				return new ResponseEntity<>("SQL exception, try later", HttpStatus.BAD_REQUEST); // SQL exception
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Already exist login
		}

	}

}
