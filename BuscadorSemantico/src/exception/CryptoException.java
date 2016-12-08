package exception;

@SuppressWarnings("serial")
public class CryptoException extends Exception {

	public CryptoException(String message, Throwable throwable) {
		super(message, throwable);
	}
}