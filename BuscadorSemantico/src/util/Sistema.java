package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sistema {
	public static String comando(String comando) throws IOException {

		String saida = null;
		String retorno = "";

		try {

			Process p = Runtime.getRuntime().exec(comando);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			while ((saida = stdInput.readLine()) != null) {
				retorno += saida + "\n";
			}

			while ((saida = stdError.readLine()) != null) {
				System.out.println(saida);
			}

		} catch (IOException e) {
			throw e;
		}

		return retorno;
	}
}
