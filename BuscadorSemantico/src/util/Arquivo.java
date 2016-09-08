package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import excessao.ErroArquivoException;

public class Arquivo {
	public static String lerArquivo(String caminho) throws ErroArquivoException {
		String nomeArquivo =  caminho;
		String texto = "";
		try {
			FileReader arq = new FileReader(nomeArquivo);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				texto += "\n" + linha;
				linha = lerArq.readLine();
			}

			arq.close();
		} catch (Exception e) {
			throw new ErroArquivoException();
		}
		return texto;
	}
	
	public static void escreverArquivo(String caminho, String texto) throws IOException {

	    FileWriter arq = new FileWriter(caminho);
	    PrintWriter gravarArq = new PrintWriter(arq);
	 
	    gravarArq.printf(texto);
	    arq.close();
	 
	  }
}
