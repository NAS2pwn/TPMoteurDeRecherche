package application;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Index {
	
	private Set<Document> listeDocuments;
	
	public Index() {
		super();
		this.listeDocuments = new HashSet<>();
	}

	public Set<Document> getListeDocuments() {
		return listeDocuments;
	}

	public void setListeD(Set<Document> listeD) {
		this.listeDocuments = listeD;
	}

	public void ajouterDoc(Document d) {
		//System.out.println(d.getUrl());
		this.listeDocuments.add(d);
	}
	
	
}