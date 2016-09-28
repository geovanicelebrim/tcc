package exception;

@SuppressWarnings("serial")
public class ErrorFileException extends Exception{
	
	private String error = "access";
	
	public ErrorFileException(String error) {
		this.error = error;
	}
	public String toString() {
		return "Error trying to " + error + " the file.";
	}
	
	@Override
	public String getMessage() {
		return "Error trying to " + error + " the file.";
	}
}
