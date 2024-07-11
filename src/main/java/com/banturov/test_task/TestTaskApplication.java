package com.banturov.test_task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

@ComponentScan("com.banturov")
@SpringBootApplication
public class TestTaskApplication {

	private static String url = "jdbc:mysql://localhost:3306/ticket_db";
	private static String userName = "root";
	private static String password = "root";

	private static Logger log = Logger.getLogger(TestTaskApplication.class.getName());

	public static void main(String[] args) throws SQLException, LiquibaseException {
		SpringApplication.run(TestTaskApplication.class, args);
		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			log.info("SQL IS CONNECTED: " + url);
		} catch (Exception e) {
			log.warning("SQL IS NOT CONNECTED " + e.getMessage());
		}

		Connection connection = DriverManager.getConnection(url, userName, password);
		Database correctDataBase = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(),
				correctDataBase);
		liquibase.update();
		System.out.println("Liquibase finish");
	}

}
