package motor;

import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		MotorEvaluation m = new MotorEvaluation();
		Map<Integer, Double> trueMap = new HashMap<>();
		Map<Integer, Double> falseMap = new HashMap<>();
		Map<Integer, Double> mapMotor = new HashMap<>();

		m.saisirDocsPertinents("src/motor/doc");
		
		//ON LANCE LE MOTEUR ET LE MINUTEUR
		m.timeStart();
		
		/*m.setFalseDoc((HashMap<Integer, Double>) falseMap);
		m.setTrueDoc((HashMap<Integer, Double>) trueMap);*/
		Map<String,Double> mapFinal = m.mesureEval((HashMap<Integer, Double>) mapMotor);
		System.out.println(mapFinal);
		
		
		
	}
}
