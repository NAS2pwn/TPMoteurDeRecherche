<?php
	ini_set("display_errors", 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include_once("./constants.php");

	session_start();

	if(isset($_GET['module']))
		$module=$_GET['module'];
	else
		$module="default";

	if(isset($_GET['action']))// && preg_match("/^.*API$/"))
		$action=$_GET['action']."API";
	else
		$action="default";


	//J'essaye de respecter 1 ou 2 principes REST pour une future évolution si jamais ça a lieu d'être, là on n'a pas le temps de s'occuper de ça

	switch($_SERVER['REQUEST_METHOD']){
		case 'GET':
			include_once(MODULES.DS."mod_default".DS."mod_default.php");
			$moduleObj=new DefaultMod($action);
		break;	
	}
?>