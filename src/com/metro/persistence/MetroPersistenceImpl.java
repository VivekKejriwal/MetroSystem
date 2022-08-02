package com.metro.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.metro.beans.Card;
import com.metro.beans.User;

public class MetroPersistenceImpl implements MetroPersistence{

	@Override
	public List<Integer> getAllUsers() {
		List<Integer> listOfUserId = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Metro","root","wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("Select * from user_details");) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int user_id = resultSet.getInt("user_id");
				listOfUserId.add(user_id);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfUserId;
	}

	@Override
	public boolean createUser(User user) {
		int rows1 = 0, rows2 = 0;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Metro","root","wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO user_details values(?,?,?,?,?)");
				PreparedStatement preparedStatement1 = connection
						.prepareStatement("INSERT INTO cards values(?,?,?)");) {
			

			preparedStatement.setInt(1, user.getUserId());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setLong(5, user.getPhoneNumber());

			rows1 = preparedStatement.executeUpdate();
			
			int currentMaxId = currentMaxCardId();
			
			preparedStatement1.setInt(1, user.getUserId());
			preparedStatement1.setInt(2, currentMaxId == 0 ? 101 : currentMaxId+1);
			preparedStatement1.setDouble(3,100);
			
			user.setCard(new Card(currentMaxId == 0 ? 101 : currentMaxId+1,100));
			
			rows2 = preparedStatement1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rows1 * rows2) != 0;
	}
	
	private int currentMaxCardId() {
		int result = -1;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Metro","root","wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select max(card_id) as 'id' from cards");) {

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result = resultSet.getInt("id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
