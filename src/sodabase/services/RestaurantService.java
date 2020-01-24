package sodabase.services;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
		try {
			if (restName.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Er1: Restaurant name cannot be empty.");
				return false;
			} else if (addr.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Restaurant address cannot be empty.");
				return false;
			} else if (contact.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Restaurant contact cannot be empty.");
				return false;
			}
			String query = "{? = call AddRestaurant(@restName = ?, @addr = ?, @contact = ?)}";
			CallableStatement call = this.dbService.getConnection().prepareCall(query);
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, restName);
			call.setString(3, addr);
			call.setString(4, contact);
			call.execute();

			if (call.getInt(1) == 2) {
				JOptionPane.showMessageDialog(null, "Er2: Restaurant name must not already exist in table.");
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public ArrayList<String> getRestaurants() {
		ArrayList<String> rests = new ArrayList<String>();
		String query = "SELECT name FROM Rest";

		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) rests.add(rs.getString("name"));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rests;
	}
}
