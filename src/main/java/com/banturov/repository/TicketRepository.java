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

	// Filter by carrier name
	public static List<Ticket> getTicketFilterCarrier(String url, String userName, String password, Page page) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket WHERE carrier_id = (select carrier_id FROM carrier where name = ?);");
			getFreeTickets.setInt(1, page.getLimit());
			getFreeTickets.setInt(2, page.getLimit() * page.getPage());
			getFreeTickets.setString(3, page.getFilterValue());
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

	// Filter by date
	public static List<Ticket> getTicketFilterDate(String url, String userName, String password, Page page) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement("SELECT * FROM ticket WHERE date = ?;");
			getFreeTickets.setInt(1, page.getLimit());
			getFreeTickets.setInt(2, page.getLimit() * page.getPage());
			getFreeTickets.setString(3, page.getFilterValue());
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

	// Filter by Departure
	public static List<Ticket> getTicketFilterDeparture(String url, String userName, String password, Page page) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket where route_id = (SELECT route_id FROM route WHERE departure = ?");
			getFreeTickets.setInt(1, page.getLimit());
			getFreeTickets.setInt(2, page.getLimit() * page.getPage());
			getFreeTickets.setString(3, page.getFilterValue());
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

	// Filter by Departure
	public static List<Ticket> getTicketFilterDestination(String url, String userName, String password, Page page) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket where route_id = (SELECT route_id FROM route WHERE destination = ?");
			getFreeTickets.setInt(1, page.getLimit());
			getFreeTickets.setInt(2, page.getLimit() * page.getPage());
			getFreeTickets.setString(3, page.getFilterValue());
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
