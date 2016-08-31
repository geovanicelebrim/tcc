<!DOCTYPE html>
<%@page import="entidade.ResultadoCypher"%>
<%@page import="entidade.ResultadoDocumento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Resultados - CEDIM</title>


<link rel="stylesheet" href="./resources/css/normalize.css">


<style>
/* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
@import url(http://fonts.googleapis.com/css?family=Open+Sans);

.btn {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	padding: 4px 10px 4px;
	margin-bottom: 0;
	font-size: 13px;
	line-height: 18px;
	color: #333333;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	vertical-align: middle;
	background-color: #f5f5f5;
	background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff),
		to(#e6e6e6));
	background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: linear-gradient(top, #ffffff, #e6e6e6);
	background-repeat: repeat-x;
	filter: progid:dximagetransform.microsoft.gradient(startColorstr=#ffffff,
		endColorstr=#e6e6e6, GradientType=0);
	border-color: #e6e6e6 #e6e6e6 #e6e6e6;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	border: 1px solid #e6e6e6;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	cursor: pointer;
	*margin-left: .3em;
}

.btn:hover,.btn:active,.btn.active,.btn.disabled,.btn[disabled] {
	background-color: #e6e6e6;
}

.btn-large {
	padding: 9px 14px;
	font-size: 15px;
	line-height: normal;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
}

.reference {
	font-style: italic;
}

.btn:hover {
	color: #333333;
	text-decoration: none;
	background-color: #e6e6e6;
	background-position: 0 -15px;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-ms-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}

.btn-primary,.btn-primary:hover {
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	color: #ffffff;
}

.btn-primary.active {
	color: rgba(255, 255, 255, 0.75);
}

.btn-primary {
	background-color: #4a77d4;
	background-image: -moz-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -ms-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6eb6de),
		to(#4a77d4));
	background-image: -webkit-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -o-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: linear-gradient(top, #6eb6de, #4a77d4);
	background-repeat: repeat-x;
	filter: progid:dximagetransform.microsoft.gradient(startColorstr=#6eb6de,
		endColorstr=#4a77d4, GradientType=0);
	border: 1px solid #3762bc;
	text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.5);
}

.btn-primary:hover,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled]
	{
	filter: none;
	background-color: #4a77d4;
}

.btn-block {
	width: 100%;
	display: block;
}

* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	-o-box-sizing: border-box;
	box-sizing: border-box;
}

html {
	width: 100%;
	height: 100%;
	overflow: hidden;
	overflow-y: scroll;
}

body {
	width: 100%;
	height: 100%;
	font-family: 'Open Sans', sans-serif;
	background: #092756;
	background: -moz-radial-gradient(0% 100%, ellipse cover, rgba(104, 128, 138, .4)
		10%, rgba(138, 114, 76, 0) 40%),
		-moz-linear-gradient(top, rgba(57, 173, 219, .25) 0%,
		rgba(42, 60, 87, .4) 100%),
		-moz-linear-gradient(-45deg, #670d10 0%, #092756 100%);
	background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104, 128, 138, .4)
		10%, rgba(138, 114, 76, 0) 40%),
		-webkit-linear-gradient(top, rgba(57, 173, 219, .25) 0%,
		rgba(42, 60, 87, .4) 100%),
		-webkit-linear-gradient(-45deg, #670d10 0%, #092756 100%);
	background: -o-radial-gradient(0% 100%, ellipse cover, rgba(104, 128, 138, .4)
		10%, rgba(138, 114, 76, 0) 40%),
		-o-linear-gradient(top, rgba(57, 173, 219, .25) 0%,
		rgba(42, 60, 87, .4) 100%),
		-o-linear-gradient(-45deg, #670d10 0%, #092756 100%);
	background: -ms-radial-gradient(0% 100%, ellipse cover, rgba(104, 128, 138, .4)
		10%, rgba(138, 114, 76, 0) 40%),
		-ms-linear-gradient(top, rgba(57, 173, 219, .25) 0%,
		rgba(42, 60, 87, .4) 100%),
		-ms-linear-gradient(-45deg, #670d10 0%, #092756 100%);
	background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104, 128, 138, .4)
		10%, rgba(138, 114, 76, 0) 40%),
		linear-gradient(to bottom, rgba(57, 173, 219, .25) 0%,
		rgba(42, 60, 87, .4) 100%),
		linear-gradient(135deg, #670d10 0%, #092756 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#3E1D6D',
		endColorstr='#092756', GradientType=1);
	background-size: 100% 140px;
	background-repeat: repeat-x;
}

.search {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -120px 0 0 -300px;
	width: 600px;
	height: 300px;
}

.result {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -340px 0 0 -640px;
	width: 600px;
	height: 300px;
}

.resultados {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -640px;
	width: 90%;
	height: 300px;
}

.tempo {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -200px 0 0 -640px;
	width: 90%;
	height: 200px;
	font-style: italic;
	font-size: 15px;
}

.search h1 {
	color: #fff;
	text-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
	letter-spacing: 1px;
	text-align: center;
}

.result h1 {
	color: #fff;
	text-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
	letter-spacing: 1px;
	text-align: left;
}

.searchButton {
	position: absolute;
	margin: -49px 0 0 615px;
	width: 100px;
	height: 40px;
}

input {
	width: 100%;
	margin-bottom: 10px;
	background: rgba(0, 0, 0, 0.3);
	border: none;
	outline: none;
	padding: 10px;
	font-size: 13px;
	color: #fff;
	text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
	border: 1px solid rgba(0, 0, 0, 0.3);
	border-radius: 4px;
	box-shadow: inset 0 -5px 45px rgba(100, 100, 100, 0.2), 0 1px 1px
		rgba(255, 255, 255, 0.2);
	-webkit-transition: box-shadow .5s ease;
	-moz-transition: box-shadow .5s ease;
	-o-transition: box-shadow .5s ease;
	-ms-transition: box-shadow .5s ease;
	transition: box-shadow .5s ease;
}

input:focus {
	box-shadow: inset 0 -5px 45px rgba(100, 100, 100, 0.4), 0 1px 1px
		rgba(255, 255, 255, 0.2);
}

table {
	border: none;
	border-collapse: collapse;
}

table td {
	border-left: 1px solid #000;
	border-color: #f2f2f2;
	vertical-align: top;
	padding: 5px 10px;
}

table td:first-child {
	border-left: none;
}
</style>

</head>

<body>

	<div class="result">
		<h1>Resultados:</h1>
		<form action="PaginaResultados?action=buscar" method="get">
			<input type="text" name="campoBusca" placeholder="Digite sua busca"
				<%String valor = (String) request.getAttribute("valor");
			out.println("value=\"" + valor.replace("\"", "&quot;") + "\"");%>
				required />
			<button type="submit"
				class="btn btn-primary btn-block btn-large searchButton">Buscar</button>
		</form>

	</div>
	<div class="tempo">
		<%
			String mensagemErro = (String) request.getAttribute("mensagemErro");
			if (mensagemErro != null) {
				out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
				out.println("alert(\""
						+ mensagemErro.replace("\"", "\\\"").replace("\n",
								"\\n") + "\");");
				out.println("</script>");
			}

			ResultadoCypher resultadoCypher = ((ResultadoCypher) request
					.getAttribute("resultadoCypher"));
			if (resultadoCypher != null) {
				out.println("Tempo gasto para realizar a busca: "
						+ resultadoCypher.getTempo() + " ms.");
			}
		%>
	</div>
	<div class="resultados">
		<table style="width: 100%">
			<tr>
				<th>Resultado Cypher</th>
				<th>Resultado dos Documentos</th>
			</tr>
			<tr>
				<td width="50%">
					<%
						if (resultadoCypher != null) {
							out.println(resultadoCypher.toString()
									.replace("\n", "<br><br>"));
						}
					%>
				</td>
				<td width="50%">
					<%
						@SuppressWarnings("unchecked")
						ArrayList<ResultadoDocumento> resultadoDocumento = (ArrayList<ResultadoDocumento>) request
								.getAttribute("resultadoDocumento");

						if (resultadoDocumento != null) {
							for (int i = 0; i < resultadoDocumento.size(); i++) {
					%>
					<form id="<%out.print(i);%>"
						action="PaginaResultados?action=<%out.println(resultadoDocumento.get(i).getNomeDocumento());%>"
						method="get">
						<input type="hidden" name="documento"
							value="<%out.print(resultadoDocumento.get(i).getNomeDocumento());%>" />

						<input type="hidden" name="trecho"
							value="<%out.print(resultadoDocumento.get(i).getTrecho()
							.replace("\"", "&quot;"));%>" />
						<a href="javascript:{}"
							onclick="document.getElementById('<%out.print(i);%>').submit(); return false;">
							<%
								out.println(resultadoDocumento.get(i).getNomeDocumento()
												.split("/")[2]);
							%>
						 
						</a>
					</form>
					
					<label class="reference">
						<%
							out.print("(" + resultadoDocumento.get(i).getAutor()
										+ ", " + resultadoDocumento.get(i).getFonte() + ")");
						%>
					</label>
					<div id="div<%out.print(i);%>">
						<%
							out.println("[...] "
											+ resultadoDocumento.get(i).getTrecho() + " [...]");
									out.println("<br><br>");
						%>
					</div> <%
 	}
 	}
 %>
				</td>
			</tr>
		</table>

	</div>

</body>
</html>
