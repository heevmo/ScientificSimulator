package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * @author alex
 *
 */

public class AveragePrediction {

	private Map<Stimulus, List<Double>> sumMapStimulusPrediction;
	private Map<Stimulus, List<Double>> averageMapStimulusPrediction;

	private Map<String, List<Double>> sumMapStimulusPlusPrediction;
	private Map<String, List<Double>> averageMapStimulusPlusPrediction;

	private Map<String, List<Double>> sumMapStimulusMinusPrediction;
	private Map<String, List<Double>> averageMapStimulusMinusPrediction;

	public AveragePrediction() {
		sumMapStimulusPrediction = new TreeMap<>();
		sumMapStimulusPlusPrediction = new TreeMap<>();
		sumMapStimulusMinusPrediction = new TreeMap<>();
	}

	public void addStimulusListPrediction(Map<Stimulus, List<Double>> stimulusListPrediction) {

		for (Stimulus stimulus : stimulusListPrediction.keySet())
			if (sumMapStimulusPrediction.containsKey(stimulus)) {

				// add to sum
				List<Double> l1 = stimulusListPrediction.get(stimulus);
				List<Double> l2 = sumMapStimulusPrediction.get(stimulus);

				List<Double> l3 = new ArrayList<>();

				for (int i = 0; i < l1.size(); i++) {
					double d = l1.get(i) + l2.get(i);
					l3.add(new Double(d));
				}

				sumMapStimulusPrediction.put(stimulus, l3);

			} else {
				List<Double> listPrediction = new ArrayList<>();
				sumMapStimulusPrediction.put(stimulus, listPrediction);
				for (Double prediction : stimulusListPrediction.get(stimulus))
					listPrediction.add(new Double(prediction));
			}
	}

	public void average(int random) {

		averageMapStimulusPrediction = new TreeMap<>();

		for (Stimulus keyStimulus : sumMapStimulusPrediction.keySet()) {

			List<Double> l1 = sumMapStimulusPrediction.get(keyStimulus);
			List<Double> l2 = new ArrayList<>();
			for (Double d : l1) {
				double average = d / random;
				l2.add(new Double(average));
			}
			averageMapStimulusPrediction.put(keyStimulus, l2);
		}
	}

	// plus
	public void addStimulusPlusListPrediction(Map<String, List<Double>> stimulusListPlusPrediction) {

		for (String stimulus : stimulusListPlusPrediction.keySet())
			if (sumMapStimulusPlusPrediction.containsKey(stimulus)) {
				// add to sum
				List<Double> l1 = stimulusListPlusPrediction.get(stimulus);
				List<Double> l2 = sumMapStimulusPlusPrediction.get(stimulus);

				List<Double> l3 = new ArrayList<>();

				for (int i = 0; i < l1.size(); i++) {
					double d = l1.get(i) + l2.get(i);
					l3.add(new Double(d));
				}

				sumMapStimulusPlusPrediction.put(stimulus, l3);

			} else {
				List<Double> listPrediction = new ArrayList<>();
				sumMapStimulusPlusPrediction.put(stimulus, listPrediction);
				for (Double prediction : stimulusListPlusPrediction.get(stimulus))
					listPrediction.add(new Double(prediction));
			}
	}

	// plus
	public void averagePlus(int random) {

		averageMapStimulusPlusPrediction = new TreeMap<>();

		for (String keyStimulus : sumMapStimulusPlusPrediction.keySet()) {

			List<Double> l1 = sumMapStimulusPlusPrediction.get(keyStimulus);
			List<Double> l2 = new ArrayList<>();
			for (Double d : l1) {
				double average = d / random;
				l2.add(new Double(average));
			}
			averageMapStimulusPlusPrediction.put(keyStimulus, l2);
		}
	}

	// minus
	public void addStimulusMinusListPrediction(Map<String, List<Double>> map) {
		
		for (String stimulus : map.keySet())
			if (sumMapStimulusMinusPrediction.containsKey(stimulus)) {

				// add to sum
				List<Double> l1 = map.get(stimulus);
				List<Double> l2 = sumMapStimulusMinusPrediction.get(stimulus);

				List<Double> l3 = new ArrayList<>();

				for (int i = 0; i < l1.size(); i++) {
					double d = l1.get(i) + l2.get(i);
					l3.add(new Double(d));
				}

				sumMapStimulusMinusPrediction.put(stimulus, l3);

			} else {
				List<Double> listPrediction = new ArrayList<>();
				sumMapStimulusMinusPrediction.put(stimulus, listPrediction);
				for (Double prediction : map.get(stimulus))
					listPrediction.add(new Double(prediction));

			}

	}
	// minus
	public void averageMinus(int random) {

		averageMapStimulusMinusPrediction = new TreeMap<>();
		
		for (String keyStimulus : sumMapStimulusMinusPrediction.keySet()) {

			List<Double> l1 = sumMapStimulusMinusPrediction.get(keyStimulus);
			List<Double> l2 = new ArrayList<>();
			for (Double d : l1) {
				double average = d / random;
				l2.add(new Double(average));
			}
			averageMapStimulusMinusPrediction.put(keyStimulus, l2);
		}
	}

	public Map<Stimulus, List<Double>> getAverage() {
		return averageMapStimulusPrediction;
	}

	public Map<String, List<Double>> getAveragePlus() {
		return averageMapStimulusPlusPrediction;
	}
	
	public Map<String, List<Double>> getAverageMinus() {
		return averageMapStimulusMinusPrediction;
	}
}
