package application;

public enum Operator {
	and(2,false), or(1,false), not(3,true);
	
	private int predominance;
	private boolean right;
	
	private Operator(int predominance, boolean right) {
		this.predominance=predominance;
		this.right=right;
	}
	
	public int getPredominance() {
		return this.predominance;
	}
	
	public static boolean contains(String test) {
	    for (Operator o : Operator.values())
	        if (o.name().equals(test))
	            return true;
	    return false;
	}
}
