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
import control.ManagementAddNewFile;
import entity.User;

/**
 * Controlador Servlet responsável pela página de gerenciamento.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/ManagementAddNewFilePage")
@MultipartConfig
public class ManagementAddNewFilePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementAddNewFilePage() {
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

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			gotoIndex(request, response);
			return;
		}

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
				ManagementAddNewFile.addFile(pathTextFile, textFilePart, "txt");
				ManagementAddNewFile.addFile(pathAnnFile, annFilePart, "ann");
				ManagementAddNewFile.createMetaFile(textFilePart, title, author, year, source);
				
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
				redirectToManagementAddNewFile(request, response);
				return;
				
			} catch (Exception e) {
				String error = e.getMessage();
				request.setAttribute("error", error);
				request.setAttribute("title", title);
				System.out.println("Error: " + e.getMessage());
				gotoManagementAddNewFile(request, response);
				return;
			}

			
		} else if (action.equals("index")) {
			String option = request.getParameter("selectBoxIndexer");
			String schedule = request.getParameter("birthdateIndexer");
			option = option == null ? "" : option;
			schedule = schedule == null ? "" : schedule;

			if (option.equals("execute")) {
				try {
					ManagementAddNewFile.indexerData(OpenMode.CREATE_OR_APPEND);
				} catch (Exception e) {
					System.out.println("Erro execute: " + e.getMessage());
				}
			} else if (option.equals("schedule")) {
				try {
					ManagementAddNewFile.scheduleIndex(schedule);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} else if (action.equals("import")) {
			String option = request.getParameter("selectBoxImport");
			String schedule = request.getParameter("birthdateImport");
			option = option == null ? "" : option;
			schedule = schedule == null ? "" : schedule;

			if (option.equals("execute")) {
				try {
					ManagementAddNewFile.importAnn();
				} catch (Exception e) {
					System.out.println("Erro execute: " + e.getMessage());
				}
			} else if (option.equals("schedule")) {
				try {
					ManagementAddNewFile.scheduleImport(schedule);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			request.getSession().setAttribute("files", null);
			redirectToManagement(request, response);
		}
	}

	private void redirectToManagement(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("target", "ManagementLoginPage?action=authenticate");
		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/redirect.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void redirectToManagementAddNewFile(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("target", "MenuManagement?option=add_file");
		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/redirect.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gotoManagementAddNewFile(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_add_new_file.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gotoIndex(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/index.jsp");

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