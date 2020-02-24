<?php
require_once(ROOT.DS."generic_view.php");

class DefaultView extends GenericView{
	function jsonXML($XML){
		echo json_encode($XML);
	}
}
?>