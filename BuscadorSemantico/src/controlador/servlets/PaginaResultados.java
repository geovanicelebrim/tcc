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
import entidade.ResultadoCypher;
import entidade.ResultadoDocumento;

@WebServlet("/PaginaResultados")
public class PaginaResultados extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaginaResultados () {
		super();
	}

	private void processarRequisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		String busca = null;
		String tipoBusca = null;
		
		try {
			tipoBusca = java.net.URLDecoder.decode(request.getQueryString(), "UTF-8").split("=")[0];
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if(tipoBusca.equals("campoBusca")) {
			
			try {
				busca = java.net.URLDecoder.decode(request.getQueryString().substring(11,
						request.getQueryString().length()), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (busca != null) {
				request.setAttribute("valor", busca);
				
				ResultadoCypher resultado = null;
				ArrayList<ResultadoDocumento> resultadoDocumento = new ArrayList<>();
				try {
					resultado = BuscaSemantica.buscaCypher(busca);
					resultadoDocumento = BuscaSemantica.buscaDocumento(busca);
				} catch (Exception e) {
					if(!e.getMessage().equals("1")) {
						request.setAttribute("mensagemErro", e.getMessage());
					}
				}
				
				request.setAttribute("resultadoCypher", resultado);
				request.setAttribute("resultadoDocumento", resultadoDocumento);
				
				irParaResultados(request, response);
			}
			
		} else if (tipoBusca.equals("documento")) {
			String texto = null, nome = null, trecho = null;
			try {
				if(request.getQueryString().contains("&trecho=")){
					nome = java.net.URLDecoder.decode(request.getQueryString().split("&trecho=")[0].substring(10,
						request.getQueryString().split("&trecho=")[0].length()), "UTF-8");
				
				
					trecho = java.net.URLDecoder.decode(request.getQueryString().split("&trecho=")[1].substring(0,
						request.getQueryString().split("&trecho=")[1].length()), "UTF-8");
				} else {
					nome = java.net.URLDecoder.decode(request.getQueryString().substring(10,
							request.getQueryString().length()), "UTF-8");
				}
				
				texto = Documentos.lerArquivo(nome);
				request.setAttribute("nome", nome);
				request.setAttribute("texto", texto);
				request.setAttribute("trecho", trecho);
			} catch (Exception e) {
				System.out.println(e);
				request.setAttribute("mensagemErro", e.getMessage());
			}
			
			irParaDocumento(request, response);
		}		
	}

	private void irParaResultados(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("publica/resultados.jsp");

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