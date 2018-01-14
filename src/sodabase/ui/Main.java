package sodabase.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import sodabase.services.DatabaseConnectionService;
import sodabase.services.EncryptionService;
import sodabase.services.RestaurantService;
import sodabase.services.SodaService;
import sodabase.services.SodasByRestaurantService;
import sodabase.services.UserService;

public class Main {
	private static String serverUsername = null;
	private static String serverPassword = null;
	private static DatabaseConnectionService dbService = null;
	private static LoginPrompt lp = null;
	private static WindowCloseListener wcl = null;
	private static EncryptionService es = new EncryptionService();
	

	public static void main(String[] args) {

		Properties props = loadProperties();
		serverUsername = props.getProperty("serverUsername");
		serverPassword = props.getProperty("serverPassword");
		dbService = new DatabaseConnectionService(props.getProperty("serverName"), props.getProperty("databaseName"));
		wcl = new WindowCloseListener(dbService);
		final UserService userService = new UserService(dbService);
		LoginComplete lc = new LoginComplete() {
			@Override
			public void login(String u, String p) {
				if (userService.login(u, p)) {
					loginSucceeded();
				} else {
					JOptionPane.showMessageDialog(null, "Login unsuccessful.");
				}
			}

			@Override
			public void register(String u, String p) {
				if (userService.register(u, p)) {
					loginSucceeded();
				} else {
					JOptionPane.showMessageDialog(null, "Registration unsuccessful.");
				}
			}
		};

		if (!dbService.connect(serverUsername, serverPassword)) {
			JOptionPane.showMessageDialog(null, "Connection to database could not be made.");
		} else {
			if (userService.useApplicationLogins()) {
				lp = new LoginPrompt(lc);
				lp.addWindowListener(wcl);	
			}
			else {
				loginSucceeded();
			}
		}

	}

	public static void loginSucceeded() {
		if (lp!=null) {
			lp.setVisible(false);
			lp.dispose();
		}
		RestaurantService rService = new RestaurantService(dbService);
		SodaService sService = new SodaService(dbService);
		SodasByRestaurantService srService = new SodasByRestaurantService(dbService);
		JFrame sodaFrame = new Frame(rService, sService, srService);
		sodaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sodaFrame.addWindowListener(wcl);
	}

	public static Properties loadProperties() {
		String binDir = System.getProperty("user.dir") + "/bin/";
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(es.getEncryptionPassword());
		FileInputStream fis = null;
		EncryptableProperties props = new EncryptableProperties(encryptor);
		try {
			fis = new FileInputStream(binDir + "sodabaseapp.properties");
			props.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("template.properties file not found");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("template.properties file could not be opened");
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println("Input Stream could not be closed.");
					e.printStackTrace();
				}
			}
		}
		return props;
	}

}
