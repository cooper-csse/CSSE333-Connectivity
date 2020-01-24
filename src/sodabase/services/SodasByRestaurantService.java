package sodabase.services;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;

public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		try {
			String query = "{? = call AddSells(@sodaName = ?, @restName = ?, @price = ?)}";
			CallableStatement call = this.dbService.getConnection().prepareCall(query);
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, soda);
			call.setString(3, rest);
			call.setDouble(4, price);
			call.execute();

			switch (call.getInt(1)) {
				case 1:
					JOptionPane.showMessageDialog(null, "ERROR 1: Soda name cannot be null or empty.");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "ERROR 2: Rest name cannot be null or empty.");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "ERROR 3: Price cannot be null or empty.");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, "ERROR 4: Given soda does not exist.");
					break;
				case 5:
					JOptionPane.showMessageDialog(null, "ERROR 5: Given restaurant name does not exist.");
					break;
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price, boolean useGreaterThanEqual) {
		try {
			String query = buildParameterizedSqlStatementString(rest, soda, (price.isEmpty() ? null : Double.valueOf(price)), useGreaterThanEqual);

			PreparedStatement prep = this.dbService.getConnection().prepareStatement(query);
			int count = 0;
			if (rest != null) prep.setString(++count, rest);
			if (soda != null) prep.setString(++count, soda);
			if (!price.isEmpty()) prep.setDouble(++count, Double.parseDouble(price));
			prep.executeQuery();

			return parseResults(prep.getResultSet());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price, boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (rest != null) wheresToAdd.add("Restaurant = ?");
		if (soda != null) wheresToAdd.add("Soda = ?");
		if (price != null) {
			if (useGreaterThanEqual) wheresToAdd.add("Price >= ?");
			else wheresToAdd.add("Price <= ?");
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			System.out.println(sodasByRestaurants.size());
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}
}
