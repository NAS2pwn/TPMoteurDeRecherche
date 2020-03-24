package application;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class CriteriumTree {
	private String value;
	private CriteriumTree filsG;
	private CriteriumTree filsD;
	
	public CriteriumTree() {
		this.value=null;
		this.filsG=null;
		this.filsD=null;
	}
	
	public CriteriumTree(String value) {
		this.value=value;
		this.filsG=new CriteriumTree();
		this.filsD=new CriteriumTree();
	}
	
	public CriteriumTree(ArrayList<String> postfix) {
		CriteriumTree t=this.buildTree(postfix);
		this.value=t.value;
		this.filsG=t.filsG;
		this.filsD=t.filsD;
	}
	
	private boolean estVide() {
		return (this==null||this.value==null);
	}
	
	public CriteriumTree buildTree(ArrayList<String> postfix) {
		Stack<CriteriumTree> stack = new Stack<>();
		CriteriumTree t;
		
		for(String s : postfix) {
			if(!Operator.contains(s)) {
				t=new CriteriumTree(s);
				stack.push(t);
			}
			else if(!Operator.valueOfRep(s).unary){
				t=new CriteriumTree(s);
				t.filsG=stack.pop();
				t.filsD=stack.pop();
				stack.push(t);
			}
			else {
				t=new CriteriumTree(s);
				t.filsG=stack.pop();
				stack.push(t);
			}
		}
		
		return stack.pop();
	}
	
	public ArrayList<String> preorder() {
		ArrayList<String> out=new ArrayList<>();
		out.add(this.value);
		if(!this.filsG.estVide())
			out.addAll(this.filsG.preorder());
		if(!this.filsD.estVide())
			out.addAll(this.filsD.preorder());
		return out;
	}
	
	public ArrayList<String> inorder() {
		ArrayList<String> out=new ArrayList<>();
		if(!this.filsG.estVide())
			out.addAll(this.filsG.inorder());
		out.add(this.value);
		if(!this.filsD.estVide())
			out.addAll(this.filsD.inorder());
		return out;
	}
	
	public ArrayList<String> postorder() {
		ArrayList<String> out=new ArrayList<>();
		if(!this.filsG.estVide())
			out.addAll(this.filsG.postorder());
		if(!this.filsD.estVide())
			out.addAll(this.filsD.postorder());
		out.add(this.value);
		return out;
	}
	
	private boolean estFeuille() {
		/*if(this.filsG.estVide()==!this.filsD.estVide())
			throw new Error("Soit c'est une feuille, soit c'est un opérateur à quoi tu joues poto"); et les unaires lo réflechis*/
			
		return (this.filsG.estVide() && this.filsD.estVide());
	}
	
	public Set<Document> calcAll(){
		System.out.println(this.value);
		if(this.estFeuille())
			return ServletHehe.modeleBooleen.getSetDoc(this.value);
		
		if(!Operator.contains(this.value))
			throw new Error("arbre invalide");
		
		if(Operator.valueOfRep(this.value).unary) {
			if(!this.filsG.estVide())
				return ServletHehe.modeleBooleen.applyOperator(Operator.valueOfRep(this.value), this.filsG.calcAll());
			else
				return ServletHehe.modeleBooleen.applyOperator(Operator.valueOfRep(this.value), this.filsD.calcAll());
		}
		else {
			Set<Document> out=ServletHehe.modeleBooleen.applyOperator(this.filsG.calcAll(), Operator.valueOfRep(this.value), this.filsD.calcAll());
			
			if(!this.filsG.estVide() && !Operator.contains(this.filsG.getValue()))
				ServletHehe.modeleBooleen.applyTermeOnSet(this.filsG.getValue(), out);
			
			if(!this.filsD.estVide() && !Operator.contains(this.filsD.getValue()))
				ServletHehe.modeleBooleen.applyTermeOnSet(this.filsD.getValue(), out);
			
			return out;
		}
	}
	
	public String getValue() {
		return this.value;
	}
	
	public CriteriumTree getFilsG() {
		return this.filsG;
	}
	
	public CriteriumTree getFilsD() {
		return this.filsD;
	}
}
