package model;

/**
 * 
 * @author Alex
 *
 */

public class Parameters {

	private Double betaPlus;
	private Double betaMinus;
	private Double lambdaPlus;
	private Double lambdaMinus;
	private boolean parametersPerPhase;
	
	public Parameters(double betaPlus, double betaMinus, double lambdaPlus, double lambdaMinus) {
		
		this.betaPlus = betaPlus;
		this.betaMinus = betaMinus;
		this.lambdaPlus = lambdaPlus;
		this.lambdaMinus = lambdaMinus;
		
	}

	public String getBetaPlus() {
		return betaPlus.toString();
	}

	public String getBetaMinus() {
		return betaMinus.toString();
	}

	public String getLambdaPlus() {
		return lambdaPlus.toString();
	}

	public String getLambdaMinus() {
		return lambdaMinus.toString();
	}

	public boolean isParametersPerPhase() {
		return parametersPerPhase;
	}

	public void setParametersPerPhase(boolean parametersPerPhase) {
		this.parametersPerPhase = parametersPerPhase;
	}
	
}
