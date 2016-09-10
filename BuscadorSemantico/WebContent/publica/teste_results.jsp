<!doctype html>
<%@page import="entidade.resultados.ResultadoCypher"%>
<%@page import="entidade.resultados.ResultadoDocumento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->

<head>

	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Results</title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="apple-touch-icon" href="publica/icons/apple-touch-icon.png">
	<link rel="shortcut icon" href="publica/icons/favicon.ico">
	
	<link rel="stylesheet" href="publica/css/bootstrap.min.css">
	<link rel="stylesheet" href="publica/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="publica/css/main.css">
	
	<script type="text/javascript" src="publica/js/vis/dist/vis.js"></script>
	<link href="publica/js/vis/dist/vis.css" rel="stylesheet" type="text/css" />
	
	<link rel="stylesheet"
		href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
	
	<script type="text/javascript" src="publica/js/results/util.js"></script>
	
	<!--[if lt IE 9]>
	            <script src="publica/js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
	        <![endif]-->
	
	<style>
	.container {
		margin-right: auto;
		margin-left: auto;
		padding-left: 6px;
		padding-right: 6px;
	}
	
	.container:before,.container:after {
		content: " ";
		display: table;
	}
	
	.container:after {
		clear: both;
	}
	
	@media ( min-width : 768px) {
		.container {
			width: 732px;
		}
	}
	
	@media ( min-width : 992px) {
		.container {
			width: 952px;
		}
	}
	
	@media ( min-width : 1200px) {
		.container {
			width: 1300px;
		}
	}
	
	#mynetwork {
		height: 86vh;
		width: 98%;
		border: 1px solid lightgrey;
	}
	
	p {
		max-width: 700px;
	}
	</style>
	
	<script type="text/javascript">
	          window.onload = function() {
	        	  document.activeElement.blur();
	              scroll();
	
	              $(".scroll_to_tab").click(function () {
	              	scroll();
	              });
	          }
	        </script>

	<script type="text/javascript">
	          function draw() {
	
	            // create an array with edges
	            var edges = [{
	              from: 1,
	              to: 3,
	              label: '',
	              font: {align: 'bottom'}
	            }, {
	              from: 2,
	              to: 4
	            }, {
	              from: 3,
	              to: 5
	            }, {
	              from: 4,
	              to: 5
	            }, {
	              from: 6,
	              to: 4
	            }, {
	              from: 7,
	              to: 3
	            }];
	
	            /*
	             * Example for Ionicons
	             */
	            var optionsIO = {
	              interaction: {tooltipDelay: 400},
	              physics: {
	                          maxVelocity: 16,
	                          // solver: 'forceAtlas2Based',
	                          timestep: 0.35,
	                          stabilization: {
	                              enabled:false,
	                              iterations:2000,
	                              updateInterval:25
	                          }
	                        },
	                      
	              groups: {
	                usergroups: {
	                  shape: 'icon',
	                  icon: {
	                    face: 'Ionicons',
	                    code: '\uf47c',
	                    size: 50,
	                    color: '#57169a'
	                  }
	                },
	                users: {
	                  shape: 'icon',
	                  icon: {
	                    face: 'Ionicons',
	                    code: '\uf47e',
	                    size: 50,
	                    color: '#aa00ff'
	                  }
	                },
	                Organisation: {
	                  shape: 'icon',
	                  icon: {
	                    face: 'Ionicons',
	                    code: '\uf276',
	                    size: 50,
	                    color: '#f0a30a'
	                  }
	                }
	              }
	            };
	
	            // create an array with nodes
	            var nodesIO = [{
	              id: 1,
	              title: 'Esse ÃÂ© um menu pop-up que mostra uma mensagem',
	              label: 'Geovani',
	              group: 'users'
	            }, {
	              id: 2,
	              label: 'Leandro',
	              group: 'users'
	            }, {
	              id: 3,
	              label: 'Alunos',
	              group: 'usergroups'
	            }, {
	              id: 4,
	              label: 'Professores',
	              group: 'usergroups'
	            }, {
	              id: 5,
	              label: 'UFRRJ',
	              group: 'Organisation'
	            }, {
	              id: 6,
	              label: 'Luis',
	              group: 'users'
	            }, {
	              id: 7,
	              label: 'Ricardo',
	              group: 'users'
	            }];
	
	
	            // create a network
	           var containerIO = document.getElementById('mynetwork');
	           var dataIO = {
	              nodes: nodesIO,
	              edges: edges
	            };
	
	           var networkIO = new vis.Network(containerIO, dataIO, optionsIO);
	          }
	        </script>

</head>
<body style="padding-top: 40px;">
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	
	
	<%
		String error_message = (String) request.getAttribute("error_message");
		if (error_message != null) {
			out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
			out.println("alert(\""
					+ error_message.replace("\"", "\\\"").replace("\n",
							"\\n") + "\");");
			out.println("</script>");
		}

	%>
	
	
	<div class="container">
		<div class="row">
			<div
				class="col-xs-8 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2">

				<form action="PaginaResultados?action=buscar" method="get">
					<div class="form-group text-center">
						<img class="img-responsive center-block" src="publica/images/cedim.jpg"
							for="search-query" style="width: 40%; height: 40%;">

						<div class="input-group">
							<input type="text" class="form-control input-lg"
								id="search-query" name="search-query"
								placeholder="Type your query"
								<%	
									String query = (String) request.getAttribute("query");
									out.println("value=\"" + query.replace("\"", "&quot;") + "\"");
								%>>

							<div class="input-group-btn">
								<select name="search-mode" class="form-control input-lg"
									id="search-mode">
									<option value="normal" selected>Normal search</option>
									<option value="semantic">Semantic search</option>
								</select>
							</div>

						</div>
					</div>

					<div class="form-group text-center">
						<button type="submit" class="btn btn-primary btn-lg">Search</button>
					</div>

				</form>

			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a
						href="#simple_results" aria-controls="simple_results" role="tab"
						data-toggle="tab"
						onclick="setTimeout(hide_graph, 1000); scroll();"
						class="scroll_to_tab">Simple Results</a></li>
					<li role="presentation"><a href="#db_results"
						aria-controls="db_results" role="tab" data-toggle="tab"
						onclick="setTimeout(hide_graph, 1000); scroll();"
						class="scroll_to_tab">Data Base Results</a></li>
					<li role="presentation"><a href="#graph_results"
						aria-controls="graph_results" role="tab" data-toggle="tab"
						onclick="show_graph(); scroll(); draw();" class="scroll_to_tab">Graph
							Results</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active"
						id="simple_results">

						<div class="list-group">

							<%
								@SuppressWarnings("unchecked")
								ArrayList<ResultadoDocumento> resultadoDocumento = (ArrayList<ResultadoDocumento>) request
								.getAttribute("resultadoDocumento");

								if (resultadoDocumento != null) {
									for (int i = 0; i < resultadoDocumento.size(); i++) {
							%>
										<form id="<%out.print(i);%>"
											action="PaginaResultados?action=<% out.println(resultadoDocumento.get(i).getNomeDocumento());%>"
											method="get">
											<input type="hidden" name="documento"
												value="<%out.print(resultadoDocumento.get(i).getNomeDocumento());%>" />
											<input type="hidden" name="trecho"
												value="<%out.print(resultadoDocumento.get(i).getTrecho()
												.replace("\"", "&quot;"));%>" />
											<div class="panel panel-default list-group-item">
												<div class="panel-body">
													<div class="media">
														<div class="media-left">
															<a href="javascript:{}"
																onclick="document.getElementById('<%out.print(i);%>').submit(); return false;"> <img class="media-object img-rounded"
																src="publica/images/docs/A-aventura-dos-pracinhas-brasileiros-na-Segunda-Guerra-Mundial.png" alt="..." width="90" height="120" >
															</a>
														</div>
														<div class="media-body">
															<h4 class="media-heading">
																<a href="javascript:{}"
																	onclick="document.getElementById('<%out.print(i);%>').submit(); return false;">
																	<%
																		out.println(resultadoDocumento.get(i).getNomeDocumento()
																			.split("/")[2]);
																	%>
																</a>
															</h4>
															
															<label class="reference">
																<%
																	out.print("(" + resultadoDocumento.get(i).getAutor()
																			+ ", " + resultadoDocumento.get(i).getFonte() + ")");
																%>
															</label>
															<div id="div<%out.print(i);%>">
																<%
																	out.println(resultadoDocumento.get(i).getTrecho() + " [...]");
																	out.println("<br><br>");
																%>
															</div>
														</div>
													</div>
												</div>
											</div>
										</form>
										<br>
								<% 	}
								} %>
						</div>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="db_results">

						<table class="table table-bordered table-hover">
						    <thead align="center">
						      <tr>
						           <th class="text-center">Entidade 1 (E1)</th>
						           <th class="text-center">Entidade 2 (E2)</th>
						           <th class="text-center">Citações (E1)</th>
						           <th class="text-center">Citações (E2)</th>
						           <th class="text-center">Relações (E1)</th>
						           <th class="text-center">Relações (E2)</th>
						           <th class="text-center">Documento</th>
						       </tr>
						    </thead>
						    <tbody>
						    <%
						    	@SuppressWarnings("unchecked")
								ArrayList<ResultadoCypher> resultadoCypher = ((ArrayList<ResultadoCypher>) request
									.getAttribute("resultadoCypher"));
							
								if (resultadoCypher != null) {
									for(int i = 0; i < resultadoCypher.size(); i++) {
										out.println("<tr>");
										
										out.println("<td> " + resultadoCypher.get(i).getEntidade1() + " </td>");
										out.println("<td> " + resultadoCypher.get(i).getEntidade2() + " </td>");
										out.println("<td class=\"text-center\" > " + resultadoCypher.get(i).getCitacoesEntidade1() + " </td>");
										out.println("<td class=\"text-center\" > " + resultadoCypher.get(i).getCitacoesEntidade2() + " </td>");
										out.println("<td class=\"text-center\" > " + resultadoCypher.get(i).getRelacoesEntidade1() + " </td>");
										out.println("<td class=\"text-center\" > " + resultadoCypher.get(i).getRelacoesEntidade2() + " </td>");
										out.println("<td> " + resultadoCypher.get(i).getNomeDocumento() + " </td>");
										
										out.println("</tr>");
									}
								}
							%>						       

						    </tbody>
						</table>
												

						<br> <br> <br> <br> <br> <br> <br>
						<br> <br> <br> <br> <br> <br> <br>
						<br> <br> <br> <br> <br> <br> <br>
						<br> <br> <br> <br> <br> <br> <br>
						<br> <br> <br> <br> <br> <br> <br>
						<br> <br> <br> <br> <br>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="graph_results">

					</div>

				</div>

			</div>
		</div>
	</div>

	<div id="graph" style="display: none">
		<div class="container" id="mynetwork"></div>
		<br>
	</div>

	<script src="publica/js/vendor/jquery-1.11.2.min.js"></script>
	<script src="publica/js/vendor/bootstrap.min.js"></script>
	<script src="publica/js/main.js"></script>
</body>
</html>
