<!doctype html>
<%@page import="entity.results.SimpleResults"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
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
	<title>Results</title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="apple-touch-icon" href="public/icons/apple-touch-icon.png">
	<link rel="shortcut icon" href="public/icons/favicon.ico">
	
	<link rel="stylesheet" href="public/css/bootstrap.min.css">
	<link rel="stylesheet" href="public/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="public/css/main.css">
	
	<script type="text/javascript" src="public/js/results/util.js"></script>
	
	<!--[if lt IE 9]>
	            <script src="public/js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
	        <![endif]-->
	
<style>
	.container {
		margin-right: auto;
		margin-left: auto;
		padding-left: 6px;
		padding-right: 6px;
	}
	
	.container:before,.container:after {
		content: " ";
		display: table;
	}
	
	.container:after {
		clear: both;
	}
	
	@media ( min-width : 768px) {
		.container {
			width: 732px;
		}
	}
	
	@media ( min-width : 992px) {
		.container {
			width: 952px;
		}
	}
	
	@media ( min-width : 1200px) {
		.container {
			width: 1300px;
		}
	}

</style>
	
	<script type="text/javascript">
	          window.onload = function() {
	        	  document.activeElement.blur();
	              scroll();
	
	              $(".scroll_to_tab").click(function () {
	              	scroll();
	              });
	          }
	</script>
	
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		
	<script>
		$( function() {
			var availableTags = [
				"(Pessoa",
				"(Evento",
				"(Local",
				"(Data",
				"(AutorReporter",
				"(Pesquisador",
				"(Organizacao",
				"(Fonte",
				"(TempoFonte",
				"(URLFonte",
				"(Artefato",
				"(Quantidade",
				"(Grupo"
			];

    function split( val ) {
      return val.split( /--/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#search-query" )
      // don't navigate away from the field on tab when selecting an item
      .on( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value + ":\"\")");
          // add placeholder to get the comma-and-space at the end
          // terms.push( "" );
          this.value = terms.join( "--");
          setCaretToPos(this, this.value.length - 2);
          return false;
        }
      });
  } );

  function setSelectionRange(input, selectionStart, selectionEnd) {
    if (input.setSelectionRange) {
      input.focus();
      input.setSelectionRange(selectionStart, selectionEnd);
    }
    else if (input.createTextRange) {
      var range = input.createTextRange();
      range.collapse(true);
      range.moveEnd('character', selectionEnd);
      range.moveStart('character', selectionStart);
      range.select();
    }
  }

  function setCaretToPos (input, pos) {
    setSelectionRange(input, pos, pos);
  }


  </script>
</head>

<body style="padding-top: 40px;">
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	
	<%
			String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null) {
			out.println("<script LANGUAGE=\"JavaScript\" type=\"text/javascript\">");
			out.println("alert(\""
					+ errorMessage.replace("\"", "\\\"").replace("\n",
							"\\n") + "\");");
			out.println("</script>");
				}
		%>
	
	<div class="container">
		<div class="row">
			<div
				class="col-xs-8 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2">

				<form action="ResultsPage?action=buscar" method="get">
					<div class="form-group text-center">
						<img class="img-responsive center-block" src="public/images/cedim.jpg"
							style="width: 40%; height: 40%;">

						<div class="input-group">
							<input type="text" autocomplete="off" class="form-control input-lg"
								id="search-query" name="search-query"
								placeholder="Type your query" required autocomplete="off"
								<%String query = (String) request.getAttribute("query");
									if(query != null)
										out.println("value=\"" + query.replace("\"", "&quot;") + "\"");%>>

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

	<div class="container">
		<div class="row">
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a
						href="#simple_results" aria-controls="simple_results" role="tab"
						data-toggle="tab"
						onclick="setTimeout(hide_graph, 300); scroll();"
						class="scroll_to_tab">Simple Results</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active"
						id="simple_results">

						<div class="list-group">

							<%
								@SuppressWarnings("unchecked")
													ArrayList<SimpleResults> simpleResults = (ArrayList<SimpleResults>) request.getAttribute("simpleResults");

													if (simpleResults != null) {
														for (int i = 0; i < simpleResults.size(); i++) {
							%>
										<form id="<%out.print(i);%>"
											action="ResultsPage?action=<%out.println(simpleResults.get(i).getDocumentName());%>"
											method="get">
											<input type="hidden" name="viewDoc"
												value="<%out.print(simpleResults.get(i).getDocumentName());%>" />
											<input type="hidden" name="trecho"
												value="<%out.print(simpleResults.get(i).getSlice()
												.replace("\"", "&quot;"));%>" />
											<div class="panel panel-default list-group-item">
												<div class="panel-body">
													<div class="media">
														<div class="media-left">
															<a href="javascript:{}"
																onclick="document.getElementById('<%out.print(i);%>').submit(); return false;"> <img class="media-object img-rounded"
																src="<% out.println("public/images/docs/" + simpleResults.get(i).getDocumentName().split("/")[2].replace(".txt", ".png"));%>" alt="..." width="90" height="120" >
															</a>
														</div>
														<div class="media-body">
															<h4 class="media-heading">
																<a href="javascript:{}"
																	onclick="document.getElementById('<%out.print(i);%>').submit(); return false;">
																	<%
																		out.println(simpleResults.get(i).getDocumentName().split("/")[2]);
																	%>
																</a>
															</h4>
															
															<label class="reference">
																<%
																	out.print("(" + simpleResults.get(i).getAuthor()
																																																													+ ", " + simpleResults.get(i).getSource() + ")");
																%>
															</label>
															<div id="div<%out.print(i);%>">
																<%
																	out.println(simpleResults.get(i).getSlice() + " [...]");
																															out.println("<br><br>");
																%>

															</div>
														</div>
													</div>
												</div>
											</div>
										</form>
										<br>
								<% 	}
								} %>
						</div>
						
						<%
							if(simpleResults.size() < 3) {
								for(int i = simpleResults.size(); i < 3; i++) { 
									out.print("<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br>");
								}
							}
						%>
						
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>