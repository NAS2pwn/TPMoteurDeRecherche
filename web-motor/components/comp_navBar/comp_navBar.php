<?php
	include_once(COMPONENTS.DS."comp_navBar".DS."cont_navBar.php");

	class NavBarComp{
		private $controller;

		public function __construct(){
			$this->controller=new NavBarCont();
		}

	}
?>