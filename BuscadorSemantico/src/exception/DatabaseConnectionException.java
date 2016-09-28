package exception;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception{
	
	public String toString() {
		return "Failed to connect to the database.";
	}
	
	@Override
	public String getMessage() {
		return "Failed to connect to the database.";
	}
}
