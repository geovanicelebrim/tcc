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
<link rel="apple-touch-icon" href="./public/icons/apple-touch-icon.png">
<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">

<!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->


<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="./public/js/search/autocomplete.js"></script>

<style>
/* Full-width input fields */
.in {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

/* Set a style for all buttons */
.btnPer {
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 100%;
}

/* Center the image and position the close button */
.imgcontainer {
    text-align: center;
    margin: 24px 0 12px 0;
    position: relative;
}

img.avatar {
    width: 20%;
    border-radius: 20%;
}

span.psw {
    float: right;
    padding-top: 16px;
}

/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: relative; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    padding-top: 7%;
}

/* Modal Content/Box */
.modal-content {
    background-color: #fefefe;
    margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
    border: 1px solid #888;
    
}

@media ( min-width : 1px) {
	.modal-content {
		width: 90%;
	}
}

@media ( min-width : 25em) {
	.modal-content {
		width: 25em;
	}
}

/* The Close Button (x) */
.close {
    position: absolute;
    right: 25px;
    top: 0;
    color: #000;
    font-size: 35px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: red;
    cursor: pointer;
}

/* Add Zoom Animation */
.animate {
    -webkit-animation: animatezoom 0.6s;
    animation: animatezoom 0.6s
}

@-webkit-keyframes animatezoom {
    from {-webkit-transform: scale(0)} 
    to {-webkit-transform: scale(1)}
}
    
@keyframes animatezoom {
    from {transform: scale(0)} 
    to {transform: scale(1)}
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
    span.psw {
       display: block;
       float: none;
    }
    .cancelbtn {
       width: 100%;
    }
}
</style>

</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

	<div id="id01" class="modal">

		<form class="modal-content animate" action="ManagementLoginPage?action=authenticate" method="post">
			<div class="imgcontainer">
				<span onclick="$( '#id01' ).fadeOut( 'mediun' );" class="close"
					title="Close Modal">&times;</span> <img
					src="./public/images/cedim.jpg" alt="Avatar" class="avatar">
			</div>

			<div style="padding: 16px;">
				<label><b>Username</b></label> <input class="in" type="text"
					placeholder="Enter Username" name="user" id="user" required> <label><b>Password</b></label>
				<input class="in" type="password" placeholder="Enter Password" name="password" id="password"
					required>

				<button class="btnPer btn btn-primary" type="submit">Login</button>
				<input type="checkbox" checked="checked"> Remember me
			</div>

			<div style="padding: 16px;" style="background-color:#f1f1f1">
				<a href="#">Forgot password?</a>
			</div>
		</form>
	</div>

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
				<a href="#" onclick="document.getElementById('id01').style.display='block'; $('#user').focus();">Management</a>
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
							<input type="text" class="form-control input-lg"
								id="search-query" name="search-query"
								placeholder="Type your query" required autocomplete="off">

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




	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>
