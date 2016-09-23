package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Realiza chamadas do Sistema.
 * 
 * @author Geovani Celebrim
 */
public class Sys {
	/**
	 * Envia um comando para o terminal do sistema e pega sua saída.
	 * 
	 * @param command
	 *            String que contém o comando a ser executado.
	 * @return <b>String</b> contendo a saída do comando.
	 * @throws IOException
	 *             caso ocorra uma falha ao enviar o comando para o sistema.
	 */
	public static String command(String command) throws IOException {

		String out = null;
		String returned = "";

		try {

			Process p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			while ((out = stdInput.readLine()) != null) {
				returned += out + "\n";
			}

			while ((out = stdError.readLine()) != null) {
				System.out.println(out);
			}

		} catch (IOException e) {
			throw e;
		}

		return returned;
	}
}
