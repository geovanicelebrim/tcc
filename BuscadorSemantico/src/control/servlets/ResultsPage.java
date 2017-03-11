package control.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Syntactic;
import DAO.File;
import control.SemanticSearch;
import control.SimpleSearch;
import entity.Graph;
import entity.results.CypherResults;
import entity.results.DocumentResult;
import entity.results.SimpleResults;
import exception.DatabaseConnectionException;
import exception.ErrorFileException;
import exception.InvalidQueryException;

/**
 * Controlador Servlet responsável pela página de resultados.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/ResultsPage")
public class ResultsPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResultsPage() {
		super();
	}

	/**
	 * Processa uma requisição vinda da jsp.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 */
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		Boolean accessed = (Boolean) request.getSession().getAttribute("accessed");
		
		if (accessed == null) {
			accessed = new Boolean(true);
			request.getSession().setAttribute("accessed", accessed);
			util.Log.getInstance().addAccess();
		}
		
		String viewDoc = request.getParameter("viewDoc");
		
		if(viewDoc != null) {
			String text = null, beginSlice = null, endSlice = null;
	
			beginSlice = request.getParameter("beginSlice");		
			endSlice = request.getParameter("endSlice");
			
			try {
				text = File.readPrefixedFile(viewDoc);
			} catch (ErrorFileException e) {
				util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), e.toString());
			}
	
			//TODO acrescentar título e autor para a função de citar
			//TODO remover o "replace" do nome futuramente 
			request.setAttribute("title", viewDoc.split(".txt")[0].replace("-", " ").replaceAll("[0-9]*", ""));
			request.setAttribute("text", text);
			
			if(beginSlice != null && endSlice != null) {
				request.setAttribute("beginSlice", Integer.parseInt(beginSlice));
				request.setAttribute("endSlice", Integer.parseInt(endSlice));
			}
	
			gotoDocument(request, response);
			return;
		}

		String query = null;
		String searchMode = null;
		
		query = (String) request.getParameter("search-query");
		searchMode = (String) request.getParameter("search-mode");

		if (searchMode != null) {

			switch (searchMode) {
			case "normal":
				if (query != null) {

					request.setAttribute("query", query.trim());

					ArrayList<SimpleResults> simpleResults = null;
					String suggestion = null;
				
					try {
						if(query.trim().length() == 0) {
							throw new Exception("The query can not be empty.");
						}
						simpleResults = SimpleSearch.simpleSearch(query.trim());
						if (simpleResults.size() == 0) {
							suggestion = SimpleSearch.getSuggestion(query.trim());
						}
					} catch (Exception e) {
						if (!e.getMessage().equals("1")) {
							util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), "Normal Search: " + e);
							request.setAttribute("errorMessage", e.getMessage());
						}
					}
					
					String page = (String) request.getParameter("page");	
					
					request.setAttribute("page", page);
					request.setAttribute("suggestion", suggestion);
					request.setAttribute("simpleResults", simpleResults);

					gotoSimpleResults(request, response);
				}
				break;

			case "semantic":

				if (query != null) {

					request.setAttribute("query", query);

					ArrayList<CypherResults> cypherResults = null;
					ArrayList<DocumentResult> documentResults = new ArrayList<>();
					Graph graph = null;
					
					String newQuery;
					try {
						newQuery = Syntactic
								.translateToCypherQuery(query);
						cypherResults = SemanticSearch
								.cypherSearchBolt(newQuery);
						documentResults = SemanticSearch
								.documentSearch(newQuery);
						graph = SemanticSearch.buscaCypherRest(newQuery);
					} catch (InvalidQueryException | DatabaseConnectionException | ErrorFileException e) {
						if (!e.getMessage().equals("1")) {
							util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), "Semantic Search: " + e);
							request.setAttribute("errorMessage", e.getMessage());
						}
					}
					
					String page = (String) request.getParameter("page");	
					
					request.setAttribute("page", page);
					request.setAttribute("cypherResults", cypherResults);
					request.setAttribute("documentResults", documentResults);
					request.setAttribute("graph", graph);

					gotoSemanticResults(request, response);
				}
				break;
			}
		}
	}

	/**
	 * Redireciona para a página de resultados simples.
	 * 
	 * @param request
	 * @param response
	 */
	private void gotoSimpleResults(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/normal_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), e.toString());
		}
	}

	/**
	 * Redireciona para a pagina de resultados semânticos.
	 * 
	 * @param request
	 * @param response
	 */
	private void gotoSemanticResults(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/semantic_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), e.toString());
		}
	}

	/**
	 * Redireciona para a leitura de um documento específico.
	 * 
	 * @param request
	 * @param response
	 */
	private void gotoDocument(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("public/view_document.jsp");
		
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			util.Log.getInstance().addSystemEntry(request.getRemoteAddr(), e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Trata requisições do tipo GET.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Trata requisições do tipo POST.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}