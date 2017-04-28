<!doctype html>
<%@page import="entity.Graph"%>
<%@page import="entity.results.CypherResults"%>
<%@page import="entity.results.DocumentResult"%>
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
	
	<link rel="shortcut icon" href="./public/icons/icon.png">
	
	<link rel="stylesheet" href="./public/css/bootstrap.min.css">
	<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="./public/css/main.css">
	<link rel="stylesheet" href="./public/css/results.css">
	<link rel="stylesheet" href="./public/css/jquery-ui.css">
	<link rel="stylesheet" href="./public/js/vis/dist/vis.css">
	<link rel="stylesheet" href="./public/css/dataTables.bootstrap.min.css">
	<link rel="stylesheet" href="./public/css/ionicons-2.0.1/css/ionicons.min.css">
	
	<script src="./public/js/jquery/jquery-1.12.4.js"></script>
	<script src="./public/js/jquery/jquery-ui.js"></script>
	<script src="./public/js/search/autocomplete.js"></script>
	<script src="./public/js/results/util.js"></script>
	<script src="./public/js/results/graph.js"></script>
	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/vis/dist/vis.js"></script>	
	<script src="./public/js/jquery/jquery.dataTables.min.js"></script>	
	<script src="./public/js/jquery/dataTables.bootstrap.min.js"></script>
		
	<script type="text/javascript">
		window.onload = function() {
			document.activeElement.blur();
			scroll();
			$(".scroll_to_tab").click(function () {
				scroll();
			});
		}

		<%Graph graph = (Graph) request.getAttribute("graph");%>
		var edges = [{
			<%
				if(graph != null) {
					for (int i = 0; i < graph.getEdges().size(); i++) {
						out.println("from: " + graph.getEdges().get(i).getFrom() + ",");
						out.println("to: " + graph.getEdges().get(i).getTo());
						
						if( i + 1 != graph.getEdges().size()) {
							out.println("}, {");
						}
					}
				}
			%>
		}];
		
		var nodesIO = [{
			<%
				if( graph != null) {
					for (int i = 0; i < graph.getVertices().size(); i++) {
						out.println("id: " + graph.getVertices().get(i).getID() + ",");
						out.println("label: '" + graph.getVertices().get(i).getSlice() + "',");
						out.println("group: '" + graph.getVertices().get(i).getLabel() + "',");
						
						if ( i + 1 != graph.getVertices().size()) {
							out.println("}, {");
						}
					}
				}
			%>
		}];
		
		function draw() {
			<%
				if(graph != null && graph.getVertices().size() > 0) {
			%>
					drawGraph(edges, nodesIO);
			<%
				}
			%>
			
		}
		
	</script>

</head>
<body style="padding-top: 40px;">
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->
	<%
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
			out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
			out.println("alert(\""
				+ errorMessage.replace("\"", "\\\"").replace("\n", "\\n") + "\");");
			out.println("</script>");
		}
	%>
	
	<div class="container">
		<div class="row">
			<div
				class="col-xs-8 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2">

				<form action="ResultsPage?action=buscar" method="get">
					<div class="form-group text-center">
						<img class="img-responsive center-block" src="public/images/cedim.jpg" id="logo"
							style="width: 40%; height: 40%;">
						<script type="text/javascript">
							document.getElementById('logo').ondragstart = function() { return false; };
						</script>
						<div class="input-group">
							<input type="text" class="form-control input-lg" id="search-query" name="search-query"
								placeholder="Type your query" required autocomplete="off"
								<%String query = (String) request.getAttribute("query");
									if (query != null)
										out.println("value=\"" + query.replace("\"", "&quot;") + "\"");%>>

							<div class="input-group-btn">
								<select name="search-mode" class="form-control input-lg"
									id="search-mode" onclick="$('#search-query').select();">
									<option value="normal">Normal search</option>
									<option value="semantic" selected>Semantic search</option>
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
						onclick="setTimeout(hide_graph, 300); scroll();"
						class="scroll_to_tab">Simple Results</a></li>
					<li role="presentation"><a href="#db_results"
						aria-controls="db_results" role="tab" data-toggle="tab"
						onclick="setTimeout(hide_graph, 300); scroll();"
						class="scroll_to_tab">Data Base Results</a></li>
					<li role="presentation"><a href="#graph_results"
						aria-controls="graph_results" role="tab" data-toggle="tab"
						onclick="setTimeout(show_graph, 150); scroll(); setTimeout(draw, 200);" class="scroll_to_tab">Graph Results</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active"
						id="simple_results">

						<div class="list-group">

							<%
								@SuppressWarnings("unchecked")
								ArrayList<DocumentResult> documentResults = (ArrayList<DocumentResult>) request.getAttribute("documentResults");
								if (documentResults == null) { documentResults = new ArrayList<>(); }
								
								final Integer resultsPerPage = 10;
								String cp = (String) request.getAttribute("page");
								Integer currentPage;
								try {
									currentPage = Integer.parseInt(cp);
								} catch (Exception e) {
									currentPage = 0;
								}

								if (documentResults != null) {
									
									int begin = resultsPerPage * currentPage;
									int end = (begin + resultsPerPage) > documentResults.size() ? documentResults.size() : (begin + resultsPerPage);
									String lastDocument = "";
									
									for (int i = begin; i < end; i++) {
										String url = "ResultsPage?" + "viewDoc=" + documentResults.get(i).getDocumentName() + "&beginSlice=" + documentResults.get(i).getBeginSlice() + "&endSlice=" + documentResults.get(i).getEndSlice();
										
										if (!lastDocument.equals(documentResults.get(i).getDocumentName())) {
											lastDocument = documentResults.get(i).getDocumentName();
							%>
											<div class="panel panel-default list-group-item">
												<div class="panel-body">
													<div class="media">
														<div class="media-left">
															<a href="<% out.print(url); %>"> 
																<img class="media-object img-rounded"
																	src="<% out.println("public/images/docs/" + documentResults.get(i).getDocumentName().replace(".txt", ".png"));%>" alt="..." width="90" height="120" >
															</a>
														</div>
														<div class="media-body">
															<h4 class="media-heading">
																<a href="<%out.print(url);%>">
																	<%
																		out.println(documentResults.get(i).getDocumentName().replace(".txt", ""));
																	%>
																</a>
															</h4>
																
															<label class="reference">
																<%
																	out.print("(" + documentResults.get(i).getAuthor() + ", " + documentResults.get(i).getSource() + ")");
																%>
															</label>
															<div id="div<%out.print(i);%>">
																<%
																	out.println(documentResults.get(i).getSlice() + " [...]");
																	out.println("<br><br>");
																%>
															</div>
														</div>
													</div>
												</div>
											</div>
										
							<%
										} else {
										%>
											<div class="panel panel-default list-group-item">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
															<div class="media-left">
																<a href="<% out.print(url); %>"> 
																
																	<img class="media-object img-rounded"
																		src="<% out.println("public/images/docs/" + documentResults.get(i).getDocumentName().replace(".txt", ".png"));%>" alt="..." width="90" height="120" >
																</a>
															</div>
															<div class="media-body">
																<h4 class="media-heading">
																	<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" ">
																		<%
																			out.println(documentResults.get(i).getDocumentName().replace(".txt", ""));
																		%>
																	</a>
																</h4>
															
																<label class="reference">
																	<%
																		out.print("(" + documentResults.get(i).getAuthor() + ", " + documentResults.get(i).getSource() + ")");
																	%>
																</label>
					
																<div id="div<%out.print(i);%>">
																	<%
																		out.println(documentResults.get(i).getSlice() + " [...]");
																		out.println("<br><br>");
																	%>
																</div>
															</div>
														</div>
														
														<div id="collapseOne" class="panel-collapse collapse">
															<div class="panel panel-default list-group-item">
																<div class="panel-body">
																	<div class="media">
																		<div class="media-left">
																			<a href="<% out.print(url); %>"> 
																				<img class="media-object img-rounded"
																					src="<% out.println("public/images/docs/" + documentResults.get(i).getDocumentName().replace(".txt", ".png"));%>" alt="..." width="90" height="120" >
																			</a>
																		</div>
																		<div class="media-body">
																			<h4 class="media-heading">
																				<a href="<%out.print(url);%>">
																				<%
																					out.println(documentResults.get(i).getDocumentName().replace(".txt", ""));
																				%>
																				</a>
																			</h4>
																	
																			<label class="reference">
																			<%
																				out.print("(" + documentResults.get(i).getAuthor() + ", " + documentResults.get(i).getSource() + ")");
																			%>
																			</label>
				
																			<div id="div<%out.print(i);%>">
																			<%
																				out.println(documentResults.get(i).getSlice() + " [...]");
																				out.println("<br><br>");
																			%>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
										              </div>
										            </div>
												</div>
											</div>
										
										<%
										}
									}
								}
							%>
						</div>
						
						<div align="center">
							<ul class="pagination">
							<%
								//========================================================================
								int nSeePage = 9;
								if (currentPage == 0) {
									%>
										<li><a style="cursor:not-allowed" >&laquo;</a></li>
									<%
								} else {
									%>
										<li><a href="ResultsPage?<% out.print(request.getQueryString().replaceAll("&page=[0-9]*", "") + "&page=" + (currentPage - 1));%>">&laquo;</a></li>
									<%
								}
								
								//========================================================================
								int nPage = (documentResults.size()%resultsPerPage) == 0 ? (documentResults.size()/resultsPerPage) : (documentResults.size()/resultsPerPage) + 1;
								
								int end = (currentPage + nSeePage/2) > nPage ? nPage : (currentPage + nSeePage/2);
								int begin = (end - nSeePage) < 0 ? 0 : (end - nSeePage);
								
								/*
								int begin = currentPage;
								for (int i = nSeePage/2; i > 0; i--) {
									if (currentPage - i > 0) {
										begin = currentPage - i;
										break;
									}
								} 
								int end = (begin + nSeePage) > nPage ? nPage : (begin + nSeePage);
								*/
								if (!(begin < end)) { 
									%>
										<li class="active"><a>1</a></li> 
									<%
								}
								for(int i = begin; i < end; i++) {
									if(i == currentPage) {
										%>
											<li class="active"><a><%out.print(i+1);%></a></li>														
										<%
									} else {
										String queryString = "ResultsPage?" + request.getQueryString().replaceAll("&page=[0-9]*", "") + "&page=" + i;
										%>
											<li><a href="<%out.print(queryString);%>"><%out.print(i+1);%></a></li>
										<%
									}
								}
								//========================================================================
								
								if (currentPage + 1 >= (documentResults.size()/resultsPerPage)) {
									%>
										<li><a style="cursor:not-allowed">&raquo;</a></li>
									<%
								} else {
									%>
										<li><a href="ResultsPage?<% out.print(request.getQueryString().replaceAll("&page=[0-9]*", "") + "&page=" + (currentPage + 1)); %>">&raquo;</a></li>
									<%
								}
							%>
							</ul>
						</div>
						
						<%
							if(documentResults != null) {
								if(documentResults.size() < 3) {
									for(int i = documentResults.size(); i < 3; i++) { 
										out.print("<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br>");
									}
								}
							}
						%>
					</div>

					<div role="tabpanel" class="tab-pane fade" id="db_results">
						
						<script type="text/javascript">
							$(document).ready(function() {
								$('#entities').DataTable( {
									"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
								});
							});
						</script>
						
						<table id="entities" class="table table-bordered table-hover">
						    <thead align="center">
								<tr>
									<th class="text-center">Entity</th>
									<th class="text-center">Slice</th>
									<th class="text-center">Citations</th>
									<th class="text-center">Relations</th>
									<th class="text-center">Document</th>
								</tr>
						    </thead>
						    <tbody>
							    <%
							    	@SuppressWarnings("unchecked")
							    	ArrayList<CypherResults> cypherResults = ((ArrayList<CypherResults>) request.getAttribute("cypherResults"));
							    	
							    	if (cypherResults != null) {
							    		for(int i = 0; i < cypherResults.size(); i++) {
							    			
							    			out.println("<tr>");
							    			out.println("<td class=\"text-center\" > " + cypherResults.get(i).getLabel() + " </td>");
											out.println("<td class=\"text-center\" > " + cypherResults.get(i).getSlice() + " </td>");
							    			out.println("<td class=\"text-center\" > " + cypherResults.get(i).getCitations() + " </td>");
							    			out.println("<td class=\"text-center\" > " + cypherResults.get(i).getRelations() + " </td>");
							    			out.println("<td class=\"text-center\" > " + cypherResults.get(i).getDocument() + " </td>");
							    			out.println("</tr>");
										}
							    	}
							    %>
						    </tbody>
						</table>
						
						<%
							int size = 0;
							if (cypherResults != null) {
								size = cypherResults.size()%15;
							}

							for(int i = size; i < 20; i++) { 
								out.print("<br> <br>");
							}						
						%>
					</div>

					<div role="tabpanel" class="tab-pane fade" id="graph_results"></div>
					
				</div>

			</div>
		</div>
	</div>

	<div id="graph" style="display: none">
		<div class="container" id="mynetwork"></div>
		<br>
	</div>

	

</body>
</html>
