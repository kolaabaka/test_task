package com.banturov.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banturov.entity.Ticket;
import com.banturov.entity.User;
import com.banturov.pagination.Page;
import com.banturov.repository.TicketRepository;
import com.banturov.repository.UserRepository;

@RestController
@RequestMapping(produces = "application/json")
public class MainController {

	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String userName = "root";
	private static String password = "root";

	private static Logger log = Logger.getLogger(MainController.class.getName());

	@GetMapping(path = "/show/user")
	public ResponseEntity<List<User>> getAll() throws SQLException {
		List<User> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT user_login, user_name, user_password FROM user;");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
				resultList.add(user);
			}
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

}
