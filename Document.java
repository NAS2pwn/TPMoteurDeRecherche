package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Document{
	public Map<String,Frequences> termes;
	private String docno;
	private String date;
	private String title;
	private int longueur;
	
	public Document() {
		this.termes = new HashMap<>();
		this.longueur=0;
	}
	
	public void setDocno(String docno) {
		this.docno=docno;
	}
	
	public String getDocno() {
		return this.docno;
	}
	
	public void setDate(String date) {
		this.date=date;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}

	public Map<String, Frequences> getTermes() {
		return termes;
	}

	public void setTermes(Map<String, Frequences> termes) {
		this.termes = termes;
	}
	
	
	public void addTermes (List<String> listeTerme) {
		 for( String s : listeTerme) {
			 if(!s.equals("")) {
				 this.longueur++;
				 if(!this.termes.containsKey(s))
					 this.termes.put(s, new Frequences(1));
				 else
					 this.termes.get(s).addOcc();
			 }
		 }
	}
	
	public void calcFrequence() {
		for (Map.Entry<String, Frequences> entry : this.termes.entrySet()) {
	        entry.getValue().calcFrequence(this.longueur);
	    }
	}
	
	public static ArrayList<String> rmStopWords(ArrayList<String> in, TreeSet<String> stopWords){
		//int hohoho=0;
		Iterator<String> it=in.iterator();
		String word="";
		while(it.hasNext()) {
			/*System.out.println(hohoho);
			hohoho++;*/
			word=it.next();
			if(stopWords.contains(word))
				it.remove();
		}
		return in;
	}
	
	@Override
	public String toString() {
		return "Document "+this.title+" Date "+this.date+" n°"+this.docno;
	}
	
}
