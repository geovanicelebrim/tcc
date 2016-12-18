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
		User ur = (User) request.getAttribute("user");
		String  email = ur == null ? "" : ur.getEmail();
		
		if(action.equals("logout")) {
			util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, request.getRemoteAddr(), email, "Logout");
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
		
		String ip = request.getRemoteAddr();
		util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, ip, userName, "Login Attempt.");
		
		User user = null;
		
		try {
			user = new User(userName, password);
			User anthenticated = Login.authenticateUser(user);
			if(anthenticated != null) {
				util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, ip, userName, "User authenticated.");
				request.getSession().setAttribute("user", anthenticated);
				redirectToManagement(request, response);
			} else {
				util.Log.getInstance().addManagementEntry(util.Log.ACTIVITY_TYPE, ip, userName, "User not authenticated.");
				String errorLogin = "E-mail or password incorrect.";
				request.setAttribute("errorLogin", errorLogin);
				request.setAttribute("email", user.getEmail());
				gotoIndex(request, response);
			}
		} catch (Exception e) {
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
			String errorLogin = "Entry your e-mail and password.";
			request.setAttribute("errorLogin", errorLogin);
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
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, request.getRemoteAddr(), email, e.toString());
		}
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