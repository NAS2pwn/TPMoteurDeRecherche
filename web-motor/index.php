<?php
	ini_set("display_errors", 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
setlocale(LC_ALL, 'fr_FR.UTF8', 'fr_FR','fr','fr','fra','fr_FR@euro');
	include_once("./constants.php");

	session_start();

?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SocialWorkout</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>SocialWorkout</title>
	<link href="style.css" rel="stylesheet" media="all" type="text/css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
   integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
   crossorigin=""/>
	<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
   integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
   crossorigin=""></script>
   <link href="style.css" rel="stylesheet" media="all" type="text/css">
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>

    <script src="https://code.highcharts.com/highcharts.js"></script>
    <!--<script src="./assets/js/affix.js"></script>-->
    <style>

    	@media (max-width: 544) {
		    .textSized{
	    		font-size:0.5em;
	    	}
		}

		@media (max-width: 768) {
		    .textSized{
	    		font-size:0.3em;
	    	}
		}

	</style>
</head>
<body>
	<header>
		<nav>
		<?php
			include_once(COMPONENTS.DS."comp_navBar".DS."comp_navBar.php");
			$navBar=new NavBarComp();
		?>
		</nav>
	</header>
		<article id="article" class="col-xl-8 col-lg-9 col-md-10 col-sm-12 col-xs-12 mx-auto w-100 p-4" style="margin-top: 7%">
			
		</article>
		<div>

		</div>
<!-- jsuis debile sérieux https://getbootstrap.com/docs/4.0/layout/grid/ script>
	var size={"xs" : 544, "sm" : 768, "md" : 992};
	var article=$("article");
	$(window).resize(function(){
	        	if($(window).width()<=size["sm"])
	        		article.removeClass("col-8 col-10").addClass("col-12");
	        	else if($(window).width()<=size["md"])
	        		article.removeClass("col-8 col-12").addClass("col-10");
	        	else
	        		article.removeClass("col-10 col-12").addClass("col-8");
	});

	$(document).ready(function() {
				article=$("article");
	            if($(window).width()<=size["sm"])
	        		article.removeClass("col-8 col-10").addClass("col-12");
	        	else if($(window).width()<=size["md"])
	        		article.removeClass("col-8 col-12").addClass("col-10");
	        	else
	        		article.removeClass("col-10 col-12").addClass("col-8");
	});
</script>-->
	</div>
</body>
</html>
