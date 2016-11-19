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

		String query = null;
		String searchType = null;

		try {

			String queryString = java.net.URLDecoder.decode(
					request.getQueryString(), "UTF-8");
			
			if (queryString.split("viewDoc=").length > 1) {
				String text = null, name = null, beginSlice = null, endSlice = null;

				if (request.getQueryString().contains("&beginSlice=")) {
					name = java.net.URLDecoder.decode(request.getQueryString()
							.split("&beginSlice=")[0].substring(8, request
							.getQueryString().split("&beginSlice=")[0].length()),
							"UTF-8");

					beginSlice = java.net.URLDecoder.decode(request.getQueryString()
							.split("&beginSlice=")[1].substring(0, request
							.getQueryString().split("&beginSlice=")[1].split("&endSlice")[0].length()),
							"UTF-8");
					endSlice = java.net.URLDecoder.decode(request.getQueryString()
							.split("&endSlice=")[1].substring(0, request
							.getQueryString().split("&endSlice=")[1].length()),
							"UTF-8");
				} else {
					name = java.net.URLDecoder.decode(request.getQueryString()
							.split("&beginSlice=")[0].substring(8, request
							.getQueryString().split("&beginSlice=")[0].length()),
							"UTF-8");
				}
				
				
				text = File.readPrefixedFile(name);

				//TODO acrescentar título e autor para a função de citar
				//TODO remover o "replace" do nome futuramente 
				request.setAttribute("title", name.split(".txt")[0].replace("-", " ").replaceAll("[0-9]*", ""));
				request.setAttribute("text", text);
				
				if(beginSlice != null && endSlice != null) {
					request.setAttribute("beginSlice", Integer.parseInt(beginSlice));
					request.setAttribute("endSlice", Integer.parseInt(endSlice));
				}

				gotoDocument(request, response);
				return;
			} else {
				throw new ErrorFileException("access");
			}

		} catch (Exception e) {

		}

		try {
			String queryString = java.net.URLDecoder.decode(
					request.getQueryString(), "UTF-8");

			query = queryString.split("&search-mode=")[0].split("query=")[1];

			searchType = queryString.split("&search-mode=")[1];

		} catch (Exception e) {

		}

		if (searchType != null) {

			switch (searchType) {
			case "normal":
				if (query != null) {

					request.setAttribute("query", query);

					ArrayList<SimpleResults> simpleResults = null;

					
						try {
							simpleResults = SimpleSearch.simpleSearch(query);
						} catch (Exception e) {
							if (!e.getMessage().equals("1")) {
								request.setAttribute("errorMessage", e.getMessage());
							}
						}
					
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
								request.setAttribute("errorMessage", e.getMessage());
							}
						}

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
		// rd = request.getRequestDispatcher("public/resultados.jsp");
		rd = request.getRequestDispatcher("public/normal_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		// rd = request.getRequestDispatcher("public/resultados.jsp");
		rd = request.getRequestDispatcher("public/semantic_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		// rd = request.getRequestDispatcher("public/exibirDocumento.jsp");
		// rd = request.getRequestDispatcher("public/building.html");
		rd = request.getRequestDispatcher("public/view_document.jsp");
		
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