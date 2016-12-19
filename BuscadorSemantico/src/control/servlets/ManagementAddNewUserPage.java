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
		String ip = request.getRemoteAddr();

		if (user == null) {
			String errorLogin = "Entry your e-mail and password.";
			request.setAttribute("errorLogin", errorLogin);
			gotoIndex(request, response);
			return;
		}

		String action = request.getParameter("action");
		
		if (action.equals("add_user")) {
			util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, ip, user.getEmail(), "Add new user.");
			String email = request.getParameter("email");
			String password = (String) request.getParameter("password");
			String name = (String) request.getParameter("name");

			User u = null;
			String error;
			try {
				u = new User(email, password, name);
				ManagementAddNewUser.addNewUser(u);
				util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, ip, user.getEmail(), "New user added.");
				redirectToManagement(request, response);
			} catch (Exception e) {
				error = e.getMessage();
				request.setAttribute("error", error);
				gotoAddNewUser(request, response);
				util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
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
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, request.getRemoteAddr(), email, e.toString());
		}
	}
	
	private void gotoAddNewUser(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_add_new_user.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, request.getRemoteAddr(), email, e.toString());
		}
	}
		
	private void gotoIndex(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/index.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, request.getRemoteAddr(), email, e.toString());
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