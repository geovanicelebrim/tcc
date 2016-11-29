package control;

import java.util.ArrayList;

import DAO.Paths;
import entity.User;

public class Login {
	
	public static User DEFAULT_USER = new User("geovanicelebrim", "celebrim");
	private static String absolutePath = Paths.REPOSITORY.toString() + "users.dat";
	
	private static ArrayList<User> loadUsers(String absolutePath) throws Exception {
		if(!DAO.File.existFile(absolutePath)) {
			throw new Exception("Arquivo inexistente");
		}
		
		ArrayList<String> lines = DAO.File.readLinesFile(absolutePath);
		ArrayList<User> users = new ArrayList<>();
		
		for(String l : lines) {
			users.add(new User(l.split("\t")[0], l.split("\t")[1]));
		}
		
		return users;
	}
	
	public static void writeUsers(String absolutePath, User user) throws Exception {
		ArrayList<User> users;
		if(DAO.File.existFile(absolutePath)) {
			users = loadUsers(absolutePath);
		} else {
			users = new ArrayList<>();
			users.add(user);
		}
		
		String text = "";
		for(User u : users) {
			text += u.getUserName() + "\t" + u.getPassword() + "\n";
		}
		
		DAO.File.writeFile(absolutePath, text);
	}
	
	public static boolean authenticateUser(User user) throws Exception {
		
		ArrayList<User> users = loadUsers(absolutePath);
		
		for (User u : users) {
			if (u.authenticate(user)) {
				return true;
			}
		}
		return false;
	}
}
