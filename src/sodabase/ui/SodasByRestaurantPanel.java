package sodabase.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import sodabase.services.RestaurantService;
import sodabase.services.SodaService;
import sodabase.services.SodasByRestaurantService;

public class SodasByRestaurantPanel extends JPanel {
	
	private JComboBox<String> restaurantComboBox = null;
	private JComboBox<String> sodaComboBox = null;
	private JTextField priceTextField = null;
	private RestaurantService rService = null;
	private SodaService sService = null;
	private JButton filterButton = null;
	private JTable sodasByRestTable = null;
	private SodasByRestaurantService srService;
	private JCheckBox useGreaterThan;
	
	public SodasByRestaurantPanel(RestaurantService rService, SodaService sService, SodasByRestaurantService srService) {
		this.rService = rService;
		this.sService = sService;
		this.srService = srService;
		JPanel filterPanel = generateFilterUiItems();
		this.setLayout(new BorderLayout());
		this.add(filterPanel, BorderLayout.NORTH);
		JScrollPane tablePane = generateSodaByRestaurantTable();
		this.add(tablePane, BorderLayout.CENTER);
	}

	private JScrollPane generateSodaByRestaurantTable() {
		this.sodasByRestTable = new JTable(this.search());
		JScrollPane scrollPane = new JScrollPane(this.sodasByRestTable);
		this.sodasByRestTable.setFillsViewportHeight(true);
		return scrollPane;
		
	}

	private JPanel generateFilterUiItems() {
		JPanel filterPanel = new JPanel();
		this.restaurantComboBox = new JComboBox<String>();
		this.populateRestaurants();
		this.sodaComboBox = new JComboBox<String>();
		this.populateSodas();
		this.priceTextField = new JTextField();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		filterPanel.setLayout(layout);
		filterPanel.add(new JLabel("Restaurant"));
		filterPanel.add(this.restaurantComboBox);
		filterPanel.add(new JLabel("Soda"));
		filterPanel.add(this.sodaComboBox);
		filterPanel.add(new JLabel("Price"));
		filterPanel.add(this.priceTextField);
		this.useGreaterThan = new JCheckBox("Use Greater Than Or Equal To for Price");
		filterPanel.add(this.useGreaterThan);
		this.priceTextField.setPreferredSize(new Dimension(200,30));
		this.filterButton = new JButton("Search");
		filterPanel.add(filterButton);
		this.filterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sodasByRestTable.setModel(search());
			}
		});
		return filterPanel;
	}
	
	private SodaByRestaurantTableModel search() {
		String selectedRest = (String)this.restaurantComboBox.getSelectedItem();
		String selectedSoda = (String)this.sodaComboBox.getSelectedItem();
		String price = this.priceTextField.getText();
		
		boolean useGreaterThan = this.useGreaterThan.isSelected();
		String restSearch = selectedRest == "None" ? null : selectedRest;
		String sodaSearch = selectedSoda == "None" ? null : selectedSoda;
		ArrayList<SodaByRestaurant> data = this.srService.getSodasByRestaurants(restSearch, sodaSearch, price, useGreaterThan);
		return new SodaByRestaurantTableModel(data);
	}
	
	private void populateRestaurants() {
		this.restaurantComboBox.addItem("None");
		for (String s : this.rService.getRestaurants()) {
			this.restaurantComboBox.addItem(s);
		}
	}
	
	private void populateSodas() {
		this.sodaComboBox.addItem("None");
		for (String s : this.sService.getSodas()) {
			this.sodaComboBox.addItem(s);
		}
	}

	public void updateLists() {
		String curRest = (String)this.restaurantComboBox.getSelectedItem();
		String curSoda = (String)this.sodaComboBox.getSelectedItem();
		this.restaurantComboBox.removeAllItems();
		this.sodaComboBox.removeAllItems();
		this.populateRestaurants();
		this.populateSodas();
		findAndSelectItem(this.restaurantComboBox, curRest);
		findAndSelectItem(this.sodaComboBox, curSoda);
		this.sodasByRestTable.setModel(this.search());
	}
	
	private void findAndSelectItem(JComboBox<String> box, String item) {
		for (int i=0;i<box.getItemCount();i++) {
			if (box.getItemAt(i).equals(item)) {
				box.setSelectedItem(box.getItemAt(i));
				return;
			}
		}
	}
	
}
