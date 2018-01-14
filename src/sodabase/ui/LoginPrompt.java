package sodabase.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPrompt extends JFrame {

	private JTextField usernameBox = null;
	private JTextField passwordBox = null;
	private JButton loginButton = null;
	private JButton registerButton = null;
	
	public LoginPrompt(LoginComplete complete) {
		this.add(getPanel(complete));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private JPanel getPanel(LoginComplete complete) {
		JPanel panel = new JPanel();
		this.usernameBox = new JTextField();
		this.passwordBox = new JPasswordField();
		GridLayout layout = new GridLayout(3,2);
		layout.setHgap(25);
		layout.setVgap(25);
		panel.setLayout(layout);
		panel.add(new JLabel("Username:"));
		panel.add(this.usernameBox);
		panel.add(new JLabel("Password:"));
		panel.add(this.passwordBox);
		this.loginButton = new JButton("Login");
		this.registerButton = new JButton("Register");
		
		this.loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				complete.login(usernameBox.getText(), passwordBox.getText());
			}
		});

		this.registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				complete.register(usernameBox.getText(), passwordBox.getText());
			}
		});
		
		panel.add(this.loginButton);
		panel.add(this.registerButton);
		return panel;

	}
}
