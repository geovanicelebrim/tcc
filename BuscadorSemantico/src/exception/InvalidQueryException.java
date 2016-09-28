package exception;

@SuppressWarnings("serial")
public class InvalidQueryException extends Exception{
	
	private String token = "";
	
	public InvalidQueryException(String token) {
		this.token = token;
	}
	
	public InvalidQueryException() {
		
	}
	
	public String toString() {
		if (token.equals("")){
			return "Invalid query.";
		} else {
			return "Invalid query. There is an error in the token " + token;
		}
	}
	
	@Override
	public String getMessage() {
		if (token.equals("")){
			return "Invalid query.";
		} else {
			return "Invalid query. There is an error in the token " + token;
		}
	}
}
