package sodabase.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sodabase.services.RestaurantService;

public class AddRestaurantPanel extends JPanel {
	
	private RestaurantService rService = null;
	JTextField newRestName = null;
	JTextField newRestAddr = null;
	JTextField newRestContact = null;
	
	public AddRestaurantPanel(RestaurantService rService) {
		this.rService = rService;
		this.setLayout(new BorderLayout());
		JPanel createRestPanel = this.createRestaurantAddtionPanel();
		this.add(createRestPanel, BorderLayout.CENTER);
	}

	private JPanel createRestaurantAddtionPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.newRestAddr = new JTextField();
		this.newRestAddr.setPreferredSize(new Dimension(100, 30));
		this.newRestContact = new JTextField();
		this.newRestContact.setPreferredSize(new Dimension(100, 30));
		this.newRestName = new JTextField();
		this.newRestName.setPreferredSize(new Dimension(100, 30));
		
		toReturn.add(new JLabel("Restaurant Name:"));
		toReturn.add(this.newRestName);

		toReturn.add(new JLabel("Address:"));
		toReturn.add(this.newRestAddr);

		toReturn.add(new JLabel("Contact:"));
		toReturn.add(this.newRestContact);
		
		JButton addButton = new JButton("Add Restaurant");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addRestaurant();
				
			}
			
		});
		
		return toReturn;
	}
	
	private boolean addRestaurant() {
		if (this.rService.addResturant(this.newRestName.getText(), this.newRestAddr.getText(), this.newRestContact.getText())) {
			this.newRestName.setText("");
			this.newRestAddr.setText("");
			this.newRestContact.setText("");
			return true;
		}
		return false;
	}
	
	
}
