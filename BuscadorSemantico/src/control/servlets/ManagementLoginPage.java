package control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.Login;
import entity.User;

/**
 * Controlador Servlet responsável pela página principal.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/ManagementLoginPage")
public class ManagementLoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementLoginPage() {
		super();
	}

	/**
	 * Processa uma requisição vinda da jsp.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 */
	private void processarRequisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String action = (String) request.getParameter("action");
		action = action == null ? "" : action;
		
		if(action.equals("logout")) {
			request.getSession().invalidate();
			gotoIndex(request, response);
			return;
		}
		
		User userAuthenticated = (User) request.getSession().getAttribute("user");
		
		if(userAuthenticated != null) {
			gotoManagement(request, response);
			return;
		}
		
		String userName = (String) request.getParameter("email");
		String password = (String) request.getParameter("password");
		userName = userName == null ? "" : userName;
		password = password == null ? "" : password;
		
		User user = null;
		
		try {
			user = new User(userName, password);
			User anthenticated = Login.authenticateUser(user);
			if(anthenticated != null) {
				request.getSession().setAttribute("user", anthenticated);
				gotoManagement(request, response);
			} else {
				String errorLogin = "Could not perform authentication.";
				request.setAttribute("errorLogin", errorLogin);
				request.setAttribute("email", user.getEmail());
				gotoIndex(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			gotoIndex(request, response);
		}
		
	}
	
	private void gotoManagement(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/menu_management.jsp");

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);

	}

	/**
	 * Trata requisições do tipo POST.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}
}