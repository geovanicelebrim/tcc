package DAO;

import java.io.BufferedReader;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import exception.ErrorFileException;

/**
 * Intermedia as operações de leitura e escrita no disco.
 * 
 * @author Geovani Celebrim
 * 
 */
public class File {
	/**
	 * Lê o arquivo passado por parâmetro e retorna uma String com o seu
	 * conteúdo.
	 * 
	 * @param path
	 *            que é uma String o contendo caminho do arquivo.
	 * @return <b>String</b> contendo as informações internas do arquivo.
	 * @throws ErrorFileException
	 *             informando que não foi possível realizar a leitura do
	 *             arquivo.
	 */
	public static String readFile(String path) throws ErrorFileException {
		String nameFile = path;
		String text = "";
		try {
			FileReader f = new FileReader(nameFile);
			BufferedReader readFile = new BufferedReader(f);

			String line = readFile.readLine();

			while (line != null) {
				text += "\n" + line;
				line = readFile.readLine();
			}

			f.close();
		} catch (Exception e) {
			throw new ErrorFileException();
		}
		return text;
	}

	/**
	 * Lê o arquivo passado por parâmetro, que se localiza em um diretório
	 * prefixado em {@link Authentication} e retorna uma String com o seu
	 * conteúdo.
	 * 
	 * @param path
	 *            que é uma String contendo o nome do arquivo.
	 * @return <b>String</b> contendo as informações internas do arquivo.
	 * @throws ErrorFileException
	 *             informando que não foi possível realizar a leitura do
	 *             arquivo.
	 */
	public static String readPrefixedFile(String path)
			throws ErrorFileException {
		String nameFile = Paths.DATA_TEXT.toString() + path;
		String text = "";
		try {
			FileReader f = new FileReader(nameFile);
			BufferedReader readF = new BufferedReader(f);

			String line = readF.readLine();

			while (line != null) {
				text += "\n" + line;
				line = readF.readLine();
			}

			f.close();
		} catch (Exception e) {
			throw new ErrorFileException();
		}
		return text;

	}

	/**
	 * Escreve no arquivo passado por parâmetro, o texto, também passado por
	 * parâmetro. Caso não seja possível realizar a escrita, é lançada uma
	 * excessão.
	 * 
	 * @param path
	 *            que é o caminho e nome do arquivo.
	 * @param text
	 *            que é o texto a ser escrito no arquivo.
	 * @throws IOException
	 *             caso não seja possível a escrita.
	 */
	public static void writeFile(String path, String text) throws IOException {

		FileWriter f = new FileWriter(path);
		PrintWriter pf = new PrintWriter(f);

		pf.printf(text);
		f.close();
	}

	/**
	 * Lista os arquivos do diretório fixado em {@link Authentication}.
	 * 
	 * @return File[] que é um array de nomes de arquivos contidos no diretório
	 *         fixado.
	 */
	public static java.io.File[] listFiles() {
		java.io.File[] files = new java.io.File(Paths.DATA_TEXT.toString()
				+ Paths.FOLDER.toString()).listFiles(new FileFilter() {

			@Override
			public boolean accept(java.io.File pathname) {
				if (pathname.getName().contains(".txt")) {
					return true;
				}
				return false;
			}
		});

		return files;
	}

}
