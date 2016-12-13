<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<%@page import="entity.User"%>
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Semantic Search</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">
<link rel="stylesheet" href="./public/css/login.css">

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="./public/js/search/autocomplete.js"></script>
<script src="./public/js/search/mobile.js"></script>

<!--[if lt IE 9]>
	<script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
<![endif]-->


<script type="text/javascript">
function validateEmail(idEmail) {
	var email = $(idEmail).val();
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    
    if (!re.test(email)) {
    	document.getElementById('error_email').style.display='block';
    	$('#email').focus();
    }
    
    return re.test(email);
}

function getIP() {
	$(document).ready(function () {
	    $.getJSON("http://jsonip.com/?callback=?", function (data) {
	        console.log(data);
	        
	        $("input[id|='ip']").each(function (i, el) {
	            el.value = data.ip;
	        });
	    });
	});
}

</script>
</head>
<body onload="getIP();">
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
    	
		<%
			Boolean accessed = (Boolean) request.getSession().getAttribute("accessed");
			if (accessed == null) {
				request.getSession().setAttribute("accessed", accessed);
				util.Log.getInstance().addAccess();
			}
		%>
		<%
			String errorLogin = (String) request.getAttribute("errorLogin");
			if (errorLogin == null) {
		%>
				<div id="id01" class="modal" style="position: absolute;">
					<form id="login_form" class="modal-content animate" action="ManagementLoginPage?action=authenticate" method="post" onsubmit="return validateEmail('#email');">
						<div class="imgcontainer">
							<span onclick="$( '#id01' ).fadeOut( 'mediun' );" class="close"
								title="Close Modal">&times;</span> <img
								src="./public/images/cedim.jpg" alt="Avatar" class="avatar">
						</div>
			
						<div style="padding: 16px;">
							<input id="ip" name="ip" hidden="true">
							<label><b>E-mail</b></label> <input class="in" type="text" onkeypress="document.getElementById('error_email').style.display='none';"
								placeholder="Enter your e-mail" name="email" id="email" required> <label><b>Password</b></label>
							<input class="in" type="password" placeholder="Enter Password" name="password" id="password" onkeypress="document.getElementById('error_email').style.display='none';"
								required>
	
							<div id="error_email" class="alert alert-warning" hidden="true">
								<strong>Warning!</strong> E-mail is incorrect.
							</div>
							
							<button class="btnPer btn btn-primary" type="submit">Login</button>
							<input type="checkbox" checked="checked"> Remember me
						</div>
			
						<div style="padding: 16px;" style="background-color:#f1f1f1">
							<a href="#">Forgot password?</a>
						</div>
					</form>
				</div>
		<%
			} else {
		%>
				<div id="id01" style="position: absolute; display: block;" class="modal">
					<form id="login_form" class="modal-content" action="ManagementLoginPage?action=authenticate" method="post" onsubmit="return validateEmail('#email');">
						<div class="imgcontainer">
							<span onclick="$( '#id01' ).fadeOut( 'mediun' );" class="close"
								title="Close Modal">&times;</span> <img
								src="./public/images/cedim.jpg" alt="Avatar" class="avatar">
						</div>
			
						<div style="padding: 16px;">
							<input id="ip" name="ip" hidden="true">
							<label><b>E-mail</b></label> 
							<input class="in" type="text" onkeypress="document.getElementById('error_password').style.display='none';"
								placeholder="Enter your e-mail" name="email" id="email" value="${email}" required> 
							<label><b>Password</b></label>
							<input class="in" type="password" placeholder="Enter Password" name="password" id="password" 
							onkeypress="document.getElementById('error_password').style.display='none';"
								required>
	
							<div id="error_password" class="alert alert-warning">
								<strong>Warning!</strong> E-mail or password incorrect.
							</div>
							<div id="error_email" class="alert alert-warning" hidden="true">
								<strong>Warning!</strong> E-mail is incorrect.
							</div>
	
							<button class="btnPer btn btn-primary" type="submit">Login</button>
							<input type="checkbox" checked="checked"> Remember me
						</div>
			
						<div style="padding: 16px;" style="background-color:#f1f1f1">
							<a href="#">Forgot password?</a>
						</div>
					</form>
				</div>
		<%
			}
		%>
	

	<script>
		// Get the modal
		var modal = document.getElementById('id01');
		
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
	    	if (event.target == modal) {
		        //modal.style.display = "none";
	    		$( "#id01" ).fadeOut( "madiun" );
	    	}
		}
			
		$(document).keyup(function(e) {
	    	if (e.keyCode == 27) {
		    	//modal.style.display = "none";
	    		$( "#id01" ).fadeOut( "mediun" );
	   		}
		});
	</script>

	<div class="container fill">
		<%
			User user = (User) request.getSession().getAttribute("user");
			
			if(user == null) {
		%>
			<div align="right" style="position: fixed; top: 3%; right: 5%">
				<a href="#" onclick="document.getElementById('id01').style.display='block'; focusOnlyNotMobile('#email');">Management</a>
			</div>
		<%
			} else {
		%>
			<div align="right" style="position: fixed; top: 3%; right: 5%">
				<a href="ManagementLoginPage?action=authenticate">Management</a>
			</div>
		<%
		
			}
		%>
		
		<div class="row vertical-center">
			<div
				class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2">

				<form action="MainPage?action=search" method="get">
					<div class="form-group text-center">
						<img class="img-responsive center-block"
							src="./public/images/cedim.jpg" style="width: 70%; height: 70%;">

						<div class="input-group">
							<input id="ip" name="ip" hidden="true">
							<input type="text" style="position: static;" class="form-control input-lg"
								id="search-query" name="search-query"
								placeholder="Type your query" required autocomplete="off">

							<div class="input-group-btn">
								<select name="search-mode" style="position: static;" class="form-control input-lg"
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




	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>
