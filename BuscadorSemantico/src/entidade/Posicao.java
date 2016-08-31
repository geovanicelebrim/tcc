package entidade;

public class Posicao {
	int inicio, fim;
	
	public Posicao(int inicio, int fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public Posicao(String inicio, String fim) {
		try {
			this.inicio = Integer.parseInt(inicio);
			this.fim = Integer.parseInt(fim);
		} catch (Exception e) {
			
		}
	}
	
	public int getInicio() {
		return this.inicio;
	}
	
	public int getFim() {
		return this.fim;
	}
	
	@Override
	public String toString() {
		return inicio + ", " + fim;
	}
}
