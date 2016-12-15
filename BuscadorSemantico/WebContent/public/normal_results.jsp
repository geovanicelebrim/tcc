<!doctype html>
<%@page import="entity.results.SimpleResults"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!--[if lt IE 7]>  <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang=""> <![endif]-->
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
	
	<script src="./public/js/jquery/jquery-1.12.4.js"></script>
	<script src="./public/js/jquery/jquery-ui.js"></script>
	<script src="./public/js/search/autocomplete.js"></script>
	<script src="./public/js/results/util.js"></script>
	<script src="./public/js/util/util.js"></script>
	
	<%
		@SuppressWarnings("unchecked")
		ArrayList<SimpleResults> simpleResults = (ArrayList<SimpleResults>) request.getAttribute("simpleResults");
		if (simpleResults == null) { simpleResults = new ArrayList<>();}
		if (simpleResults != null && simpleResults.size() > 0) {
	%>
			<script type="text/javascript">
				window.onload = function() {
					document.activeElement.blur();
					scroll();
					$(".scroll_to_tab").click(function () {
						scroll();
					});
				}
			</script>
	<%} %>
</head>

<body style="padding-top: 40px;" onload="getIP(this);">
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->
	
	<%
			String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null) {
			out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
			out.println("alert(\""
					+ errorMessage.replace("\"", "\\\"").replace("\n",
							"\\n") + "\");");
			out.println("</script>");
				}
		%>
	
	<div class="container">
		<div class="row">
			<div
				class="col-xs-8 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2">

				<form action="ResultsPage?action=buscar" method="get">
					<div class="form-group text-center">
						<img class="img-responsive center-block" src="public/images/cedim.jpg"
							style="width: 40%; height: 40%;">

						<div class="input-group">
							<input id="ip" name="ip" hidden="true">
							<input type="text" autocomplete="off" class="form-control input-lg"
								id="search-query" name="search-query"
								placeholder="Type your query" required autocomplete="off"
								<%String query = (String) request.getAttribute("query");
									if(query != null)
										out.println("value=\"" + query.replace("\"", "&quot;") + "\"");%>>

							<div class="input-group-btn">
								<select name="search-mode" class="form-control input-lg"
									id="search-mode" onclick="$('#search-query').select();">
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
						<%if (simpleResults != null && simpleResults.size() > 0) { %>
						onclick="scroll();"
						<%}%>
						class="scroll_to_tab">Simple Results</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active"
						id="simple_results">

						<div class="list-group">						
							
							<%
								final Integer resultsPerPage = 10;
								String cp = (String) request.getAttribute("page");
								Integer currentPage;
								try {
									currentPage = Integer.parseInt(cp);
								} catch (Exception e) {
									currentPage = 0;
								}
								
								
								String suggestion = (String) request.getAttribute("suggestion");
								if ((suggestion == null || suggestion.isEmpty()) && (simpleResults == null || simpleResults.size() == 0)) {
							%>
							<div style="margin-left: 15px;">
							<%
									out.print("No suggestions for this query.");
							%>
							</div>
							<%
								}
								else if (suggestion != null && (simpleResults == null | simpleResults.size() == 0)) {
							%> 
							<div style="margin-left: 15px;">
								<form id="sugg" action="ResultsPage?action=buscar" method="get"> 
									<input id="ip" name="ip" hidden="true">
									<div hidden="true">
										<input type="text" autocomplete="off" id="search-query" name="search-query"
										placeholder="Type your query" required autocomplete="off" 
										<%out.print("value=\"" + suggestion + "\""); %>>
		
										<select name="search-mode" id="search-mode">
											<option value="normal" selected>Normal search</option>
											<option value="semantic">Semantic search</option>
										</select>
									</div>
									
									Did you mean <a href="#" onclick="document.getElementById('sugg').submit();">
									<%
										out.print(suggestion);
									%></a>?
								</form>
							</div>
							<%
								}
								else {
									int begin = resultsPerPage * currentPage;
									int end = (begin + resultsPerPage) > simpleResults.size() ? simpleResults.size() : (begin + resultsPerPage); 
									
									for (int i = begin; i < end; i++) {
							%>
										<div class="panel panel-default list-group-item">
											<div class="panel-body">
												<div class="media">
													<div class="media-left">
														<a href="<% out.print("ResultsPage?" + "viewDoc=" + simpleResults.get(i).getDocumentName()); %>"> 
														<img class="media-object img-rounded"
															src="<% out.println("public/images/docs/" + simpleResults.get(i).getDocumentName().replace(".txt", ".png"));%>" alt="..." width="90" height="120" >
														</a>
													</div>
													<div class="media-body">
														<h4 class="media-heading">
															<a href="<% out.print("ResultsPage?" + "viewDoc=" + simpleResults.get(i).getDocumentName()); %>">
																<%
																	out.println(simpleResults.get(i).getTitle());
																%>
															</a>
														</h4>
															
														<label class="reference">
														<%
															out.print("(" + simpleResults.get(i).getAuthor()
																+ ", " + simpleResults.get(i).getSource() + ")");
														%>
														</label>
														<div id="div<%out.print(i);%>">
														<%
															out.println(simpleResults.get(i).getSlice() + " [...]");
														%>
														</div>
													</div>
												</div>
											</div>
										</div>
								<% 	}
								} %>
						
							<div align="center">
								<ul class="pagination">
									<%
										//========================================================================
										int nSeePage = 10;
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
										int nPage = (simpleResults.size()%resultsPerPage) == 0 ? (simpleResults.size()/resultsPerPage) : (simpleResults.size()/resultsPerPage) + 1;
								
										int end = (currentPage + nSeePage/2) > nPage ? nPage : (currentPage + nSeePage/2);
										int begin = (end - nSeePage) < 0 ? 0 : (end - nSeePage);
										
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
										
										if (currentPage + 1 >= (simpleResults.size()/resultsPerPage)) {
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
