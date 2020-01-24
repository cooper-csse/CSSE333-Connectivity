package sodabase.services;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;
	
	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		try {
			if (sodaName.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Er1: Soda name cannot be empty.");
				return false;
			} else if (manf.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Soda manufacturer cannot be empty.");
				return false;
			}
			String query = "{? = call AddSoda(@sodaName = ?, @manf = ?)}";
			CallableStatement call = this.dbService.getConnection().prepareCall(query);
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, sodaName);
			call.setString(3, manf);
			call.execute();

			if (call.getInt(1) == 2) {
				JOptionPane.showMessageDialog(null, "Er2: Soda name must not already exist in table.");
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getSodas() {
		ArrayList<String> rests = new ArrayList<String>();
		String query = "SELECT name FROM Soda";

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
