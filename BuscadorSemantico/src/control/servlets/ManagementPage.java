package control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
		if (action == null) {
			gotoManagement(request, response);
		}
		else if (action.equals("upload")) {
			String title = request.getParameter("title");
			String author = (String) request.getParameter("author");
			String year = (String) request.getParameter("year");
			String source = (String) request.getParameter("source");

			final String pathTextFile = "/home/geovani/teste/txt";
			final String pathAnnFile = "/home/geovani/teste/ann";
			final Part textFilePart = request.getPart("textFile");
			final Part annFilePart = request.getPart("annFile");

			System.out.println("Titulo: " + title + "\tAutor: " + author + "\tAno: " + year + "\tFonte: " + source);

			try {
				Management.addFile(pathTextFile, textFilePart);
				System.out.println("Texto: Ok");
			} catch (Exception e) {
				System.out.println("Texto: " + e.getMessage());
			}

			try {
				Management.addFile(pathAnnFile, annFilePart);
				System.out.println("Ann: Ok");
			} catch (Exception e) {
				System.out.println("Ann: " + e.getMessage());
			}
		} else if (action.equals("index")) {
			
		} else if (action.equals("import")) {
			
		}
	}
	
	private void gotoManagement(HttpServletRequest request,
			HttpServletResponse response) {

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