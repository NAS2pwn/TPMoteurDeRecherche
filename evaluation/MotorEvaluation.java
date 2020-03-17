package motor;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class MotorEvaluation {
	
	//Au début du main
	private double timeStart;
	private HashMap<Integer,Double> doc;
	private String requete;
	private int nbFalse;
	private int nbTrue;
	
	public MotorEvaluation() {
		this.doc = new HashMap<>();
	}
	
	public void timeStart() {
		this.timeStart=System.currentTimeMillis();
	}
	
	public void saisirDocsPertinents(String rep) {
		File repertoire = new File(rep);
        String liste[] = repertoire.list();      
        Scanner sc = new Scanner(System.in);
        String test = "";
        int test1 = 0;
        
        
        if (liste != null) {
            for (int i = 0; i < liste.length; i++) {
            	do {
            		System.out.println("Le document " + liste[i] + " est-il pertinent selon vous ? y/n");
            		test = sc.nextLine();
            		
            	}while(test.equals("y") && test.equals("n"));
            	
            	if(test=="y") {
            		System.out.println("Quel est son classement ?");
        			test1 = sc.nextInt();
        			doc.put(i, (double) test1);
        			nbTrue++;
            	}
            	else {
            		doc.put(i, (double) 0);
            		nbFalse++;
            	}
            	
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
	}
	
	public void requete(String r) {
		this.requete=r;
	}
	
	/*public void setTrueDoc(HashMap<Integer,Double> m) {
		this.trueDoc = m;
	}
	
	public void setFalseDoc(HashMap<Integer,Double> m) {
		this.falseDoc = m;
		/*Iterator iterator = this.totalDoc.entrySet().iterator();
        while (iterator.hasNext()) {
        	Map.Entry mapentry = (Map.Entry) iterator.next();
        	if(!this.trueDoc.containsKey(mapentry.getKey()))
        		falseDoc.put((Integer)mapentry.getKey(), (Double)mapentry.getValue());
        }*/
		
	//}
	
	
	//retourne les mesures
	public Map<String,Double> mesureEval(HashMap<Integer,Double> m){
		int vp = 0;
		int fp = 0;
		int fn = 0;
		int vn = nbFalse;
		Iterator iterator = m.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry mapentry = (Map.Entry) iterator.next();
          if(this.doc.containsKey(mapentry.getKey()) && this.doc.get(mapentry.getKey()).equals(mapentry.getValue())) {
        	  vp++;
          }
          else {
        	  fp++;
        	  fn++;
        	  vn--;
          }
        }
        System.out.println(vp);
        System.out.println(fp);
        System.out.println(fn);
        System.out.println(vn);
        
		Map<String,Double> map = new HashMap<>();
		map.put("tmpExecution",System.currentTimeMillis()-this.timeStart);
		map.put("precision", calculPrecision(vp,fp));
		map.put("rappel", calculRappel(vp,fn));
		map.put("justesse", calculJustesse(vp,vn,fp,fn));
		map.put("tvp", calculTVP(vp,fn));
		map.put("tfp", calculTFP(vn,fp));
		return map;
	}
	
	//ou calcul sensibilité
	public static double calculTVP(int vp, int fn) {
		return (double)vp/(vp+fn);
	}
	
	public static double calculTFP(int vn, int fp) {
		return (double)fp/(fp+vn);
	}
	
	public static double calculSpecificite(int vn, int fp) {
		return (double)vn/(vn+fp);
	}
	
	public static double calculPrecision(int vp, int fp) {
		return (double)vp/(vp+fp);
	}
	
	public static double calculRappel(int vp, int fn) {
		return (double)vp/(vp+fn);
	}
	
	public static double calculJustesse(int vp, int vn, int fp, int fn) {
		return (double)(vp+vn)/(vp+vn+fp+fn);
	}
}
