package entity;

public class User implements java.io.Serializable {
	private String user;
	private String password;
	
	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	public String getUserName() {
		return this.user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean authenticate(User user) {
		if (this.user.equals(user.user) && this.password.equals(user.password)) {
			return true;
		}
		return false;
	}
}
