package application;

public class Frequences {
	private int nbOc;
	private double frequence;
	private int tfIDF;
	
	public Frequences(int n) {
		this.nbOc=n;
	}
	
	public Frequences(int n, double f) {
		this.nbOc=n;
		this.frequence=f;
	}

	public int getNbOc() {
		return nbOc;
	}

	public void setNbOc(int nbOc) {
		this.nbOc = nbOc;
	}

	public double getFrequence() {
		return frequence;
	}
	
	public void addOcc() {///*int nbTermes*/) {
		this.nbOc++;
		//this.frequence=nbOc/nbTermes;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}
	
	public void calcFrequence(int longueur) {
		this.frequence=((double) this.nbOc)/((double) longueur);
	}

	public int getTfIDF() {
		return tfIDF;
	}

	public void setTfIDF(int tfIDF) {
		this.tfIDF = tfIDF;
	}
	
	@Override
	public String toString() {
		return "nbOc : "+nbOc+" frequence "+frequence+" tfIDF "+tfIDF+"\n";
	}
}
