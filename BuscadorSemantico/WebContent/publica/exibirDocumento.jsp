<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
<style>
/* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
@import url(http://fonts.googleapis.com/css?family=Open+Sans);

.btn {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	padding: 4px 10px 4px;
	margin-bottom: 0;
	font-size: 13px;
	line-height: 18px;
	color: #333333;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	vertical-align: middle;
	background-color: #f5f5f5;
	background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff),
		to(#e6e6e6));
	background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: linear-gradient(top, #ffffff, #e6e6e6);
	background-repeat: repeat-x;
	filter: progid:dximagetransform.microsoft.gradient(startColorstr=#ffffff,
		endColorstr=#e6e6e6, GradientType=0);
	border-color: #e6e6e6 #e6e6e6 #e6e6e6;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	border: 1px solid #e6e6e6;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	cursor: pointer;
	*margin-left: .3em;
}

.btn:hover,.btn:active,.btn.active,.btn.disabled,.btn[disabled] {
	background-color: #e6e6e6;
}

.btn-large {
	padding: 9px 14px;
	font-size: 15px;
	line-height: normal;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
}

.btn:hover {
	color: #333333;
	text-decoration: none;
	background-color: #e6e6e6;
	background-position: 0 -15px;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-ms-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}

.btn-primary,.btn-primary:hover {
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	color: #ffffff;
}

.btn-primary.active {
	color: rgba(255, 255, 255, 0.75);
}

.btn-primary {
	background-color: #4a77d4;
	background-image: -moz-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -ms-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6eb6de),
		to(#4a77d4));
	background-image: -webkit-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: -o-linear-gradient(top, #6eb6de, #4a77d4);
	background-image: linear-gradient(top, #6eb6de, #4a77d4);
	background-repeat: repeat-x;
	filter: progid:dximagetransform.microsoft.gradient(startColorstr=#6eb6de,
		endColorstr=#4a77d4, GradientType=0);
	border: 1px solid #3762bc;
	text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.5);
}

.btn-primary:hover,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled]
	{
	filter: none;
	background-color: #4a77d4;
}

.btn-block {
	width: 100%;
	display: block;
}

.goBack {
	position: fixed;
	top: 50%;
	left: 100%;
	margin: -280px 0 0 -150px;
	width: 100px;
	height: 300px;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<% String nome = (String)request.getAttribute("nome");
					if (nome != null) {
						out.println(nome.split("/")[2]);
					}
%>
</title>

<script type="text/javascript">
		window.onload = function(){
		FindNext ();
       }
    </script>

<script type="text/javascript">
        function FindNext () {
            var str = document.getElementById ("findInput").value;
            if (str == "") {
                alert ("Please enter some text to search!");
                return;
            }
            
            if (window.find) {        // Firefox, Google Chrome, Safari
                var found = window.find (str);
                if (!found) {
                    alert ("The following text was not found:\n" + str);
                }
            }
            else {
                alert ("Your browser does not support this example!");
            }
        }
    </script>
</head>
<body>
	<div class="goBack">
		<button class="btn btn-primary btn-block btn-large searchButton" onclick="goBack()">Voltar</button>
		
		<script>
			function goBack() {
			    window.history.back();
			}
		</script>
	</div>
	<h1 align="center">
		<%
		String mensagemErro = (String) request.getAttribute("mensagemErro");
		if (mensagemErro != null) {
			out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
			out.println("alert(\""
					+ mensagemErro.replace("\"", "\\\"").replace("\n",
							"\\n") + "\");");
			out.println("</script>");
		}
		
		if (nome != null) {
			out.println(nome.split("/")[2]);
		}
	
		%>
	
	
	
	</h1>

	<%	String texto = (String) request.getAttribute("texto");
		if (texto != null) {
			out.println(texto.replace("\n", "<br>"));
		}
	%>
	<input type="hidden" id="findInput"
		value="<% 	String trecho = (String) request.getAttribute("trecho");
													if (trecho != null) {
														out.print(trecho.replace("\"", "&quot;"));
													}
												%>"
		size="20" />

</body>
</html>