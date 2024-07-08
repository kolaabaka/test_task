package com.banturov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.banturov.entity.User;
import com.banturov.exception.AlreadyExistException;

@Component
public class UserRepository {

	private static Logger log = Logger.getLogger(UserRepository.class.getName());

	public UserRepository() {
	}

	// Registration
	public boolean addUser(String url, String userName, String password, User user) throws AlreadyExistException, SQLException {
		int id = 0;
		if (user.getLogin().length() == 0 || user.getName().length() == 0 || user.getPassword().length() == 0) {
			throw new IllegalArgumentException("Illegal user atribute");
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement checkLogin = connection.prepareStatement("SELECT user_id FROM user WHERE user_login = ?;");
			checkLogin.setString(1, user.getLogin());
			ResultSet checkSet = checkLogin.executeQuery();
			if(checkSet.isBeforeFirst()) {
				throw new AlreadyExistException("User login already exist");
			}
			checkLogin.close();
			checkSet.close();
			
			PreparedStatement takeId = connection
					.prepareStatement("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1;");
			ResultSet resultSet = takeId.executeQuery();
			if (resultSet.next())
				id = resultSet.getInt(1) + 1;
			else {
				log.log(Level.WARNING, "user_id is not found");
				return false;
			}
			PreparedStatement insertQuery = connection.prepareStatement("INSERT INTO user VALUES(?,?,?,?);");
			insertQuery.setInt(1, id);
			insertQuery.setString(2, user.getLogin());
			insertQuery.setString(3, user.getName());
			insertQuery.setString(4, user.getPassword());
			insertQuery.executeUpdate();
			takeId.close();
			insertQuery.close();
			connection.close();
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		log.log(Level.INFO, "Create new user: " + user.getLogin());
		return true;
	}

	// Login
	public boolean checkUser(String url, String userName, String password, User user) throws SQLException {
		if (user.getLogin().length() == 0 || user.getName().length() == 0 || user.getPassword().length() == 0) {
			throw new IllegalArgumentException("Illegal user atributes");
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement loginUser = connection.prepareStatement(
					"SELECT user_id FROM user WHERE user_login = ? and user_name = ? and user_password = ?;");
			loginUser.setString(1, user.getLogin());
			loginUser.setString(2, user.getName());
			loginUser.setString(3, user.getPassword());
			ResultSet resultSet = loginUser.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				return false;
			}
			loginUser.close();
			connection.close();
			log.log(Level.INFO, "User  " + user.getLogin() + " logged in");
			return true;
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
	}

}
