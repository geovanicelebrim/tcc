package entity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.CryptoException;
import util.CryptoUtils;

public class User {
	private String password;
	private String name;
	private String email;
	
	public User(String email, String password, String name) throws Exception {
		if (!validadeEmail(email)) {
			throw new Exception("Email inválido");
		}
		this.password = password;
		this.name = name;
		this.email = email;
	}
	
	public User(String email, String password) throws Exception {
		if (!validadeEmail(email)) {
			throw new Exception("Email inválido");
		}
		this.password = password;
		this.email = email;
	}
	
	private boolean validadeEmail(String email) {
		boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public static byte[] encryptAll(ArrayList<User> users) throws CryptoException {
		String textToEncrypt = "";
		for (User user : users) {
			textToEncrypt += user.userToString() + "\n";
		}
		return CryptoUtils.encrypt(textToEncrypt);
	}
	
	public static byte[] decryptAll(byte[] textBytes) throws CryptoException {
		return CryptoUtils.decrypt(textBytes);
	}
	
	private String userToString() {
		return this.email + "\t" + this.password + "\t" + this.name;
	}
	
	public boolean authenticate(User user) {
		if (this.email.equals(user.email) && this.password.equals(user.password)) {
			return true;
		}
		return false;
	}
}
