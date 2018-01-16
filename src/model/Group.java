package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * @author Alex
 * 
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 5461757655753464693L;

	// private static int groupId = Constants.getGroupId();

	private int random;
	
	private int discriminability;

	private String groupName;

	private PhaseDatabase phaseDatabase;

	private boolean parametersPerPhase;

	private Map<String, Double> stimulusAlphaMap;

	private Double alpha;

	public Group(String groupName, PhaseDatabase phaseDatabase) {
		alpha = new Double(1);
		stimulusAlphaMap = new TreeMap<>();
		parametersPerPhase = false;
		this.random = 1000;
		this.discriminability = 2;
		this.groupName = groupName;
		this.phaseDatabase = phaseDatabase;
	}

	public void setGroupId(int id) {
		// this.id = id;
		this.groupName = groupName + " " + id;
	}

	public List<Phase> getPhases() {
		return phaseDatabase.getPhases();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void removePhase() {
		phaseDatabase.removePhase();
	}

	public void addPhase(Phase phase) {
		phaseDatabase.addPhase(phase);
	}

	public void setPhaseId(int id) {
		phaseDatabase.setPhaseId(id);
	}

	public void setRandom(int random) {
		this.random = random;
	}
	
	public void setDiscriminability(int discriminability) {
		this.discriminability = discriminability;
	}

	public int getRandom() {
		return this.random;
	}
	
	public int getDiscriminability() {
		return this.discriminability;
	}

	public boolean isParametersPerPhase() {
		return parametersPerPhase;
	}

	public void setIsParametersPerPhase(boolean perPhase) {
		parametersPerPhase = perPhase;
	}

	public Map<String, Double> getStimulusAlphaMap() {
		return stimulusAlphaMap;
	}

	public void updateAlphaValues(Map<String, Double> stimulusAlphaMap) {
		//
	}

	public void addStimulusList(Set<String> stimulusList) {
		//
	}
	public void setStimulusAlphaMap(Map<String, Double> map) {
		stimulusAlphaMap.putAll(map);
	}
	
	public void clearAll() {
		for (Phase p : phaseDatabase.getPhases())
			p.setExperimentSequence("");
	}
}
