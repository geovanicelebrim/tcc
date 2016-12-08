package exception;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception{
	
	private String className = "";
	
	public DatabaseConnectionException(String className) {
		this.className = className;
	}
	
	public String toString() {
		return this.className + ": Failed to connect to the database.";
	}
	
	@Override
	public String getMessage() {
		return "Failed to connect to the database.";
	}
}
