<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="java.util.ArrayList"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Status of System</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="apple-touch-icon" href="./public/icons/apple-touch-icon.png">
<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">

<!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->


<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- Include Required Prerequisites -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap/3/css/bootstrap.css" />

<script type="text/javascript" src="public/js/vis/dist/vis.js"></script>
<link href="public/js/vis/dist/vis.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

	$(document).ready(function() {
	    /*disable non active tabs*/
	    $('.nav li').not('.active').addClass('disabled');
	    $('.nav li').not('.active').find('a').removeAttr("data-toggle");
	    
	    $('gotoIndexer').click(function(){
	        /*enable next tab*/
	        $('.nav li.active').next('li').removeClass('disabled');
	        $('.nav li.active').next('li').find('a').attr("data-toggle","tab")
	    });
	    $('gotoImport').click(function(){
	        /*enable next tab*/
	        $('.nav li.active').next('li').removeClass('disabled');
	        $('.nav li.active').next('li').find('a').attr("data-toggle","tab")
	    });
	});
	
	function scrollTo(element) {
		var scrollTo = $(element);

		$('html, body').animate({
			scrollTop : scrollTo.offset().top + 300
		}, 1000);
	}
	
</script>

</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->



	<div class="container">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center" style="font-size: 35pt; position: relative;">Status
					of system</div>
			</div>
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#add_user"
						aria-controls="add_user" role="tab" data-toggle="tab">Status
							of system</a></li>
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
										class="glyphicon glyphicon-folder-close"> </span> Current
										state</a>
								</h4>
								
								<div id="collapseOne" class="panel-collapse collapse in">
									<div class="panel-body">

										<table class="table">
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">System boot</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-pencil text-primary"></span><a
													href="http://www.jquery2dotnet.com">Index</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">Dictionary</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">Database</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">Key word search engine</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">Semantic search engine</a></td>
											</tr>
											<tr>
												<td><span
													class="glyphicon glyphicon-flash text-success"></span><a
													href="http://www.jquery2dotnet.com">Management</a></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" data-toggle="collapse" data-parent="#accordion"
										href="#collapseThree" onclick="setTimeout(scrollTo('#collapseThree'), 3);"><span
										class="glyphicon glyphicon-user"> </span> Activity record</a>
								</h4>
								<div id="collapseThree" class="panel-collapse collapse">
									<div class="panel-body">

										<!-- Scroll bar present and enabled -->        
										<div style="width: 100%; height: 50%; overflow-y: scroll;">
											<table class="table">
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Change
															Password</a></td>
												</tr>
												<tr>
													<td><a href="http://www.jquery2dotnet.com">Notifications</a>
														<span class="label label-info">5</span></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a style="width: 100%; color: white;" class="btn btn-info" data-toggle="collapse" data-parent="#accordion"
										href="#collapseFour" onclick="setTimeout(scrollTo('#collapseFour'), 3);"><span
										class="glyphicon glyphicon-file"> </span> Access</a>
								</h4>
								<div id="collapseFour" class="panel-collapse collapse">
									<div class="panel-body">
										
										<div id="visualization"></div>
										<script type="text/javascript">

											var container = document.getElementById('visualization');
											var items = [
												{x: '2016-01-01', y: 10},
												{x: '2016-02-01', y: 25},
												{x: '2016-03-01', y: 30},
												{x: '2016-04-01', y: 10},
												{x: '2016-05-01', y: 15},
												{x: '2016-06-01', y: 30},
												{x: '2016-07-01', y: 30}
											];
											var dataset = new vis.DataSet(items);
											var options = {
												start: '2016-01-01',
												end: '2017-01-01'
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
										class="glyphicon glyphicon-file"> </span> System log</a>
								</h4>
								<div id="collapseFive" class="panel-collapse collapse">
									<div class="panel-body">
										<div style="width: 100%; height: 50%; overflow-y: scroll;">
											<table class="table">
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-usd"></span><a
														href="http://www.jquery2dotnet.com">Sales</a></td>
												</tr>
												<tr>
													<td><span class="glyphicon glyphicon-user"></span><a
														href="http://www.jquery2dotnet.com">Customers</a></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>