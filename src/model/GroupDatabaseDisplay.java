package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * @author Alex
 *
 */
public class GroupDatabaseDisplay {
	
	private List<List<Object>> table;
	
	public GroupDatabaseDisplay() {
		table = new LinkedList<>();
	}
	
	public List<List<Object>> getData() {
		return Collections.unmodifiableList(table);
	}
	
	public void setGroupDatabase(GroupDatabase groupDatabase) {
		table.clear();
		for (Group group : groupDatabase.getGroups()) {
			List<Object> row = new LinkedList<>();
			row.add(group.getGroupName());
			for (Phase phase : group.getPhases()){
				row.add(phase.getExperiment());
				row.add(phase.isExperimentSequenceRandom());
			}
			table.add(row);
		}
	}
	
	public void clear() {
		table.clear();
	}
	
	public int size() {
		return table.size();
	}
}
