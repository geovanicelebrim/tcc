package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import DAO.Paths;
import entity.User;
import util.CryptoUtils;

public class Login {

	private static String absolutePath = Paths.REPOSITORY.toString() + "users.dat";

	private static ArrayList<User> loadUsers(String absolutePath) throws Exception {
		if (!DAO.File.existFile(absolutePath)) {
			throw new Exception("Arquivo inexistente");
		}
		
		File inputFile = new File(absolutePath);
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);
		
		byte[] textByte = CryptoUtils.decrypt(inputBytes);

		String text = new String(textByte);
		String textLines[] = text.split("\n");
		
		ArrayList<User> users = new ArrayList<>();
		for (String l : textLines) {
			users.add(new User(l.split("\t")[0], l.split("\t")[1], l.split("\t")[2]));
		}
		
		inputStream.close();
		return users;
	}

	public static void writeUsers(String absolutePath, User user) throws Exception {
		ArrayList<User> users;
		if (DAO.File.existFile(absolutePath)) {
			users = loadUsers(absolutePath);
		} else {
			users = new ArrayList<>();
			users.add(user);
		}

		byte[] textByte = User.encryptAll(users);
		
		File outputFile = new File(absolutePath);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(textByte);
		
		outputStream.close();
	}

	public static User authenticateUser(User user) throws Exception {

		ArrayList<User> users = loadUsers(absolutePath);

		for (User u : users) {
			if (u.authenticate(user)) {
				return u;
			}
		}
		return null;
	}
}
