package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class TraitementDocument {
	
	public static String extraireTexte(String url) throws IOException {
		BufferedReader lecteurAvecBuffer = null;
	    String ligne;
	    String texte="";

	    try{
	    	lecteurAvecBuffer = new BufferedReader(new FileReader(url));
	    }
	    catch(FileNotFoundException exc){
	    	System.out.println("Who said issou");
	    }
	    while ((ligne = lecteurAvecBuffer.readLine()) != null)
	      texte+=ligne;
	    lecteurAvecBuffer.close();
	    return texte;
	}
	
	public static String enleveBalise(String texte) {
		return texte.replaceAll("<[^>]*>", "");
	}
	
	public static String sanitize(String texte) {
		texte=texte.replaceAll("\n", " ");
		texte=texte.replaceAll("[.(),\"\t'`]", "");
		return texte.replaceAll("\\s+", " ");
	}
	
	public static List<String> splitDoc(String texte) {
		String[] gaco=texte.split(" ");
		return Arrays.asList(gaco);
	}
	
	//Dans le constructeur lis le fichier puis split('\n') mais ça va finir dans le crawler après

	public static List<String> rmStopWords(List<String> in, List<String> stopWords){
		Iterator<String> it=in.iterator();
		String word="";
		while(it.hasNext())
			word=(String) it.next();
			for (String s : stopWords){
				if(word.equals(s))
					it.remove();
			}
			
		return in;
	}
	
	
}
