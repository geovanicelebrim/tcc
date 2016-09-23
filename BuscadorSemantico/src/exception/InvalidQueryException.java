package exception;

@SuppressWarnings("serial")
public class InvalidQueryException extends Exception{
	
	public String toString() {
		return "As queryes devem conter um apelido para os retornos.";
	}
	
	@Override
	public String getMessage() {
		return "As queryes devem conter um apelido para os retornos.";
	}
}
