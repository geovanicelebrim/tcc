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
        <link rel="apple-touch-icon" href="./publica/icons/apple-touch-icon.png">
        <link rel="shortcut icon" href="./publica/icons/favicon.ico">

        <link rel="stylesheet" href="./publica/css/bootstrap.min.css">
        <link rel="stylesheet" href="./publica/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="./publica/css/main.css">

        <!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->
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
              	<img class="img-responsive center-block" src="./publica/images/cedim.jpg" for="search-query" style="width:70%;height:70%;">
                
                <div class="input-group">
                    <input type="text" class="form-control input-lg" id="search-query" name="search-query" placeholder="Type your query">

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

      <script src="./publica/js/vendor/jquery-1.11.2.min.js"></script>
      <script src="./publica/js/vendor/bootstrap.min.js"></script>
      <script src="./publica/js/main.js"></script>
    </body>
</html>
