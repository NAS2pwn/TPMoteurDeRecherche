<?php

include_once(MODULES.DS."mod_default".DS."cont_default.php");

class DefaultMod{

	private $controller;

	public function __construct($whatDoUDo){
		$this->controller=new DefaultCont($whatDoUDo);
	}

	public function getDisplay(){
		return $this->controller->getView()->getDisplay();
	}
}

?>