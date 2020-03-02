<?php
	include_once(COMPONENTS.DS."comp_navBar".DS."model_navBar.php");
	include_once(COMPONENTS.DS."comp_navBar".DS."view_navBar.php");

	class NavBarCont{
		private $view;
		private $model;

		public function __construct(){
			$this->view=new NavBarView();
			$this->model=new NavBarModel();
			$this->menu();
		}

		public function menu(){
			$this->view->openList();

			$this->view->displayMenu($this->model->getMenuAction("profile"));
			$this->view->displayMenu($this->model->getMenuAction("event"));
			$this->view->displayMenu($this->model->getMenuAction("stats"));
			$this->view->firstList();
			$this->view->closeList2();
		


		}
	}
?>
