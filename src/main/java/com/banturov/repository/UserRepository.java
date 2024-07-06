package com.banturov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banturov.entity.User;

public class UserRepository {

	private static Logger log = Logger.getLogger(UserRepository.class.getName());

	//Registration
	public static boolean addUser(String url, String userName, String password, User user) {
		int id = 0;
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
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
			return false;
		}
		log.log(Level.INFO, "Create new user: " + user.getLogin());
		return true;
	}

	//Login 
	public static boolean checkUser(String url, String userName, String password, User user) {
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
			return false;
		}
	}
}
