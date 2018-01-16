package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * For each group there is a phase database.
 * 
 * @author Alex
 *
 */
public class PhaseDatabase implements Serializable {

	private static final long serialVersionUID = -2482154153412754126L;
	private List<Phase> phases;
	
	private int phaseId;
	
	public PhaseDatabase() {
		phaseId = 0;
		phases = new LinkedList<Phase>();
	}

	public void removePhase() {
		phaseId--;
		phases.remove(phases.size() - 1);
	}

	public void addPhase(Phase phase) {
		phaseId++;
		phase.setPhaseId(phaseId);
		phases.add(phase);
	}

	public List<Phase> getPhases() {
		return phases;
	}
	
	public void setPhaseId(int id) {
		phaseId = id;
	}
}
