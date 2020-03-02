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
	
	private void calcCollection() {
		Vector<Double> temp;
		for(Document d : this.i.getListeDocuments()) {
			temp=new Vector<>();
			for(String terme : this.iinv.getTermesSorted()) {
				if(d.containsTerme(terme))
					temp.add(d.getNbOc(terme));
				else
					temp.add(0);
			}
			this.collection.put(d,temp);
		}
	}
	
	public List<Document> sortedSearch(Vector<Integer> requete){
		Map<Document,Double> mapSearch=this.search(requete);
		return mapSearch.entrySet().parallelStream().filter(x -> x.getValue()!=0.0).sorted(Map.Entry.comparingByValue()).map(x -> x.getKey()).collect(Collectors.toList());
	}

	public Map<Document,Double> search(Vector<Integer> requete) {
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
