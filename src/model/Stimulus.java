package model;

/**
 * 
 * @author Alex
 *
 */
public class Stimulus implements Comparable<Stimulus> {

	private String name;
	private double learning;
	private double cuPrediction;
	private double levelOfActivation;
	private double sumPrediction;
	private double patternPrediction;
	private double alpha;
	
	private boolean hat;
	private boolean isReinforced;
	
	public Stimulus(String name) {
		this.name = name;
		learning = 0;
		cuPrediction = 0;
		sumPrediction = 0;
		levelOfActivation = 1;
		patternPrediction = 0;
		alpha = 1;
		hat = false;
	}
	
	public double getLevelOfActivation() {
		return levelOfActivation;
	}

	public void setLevelOfActivation(double levelOfActivation) {
		this.levelOfActivation = levelOfActivation;
	}

	public void sumPrediction(double patternPrediction) {
		this.sumPrediction += levelOfActivation * patternPrediction; 
	}
	
	public double getSumPrediction() {
		return sumPrediction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLearning() {
		return learning;
	}

	public void setLearning(double learning) {
		this.learning = learning;
	}

	public double getCuPrediction() {
		return cuPrediction;
	}

	public void setCuPrediction(double prediction) {
		this.cuPrediction = prediction;
	}
	
	public double getPatternPrediction() {
		return patternPrediction;
	}

	public void setPatternPrediction(double prediction) {
		this.patternPrediction = prediction;
	}

	@Override
	public int compareTo(Stimulus s) {
		return name.compareTo(s.getName());
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if ( (obj instanceof Stimulus) && ((Stimulus) obj).getName().equals(this.name))
			return true;
		else return false;
	}
	
	public void a() {
		cuPrediction += this.sumPrediction;
	}
	
	public void setAlpha(double a) {
		alpha = a;
	}
	
	public double getAlpha() {
		return alpha;
	}

	public boolean isHat() {
		return hat;
	}

	public void setHat(boolean hat) {
		this.hat = hat;
	}

	public boolean isReinforced() {
		return isReinforced;
	}

	public void setReinforced(boolean isReinforced) {
		this.isReinforced = isReinforced;
	}
	
}