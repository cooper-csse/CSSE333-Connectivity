package sodabase.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
		//TODO: Task 5
		JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
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
