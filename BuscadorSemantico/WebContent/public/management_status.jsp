<!doctype html>
<!--[if lt IE 7]>  <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Status of System</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap.css">

<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">
<link href="./public/js/vis/dist/vis.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="./public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./public/fonts/font-awesome-4.7.0/css/font-awesome.css">

<script src="./public/js/jquery/jquery-1.12.4.js"></script>
<script src="./public/js/util/util.js"></script>
<script src="./public/js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="public/js/vis/dist/vis.js"></script>
</head>
<body>
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->

	<div class="container" style="padding-bottom: 3em;">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block" id="logo"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center">
						<h2>Status of system</h2>
				</div>
				<script type="text/javascript">
					document.getElementById('logo').ondragstart = function() { return false; };
				</script>
			</div>
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#add_user"
						aria-controls="add_user" role="tab" data-toggle="tab">Status of system</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="add_user">

						<div align="center">
							Here you can get an overview of the system and its working state.<br>
							<br>
						</div>

						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" id="current_state" data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne"><span
										class="glyphicon glyphicon-list-alt"> </span> Current state</a>
								</h4>
								
								<div id="collapseOne" class="panel-collapse collapse">
									<div class="panel-body">

										<table class="table">
											<tr>
												<td style="width: 30%;"><span style="color: gray;" 
													class="glyphicon glyphicon-cog"></span> System boot </td>
													<%
														String systemBoot = (String) request.getAttribute("systemBoot");
														if(systemBoot.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(systemBoot.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(systemBoot); %> </td>
													<%
														}
													%>
											</tr>
											<tr>
												<td><span style="color: gray;"
													class="glyphicon glyphicon-pencil text-primary"></span> Index </td>
													<%
														String index = (String) request.getAttribute("index");
														if(index.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(index.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(index); %> </td>
													<%
														}
													%>
											</tr>
											<tr>
												<td><span style="color: gray;"
													class="glyphicon glyphicon-edit"></span> Dictionary </td>
													<%
														String dictionary = (String) request.getAttribute("dictionary");
														if(dictionary.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(dictionary.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(dictionary); %> </td>
													<%
														}
													%>
											</tr>
											<tr>
												<td><span style="color: gray;"
													class="fa fa-database"></span> Database </td>
													<%
														String database = (String) request.getAttribute("database");
														if(database.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(database.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(database); %> </td>
													<%
														}
													%>
											</tr>
											<tr>
												<td><span style="color: gray;"
													class="glyphicon glyphicon-search"></span> Key word search engine </td>
													<%
														String keyWordSearch = (String) request.getAttribute("keyWordSearch");
														if(keyWordSearch.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(keyWordSearch.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(keyWordSearch); %> </td>
													<%
														}
													%>
											</tr>
											<tr>
												<td><span style="color: gray;" 
													class="glyphicon glyphicon-search"></span> Semantic search engine </td>
													<%
														String semanticSearch = (String) request.getAttribute("semanticSearch");
														if(semanticSearch.contains("error:\t")) {
													%>
															<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(semanticSearch.replace("error:\t", "")); %> </td>
													<%
														} else {
													%>
															<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(semanticSearch); %> </td>
													<%
														}
													%>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" data-toggle="collapse" data-parent="#accordion"
										href="#collapseThree" onclick="setTimeout(scrollTo('#collapseThree'), 3);"><span
										class="glyphicon glyphicon-tasks"> </span> Activity record</a>
								</h4>
								<div id="collapseThree" class="panel-collapse collapse">
									<div class="panel-body">

										<!-- Scroll bar present and enabled -->
										<div style="width: 100%; height: 30em; overflow-y: scroll;">
											<table class="table">
												<%
													@SuppressWarnings("unchecked")
													ArrayList<String> logManagement = (ArrayList<String>) request.getAttribute("logManagement");
													if(logManagement != null) {
														for(int i = logManagement.size() - 1; i >= 0; i--) {
												%>
															<tr>
																<%
																	if(logManagement.get(i).split("\t")[0].equals("ERROR")) {
																%>
																
																		<td><span style="color: red;" class="glyphicon glyphicon-remove"></span> <% out.print(logManagement.get(i)); %></td>
																<%
																	} else {
																%>
																		<td><span style="color: green;" class="glyphicon glyphicon-ok"></span> <% out.print(logManagement.get(i)); %></td>
																<%
																	}
																%>
															</tr>
												<%
														}
													}
												%>
											</table>
											<%
												String csv = "";
												if(logManagement != null) {
													for(int i = logManagement.size() - 1; i >= 0; i--) {
														csv += logManagement.get(i) + "\n";
													}
												}
											%>
											
											<div style="width: 10em;">
												<textarea id="textboxManagement" hidden="true"><% out.print(csv); %></textarea> 
												<button class="btn btn-default" style="width: 100%;" id="createManagement">Generate file</button> 
												<a class="btn btn-info" download="logManagement.csv" id="downloadlinkManagement" style="display: none">Download</a>
											</div>
											
											<script type="text/javascript">											
												(function () {
													var textFile = null, makeTextFile = function (text) {
														var data = new Blob([text], {type: 'text/plain'});
														if (textFile !== null) {
															window.URL.revokeObjectURL(textFile);
														}
														textFile = window.URL.createObjectURL(data);
														return textFile;
													};
	
													var createManagement = document.getElementById('createManagement'),
													textboxManagement = document.getElementById('textboxManagement');
	
													createManagement.addEventListener('click', function () {
														var link = document.getElementById('downloadlinkManagement');
														link.href = makeTextFile(textboxManagement.value);
														createManagement.style.display = 'none';
														link.style.display = 'block';
													}, false);
												})();
											</script>

										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" data-toggle="collapse" data-parent="#accordion"
										href="#collapseFour" onclick="setTimeout(scrollTo('#collapseFour'), 3);"><span
										class="glyphicon glyphicon-globe"> </span> Access</a>
								</h4>
								<div id="collapseFour" class="panel-collapse collapse">
									<div class="panel-body">
										
										<div id="visualization"></div>
										<script type="text/javascript">

											var container = document.getElementById('visualization');
											var items = [
												<%
													@SuppressWarnings("unchecked")
													ArrayList<String> accessList = (ArrayList<String>) request.getAttribute("accessList");
													for (int i = 0; i < accessList.size(); i++) {
														if(i + 1 == accessList.size()) {
															out.print("{x: '" + accessList.get(i).split("\t")[0] + "', y: " + accessList.get(i).split("\t")[1] + "}");
														} else {
															out.print("{x: '" + accessList.get(i).split("\t")[0] + "', y: " + accessList.get(i).split("\t")[1] + "},");
														}
													}
												%>
											];
											var dataset = new vis.DataSet(items);
											var options = {
												start: <% out.print("'" + Calendar.getInstance().get(Calendar.YEAR) + "-01-01'");%>,
												end: <% out.print("'" + (Calendar.getInstance().get(Calendar.YEAR) + 1) + "-01-01'"); %>
											};
											var graph2d = new vis.Graph2d(container, dataset, options);
										</script>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" data-toggle="collapse" data-parent="#accordion"
										href="#collapseFive" onclick="setTimeout(scrollTo('#collapseFive'), 3);"><span
										class="glyphicon glyphicon-file"> </span> System log error</a>
								</h4>
								<div id="collapseFive" class="panel-collapse collapse">
									<div class="panel-body">
										<div style="width: 100%; height: 30em; overflow-y: scroll;">
											<table class="table">
												<%
													@SuppressWarnings("unchecked")
													ArrayList<String> logSystem = (ArrayList<String>) request.getAttribute("logSystem");
													if(logSystem != null) {
														for(int i = logSystem.size() - 1; i >= 0; i--) {
												%>
															<tr>
																<td><% out.print(logSystem.get(i)); %></td>
															</tr>
												<%
														}
													}
												%>
											</table>
											<%
												csv = "";
												if(logSystem != null) {
												for(int i = logSystem.size() - 1; i >= 0; i--) {
													csv += logSystem.get(i) + "\n";
												}
											}
											%>
											<div style="width: 10em;">
												<textarea id="textboxSystem" hidden="true"><% out.print(csv); %></textarea> 
												<button class="btn btn-default" style="width: 100%;" id="createSystem">Generate file</button> 
												<a class="btn btn-info" download="logSystem.csv" id="downloadlinkSystem" style="display: none">Download</a>
											</div>
											
											<script type="text/javascript">
												(function () {
													var textFile = null, makeTextFile = function (text) {
														var data = new Blob([text], {type: 'text/plain'});
														if (textFile !== null) {
															window.URL.revokeObjectURL(textFile);
														}
														textFile = window.URL.createObjectURL(data);
														return textFile;
													};
													var createSystem = document.getElementById('createSystem'),
													textboxSystem = document.getElementById('textboxSystem');
	
													createSystem.addEventListener('click', function () {
														var link = document.getElementById('downloadlinkSystem');
														link.href = makeTextFile(textboxSystem.value);
														createSystem.style.display = 'none';
														link.style.display = 'block';
													}, false);
												})();
											</script>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div align="center">
				<a class="btn btn-warning" href="ManagementLoginPage?action=authenticate" >Back</a>
			</div>
		</div>
	</div>
</body>
</html>
