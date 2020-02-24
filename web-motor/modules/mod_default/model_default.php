<?php
class DefaultModel{
	public function __construct(){
		
	}


	function xml2array ( $xmlObject, $out = array () ){
	    foreach ( (array) $xmlObject as $index => $node )
	        $out[$index] = ( is_object ( $node ) ) ? xml2array ( $node ) : $node;
	    return $out;
	}

	public function getArrayTexte($name){
		$dir = scandir("./docs");
		$result = "";
		//var_dump($dir);
		foreach ($dir as $key => $value) {
			if($value!=='./' && $value!=='../'){
				foreach (scandir("./docs/".$value) as $k => $val) {
					if($name===$val){
						return simplexml_load_file(htmlspecialchars("./docs/".$value.'/'.$val));
					}
				}
			}
		}
		return $result;
	}
}
?>