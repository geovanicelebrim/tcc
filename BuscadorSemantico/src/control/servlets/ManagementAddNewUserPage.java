package control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.ManagementAddNewUser;
import entity.User;

/**
 * Controlador Servlet responsável pela página de gerenciamento.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/ManagementAddNewUserPage")
public class ManagementAddNewUserPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementAddNewUserPage() {
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

		String action = request.getParameter("action");
		
		if (action.equals("add_user")) {
			String email = request.getParameter("email");
			String password = (String) request.getParameter("password");
			String name = (String) request.getParameter("name");

			User u = null;
			String error;
			try {
				u = new User(email, password, name);
				ManagementAddNewUser.addNewUser(u);
				redirectToManagement(request, response);
			} catch (Exception e) {
				error = e.getMessage();
				request.setAttribute("error", error);
				gotoAddNewUser(request, response);
			}
			return;
		}
		redirectToManagement(request, response);
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
	
	private void gotoAddNewUser(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_add_new_user.jsp");

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