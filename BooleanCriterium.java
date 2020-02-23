package application;

public class BooleanCriterium {
	private Operator operator;
	private String terme;
	
	public BooleanCriterium(Operator operator, String terme) {
		this.operator=operator;
		this.terme=terme;
	}
	
	public Operator getOperator() {
		return this.operator;
	}
	
	public String getTerme() {
		return this.terme;
	}
	
	@Override
	public String toString() {
		return "[BooleanCriterium "+this.operator.toString()+" "+this.terme+"]";
	}
}
