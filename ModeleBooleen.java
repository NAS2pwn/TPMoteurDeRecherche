package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModeleBooleen {
	
	public static final double INFLUENCE_LIKES_RATIO=1.5;
	private Index index;
	private IndexInverse indexInverse;
	public static Pattern parenthesisPattern;
	//private Set<Document> s;
	
	
	public ModeleBooleen(Index i, IndexInverse iinv) {
		this.index=i;
		this.indexInverse=iinv;
		parenthesisPattern=Pattern.compile("[()][()]+");
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
		if(this.indexInverse.containsTerme(terme))
			return this.indexInverse.getSetDoc(terme);
		
		return new HashSet<Document>();
	}
	
	public SortedSet<Document> search(String request) {
		ArrayList<String> liste=this.parseRequest(request);
		CriteriumTree t=new CriteriumTree(liste);
		System.out.println("Preorder : "+t.preorder()+"\nInorder : "+t.inorder()+"\nPostorder : "+t.postorder());
		System.out.println(t.getValue());
		System.out.println(t.getFilsG().getValue());
		System.out.println(t.getFilsD().getValue());
		Set<Document> unordered=t.calcAll();
		return orderResults(unordered);
	}
	
	public ArrayList<String> parseRequest(String request){
		String tokens[] = Crawler.simpleTokenizer.tokenize(request);
		ArrayList<String> req=new ArrayList<>();
		for(int i=1;i<=tokens.length;i++) {
			if(parenthesisPattern.matcher(tokens[i-1]).matches()) {
				System.out.println("oui oui oui ouiiiii");
				for(int j=0;j<tokens[i-1].length();j++) {
					req.add(Character.toString(tokens[i-1].charAt(j)));
				}
			}
			else {
				req.add(tokens[i-1]);
			}
		}
		int i=1;
		while(i<req.size()) {
			if(!Operator.containsLeft(req.get(i-1)) && !Operator.containsRight(req.get(i))) {
				req.add(i,Operator.AND.representation);
			}
			i++;
		}
		System.out.println("Requête : "+req);
		Stack<String> operators=new Stack<>();
		ArrayList<String> rpn=new ArrayList<String>();
		Boolean hehe=false;
		for(String s : req) {
			if((Operator.containsForReal(s)&&(!hehe||Operator.valueOfRep(s).predominance<=Operator.valueOfRep(operators.peek()).predominance))||s.equals("(")) {
				hehe=true;
				operators.push(s);
			}
			else if(s.equals(")")||(hehe&&Operator.valueOfRep(s).predominance>Operator.valueOfRep(operators.peek()).predominance)) {
				while(operators.size()>1&&(!operators.lastElement().equals("(")||Operator.valueOfRep(s).predominance>Operator.valueOfRep(operators.peek()).predominance))
					rpn.add(operators.pop());
				operators.pop();
			}
			else
				rpn.add(s);
			hehe=false;
		}
		while(operators.size()>0) {
			rpn.add(operators.pop());
		}
		
		return rpn;
	}
	
	public SortedSet<Document> applyOperatorFinal(Set<Document> doc1, Operator op, Set<Document> doc2){
		Set<Document> docs=this.applyOperator(doc1, op, doc2);
		
		SortedSet<Document> out=new TreeSet<Document>(new ComparatorDocPertinence());
		
		for(Document d : docs)
			out.add(d);
		
		return out;
	}
	
	public SortedSet<Document> orderResults(Set<Document> docs){
		SortedSet<Document> out=new TreeSet<Document>(new ComparatorDocPertinence());
		
		for(Document d : docs)
			out.add(d);
		
		return out;
	}
	
	public Set<Document> applyOperator(Set<Document> doc1, Operator op, Set<Document> doc2){
		switch(op) {
			case OR:
				for(Document d : doc2)
					doc1.add(d);
				break;
			case AND:
				doc1=doc1.parallelStream().filter(d -> doc2.contains(d)).collect(Collectors.toSet());
				break;
			default:
				throw new Error("C'pas tabou, c'pas tabou");
		}
		
		return doc1;
	}
	
	public Set<Document> applyOperator(Operator op, Set<Document> doc2){
		Set<Document> doc1=this.index.getListeDocuments();
		
		switch(op) {
			case NOT:
				doc1=doc1.parallelStream().filter(d -> !doc2.contains(d)).collect(Collectors.toSet());
				break;
			default:
				throw new Error("C'pas tabou, c'pas tabou");
		}
		
		return doc1;
	}
	
	public void applyTermeOnSet(String terme, Set<Document> docs) {
		for(Document d : docs)
			d.calcBoolPertinence(terme);
	}
}
