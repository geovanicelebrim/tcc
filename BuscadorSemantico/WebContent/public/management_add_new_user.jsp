<!doctype html>
<!--[if lt IE 7]>  <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="java.util.ArrayList"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Add New User</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">

<script src="./public/js/jquery/jquery-1.12.4.js"></script>
<script src="./public/js/util/util.js"></script>

</head>
<body onload="getIP(this)">
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->

	<div class="container">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block" id="logo"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
					<div align="center">
						<h2>Add User</h2>
					</div>
				<script type="text/javascript">
					document.getElementById('logo').ondragstart = function() { return false; };
				</script>
			</div>
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#add_user"
						aria-controls="add_user" role="tab" data-toggle="tab">Add User</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="add_user">

						<div align="center">
							Here you can enter a new administrator for the system.<br> <br>
						</div>

						<form id="add_user_form" method="POST" action="ManagementAddNewUserPage?action=add_user"
							onsubmit="return checkPassword();">
							<input id="ip" name="ip" hidden="true">
							<div align="center" style="min-width: 20em; width: 40%; margin: 0 auto;">
								<div class="form-group">
									<label for="name">Name:</label> <input type="text" onkeypress="$('#error').hide();"
										class="form-control" name="name" autofocus="autofocus"
										placeholder="Inform the name." required autocomplete="off">
								</div>

								<div class="form-group">
									<label for="email">E-mail:</label> <input type="text" onkeypress="$('#error').hide();"
										class="form-control" name="email" id="email"
										placeholder="Inform the e-mail." required autocomplete="off">
								</div>

								<div class="form-group">
									<label for="password">Password:</label> <input type="password"
										class="form-control" name="password" id="password" onkeypress="$('#error_password').hide(); $('#error').hide();"
										placeholder="Inform the password." required autocomplete="off">
								</div>

								<div class="form-group">
									<label for="confirm_password">Confirm Password:</label> <input
										type="password" class="form-control" name="confirm_password" id="confirm_password" onkeypress="$('#error_password').hide(); $('#error').hide();"
										placeholder="Confirm the password." required
										autocomplete="off">
								</div>
								
								<div id="error_password" class="alert alert-warning" hidden="true">
									<strong>Warning!</strong> The passwords you entered do not match.
								</div>
								<%
									String error = (String) request.getAttribute("error");
									if(error != null) {
								%>
										<div id="error" class="alert alert-danger">
											<strong>Danger!</strong> <% out.print(error); %>
										</div>
								<%
									}
								%>
							</div>


							<div align="center">
								<br> <a class="btn btn-warning" href="ManagementLoginPage?action=authenticate" >Back</a> <input
									class="btn btn-primary" type="submit" value="Add User"
									name="add_user" id="add_user"/>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
