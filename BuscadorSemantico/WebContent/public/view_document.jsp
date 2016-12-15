<!doctype html>
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
	<%
		String title = (String) request.getAttribute("title");
		if(title != null) {
			out.print("<title>View Document - " + title + "</title>");
		}
		else {
			out.print("<title>View Document</title>");
		}
	%>
	
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="shortcut icon" href="./public/icons/icon.png">
	
	<link rel="stylesheet" href="./public/css/bootstrap.min.css">
	<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="./public/css/main.css">
	<link rel="stylesheet" href="./public/css/util.css">
	<link rel="stylesheet" href="./public/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="./public/fonts/font-awesome-4.7.0/css/font-awesome.css">
	<link rel="stylesheet" href="./public/css/jquery-ui.css">
	
	<script src="./public/js/jquery/jquery-1.12.4.js"></script>
	<script src="./public/js/jquery/jquery-ui.js"></script>
	<script src="./public/js/viewDocument/actions.js"></script>

</head>

<body>
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->

	<div id="menu" hidden="true">
		<table class="tableViewDocument menuViewDocument">
		  <tr>
		    <th class="VDth"><a class="menu-bar VDbutton" href="javascript:goBack()"> <i class="fa fa-arrow-left" aria-hidden="true"></i> </a></th>
		    <th class="VDth"><a class="menu-bar VDbutton" href="javascript:downloadDocument()"> <i class="fa fa-download" aria-hidden="true"></i> </a></th>
			<th class="VDth"><a class="menu-bar VDbutton" href="javascript:printDocument('document')"> <i class="fa fa-print" aria-hidden="true"></i> </a></th>
			<th class="VDth"><a class="menu-bar VDbutton" href="javascript:trustworthy('#thanks-message')"> <i class="fa fa-thumbs-up" aria-hidden="true"></i> </a></th>
			<th class="VDth"><a class="menu-bar VDbutton" href="javascript:unreliable('#thanks-message')"> <i class="fa fa-thumbs-down" aria-hidden="true"></i> </a></th>
			<th class="VDth"><a class="menu-bar VDbutton" href="javascript:cite('#citation')"> <i class="fa fa-quote-right fa-1x fa-pull-left" aria-hidden="true"></i> </a></th>
		  </tr>
		</table>
	</div>
	
	<div hidden="true" id="citation" title="To quote">
		<%
			out.print("Diego Antonelli. ");
			out.print("\"" + title + "\", ");
			out.print("Gazeta do Povo, 2015.");
		%>
	</div>
	
	<div hidden="true" id="thanks-message" title="Thank you!">
	    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
	    Your feedback for this document was computed.
		<br><br>
	    Thanks for contributing to improve the results!
	</div>

	<div id="document" class="container" onclick="showTimeoutMenu();">
		<div class="row" align="justify">
			
			<h1 align="center"> <% out.print(title); %></h1>
			
			<div class="top20">
				
				<%
					String text = (String) request.getAttribute("text");
					Integer beginSlice = (Integer) request.getAttribute("beginSlice");
					Integer endSlice = (Integer) request.getAttribute("endSlice");
					
					if(text != null && beginSlice != null && endSlice != null) {
						text = "<p> " + text;
						for(int i = beginSlice; i > 1; i--) {
							if(text.substring(i - 1, i).contains("\n")) {
								beginSlice = i;
								break;
							}
						}
						for(int i = endSlice; i < text.length()-1; i++) {
							if(text.substring(i, i + 1).contains("\n")) {
								endSlice = i;
								break;
							}
						}
						String firstText = text.substring(0, beginSlice).replaceAll("[\n]+", "\n");
						String secondText = text.substring(endSlice, text.length()).replaceAll("[\n]+", "\n");
						String sliceText = text.substring(beginSlice, endSlice).replaceAll("[\n]+", "\n").replaceFirst("\n", "</span>\n<span id=\"slice2\" style=\"background: #33cccc\">");
						String initSpanText = "<span id=\"slice1\" style=\"background: #33cccc\">";
						String finishSpanText = "</span>";
						
						text = firstText + initSpanText +  sliceText + finishSpanText + secondText;
						text = text.replaceAll("\n", "</p><p>");
						out.print(text);
					} else if (text != null) {
						text = "<p> " + text;
						text = text.replaceAll("[\n]+", "\n").replaceAll("\n", "</p><p>");
						out.print(text);
					}
				%>
				
				<script>
					$( "#slice1" ).animate({
						backgroundColor: "rgb( 255, 255, 255 )"
					}, 10000);
					$( "#slice2" ).animate({
						backgroundColor: "rgb( 255, 255, 255 )"
					}, 10000);
					
					var container = $('div'),
					scrollTo = $('#slice1');

					$('html, body').animate({
						scrollTop : scrollTo.offset().top - container.offset().top + container.scrollTop()
					}, 1000);
					
				</script>
				
			</div>
		</div>
	</div>
	<br><br><br>
</body>

</html>