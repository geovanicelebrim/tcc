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
import control.SemanticSearch;
import control.SimpleSearch;
import entity.Graph;
import entity.results.CypherResults;
import entity.results.DocumentResult;
import entity.results.SimpleResults;

/**
 * Controlador Servlet responsável pela página principal.
 * 
 * @author Geovani Celebrim
 * 
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainPage() {
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

		String query = null;
		String searchType = null;

		try {
			String queryString = java.net.URLDecoder.decode(
					request.getQueryString(), "UTF-8");

			query = queryString.split("&search-mode=")[0].split("query=")[1];

			searchType = queryString.split("&search-mode=")[1];

		} catch (Exception e) {
			e.printStackTrace();
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
					try {
						String newQuery = Syntactic.translateToCypherQuery(query);
						cypherResults = SemanticSearch.cypherSearchBolt(newQuery);
						documentResults = SemanticSearch.documentSearch(newQuery);
						graph = SemanticSearch.buscaCypherRest(newQuery);
					} catch (Exception e) {
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

			default:
				break;
			}
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
		// rd = request.getRequestDispatcher("publica/resultados.jsp");
		rd = request.getRequestDispatcher("publica/semantic_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
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
		// rd = request.getRequestDispatcher("publica/resultados.jsp");
		rd = request.getRequestDispatcher("publica/normal_results.jsp");

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