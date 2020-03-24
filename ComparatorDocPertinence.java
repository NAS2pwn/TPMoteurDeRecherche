package application;

import java.util.Comparator;

public class ComparatorDocPertinence implements Comparator<Document>{

	@Override
	public int compare(Document arg0, Document arg1) {
		Double pert1=arg0.getPertinence();
		Double pert2=arg1.getPertinence();
		
		if (pert1!=pert2)
			return pert1.compareTo(pert2);
		else if (arg0.getNbLikes()!=arg1.getNbLikes())
			return arg0.getNbLikes()-arg1.getNbLikes();
		
		return 0;
	}
	
}
