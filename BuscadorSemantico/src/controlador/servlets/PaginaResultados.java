package controlador.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Documentos;
import controlador.BuscaSemantica;
import controlador.BuscaSimples;
import entidade.Grafo;
import entidade.resultados.ResultadoCypher;
import entidade.resultados.ResultadoDocumento;
import entidade.resultados.ResultadoSimples;

@WebServlet("/PaginaResultados")
public class PaginaResultados extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaginaResultados () {
		super();
	}

	private void processarRequisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		String query = null;
		String tipoBusca = null;
		
		try {
			
			String queryString = java.net.URLDecoder.decode(request.getQueryString(), "UTF-8");

			if(queryString.split("viewDoc=").length > 1) {
				String texto = null, nome = null, trecho = null;
				try {
					if(request.getQueryString().contains("&trecho=")){
						nome = java.net.URLDecoder.decode(request.getQueryString().split("&trecho=")[0].substring(8,
							request.getQueryString().split("&trecho=")[0].length()), "UTF-8");
					
					
						trecho = java.net.URLDecoder.decode(request.getQueryString().split("&trecho=")[1].substring(0,
							request.getQueryString().split("&trecho=")[1].length()), "UTF-8");
					} else {
						nome = java.net.URLDecoder.decode(request.getQueryString().substring(8,
								request.getQueryString().length()), "UTF-8");
					}
					
					texto = Documentos.lerArquivo(nome);
					request.setAttribute("nome", nome);
					request.setAttribute("texto", texto);
					request.setAttribute("trecho", trecho);
				} catch (Exception e) {
					request.setAttribute("mensagemErro", e.getMessage());
				}
				
				irParaDocumento(request, response);
				return;
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			
		}
		
		try {
			String queryString = java.net.URLDecoder.decode(request.getQueryString(), "UTF-8");
			
			query = queryString.split("&search-mode=")[0].split("query=")[1];
			
			tipoBusca = queryString.split("&search-mode=")[1];
			
		} catch (Exception e) {
			
		}
		
		if(tipoBusca != null) {

			switch (tipoBusca) {
				case "normal":
					if (query != null) {
						
						request.setAttribute("query", query);
						
						ArrayList<ResultadoSimples> resultadoSimples = null;
						
						try {
							resultadoSimples = BuscaSimples.buscaSimples(query);
						
						} catch (Exception e) {
						
							if(!e.getMessage().equals("1")) {
								request.setAttribute("error_message", e.getMessage());
							}
						}
						request.setAttribute("resultadoSimples", resultadoSimples);
						
						irParaResultadosSimples(request, response);
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
						
						irParaResultadosSemanticos(request, response);
					}
					break;
			}
			
		}
	}
	
	private void irParaResultadosSimples(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
//		rd = request.getRequestDispatcher("publica/resultados.jsp");
		rd = request.getRequestDispatcher("publica/normal_results.jsp");

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void irParaResultadosSemanticos(HttpServletRequest request,
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
	
	private void irParaDocumento(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("publica/exibirDocumento.jsp");

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