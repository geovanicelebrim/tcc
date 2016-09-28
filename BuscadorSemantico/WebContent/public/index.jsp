<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Semantic Search</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="./public/icons/apple-touch-icon.png">
        <link rel="shortcut icon" href="./public/icons/favicon.ico">

        <link rel="stylesheet" href="./public/css/bootstrap.min.css">
        <link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="./public/css/main.css">

        <!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->
        
        
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
 
    		$( "#search-query" ).on( "keydown", function( event ) {
    			
        		if ( event.keyCode === $.ui.keyCode.TAB &&
            	$( this ).autocomplete( "instance" ).menu.active ) {
          			event.preventDefault();
        		}
      		}).autocomplete({
        		minLength: 0,
        		source: function( request, response ) {
          		// delegate back to autocomplete, but extract the last term
          		var e = document.getElementById("search-mode");
        		var strUser = e.options[e.selectedIndex].value;
        		if(strUser == "semantic") {
          			response( $.ui.autocomplete.filter(
            		availableTags, extractLast( request.term ) ) );
        		}
          		
        	},focus: function() {
          		// prevent value inserted on focus
          		return false;
        	},select: function( event, ui ) {
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
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

      <div class="container fill">
        <div class="row vertical-center">
          <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2">

            <form action="MainPage?action=search" method="get">
              <div class="form-group text-center">
              	<img class="img-responsive center-block" src="./public/images/cedim.jpg" style="width:70%;height:70%;">
                
                <div class="input-group">
                    <input type="text" class="form-control input-lg" id="search-query" name="search-query" placeholder="Type your query" required autocomplete="off">

                    <div class="input-group-btn">
                      <select name="search-mode" class="form-control input-lg" id="search-mode">
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
