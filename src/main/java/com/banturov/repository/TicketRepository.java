package com.banturov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.banturov.entity.BuyTicket;
import com.banturov.entity.PurchaseFilter;
import com.banturov.entity.Ticket;
import com.banturov.pagination.Page;

@Component
public class TicketRepository {

	private static Logger log = Logger.getLogger(TicketRepository.class.getName());

	public TicketRepository() {
	}

	// Get free tickets
	public List<Ticket> getFreeTicket(String url, String userName, String password, Page page) {
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
	public List<Ticket> getTicketFilterCarrier(String url, String userName, String password, Page page)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		if (page.getFilterValue().length() < 1) {
			throw new IllegalArgumentException();
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket WHERE carrier_id = (select carrier_id FROM carrier where name = ?) limit ? offset ?;");
			getFreeTickets.setString(1, page.getFilterValue());
			getFreeTickets.setInt(2, page.getLimit());
			getFreeTickets.setInt(3, page.getLimit() * page.getPage());
			ResultSet resultSet = getFreeTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return resultList;
	}

	// Filter by date
	public List<Ticket> getTicketFilterDate(String url, String userName, String password, Page page) throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd:mm:yy");
		try {
			dateFormat.parse(page.getFilterValue());
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection
					.prepareStatement("SELECT * FROM ticket WHERE date = ? limit ? offset ?;");
			getFreeTickets.setString(1, page.getFilterValue());
			getFreeTickets.setInt(2, page.getLimit());
			getFreeTickets.setInt(3, page.getLimit() * page.getPage());
			ResultSet resultSet = getFreeTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return resultList;
	}

	// Filter by Departure
	public List<Ticket> getTicketFilterDeparture(String url, String userName, String password, Page page)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		if (page.getFilterValue().length() < 1) {
			throw new IllegalArgumentException();
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket where route_id = (SELECT route_id FROM route WHERE departure = ?)limit ? offset ?;");
			getFreeTickets.setString(1, page.getFilterValue());
			getFreeTickets.setInt(2, page.getLimit());
			getFreeTickets.setInt(3, page.getLimit() * page.getPage());
			ResultSet resultSet = getFreeTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return resultList;
	}

	// Filter by Destination
	public List<Ticket> getTicketFilterDestination(String url, String userName, String password, Page page)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		if (page.getFilterValue().length() < 1) {
			throw new IllegalArgumentException();
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection.prepareStatement(
					"SELECT * FROM ticket where route_id = (SELECT route_id FROM route WHERE destination = ?)limit ? offset ?;");
			getFreeTickets.setString(1, page.getFilterValue());
			getFreeTickets.setInt(2, page.getLimit());
			getFreeTickets.setInt(3, page.getLimit() * page.getPage());
			ResultSet resultSet = getFreeTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return resultList;
	}

	// Purchased tickets
	public List<Ticket> showPurchasedTicket(String url, String userName, String password, PurchaseFilter PurchaseFilter)
			throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		if (PurchaseFilter.getUser().getLogin().length() < 1) {
			throw new IllegalArgumentException();
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getPurchasedTickets = connection.prepareStatement(
					"SELECT * FROM ticket where buyer_id = (SELECT user_id FROM user WHERE user_login = ?) limit ? offset ?;");
			getPurchasedTickets.setString(1, PurchaseFilter.getUser().getLogin());
			getPurchasedTickets.setInt(2, PurchaseFilter.getPage().getLimit());
			getPurchasedTickets.setInt(3, PurchaseFilter.getPage().getLimit() * PurchaseFilter.getPage().getPage());
			ResultSet resultSet = getPurchasedTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return resultList;
	}

	// Buy ticket
	public String buyTicket(String url, String userName, String password, BuyTicket buyTicket)
			throws SQLException, IllegalAccessException {
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getPurchasedTickets = connection.prepareStatement(
					"SELECT ticket_id, place_number FROM ticket WHERE buyer_id is null and ticket_id = ?;");
			getPurchasedTickets.setInt(1, buyTicket.getTicket_id());
			ResultSet resultSet = getPurchasedTickets.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				throw new IllegalAccessException("Ticket already purchased or doesn`t exist");
			}
			resultSet.close();
			PreparedStatement buyTickets = connection.prepareStatement(
					"UPDATE ticket SET buyer_id = (SELECT user_id FROM user WHERE user_login = ?) WHERE ticket_id = ?;");
			buyTickets.setString(1, buyTicket.getUser().getLogin());
			buyTickets.setInt(2, buyTicket.getTicket_id());
			int amountUpdate = buyTickets.executeUpdate();
			getPurchasedTickets.close();
			resultSet.close();
			buyTickets.close();
			return amountUpdate + "";
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
	}

	public Ticket findTicketById(String url, String userName, String password, int id) throws SQLException {
		Ticket tick = new Ticket();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement finTicketById = connection.prepareStatement("SELECT * FROM ticket WHERE ticket_id = ?;");
			finTicketById.setInt(1, id);
			ResultSet resultSet = finTicketById.executeQuery();
			resultSet.next();
			tick = new Ticket(resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6),
					resultSet.getString(7));
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		return tick;
	}

}
