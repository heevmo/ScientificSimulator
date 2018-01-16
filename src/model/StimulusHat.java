package model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StimulusHat {

	// each phase represents an element in the set
	private Set<Map<String, List<Double>>> setMapStimulusListPrediction;

	public StimulusHat() {
		setMapStimulusListPrediction = new HashSet<>();
	}
	
	public void add(Map<String, List<Double>> map) {
		Map<String, List<Double>> m = new TreeMap<>();
		m.putAll(map);
		setMapStimulusListPrediction.add(m);
	}
}
