package controlador.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.BuscaSemantica;
import entidade.Grafo;
import entidade.resultados.ResultadoCypher;
import entidade.resultados.ResultadoDocumento;

@WebServlet("/PaginaPrincipal")
public class PaginaPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaginaPrincipal() {
		super();
	}

	private void processarRequisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		String query = null;
		String tipoBusca = null;
		
		try {
			String queryString = java.net.URLDecoder.decode(request.getQueryString(), "UTF-8");
			
			query = queryString.split("&search-mode=")[0].split("query=")[1];
			
			tipoBusca = queryString.split("&search-mode=")[1];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (tipoBusca != null) {
			
			switch (tipoBusca) {
			case "normal":
				//TODO implementar busca simples
				RequestDispatcher rd = null;
				rd = request.getRequestDispatcher("publica/index.jsp");

				try {
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case "semantic":
				
				
				if (query != null) {
					
					request.setAttribute("query", query);
					
					ArrayList<ResultadoCypher> resultadoCypher = null;
					ArrayList<ResultadoDocumento> resultadoDocumento = new ArrayList<>();
					Grafo grafo = null;
					try {
						resultadoCypher = BuscaSemantica.buscaCypherBolt(query);
						resultadoDocumento = BuscaSemantica.buscaDocumento(query);
						grafo = BuscaSemantica.buscaCypherRest(query);
					} catch (Exception e) {
						if(!e.getMessage().equals("1")) {
							request.setAttribute("error_message", e.getMessage());
						}
					}

					request.setAttribute("resultadoCypher", resultadoCypher);
					request.setAttribute("resultadoDocumento", resultadoDocumento);
					request.setAttribute("grafo", grafo);
					
					irParaResultados(request, response);
				}
				break;
	
			default:
				break;
			}
		}
	}

	private void irParaResultados(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
//		rd = request.getRequestDispatcher("publica/resultados.jsp");
		rd = request.getRequestDispatcher("publica/semantic_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}
}