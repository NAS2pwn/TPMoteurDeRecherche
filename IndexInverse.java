package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class IndexInverse {
	public TreeMap<String,Set<Document>> indexInverse;
	
	public IndexInverse() {
		this.indexInverse=new TreeMap<>();
	}
	
	public Map<String,Set<Document>> getIndexInverse(){
		return this.indexInverse;
	}
	
	public Set<Document> getSetDoc(String terme){
		return this.indexInverse.get(terme);
	}
	
	public boolean containsTerme(String terme){
		return this.indexInverse.containsKey(terme);
	}
	
	public SortedSet<String> getTermesSorted(){
		return (SortedSet<String>) this.indexInverse.keySet();
	}
	
	public void addTermes(List<String> listeS, Document d) {
		//System.out.println(d);
		Set<Document> temp;
		for(String s : listeS) {
			if(this.indexInverse.containsKey(s)) {
				Set<Document> setSearched=this.indexInverse.get(s);
				if(setSearched.contains(d)) {
					setSearched.remove(d);
					setSearched.add(d);
				}
				else
					setSearched.add(d);
			}
			else {
				temp=new HashSet<>();
				temp.add(d);
				this.indexInverse.put(s, temp);
			}
		}	
	}
	
	@Override
	public String toString() {
		String out="[\n";
		boolean beginning=true;
		for (Map.Entry<String, Set<Document>> entry : this.indexInverse.entrySet()) {
			if(beginning)
				beginning=false;
			else
				out+=", ";
	        out+=entry.getKey().toString()+" : {";
	        for (Document d : entry.getValue()) {
	        	out+=d.getDocno()+", ";
	        }
	        out+="}\n";
	    }
		out+="]";
		return out;
	}

}
