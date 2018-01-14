package sodabase.ui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SodaByRestaurantTableModel extends AbstractTableModel{

	private ArrayList<SodaByRestaurant> data = null;;
	private String[] columnNames = {"Restaurant", "Soda", "Manufacturer", "RestaurantContact", "Price"};
	
	public SodaByRestaurantTableModel(ArrayList<SodaByRestaurant> data) {
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.data.get(rowIndex).getValue(this.columnNames[columnIndex]);
	}
	
	@Override
	public String getColumnName(int index) {
		return this.columnNames[index];
	}
	

}
