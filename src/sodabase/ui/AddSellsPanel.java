package sodabase.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sodabase.services.SodasByRestaurantService;

public class AddSellsPanel extends JPanel {

	private SodasByRestaurantService srService = null;
	JTextField sodaName = null;
	JTextField restName = null;
	JTextField price = null;

	public AddSellsPanel(SodasByRestaurantService srService) {
		this.srService = srService;
		this.setLayout(new BorderLayout());
		JPanel createSodaPanel = this.createSellsAddtionPanel();
		this.add(createSodaPanel, BorderLayout.CENTER);
	}

	private JPanel createSellsAddtionPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.sodaName = new JTextField();
		this.sodaName.setPreferredSize(new Dimension(100, 30));
		this.restName = new JTextField();
		this.restName.setPreferredSize(new Dimension(100, 30));
		this.price = new JTextField();
		this.price.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("Soda Name:"));
		toReturn.add(this.sodaName);

		toReturn.add(new JLabel("Restaurant Name:"));
		toReturn.add(this.restName);
		
		toReturn.add(new JLabel("Price:"));
		toReturn.add(this.price);


		JButton addButton = new JButton("Add Soda to Restaurant");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addSodaToRestaurant();

			}

		});

		return toReturn;
	}

	private boolean addSodaToRestaurant() {
		Double price = null;
		try {
			price = Double.parseDouble(this.price.getText());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Price was not in proper numeric format.");
			return false;
		}
		if (this.srService.addSodaByRestaurant(this.restName.getText(), this.sodaName.getText(), price))
		{
			this.sodaName.setText("");
			this.restName.setText("");
			this.price.setText("");
			return true;
		}
		return false;
	}

}
