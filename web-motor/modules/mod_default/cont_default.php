<?php
	include_once(MODULES.DS."mod_default".DS."view_default.php");
	include_once(MODULES.DS."mod_default".DS."model_default.php");

	class DefaultCont{
		private $view;
		private $model;

		public function __construct($whatDoUDo){
			$this->view=new DefaultView();
			$this->modele=new DefaultModel();
			switch($whatDoUDo){
				case "getTexteAPI":
					if(isset($_GET['doc'])){
						$this->view->jsonXML($this->modele->getArrayTexte(htmlspecialchars($_GET['doc'])));
					}
					break;
			}
		}

		public function getView(){
			return $this->view;
		}
	}
?>