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

import sodabase.services.SodaService;

public class AddSodaPanel extends JPanel {

	private SodaService sService = null;
	JTextField newSodaName = null;
	JTextField newSodaManf = null;

	public AddSodaPanel(SodaService rService) {
		this.sService = rService;
		this.setLayout(new BorderLayout());
		JPanel createSodaPanel = this.createSodaAddtionPanel();
		this.add(createSodaPanel, BorderLayout.CENTER);
	}

	private JPanel createSodaAddtionPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.newSodaName = new JTextField();
		this.newSodaName.setPreferredSize(new Dimension(100, 30));
		this.newSodaManf = new JTextField();
		this.newSodaManf.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("Soda Name:"));
		toReturn.add(this.newSodaName);

		toReturn.add(new JLabel("Manufacturer:"));
		toReturn.add(this.newSodaManf);

		JButton addButton = new JButton("Add Soda");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addSoda();

			}

		});

		return toReturn;
	}

	private boolean addSoda() {
		if (this.sService.addSoda(this.newSodaName.getText(), this.newSodaManf.getText())) {
			this.newSodaName.setText("");
			this.newSodaManf.setText("");
			return true;
		}
		return false;
	}

}
