package application;

public enum Operator {
	and, or, not;
	
	public static boolean contains(String test) {
	    for (Operator o : Operator.values())
	        if (o.name().equals(test))
	            return true;
	    return false;
	}
}
