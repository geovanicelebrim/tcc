package excessao;

@SuppressWarnings("serial")
public class QueryInvalidaException extends Exception{
	
	public String toString() {
		return "As queryes devem conter um apelido para os retornos.";
	}
	
	@Override
	public String getMessage() {
		return "As queryes devem conter um apelido para os retornos.";
	}
}
