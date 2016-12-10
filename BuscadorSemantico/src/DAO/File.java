package DAO;

import java.io.BufferedReader;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
				text += line + "\n";
				line = readFile.readLine();
			}

			f.close();
			readFile.close();
		} catch (Exception e) {
			throw new ErrorFileException("open", File.class.getName());
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
	public static String readPrefixedFile(String path) throws ErrorFileException {
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
			readF.close();
		} catch (Exception e) {
			throw new ErrorFileException("access", File.class.getName());
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
	 * @throws ErrorFileException
	 *             caso não seja possível a escrita.
	 */
	public static void writeFile(String path, String text, boolean append) throws ErrorFileException {

		FileWriter f;
		try {
			f = new FileWriter(path, append);
			PrintWriter pf = new PrintWriter(f);
			pf.printf(text);
			f.close();
			pf.close();
		} catch (IOException e) {
			throw new ErrorFileException("write", File.class.getName());
		}

	}

	public static void writeFile(String path, String text) throws ErrorFileException {
		writeFile(path, text, false);
	}

	/**
	 * Lista os arquivos do diretório fixado em {@link Authentication}.
	 * 
	 * @return File[] que é um array de nomes de arquivos contidos no diretório
	 *         fixado.
	 */
	public static java.io.File[] listFiles() {
		java.io.File[] files = new java.io.File(Paths.DATA_TEXT.toString()).listFiles(new FileFilter() {

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

	public static ArrayList<String> readLinesFile(String path) throws IOException {
		String nameFile = path;
		ArrayList<String> lines = new ArrayList<>();

		FileReader f = new FileReader(nameFile);
		BufferedReader readF = new BufferedReader(f);

		String line = readF.readLine();

		while (line != null) {
			lines.add(line);
			line = readF.readLine();
		}
		
		f.close();
		readF.close();

		return lines;
	}

	public static java.io.File[] listFilesOfType(String path, final String type) {
		java.io.File[] files = new java.io.File(path).listFiles(new FileFilter() {

			@Override
			public boolean accept(java.io.File pathname) {
				if (pathname.getName().contains(type)) {
					return true;
				}
				return false;
			}
		});

		return files;
	}

	public static boolean existFile(String absolutePath) {
		Path path = FileSystems.getDefault().getPath(absolutePath);

		return Files.exists(path) ? true : false;
	}

}
