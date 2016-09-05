package DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

import excessao.ErroArquivoException;

public class Documentos {
	private static String caminhoPrefixo = "/home/geovani/cedim-data";
	private static final String pasta = "/CEDIM-II-GUERRA";
//	private String caminhoPrefixo = "/home/geovani/Brat/brat-v1.3_Crunchy_Frog/data/";
	
	public static String lerArquivo(String caminho) throws ErroArquivoException {
		String nomeArquivo = caminhoPrefixo + caminho;
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
	
	public static File[] listarArquivos() {
		File[] arquivos = new File(caminhoPrefixo + pasta).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getName().contains(".txt")) {
					return true;
				}
				return false;
			}
		});
		
		return arquivos;
	}
	
}
