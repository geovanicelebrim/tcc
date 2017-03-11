<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="java.util.ArrayList"%>
<%@page import="entity.User"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Menu Management</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">
<link rel="stylesheet" href="./public/css/management.css">

<script src="./public/js/jquery/jquery-1.12.4.js"></script>
<script src="./public/js/jquery/jquery-ui.js"></script>
<script src="./public/js/util/util.js"></script>

</head>
<body>
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->

	<div class="container">
		<form id="logout" name="logout" action="ManagementLoginPage?action=logout" method="post">
			<div align="right" style="position: absolute; top: 4%; right: 5%">
				<a href="#" onclick="document.getElementById('logout').submit();">Logout</a>
			</div>
			<div align="center" style="position: absolute; left: 50%; transform: translateX(-50%); top: 4%;">
				Welcome
				<%
					User user = (User) request.getSession().getAttribute("user");
					out.print(user.getName());
				%>
			</div>
		</form>
		
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block" id="logo"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
			</div>
			<script type="text/javascript">
				document.getElementById('logo').ondragstart = function() { return false; };
			</script>
		</div>
		
		<div style="padding-bottom: 2em;">
			<table class="text-center" style="width: 100%;">
				<tr>
					<td colspan="2" align="center">
						<h2>Management</h2>
					</td>
				</tr>
				<tr>
					<td align="center" style="padding-top: 20px;">
						<form id="add_user" action="MenuManagement?action=add_user" method="get">
							<input hidden="true" name="option" value="add_user">
							<div class="card">
								<div class="header btn btn-primary" onclick="document.getElementById('add_user').submit();">
									<h1>Add Admin User</h1>
								</div>
	
								<div style="padding: 10px;">
									<p>Add a new administrator. An administrator can access this folder, add new files, check the scheduled tasks, and view system status.</p>
								</div>
							</div>
						</form>
					</td>
					<td align="center" style="padding-top: 20px;">
						<form id="add_file" action="MenuManagement?action=add_file" method="get">
							<input hidden="true" name="option" value="add_file">
							<div align="center" class="card">
								<div class="header btn btn-primary" onclick="document.getElementById('add_file').submit();">
									<h1>Add New Files</h1>
								</div>
	
								<div style="padding: 10px;">
									<p>Add new files to the system, schedule or perform indexing and import into the database. These files will be available through the search engine.</p>
								</div>
							</div>
						</form>
					</td>
				</tr>
				<tr>
					<td align="center" style="padding-top: 20px;">
						<form id="view_scheduled_task" action="MenuManagement?action=view_scheduled_task" method="get">
							<input hidden="true" name="option" value="view_scheduled_task">
							<div class="card">
								<div class="header btn btn-primary" onclick="document.getElementById('view_scheduled_task').submit();">
									<h1>View Scheduled Task</h1>
								</div>
	
								<div style="padding: 10px;">
									<p>Check scheduled tasks, such as indexing and import, that were not performed by the system.</p>
								</div>
	
							</div>
						</form>
					</td>
					<td align="center" style="padding-top: 20px;">
						<form id="status" action="MenuManagement?action=status" method="get">
							<input hidden="true" name="option" value="status">
							<div class="card">
								<div id="logSystem" class="header btn btn-primary btn-lg" onclick="submitStatus();">
									<script type="text/javascript">
										function submitStatus() {
											document.getElementById('press').style.display = 'block';
											document.getElementById('noPress').style.display = 'none';
											document.getElementById('status').submit();
										}
										 
									</script>
									
									<h1 id="noPress"> Status Of System</h1>
									<h1 id="press" hidden="true"> <span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Status Of System</h1>
								</div>
								<div style="padding: 10px;">
									<p>Check the system status. Here you can check whether the system has been initialized or if it fails.</p>
								</div>
							</div>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
