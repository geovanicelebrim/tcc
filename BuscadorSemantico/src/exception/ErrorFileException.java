package exception;

@SuppressWarnings("serial")
public class ErrorFileException extends Exception{
	
	private String error = "access";
	private String className = "";
	private String message;
	
	public ErrorFileException(String error, String className) {
		this.error = error;
		this.className = className;
	}
	
	public ErrorFileException(String error, String className, String message) {
		this.error = error;
		this.className = className;
		this.message = message;
	}
	
	public String toString() {
		if (message == null)
			return this.className + ": Error trying to " + error + " the file.";
		else
			return this.className + ": " + this.message;
	}
	
	@Override
	public String getMessage() {
		if(message == null)
			return "Error trying to " + error + " the file.";
		else
			return message;
	}
}
