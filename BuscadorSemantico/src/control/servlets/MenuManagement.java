package control.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import management.RunTasks;
import management.entity.Task;

/**
 * Controlador Servlet responsável pela página de gerenciamento.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/MenuManagement")
public class MenuManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MenuManagement() {
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
		
		if(user == null) {
			gotoIndex(request, response);
			return;
		}
		
		String option = request.getParameter("option");
		option = option == null ? "" : option;
		
		switch (option) {
		case "add_file":
			gotoManagementAddNewFile(request, response);
			break;
		case "add_user":
			gotoManagementAddNewUser(request, response);
			break;
		case "view_scheduled_task":
			ArrayList<Task> tasks = RunTasks.getInstance().getScheduledTask();
			request.setAttribute("tasks", tasks);
			gotoManagementViewScheduledTask(request, response);
			break;
		case "status":
			ArrayList<String> logManagement = util.Log.getInstance().getLogManagement();
			ArrayList<String> logSystem = util.Log.getInstance().getLogSystem();
			
			request.setAttribute("systemBoot", util.Log.getInstance().getSystemBoot());
			request.setAttribute("index", util.Log.getInstance().getIndex());
			request.setAttribute("dictionary", util.Log.getInstance().getDictionary());
			request.setAttribute("database", util.Log.getInstance().getDatabase());
			request.setAttribute("keyWordSearch", util.Log.getInstance().getKeyWordEngine());
			request.setAttribute("semanticSearch", util.Log.getInstance().getSemanticEngine());
			
			request.setAttribute("accessList", util.Log.getInstance().getAccessList());
			
			request.setAttribute("logManagement", logManagement);
			request.setAttribute("logSystem", logSystem);
			gotoManagementStatus(request, response);
			break;
		default:
			gotoManagement(request, response);
			break;
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

	private void gotoManagement(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/menu_management.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			String ip = (String) request.getParameter("ip");
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
		}
	}

	private void gotoManagementAddNewUser(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_add_new_user.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			String ip = (String) request.getParameter("ip");
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
		}
	}
	
	private void gotoManagementAddNewFile(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_add_new_file.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			String ip = (String) request.getParameter("ip");
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
		}
	}
	
	private void gotoManagementViewScheduledTask(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_view_scheduled_task.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			String ip = (String) request.getParameter("ip");
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
		}
	}
	
	private void gotoManagementStatus(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = null;

		rd = request.getRequestDispatcher("public/management_status.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			String ip = (String) request.getParameter("ip");
			User ur = (User) request.getAttribute("user");
			String email = ur == null ? "" : ur.getEmail();
			util.Log.getInstance().addManagementEntry(util.Log.ERROR_TYPE, ip, email, e.toString());
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