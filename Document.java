package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	
	@Override
	public String toString() {
		return "Document "+this.title+" Date "+this.date+" n°"+this.docno;
	}
	
}
