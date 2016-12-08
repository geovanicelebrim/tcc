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

<style type="text/css">
div.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	text-align: center;
}

div.header {
	/* background-color: #b3d1ff; */
	color: white;
	padding: 10px;
	font-size: 40px;
}

@media ( min-width : 1px) {
	div.card {
		width: 10em;
	}
	h1 {
		font-size: 32%;
	}
	div.header {
		width: 3.5em;
	}
}

@media ( min-width : 480px) {
	div.card {
		width: 15em;
	}
	h1 {
		font-size: 45%;
	}
	div.header {
		width: 5.25em;
	}
}

@media ( min-width : 768px) {
	div.card {
		width: 20em;
	}
	h1 {
		font-size: 60%;
	}
	div.header {
		width: 7em;
	}
}

@media ( min-width : 992px) {
	div.card {
		width: 30em;
	}
	div.header {
		width: 10.5em;
	}
}

.glyphicon-refresh-animate {
    -animation: spin 1.5s infinite linear;
    -webkit-animation: spin2 1.5s infinite linear;
}

@-webkit-keyframes spin2 {
    from { -webkit-transform: rotate(0deg);}
    to { -webkit-transform: rotate(360deg);}
}

@keyframes spin {
    from { transform: scale(1) rotate(0deg);}
    to { transform: scale(1) rotate(360deg);}
}

</style>
</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->



	<div class="container">
		<form id="logout" name="logout" action="ManagementLoginPage?action=logout" method="post">
			<div align="right" style="position: absolute; top: 3%; right: 5%">
				<a href="#" onclick="document.getElementById('logout').submit();">Logout</a>
			</div>
			<div align="center" style="position: absolute; left: 50%; transform: translateX(-50%); top: 6%;">
				Welcome
				<%
					User user = (User) request.getSession().getAttribute("user");
					out.print(user.getName());
				%>
			</div>
		</form>
		
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center" style="font-size: 35pt; position: relative;">Management</div>
			</div>
		</div>

		<div style="padding-top: 2em; padding-bottom: 2em;">
			<table class="text-center" style="width: 100%;">
				<tr>
					<td align="center">
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
					<td align="center">
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

	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>
