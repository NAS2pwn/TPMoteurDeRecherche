package application;

public enum Operator {
	AND("&&",2,false,true), OR("||",3,false,true), NOT("!",1,true,true,true), OPEN_PARENTHESIS("(",0,false,false), CLOSE_PARENTHESIS(")",0,false,false);
	
	public String representation;
	public int predominance;
	public boolean right;
	public boolean forReal;
	public boolean unary;
	
	Operator(String representation, int predominance, boolean right, boolean forReal) {
		this.representation=representation;
		this.predominance=predominance;
		this.right=right;
		this.forReal=forReal;
		this.unary=false;
	}
	
	Operator(String representation, int predominance, boolean right, boolean forReal, boolean unary) {
		this.representation=representation;
		this.predominance=predominance;
		this.right=right;
		this.forReal=forReal;
		this.unary=unary;
	}
	
	public static Operator valueOfRep(String representation) {
		for (Operator o : Operator.values())
			if(o.representation.equals(representation))
				return o;
		
		throw new Error("Cet opérateur existe po");
	}
	
	public static boolean containsForReal(String representation) {
		for (Operator o : Operator.values())
			if(o.representation.equals(representation)&&o.forReal)
				return true;
		
		return false;
	}
	
	public static boolean containsLeft(String representation) {
		if(Operator.CLOSE_PARENTHESIS.representation.equals(representation))
			return false;
		
		for (Operator o : Operator.values())
			if(o.representation.equals(representation)&&!o.right)
				return true;
		
		return false;
	}
	
	public static boolean containsRight(String representation) {
		if(Operator.OPEN_PARENTHESIS.representation.equals(representation))
			return false;
		
		for (Operator o : Operator.values())
			if(o.representation.equals(representation))
				return true;
		
		return false;
	}
	
	public static boolean contains(String test) {
	    for (Operator o : Operator.values())
	        if (o.representation.equals(test))
	            return true;
	    return false;
	}
}
