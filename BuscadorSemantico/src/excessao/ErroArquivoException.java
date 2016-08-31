package excessao;

@SuppressWarnings("serial")
public class ErroArquivoException extends Exception{
	
	public String toString() {
		return "Houve um erro na abertura do arquivo.";
	}
	
	@Override
	public String getMessage() {
		return "Houve um erro na abertura do arquivo.";
	}
}
