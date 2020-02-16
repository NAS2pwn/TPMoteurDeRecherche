package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModeleBooleen {
	
	private IndexInverse indexInverse;
	private String requete;
	//private Set<Document> s;
	
	
	public ModeleBooleen(String r, IndexInverse i) {
		this.indexInverse=i;
		this.requete=r;
	}
	
	public Set<Document> issou(){
		Set<Document> setDoc;
		Set<Document> setDoc1;
		String[] prout = this.requete.split(" ");
		
		
		setDoc = indexInverse.getSetDoc(prout[0]);
		String operator = prout[1];
		setDoc1 = indexInverse.getSetDoc(prout[2]);
		
		switch(operator) {
			case "NOT":
				for(Document d : setDoc1)
					if(setDoc.contains(d))
						setDoc.remove(d);
				break;
			case "OR":
				for(Document d : setDoc1)
					setDoc.add(d);
				break;
			case "AND":
				for(Document d : setDoc)
					if(!setDoc1.contains(d))
						setDoc.remove(d);
				break;
			default:
				System.out.println("C'pas tabou, c'pas tabou");
		}
		
 		return setDoc;
	}
}
