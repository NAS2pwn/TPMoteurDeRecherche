package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class ModeleBooleen {

	private IndexInverse indexInverse;
	//private Set<Document> s;
	
	
	public ModeleBooleen(IndexInverse i) {
		this.indexInverse=i;
	}
	
	public ArrayList<BooleanCriterium> parseRequest(String requete){
		String tokens[] = Crawler.simpleTokenizer.tokenize(requete.toLowerCase());
		
		ArrayList<BooleanCriterium> criteriums=new ArrayList<>();
		boolean hasOperator=false;
		Operator operator=Operator.or;
		
		for(int i=0;i<tokens.length;i++) {
			if(!hasOperator && !Operator.contains(tokens[i])) {
				if(i==0)
					criteriums.add(new BooleanCriterium(Operator.or, tokens[i]));
				else
					criteriums.add(new BooleanCriterium(Operator.and, tokens[i]));
			}
			else if(!hasOperator && Operator.contains(tokens[i])) {
				hasOperator=true;
				operator=Operator.valueOf(tokens[i]);
			}
			else {
				 criteriums.add(new BooleanCriterium(operator, tokens[i]));
				 hasOperator=false;
			}
		}
			
		return criteriums;
	}
	
	public Set<Document> searchThisShit(String requete){
		Set<Document> setDoc=new HashSet<>();
		
		ArrayList<BooleanCriterium> criteriums=parseRequest(requete);
		
		for(BooleanCriterium bc : criteriums) {
			Set<Document> setDoc1;
			System.out.println(bc);
			
			if(!this.indexInverse.containsTerme(bc.getTerme()))
				setDoc1=new HashSet<>();
			else
				setDoc1=this.indexInverse.getSetDoc(bc.getTerme());
			
			switch(bc.getOperator()) {
				case not:
					setDoc=setDoc.parallelStream().filter(d -> !setDoc1.contains(d)).collect(Collectors.toSet());
					/*Iterator<Document> it1=setDoc1.iterator();
					while(it1.hasNext()) {
						Document d=it1.next();
						if(setDoc.contains(d))
							setDoc.remove(d);
					}*/
					break;
				case or:
					for(Document d : setDoc1)
						setDoc.add(d);
					break;
				case and:
					System.out.println(setDoc);
					setDoc=setDoc.parallelStream().filter(d -> {System.out.println(d); return setDoc1.contains(d);}).collect(Collectors.toSet());
					System.out.println(setDoc);
					/*Iterator<Document> it=setDoc.iterator();
					while(it.hasNext()) {
						Document d=it.next();
						if(!setDoc1.contains(d)) {
							System.out.println("oh");
							setDoc.remove(d);
						}
					}*/
					break;
				default:
					throw new Error("C'pas tabou, c'pas tabou");
			}
		}
 		return setDoc;
	}
}
