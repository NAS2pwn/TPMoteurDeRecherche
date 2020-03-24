package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Document{
	public static int maxNbLikes;
	public Map<String,Frequences> termes;
	private String docno;
	private String date;
	private String title;
	private int longueur;
	private int nbLikes;
	private double pertinence;
	private double boolGlobalFrequence;
	
	public Document() {
		this.pertinence=0;
		this.boolGlobalFrequence=0;
		this.termes = new HashMap<>();
		this.longueur=0;
		this.nbLikes=0;
	}
	
	public void calcBoolPertinence(String terme) {
		this.boolGlobalFrequence+=this.termes.get(terme).getFrequence();
		this.pertinence=this.boolGlobalFrequence*(1+(Document.maxNbLikes==0 ? 0 : (this.nbLikes/Document.maxNbLikes)*ModeleBooleen.INFLUENCE_LIKES_RATIO));
	}
	
	public double getPertinence() {
		return this.pertinence;
	}
	
	public int getNbLikes() {
		return this.nbLikes;
	}
	
	public int getNbOccurencesTotale() { 
		int out=0;
		for(Map.Entry<String, Frequences> terme : this.termes.entrySet()) 
			out+=terme.getValue().getNbOc();
		return out;
	}
	
	public void like() {
		this.nbLikes++;
		if(this.nbLikes>Document.maxNbLikes)
			Document.maxNbLikes=this.nbLikes;
	}
	
	public void unlike() {
		if(this.nbLikes>0)
			this.nbLikes--;
		/*else
			throw new Error("bon bref t'as capté");*/
	}
	
	public void clickTerme(String terme) {
		if(termes.containsKey(terme))
			termes.get(terme).click();
		/*else
			throw new Error("bon bref t'as capté");*/
	}
	
	public void clickTermes(String[] termes) {
		for(int i=0;i<termes.length;i++)
			this.clickTerme(termes[i]);
	}
	
	public int getNbOc(String terme) {
		return this.termes.get(terme).getNbOc();
	}
	
	public double getTfIDF(String terme) {
		return this.termes.get(terme).getTfIDF();
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
	
	public String getDate() {
		return this.date;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public String getTitle() {
		return this.title;
	}

	public Map<String, Frequences> getTermes() {
		return termes;
	}
	
	public boolean containsTerme(String terme) {
		return this.termes.containsKey(terme);
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
		Iterator<String> it=in.iterator();
		String word="";
		while(it.hasNext()) {
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
