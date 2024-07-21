package com.banturov.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.banturov.entity.BuyTicket;
import com.banturov.entity.Ticket;
import com.banturov.pagination.FilterData;

import redis.clients.jedis.Jedis;

@Component
public class TicketRepository {

	private static Logger log = Logger.getLogger(TicketRepository.class.getName());

	public TicketRepository() {
	}

	// Get free tickets
	public List<Ticket> getFreeTicket(String url, String userName, String password, int limit, int pageNumber) {
		List<Ticket> resultList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getFreeTickets = connection
					.prepareStatement("SELECT * FROM ticket WHERE buyer_id IS NULL limit ? offset ?;");
			getFreeTickets.setInt(1, limit);
			getFreeTickets.setInt(2, limit * pageNumber);
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

	// Filter by Destination
	public List<Ticket> getTicketFilterDestination(String url, String userName, String password, FilterData FilterData,
			int limit, int pageNumber) throws Exception {
		List<Ticket> resultList = new ArrayList<>();
		if (FilterData.getFilterCariier() != null) {
			if (FilterData.getFilterCariier().length() < 3) {
				throw new IllegalArgumentException("Carrier must be minimum 3 length");
			}
		}
		if (FilterData.getFilterDeparture() != null) {
			if (FilterData.getFilterDeparture().length() < 3) {
				throw new IllegalArgumentException("Departure must be minimum 3 length");
			}
		}
		if (FilterData.getFilterDestination() != null) {
			if (FilterData.getFilterDestination().length() < 3) {
				throw new IllegalArgumentException("Destination must be minimum 3 length");
			}
		}
		if (FilterData.getFilterDate() != null) {
			if (FilterData.getFilterDate().length() != 8) {
				throw new IllegalArgumentException("Date must be dd.mm.yy");
			}
		}
		if (FilterData.getFilterDestination() == null && FilterData.getFilterDate() == null
				&& FilterData.getFilterDeparture() == null && FilterData.getFilterCariier() == null) {
			throw new IllegalArgumentException("Every argument is null");
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			StringBuilder sqlQuery = new StringBuilder();
			if (FilterData.getFilterCariier() != null) { // Carrier
				if (sqlQuery.length() == 0) {
					sqlQuery.append("WHERE ticket.carrier_id = (SELECT carrier_id FROM carrier WHERE name = '"
							+ FilterData.getFilterCariier() + "')");
				} else {
					sqlQuery.append("AND ticket.carrier_id = (SELECT carrier_id FROM carrier WHERE name = '"
							+ FilterData.getFilterCariier() + "')");
				}

			}
			if (FilterData.getFilterDestination() != null) { // Destination
				if (sqlQuery.length() == 0) {
					sqlQuery.append("WHERE route_id = (SELECT route_id FROM route WHERE destination = '"
							+ FilterData.getFilterDestination() + "')");
				} else {
					sqlQuery.append("AND route_id = (SELECT route_id FROM route WHERE destination = '"
							+ FilterData.getFilterDestination() + "')");
				}

			}
			if (FilterData.getFilterDeparture() != null) { // Departure
				if (sqlQuery.length() == 0) {
					sqlQuery.append("WHERE route_id = (SELECT route_id FROM route WHERE departure = '"
							+ FilterData.getFilterDeparture() + "')");
				} else {
					sqlQuery.append("AND route_id = (SELECT route_id FROM route WHERE departure = '"
							+ FilterData.getFilterDeparture() + "')");
				}

			}
			if (FilterData.getFilterDate() != null) { // Date
				if (sqlQuery.length() == 0) {
					sqlQuery.append("WHERE date = '" + FilterData.getFilterDate() + "' ");
				} else {
					sqlQuery.append("AND date = '" + FilterData.getFilterDate() + "' ");
				}

			}

			sqlQuery.append(" limit " + limit + " offset " + limit * pageNumber + ";");
			String sqlQueryPrepare = "SELECT * FROM ticket " + sqlQuery;
			log.log(Level.INFO, sqlQueryPrepare);
			PreparedStatement getFreeTickets = connection.prepareStatement(sqlQueryPrepare);
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
	public List<Ticket> showPurchasedTicket(String url, String userName, String password, String login, int limit,
			int pageNumber) throws Exception {
		if (login.length() < 1) {
			throw new IllegalArgumentException();
		}
		ArrayList<Ticket> resultList = new ArrayList<>();
		Jedis jedisClient = new Jedis("localhost", 6379, 12000, false);

		String redisBase64 = jedisClient.get(login);
		if (redisBase64 != null) {
			byte[] decodedBytes = Base64.getDecoder().decode(redisBase64);
			ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object ob = ois.readObject();
			ois.close();
			resultList.addAll((Collection<? extends Ticket>) ob);
			log.log(Level.INFO, "Data from REDIS");
			return resultList;
		}
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getPurchasedTickets = connection.prepareStatement(
					"SELECT * FROM ticket where buyer_id = (SELECT user_id FROM user WHERE user_login = ?) limit ? offset ?;");
			getPurchasedTickets.setString(1, login);
			getPurchasedTickets.setInt(2, limit);
			getPurchasedTickets.setInt(3, limit * pageNumber);
			ResultSet resultSet = getPurchasedTickets.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5));
				resultList.add(ticket);
			}

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(byteArray);
			objectOut.writeObject(resultList);
			objectOut.close();
			byte[] bytes = byteArray.toByteArray();
			String encodedArray = Base64.getEncoder().encodeToString(bytes);
			jedisClient.set(login, encodedArray);
			jedisClient.expire(login, 15);
			jedisClient.close();
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new SQLException("SQL server error");
		}
		log.log(Level.INFO, "Data from SQL");
		return resultList;
	}

	// Buy ticket
	public String buyTicket(String url, String userName, String password, int ticketId, String login)
			throws SQLException, IllegalAccessException {
		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			PreparedStatement getPurchasedTickets = connection.prepareStatement(
					"SELECT ticket_id, place_number FROM ticket WHERE buyer_id is null and ticket_id = ?;");
			getPurchasedTickets.setInt(1, ticketId);
			ResultSet resultSet = getPurchasedTickets.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				throw new IllegalAccessException("Ticket already purchased or doesn`t exist");
			}
			resultSet.close();
			PreparedStatement buyTickets = connection.prepareStatement(
					"UPDATE ticket SET buyer_id = (SELECT user_id FROM user WHERE user_login = ?) WHERE ticket_id = ?;");
			buyTickets.setString(1, login);
			buyTickets.setInt(2, ticketId);
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

	// Find by id
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
