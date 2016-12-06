<html>
  <head>
    <title>Redirecting...</title>
    <%
    	String current = (String) request.getAttribute("current");
    	String target = (String) request.getAttribute("target");
    	
    	current = current == null ? "#" : current;
    	
    	if (target != null) {
    %>
    		<meta http-equiv="Refresh" content="0; url=<%out.print(target);%>"/>
    <%
    	} else {
    %>
    		<meta http-equiv="Refresh" content="0; url=<%out.print(current);%>"/>
    <%
    	}
    %>
  </head>
  <body>
  </body>
</html>
