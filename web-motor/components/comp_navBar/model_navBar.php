<?php
	class NavBarModel{
		private $menuNames;
		private $menuPictures;

		public function __construct(){
			$this->menuNames=array("connection" => array("index.php?module=connection","Connexion"), "disconnection" => array("index.php?module=connection&action=disconnection","Déconnexion"), "register" => array("index.php?module=registration", "Inscription"));
			$this->menuPictures=array("home" => array("index.php","home_icone.png","Icone accueil, petit chalêt cosy minimaliste"));
			$this->menuAction=array("profile" => array("index.php?module=profile&action=profile",'<i class="fas fa-user-circle"></i>'), "stats" => array("index.php?module=stats&action=stats",'<i class="fas fa-eye" data-toggle="tooltip" data-placement="bottom" title="<small>Statistique</small>" data-html="true"></i>'), "event" => array("index.php?module=event",'<i class="fas fa-calendar-alt"></i>'));
		}
		public function getArray(string $name){
			return $this->menuNames[$name];
		}

		public function getPicArray(string $name){
			return $this->menuPictures[$name];
		}

		public function getMenuAction(string $name){
			return $this->menuAction[$name];
		}

		public function getName(string $name){
			return $this->menuNames[$name][1];
		}

		public function getLink(string $name){
			return $this->menuNames[$name][0];
		}

		public function getPicLink(string $name){
			return $this->menuPictures[$name][0];
		}

		public function getPicturePath(string $name){
			return $this->menuPictures[$name][1];
		}

		public function getPictureAlt(string $name){
			return $this->menuPictures[$name][2];
		}
	}
?>
