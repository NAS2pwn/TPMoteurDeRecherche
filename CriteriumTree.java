package application;

import java.util.ArrayList;
import java.util.Set;

public class CriteriumTree {
	private String value;
	private CriteriumTree filsG;
	private CriteriumTree filsD;
	
	public boolean estVide() {
		return (this==null||this.value==null);
	}
	
	public boolean estFeuille() {
		if(this.filsG.estVide()==!this.filsD.estVide())
			throw new Error("Soit c'est une feuille, soit c'est un opérateur à quoi tu joues poto");
			
		return (this.filsG.estVide() && this.filsD.estVide());
	}
	
	public Set<Document> calcAll(){
		if(this.estFeuille())
			return ServletHehe.modeleBooleen.getSetDoc(this.value);
		
		if(!Operator.contains(this.value))
			throw new Error("arbre invalide");
		
		return ServletHehe.modeleBooleen.applyOperator(this.filsG.calcAll(), Operator.valueOf(this.value), this.filsD.calcAll());
	}
}
