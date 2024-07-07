package com.banturov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banturov.entity.Ticket;
import com.banturov.pagination.Page;

public class TicketRepository {

	private static Logger log = Logger.getLogger(TicketRepository.class.getName());

	// Get free tickets
	public static List<Ticket> getFreeTicket(String url, String userName, String password, Page page) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection
					.prepareStatement("SELECT * FROM ticket WHERE buyer_id IS NULL limit ? offset ?;");
			getFreeTickets.setInt(1, page.getLimit());
			getFreeTickets.setInt(2, page.getLimit() * page.getPage());
			ResultSet resultSet = getFreeTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
		}
		return resultList;

	}

}
