package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * @author Alex
 *
 */
public class Phase implements Serializable {

	private static final long serialVersionUID = 1924103237995592077L;
	// private static int phaseId = Constants.getGroupId();
	private int id;

	private String experiment;
	private boolean random;
	private double betaPlus;
	private double betaMinus;
	private double lambdaPlus;
	private double lambdaMinus;

	private String randomExperiment;
	
	private Set<String> stimulusList;
	
	private Map<Stimulus, List<Double>> stimulusTrialPrediction;

	public Phase(String experiment, boolean random) {
		
		stimulusList = new HashSet<>();
		betaPlus = 0.05;
		betaMinus = 0.05;
		lambdaPlus = 100;
		lambdaMinus = 0;

		// id = ++phaseId;
		this.experiment = experiment;
		this.random = random;
		stimulusTrialPrediction = new TreeMap<>();
		
	}

	public static void decrementPhaseId() {
		// phaseId--;
	}

	public void setPhaseId(int id) {
		this.id = id;
	}

	public String getExperiment() {
		return experiment;
	}

	public boolean isExperimentSequenceRandom() {
		return random;
	}

	public int getPhaseId() {
		return this.id;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public void setBetaPlus(double betaPlus) {
		this.betaPlus = betaPlus;
	}

	public void setBetaMinus(double betaMinus) {
		this.betaMinus = betaMinus;
	}

	public void setLambdaPlus(double lambdaPlus) {
		this.lambdaPlus = lambdaPlus;
	}

	public void setLambdaMinus(double lambdaMinus) {
		this.lambdaMinus = lambdaMinus;
	}

	public void setCalculation(Map<Stimulus, List<Double>> stimulusTrialPrediction) {
		this.stimulusTrialPrediction = stimulusTrialPrediction;
	}

	public Map<Stimulus, List<Double>> getCalculation() {
		return this.stimulusTrialPrediction;
	}

	public double getBetaPlus() {
		return betaPlus;
	}

	public double getBetaMinus() {
		return betaMinus;
	}

	public double getLambdaPlus() {
		return lambdaPlus;
	}

	public double getLambdaMinus() {
		return lambdaMinus;
	}

	public void setRandomExperiment(String randomExperiment) {
		this.randomExperiment = randomExperiment;
	}

	public String getRandomExperiment() {
		return randomExperiment;
	}
	
	public void setExperimentSequence(String experiment) {
		this.experiment = experiment;
		stimulusList.clear();
		String[] trials = experiment.toUpperCase().split("/");
		for (String trial : trials) {
			StringBuilder stimulusName = new StringBuilder();
			for (char c : trial.toCharArray()) {
				if (c >= 'A' && c <= 'Z') {
					stimulusName.append(c);
				}
			}
			stimulusList.add(stimulusName.toString());
		}
	}
	
	public Set<String> getStimulusList() {
		return stimulusList;
	}

	/**
	 * 
	 * @param sequence
	 * @return 0 for empty sequence; -1 for empty spaces; -2 for wrong format
	 */
	public static int isExperimentSequenceCorrectFormat(String sequence) {
		if (sequence.isEmpty()) {
			return 0;
		} else if (sequence.contains(" ")) {
			return -1;
		} else if (!checkExperimentSequence(sequence)) {
			return -2;
		} else {
			return 1;
		}
	}

	private static boolean checkExperimentSequence(String sequence) {
		String[] trials = sequence.toUpperCase().split("/");
		for (String trial : trials) {
			// zero OR number of trials + stimuli + (reinforcer + hat OR hat + reinforcer)
			if (!trial.matches("^(([1-9][0-9]*)?[A-Z]+(((\\^)?[+-]?)|([+-]?(\\^)?)))$")) {
				return false;
			}
		}
		return true;
	}
	
	public void removeFromStimulusAlphaList(String s) {
		stimulusList.remove(s);
	}
	
}
