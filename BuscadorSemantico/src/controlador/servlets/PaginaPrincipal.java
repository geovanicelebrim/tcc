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
import entidade.ResultadoCypher;
import entidade.ResultadoDocumento;

@WebServlet("/PaginaPrincipal")
public class PaginaPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaginaPrincipal() {
		super();
	}

	private void processarRequisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		String busca = null;
		
		try {
			busca = java.net.URLDecoder.decode(request.getQueryString().substring(11,
					request.getQueryString().length()), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (busca != null) {
			
			request.setAttribute("valor", busca);
			
			ResultadoCypher resultadoCypher = null;
			ArrayList<ResultadoDocumento> resultadoDocumento = new ArrayList<>();
			try {
				resultadoCypher = BuscaSemantica.buscaCypher(busca);
				resultadoDocumento = BuscaSemantica.buscaDocumento(busca);
			} catch (Exception e) {
				if(!e.getMessage().equals("1")) {
					request.setAttribute("mensagemErro", e.getMessage());
				}
			}
			
			request.setAttribute("resultadoCypher", resultadoCypher);
			request.setAttribute("resultadoDocumento", resultadoDocumento);
			
			irParaResultados(request, response);
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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}
}