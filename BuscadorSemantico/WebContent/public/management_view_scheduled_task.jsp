<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="java.util.ArrayList"%>
<%@page import="management.entity.Task"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>View Scheduled Task</title>
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

<!-- Include Date Range Picker -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />



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
	
</script>

</head>
<body>
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

	<div class="container">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center" style="font-size: 35pt; position: relative;">View Scheduled Task</div>
			</div>
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#add_user"
						aria-controls="add_user" role="tab" data-toggle="tab">View Scheduled Task</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="add_user">

						<div align="center">
							Here you can see tasks scheduled in the system.<br> <br>
						</div>

						<table class="table table-bordered table-hover">
						    <thead align="center">
						      <tr>
						      	   <th class="text-center">Type</th>
						           <th class="text-center">Creation</th>
						           <th class="text-center">Scheduled</th>
						           <th class="text-center">Executed</th>
						       </tr>
						    </thead>
						    <tbody>
						    <%
						    	@SuppressWarnings("unchecked")
						    	ArrayList<Task> tasks = ((ArrayList<Task>) request.getAttribute("tasks"));
						    	
						    	if (tasks != null) {
						    		for(int i = 0; i < tasks.size(); i++) {
						    			
						    			out.println("<tr>");
						    			out.println("<td class=\"text-center\" > " + tasks.get(i).getType() + " </td>");
										out.println("<td class=\"text-center\" > " + tasks.get(i).getCreation() + " </td>");
						    			out.println("<td class=\"text-center\" > " + tasks.get(i).getScheduled() + " </td>");
						    			if(tasks.get(i).getExecuted()) {
						    				out.println("<td class=\"text-center\" > <span style=\"color: green\" class=\"glyphicon glyphicon-ok\"></span> </td>");
						    				out.println("</tr>");
						    			} else {
						    				out.println("<td class=\"text-center\" > <span style=\"color: red\" class=\"glyphicon glyphicon-remove\"></span> </td>");
						    				out.println("</tr>");
						    			}
						    		}
						    	}
						    %>

						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>
