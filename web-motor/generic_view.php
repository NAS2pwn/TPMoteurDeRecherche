<?php
class GenericView{

	public function __construct(){
		ob_start();
		ob_implicit_flush(0);
	}

	public function getDisplay(){
		return ob_get_clean();
	}

	public function displayParagraph(String $content){
		echo "<p>".$content."</p>";
	}

	public function displayParagraphConfirmation(String $content, $class="alert alert-success"){
		echo "<p class=\"".$class."\">".$content."</p>";
	}

	public function displayParagraphError(String $content,$class="alert alert-danger"){
		echo "<p class=\"".$class."\">".$content."</p>";
	}

	public function beginDiv($id){
		echo "<div id=\"{$id}\">";
	}

	public function endDiv(){
		echo "</div>";
	}

	public function div($id){
		echo "<div id=\"{$id}\"></div>";
	}
}
?>