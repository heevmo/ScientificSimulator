package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Alex
 *
 */
public class ExperimentResult {

	private String groupName;

	private String phaseNumber;

	private Map<String, List<Double>> stimulusTrialPrediction;

	private Map<String, List<Double>> stimulusTrialPlusPrediction;

	private Map<String, List<Double>> stimulusTrialMinusPrediction;
	
	private Map<String, List<Double>> all;

	ExperimentResult() {
		stimulusTrialPrediction = new TreeMap<>();
		stimulusTrialPlusPrediction = new TreeMap<>();
		stimulusTrialMinusPrediction = new TreeMap<>();
		all = new TreeMap<>();
	}

	public String getGroupName() {
		return groupName;
	}

	public String getPhaseNumber() {
		return phaseNumber;
	}

	public Map<String, List<Double>> getStimulusTrialPrediction() {
		return stimulusTrialPrediction;
	}

	public Map<String, List<Double>> getStimulusTrialPlusPrediction() {
		return stimulusTrialPlusPrediction;
	}

	public Map<String, List<Double>> getStimulusTrialMinusPrediction() {
		return stimulusTrialMinusPrediction;
	}
	
	public Map<String, List<Double>> getAll() {
		return this.all;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setPhaseNumber(String phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public void setStimulusTrialPrediction(Map<Stimulus, List<Double>> stimulusTrialPrediction) {

		for (Stimulus key : stimulusTrialPrediction.keySet()) {
			String stimulus = key.getName();
			List<Double> trialPrediction = new ArrayList<>();
			trialPrediction.addAll(stimulusTrialPrediction.get(key));
			this.stimulusTrialPrediction.put(stimulus, trialPrediction);
		}
		
		all.putAll(this.stimulusTrialPrediction);
		
	}

	// hat function
	// plus
	public void setStimulusTrialPlusPrediction(Map<String, List<Double>> stimulusTrialPlusPrediction) {

		for (String key : stimulusTrialPlusPrediction.keySet()) {
			List<Double> trialPrediction = new ArrayList<>();
			trialPrediction.addAll(stimulusTrialPlusPrediction.get(key));
			this.stimulusTrialPlusPrediction.put(key, trialPrediction);
		}
		
		all.putAll(this.stimulusTrialPlusPrediction);
		
	}

	// minus
	public void setStimulusTrialMinusPrediction(Map<String, List<Double>> stimulusTrialMinusPrediction) {

		for (String key : stimulusTrialMinusPrediction.keySet()) {
			List<Double> trialPrediction = new ArrayList<>();
			trialPrediction.addAll(stimulusTrialMinusPrediction.get(key));
			this.stimulusTrialMinusPrediction.put(key, trialPrediction);
		}
		
		all.putAll(this.stimulusTrialMinusPrediction);
		
	}
}
