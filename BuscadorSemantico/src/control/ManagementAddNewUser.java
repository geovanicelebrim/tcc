package control;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import entity.User;

public class ManagementAddNewUser {
	
	private static void writeUsers(String absolutePath, User user) throws Exception {
		ArrayList<User> users;
		if (DAO.File.existFile(absolutePath)) {
			users = Login.loadUsers();
		} else {
			users = new ArrayList<>();
		}
		users.add(user);

		byte[] textByte = User.encryptAll(users);
		
		File outputFile = new File(absolutePath);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(textByte);
		
		outputStream.close();
	}
	
	public static void addNewUser(User user) throws Exception {
		if(Login.userExist(user)) {
			throw new Exception("The user already exists.");
		}
		writeUsers(Login.absolutePath, user);
	}
}
