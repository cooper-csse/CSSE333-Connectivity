package sodabase.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import sodabase.services.RestaurantService;
import sodabase.services.SodaService;
import sodabase.services.SodasByRestaurantService;

public class Frame extends JFrame {

	private JMenu menu;
	private JMenuItem restaurantList;
	private JMenuItem addRestaurant;
	private SodasByRestaurantPanel listPanel;
	private JPanel addPanel;
	private JPanel containerPanel;

	private JPanel currentPanel = null;

	public Frame(RestaurantService rService, SodaService sService, SodasByRestaurantService srService) {
		super();
		this.setSize(1000, 750);
		this.setResizable(false);
		this.setTitle("Soda Base");
		this.containerPanel = new JPanel();
		this.listPanel = new SodasByRestaurantPanel(rService, sService, srService);
		this.addPanel = getAddPanel(rService, sService, srService);

		this.currentPanel = this.listPanel;
		this.addPanel.setVisible(false);
		this.menu = new JMenu("Screens");
		this.restaurantList = new JMenuItem("Restaurant List");
		this.restaurantList.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToRestaurantList();
			}

		}));
		this.addRestaurant = new JMenuItem("Add Items");
		this.addRestaurant.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToAddRestaurant();
			}

		}));
		this.menu.add(this.restaurantList);
		this.menu.add(this.addRestaurant);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(this.menu);
		
		this.add(menuBar, BorderLayout.NORTH);
		this.add(this.containerPanel, BorderLayout.CENTER);
		this.containerPanel.add(this.listPanel);
		this.containerPanel.add(this.addPanel);
		this.setVisible(true);
		this.pack();
	}
	
	private JPanel getAddPanel(RestaurantService rService, SodaService sService, SodasByRestaurantService srService) {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(3,1);
		toReturn.setLayout(layout);
		layout.setVgap(100);
		toReturn.add(new AddRestaurantPanel(rService));
		toReturn.add(new AddSodaPanel(sService));
		toReturn.add(new AddSellsPanel(srService));
		return toReturn;
		
	}

	private void switchToRestaurantList() {
		if (this.listPanel == this.currentPanel) {
			return;
		}
		this.currentPanel = this.listPanel;
		this.listPanel.updateLists();
		this.switchPanel(this.addPanel);
	}

	private void switchPanel(Component toRemove) {
		toRemove.setVisible(false);
		this.currentPanel.setVisible(true);
	}

	private void switchToAddRestaurant() {
		if (this.addPanel == this.currentPanel) {
			return;
		}
		this.currentPanel = this.addPanel;
		this.switchPanel(this.listPanel);
	}
}
