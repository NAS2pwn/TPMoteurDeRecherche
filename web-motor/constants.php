<?php
	define('EXPIRE', 2592000); //Expire au bout de 30 jours
	define('MAX_LENGTH_NAME', 64);
	define('MAX_LENGTH_USERNAME', 32);
	define('MIN_LENGTH_USERNAME', 4);
	define('MAX_LENGTH_PASSWORD', 64);
	define('MIN_LENGTH_PASSWORD',9);
	define('MIN_AGE', 13);
	define('MAX_AGE', 99);
	define('ROOT',dirname(__FILE__));
	define('DS',DIRECTORY_SEPARATOR);
	define('MODULES',ROOT.DS.'modules');
	define('PLUGINS',ROOT.DS.'plugins');
	define('COMPONENTS', ROOT.DS.'components');
	define('MEDIAS', 'medias');
	define('PICTURES',MEDIAS.DS.'pictures');
	define('PROFILE_PICS',PICTURES.DS.'profiles');
	define('RETURN_FAILURE', 1);
	define('RETURN_SUCCESS', 2);
	define('API_NOT_FOUND', "Nothing here");
	define('POSITION_NOT_FOUND', "N/A");
	define('DEFAULT_NBLINES', 15);
	define('GUEST_ROLE',0);
	define('ADMIN_ROLE',1);//T'as bien intérêt à strict compare mon pote ===
	define('UNCHECKED',0);
	define('NOT_VALID',1);
	define('VALID',2);
?>
