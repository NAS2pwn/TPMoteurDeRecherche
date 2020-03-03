package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class ModeleBooleen {

	private IndexInverse indexInverse;
	//private Set<Document> s;
	
	
	public ModeleBooleen(IndexInverse i) {
		this.indexInverse=i;
	}
	
	/*public ArrayList<BooleanCriterium> parseRequest(String requete){
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
	}*/
	
	/*public Set<Document> searchThisShit(String requete){
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
					break;
				case or:
					for(Document d : setDoc1)
						setDoc.add(d);
					break;
				case and:
					System.out.println(setDoc);
					setDoc=setDoc.parallelStream().filter(d -> {System.out.println(d); return setDoc1.contains(d);}).collect(Collectors.toSet());
					System.out.println(setDoc);
					break;
				default:
					throw new Error("C'pas tabou, c'pas tabou");
			}
		}
 		return setDoc;
	}*/
	
	public Set<Document> getSetDoc(String terme){
		return this.indexInverse.getSetDoc(terme);
	}
	
	/*public CriteriumTree parseRequest(String request) {
		String tokens[] = Crawler.simpleTokenizer.tokenize(request);
		
		
	}*/
	
	public ArrayList<String> parseRequest(String request){
		String tokens[] = Crawler.simpleTokenizer.tokenize(request);
		Stack<String> operators=new Stack<>();
		ArrayList<String> rpn=new ArrayList<String>();
		for(int i=0;i<tokens.length;i++) {
			if(/*(*/Operator.contains(tokens[i])/*&&!(Operator.valueOf(tokens[i]).getPredominance()==Operator.valueOf(operators.lastElement()).getPredominance()))*/||tokens[i].equals("("))
				operators.push(tokens[i]);
			else if(tokens[i].equals(")")) {
				while(!operators.lastElement().equals("("))
					rpn.add(operators.pop());
				operators.pop();
			}
			else
				rpn.add(tokens[i]);
		}
		while(operators.size()>0) {
			rpn.add(operators.pop());
		}
		
		return rpn;
	}
	
	public Set<Document> applyOperator(Set<Document> doc1, Operator op, Set<Document> doc2){
		switch(op) {
			case or:
				for(Document d : doc2)
					doc1.add(d);
				break;
			case and:
				doc1=doc1.parallelStream().filter(d -> doc2.contains(d)).collect(Collectors.toSet());
				break;
			default:
				throw new Error("C'pas tabou, c'pas tabou");
		}
		
		return doc1;
	}
	
	public Set<Document> applyOperator(){
		switch(op) {
			case not:
				doc1=doc1.parallelStream().filter(d -> !doc2.contains(d)).collect(Collectors.toSet());
				break;
			default:
				throw new Error("C'pas tabou, c'pas tabou");
		}
	}
}
