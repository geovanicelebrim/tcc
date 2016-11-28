package control.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import DAO.Paths;
import control.Management;

/**
 * Controlador Servlet responsável pela página de gerenciamento.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/ManagementPage")
@MultipartConfig
public class ManagementPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementPage() {
		super();
	}

	/**
	 * Processa uma requisição vinda da jsp.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processarRequisicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String action = request.getParameter("action");

		if (action.equals("upload")) {
			String title = request.getParameter("title");
			String author = (String) request.getParameter("author");
			String year = (String) request.getParameter("year");
			String source = (String) request.getParameter("source");

			final String pathTextFile = Paths.REPOSITORY.toString() + "data";
			final String pathAnnFile = Paths.REPOSITORY.toString() + "ann";
			final Part textFilePart = request.getPart("textFile");
			final Part annFilePart = request.getPart("annFile");

			try {
				Management.createMetaFile(textFilePart, title, author, year, source);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			@SuppressWarnings("unchecked")
			ArrayList<String> files = (ArrayList<String>) request.getSession().getAttribute("files");

			if (files != null) {
				if (!files.contains(title)) {
					files.add(title);
				}
			} else {
				files = new ArrayList<>();
				files.add(title);
			}

			request.getSession().setAttribute("files", files);

			try {
				Management.addFile(pathTextFile, textFilePart, "txt");
			} catch (Exception e) {
				//TODO adicionar erro na página
				System.out.println("Texto: " + e.getMessage());
			}

			try {
				Management.addFile(pathAnnFile, annFilePart, "ann");
			} catch (Exception e) {
				//TODO adicionar erro na página
				System.out.println("Ann: " + e.getMessage());
			}

			gotoManagement(request, response);
		} else if (action.equals("index")) {
			String option = request.getParameter("selectBoxIndexer");
			String shedule = request.getParameter("birthdateIndexer");
			option = option == null ? "" : option;
			shedule = shedule == null ? "" : shedule;

			if (option.equals("execute")) {
				try {
					Management.indexerData(OpenMode.CREATE_OR_APPEND);
				} catch (Exception e) {
					System.out.println("Erro execute: " + e.getMessage());
				}
			} else if (option.equals("shedule")) {
				try {
					Management.sheduleIndex(shedule);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} else if (action.equals("import")) {
			String option = request.getParameter("selectBoxImport");
			String shedule = request.getParameter("birthdateImport");
			option = option == null ? "" : option;
			shedule = shedule == null ? "" : shedule;
			
			if (option.equals("execute")) {
				try {
					Management.importAnn();
				} catch (Exception e) {
					System.out.println("Erro execute: " + e.getMessage());
				}
			} else if (option.equals("shedule")) {
				try {
					Management.sheduleImport(shedule);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			request.getSession().invalidate();
			gotoManagement(request, response);
		}
	}

	private void gotoManagement(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Trata requisições do tipo GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processarRequisicao(request, response);

	}

	/**
	 * Trata requisições do tipo POST.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processarRequisicao(request, response);
	}
}