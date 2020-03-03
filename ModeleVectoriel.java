package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collectors;

public class ModeleVectoriel {
	private Index i;
	private IndexInverse iinv;
	private Map<Document,Vector<Double>> collection;
	
	public ModeleVectoriel(Index i, IndexInverse iinv) {
		this.collection=new HashMap<>();
		this.i=i;
		this.iinv=iinv;
		this.calcCollection();
	}
	
	private Vector<Double> calcVectDoc(Document d){
		Vector<Double> temp=new Vector<>();
		for(String terme : this.iinv.getTermesSorted()) {
			if(d.containsTerme(terme))
				temp.add(d.getTfIDF(terme));
			else
				temp.add(0d);
		}
		return temp;
	}
	
	private void calcCollection() {
		for(Document d : this.i.getListeDocuments())
			this.collection.put(d,this.calcVectDoc(d));
	}
	
	public List<Document> completeSearch(String requete) {
		Document reqDoc=this.interpreteur(requete);
		Vector<Double> reqVect=this.calcVectDoc(reqDoc);
		Map<Document,Double> results=searchVector(reqVect);
		return this.sortSearch(results);
	}
	
	private Document interpreteur(String requete) {
		Document out=new Document();
		ArrayList<String> requeteNormalized=Crawler.traiterString(requete);
		out.addTermes(requeteNormalized);
		return out;
	}
	
	private List<Document> sortSearch(Map<Document,Double> results){
		return results.entrySet().parallelStream().filter(x -> x.getValue()!=0.0).sorted(Map.Entry.comparingByValue()).map(x -> x.getKey()).collect(Collectors.toList());
	}

	private Map<Document,Double> searchVector(Vector<Double> requete) {
		Map<Document,Double> out=new HashMap<>();
		
		for(Entry<Document, Vector<Double>> entry : this.collection.entrySet())
			out.put(entry.getKey(),this.calcSimilarite(requete, entry.getValue()));
		
		return out;
	}
	
	private double calcSimilarite(Vector<Double> requete, Vector<Double> document) {
		//On va calculer le cosinus entre les deux vecteurs :
		//sim(r,d)=cos(theta)=(n sum i=1 (d[i]*q[i])/( sqrt(n sum i=1 (d[i])^2) * sqrt(n sum i=1 (q[i])^2) )
		double numerateur=0;
		for(int i=0; i<requete.size(); i++)
			numerateur+=requete.get(i)*document.get(i);
			
		double denomRequete=0;
		for(int i=0;i<requete.size();i++)
			denomRequete+=requete.get(i)*requete.get(i);
		denomRequete=Math.sqrt(denomRequete);
		
		double denomDoc=0;
		for(int i=0;i<document.size();i++)
			denomDoc+=document.get(i)*document.get(i);
		denomDoc=Math.sqrt(denomDoc);
		
		return numerateur/(denomDoc*denomRequete);
	}
}
