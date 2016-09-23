package entity;

/**
 * Representa um trecho de um texto, com uma posição de inicio e outra de fim.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Position {
	int begin, end;

	public Position(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public Position(String begin, String end) {
		try {
			this.begin = Integer.parseInt(begin);
			this.end = Integer.parseInt(end);
		} catch (Exception e) {

		}
	}

	/**
	 * Obtém o inicio do trecho.
	 * 
	 * @return {@link String}.
	 */
	public int getBegin() {
		return this.begin;
	}

	/**
	 * Obtém o fim do trecho.
	 * 
	 * @return {@link String}.
	 */
	public int getEnd() {
		return this.end;
	}

	@Override
	public String toString() {
		return begin + ", " + end;
	}
}
