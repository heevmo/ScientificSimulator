package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Alex
 * 
 */
public class GroupDatabase {

	private List<Group> groups;

	private int groupId;

	private Map<String, Double> stimulusAlphaMap;

	private Double alpha;

	public GroupDatabase() {
		alpha = new Double(1);
		stimulusAlphaMap = new TreeMap<>();
		groupId = 0;
		groups = new LinkedList<Group>();
	}

	public void addGroup(Group group) {
		groupId++;
		group.setGroupId(groupId);
		groups.add(group);
	}

	public void removeGroup(int index) {
		// updateStimulusAlpha();
		groupId--;
		groups.remove(index);
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Group[] groupsArray = groups.toArray(new Group[groups.size()]);
		oos.writeObject(groupsArray);
		oos.close();
		System.out.println("save to file");
	}

	public void openFromFile(File file) throws IOException {
		FileInputStream fos = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fos);
		try {
			Group[] groupsArray = (Group[]) ois.readObject();
			groups.clear();
			groups.addAll(Arrays.asList(groupsArray));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		groupId = groups.size();

		if (!groups.isEmpty())
			stimulusAlphaMap.putAll(groups.get(0).getStimulusAlphaMap());

		ois.close();
	}

	public int size() {
		return groups.size();
	}

	public void setGroupId(int id) {
		groupId = id;
	}

	public void addStimulusList(List<String> stimulusList) {

		if (stimulusAlphaMap.isEmpty())
			for (String s : stimulusList)
				stimulusAlphaMap.put(s, alpha);

		Map<String, Double> temp = new TreeMap<>();
		for (String s : stimulusList)
			if (this.stimulusAlphaMap.containsKey(s)) {
				temp.put(s, stimulusAlphaMap.get(s));
			} else {
				temp.put(s, alpha);
			}

		stimulusAlphaMap.clear();
		stimulusAlphaMap.putAll(temp);

		for (Group g : groups)
			g.setStimulusAlphaMap(stimulusAlphaMap);

	}

	public Map<String, Double> getStimulusAlphaMap() {
		return stimulusAlphaMap;
	}

	public void updateStimulusAlphaValues(Map<String, String> map) {
		
		for (String s : map.keySet())
			stimulusAlphaMap.put(s, new Double(map.get(s)));

		for (Group g : groups)
			g.setStimulusAlphaMap(stimulusAlphaMap);

	}
}
