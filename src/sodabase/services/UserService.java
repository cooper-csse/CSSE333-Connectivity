package sodabase.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		//TODO: Complete this method.
		return false;
	}

	public boolean register(String username, String password) {
		byte[] salt = this.getNewSalt();
		String hashed = this.hashPassword(salt, password);

		try {
			String query = "{? = call Register(@Username = ?, @PasswordSalt = ?, @PasswordHash = ?)}";
			CallableStatement call = this.dbService.getConnection().prepareCall(query);
			call.registerOutParameter(1, Types.INTEGER);
			call.setString(2, username);
			call.setString(3, this.getStringFromBytes(salt));
			call.setString(4, hashed);
			call.execute();

			if (call.getInt(1) != 0) {
				JOptionPane.showMessageDialog(null, "Registration Failed.");
				return false;
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
