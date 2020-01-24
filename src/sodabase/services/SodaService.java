package sodabase.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;
	
	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		JOptionPane.showMessageDialog(null, "Add Soda not implemented.");
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
