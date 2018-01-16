package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.ExcelFile;
import model.ExperimentCalculation;
import model.ExperimentResult;
import model.Group;
import model.GroupDatabase;
import model.GroupDatabaseDisplay;
import model.Parameters;
import model.Phase;
import model.PhaseDatabase;
import view.TaskListener;

/**
 * 
 * @author Alex
 *
 */
public class Controller {

	private GroupDatabase groupDatabase;

	private GroupDatabaseDisplay groupDatabaseDisplay;

	private ExperimentCalculation experimentCalculation;

	private ExcelFile excelFile;

	private boolean extension;

	public Controller() {
		groupDatabase = new GroupDatabase();
		groupDatabaseDisplay = new GroupDatabaseDisplay();

		// group 1 phase 1
		// !!needs updates!!
		PhaseDatabase phaseDatabase = new PhaseDatabase();
		Phase phase = new Phase("", false);
		phaseDatabase.addPhase(phase);
		Group group = new Group("Group", phaseDatabase);
		groupDatabase.addGroup(group);
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);

		extension = false;

	}

	public void removeGroup() {
		int index = groupDatabase.getGroups().size() - 1;
		if (index > 0) {
			groupDatabase.removeGroup(index);
			// Group.decrementGroupId();
			for (Phase phase : groupDatabase.getGroups().get(0).getPhases())
				Phase.decrementPhaseId();
		}
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);
	}

	// !!needs updates!!
	public void addGroup() {
		PhaseDatabase phaseDatabase = new PhaseDatabase();
		for (int i = 0; i < groupDatabase.getGroups().get(0).getPhases().size(); i++) {
			Phase phase = new Phase("", false);
			phaseDatabase.addPhase(phase);
		}
		Group group = new Group("Group", phaseDatabase);
		groupDatabase.addGroup(group);
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);
	}

	public void removePhase() {
		int size = groupDatabase.getGroups().get(0).getPhases().size();
		if (size > 1) {
			for (Group group : groupDatabase.getGroups()) {
				group.removePhase();
				// groupDatabase.removeStimulusAlphaMap(group.getPhases().get(size
				// - 1).getStimulusAlphaMap());
			}
		}
	}

	public void addPhase() {
		for (Group group : groupDatabase.getGroups()) {
			Phase phase = new Phase("", false);
			group.addPhase(phase);
		}
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);
	}

	public List<List<Object>> getData() {
		return groupDatabaseDisplay.getData();
	}

	public void setGroupName(int index, String groupName) {
		groupDatabase.getGroups().get(index).setGroupName(groupName);
	}

	public void setExperimentSequence(int groupIndex, int phaseIndex, String experimentSequence) {
		groupDatabase.getGroups().get(groupIndex).getPhases().get(phaseIndex).setExperimentSequence(experimentSequence);

	}

	public void setPhaseRandom(int groupIndex, int phaseIndex, String random) {
		groupDatabase.getGroups().get(groupIndex).getPhases().get(phaseIndex).setRandom(Boolean.parseBoolean(random));
	}

	public void setParameters(String betaPlus, String betaMinus, String lambdaPlus, String lambdaMinus)
			throws NumberFormatException {
		for (Group group : groupDatabase.getGroups()) {
			for (Phase phase : group.getPhases()) {
				// phase.setAlpha(Double.parseDouble(alpha));
				phase.setBetaPlus(Double.parseDouble(betaPlus));
				phase.setBetaMinus(Double.parseDouble(betaMinus));
				phase.setLambdaPlus(Double.parseDouble(lambdaPlus));
				phase.setLambdaMinus(Double.parseDouble(lambdaMinus));
			}
		}
	}

	public void setParametersPerPhase(int phaseId, String betaPlus, String betaMinus, String lambdaPlus,
			String lambdaMinus) throws NumberFormatException {
		for (Group group : groupDatabase.getGroups()) {
			Phase phase = group.getPhases().get(phaseId);
			// phase.setAlpha(Double.parseDouble(alpha));
			phase.setBetaPlus(Double.parseDouble(betaPlus));
			phase.setBetaMinus(Double.parseDouble(betaMinus));
			phase.setLambdaPlus(Double.parseDouble(lambdaPlus));
			phase.setLambdaMinus(Double.parseDouble(lambdaMinus));
		}
	}

	// setParameters button clicked
	public void setAlphaParameters(Map<String, String> map) {
		groupDatabase.updateStimulusAlphaValues(map);

	}

	// getStimuli button clicked
	public Map<String, Double> getAlphaStimuli() {
		Map<String, Double> stimulusAlphaMap = new TreeMap<>();
		List<String> l = new ArrayList<>();
		for (Group g : groupDatabase.getGroups()) {
			for (Phase p : g.getPhases()) {
				g.addStimulusList(p.getStimulusList());
				l.addAll(p.getStimulusList());
			}
		}
		groupDatabase.addStimulusList(l);
		stimulusAlphaMap.putAll(groupDatabase.getStimulusAlphaMap());
		return stimulusAlphaMap;
	}

	public int isExperimentSequenceCorrectFormat(String sequence) {
		return Phase.isExperimentSequenceCorrectFormat(sequence);
	}

	public boolean runExperimentCalculation(TaskListener task) {

		boolean cancel = false;

		int i = groupDatabase.getGroups().size();
		int j = groupDatabase.getGroups().get(0).getPhases().size();
		experimentCalculation = new ExperimentCalculation(i, j);
		excelFile = new ExcelFile();

		experimentCalculation.setExtension(extension);

		//
		experimentCalculation.setNoOfGroups(i);
		for (Group group : groupDatabase.getGroups()) {
			experimentCalculation.setGroup(group);
			cancel = experimentCalculation.calculate(task);

			//
			excelFile.add(experimentCalculation.getExcelFileDataSheet());
		}

		return cancel;
	}

	public ExperimentResult[][] getDataset() {
		return experimentCalculation.getDataset();
	}

	// test to console
	public void printData() {
		for (Group g : groupDatabase.getGroups()) {
			for (Phase p : g.getPhases())
				System.out.print("Exp: " + p.getExperiment() + " ");
			System.out.println();
		}
	}

	public void saveToFile(File file) throws IOException {
		groupDatabase.saveToFile(file);
	}

	public int openFromFile(File file) throws IOException {
		groupDatabase.openFromFile(file);
		groupDatabaseDisplay.clear();
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);
		// Group.setGroupId(groupDatabase.getGroups().size());
		return groupDatabase.getGroups().get(0).getPhases().size();
	}

	public GroupDatabaseDisplay getGroupDatabaseDisplay() {
		return groupDatabaseDisplay;
	}

	public void setRandom(int random) {
		for (Group g : groupDatabase.getGroups())
			g.setRandom(random);
	}
	
	public void setDiscriminability(int d) {
		for (Group g : groupDatabase.getGroups())
			g.setDiscriminability(d);
	}

	public void reset() {
		groupDatabaseDisplay.clear();
		groupDatabase.setGroupId(0);
		groupDatabase.getGroups().clear();
	}

	public String getRandomFromGroup() {
		Integer random = groupDatabase.getGroups().get(0).getRandom();
		return new String(random.toString());
	}
	
	public String getDiscriminabilityFromGroup() {
		Integer random = groupDatabase.getGroups().get(0).getDiscriminability();
		return new String(random.toString());
	}


	public List<Parameters> getParametersFromPhase() {

		List<Parameters> l = new ArrayList<>();

		if (groupDatabase.getGroups().get(0).isParametersPerPhase()) {
			for (Phase p : groupDatabase.getGroups().get(0).getPhases()) {
				Parameters parameters = new Parameters(p.getBetaPlus(), p.getBetaMinus(), p.getLambdaPlus(),
						p.getLambdaMinus());
				parameters.setParametersPerPhase(true);
				l.add(parameters);
			}

		} else {
			Phase p = groupDatabase.getGroups().get(0).getPhases().get(0);
			Parameters parameters = new Parameters(p.getBetaPlus(), p.getBetaMinus(), p.getLambdaPlus(),
					p.getLambdaMinus());
			parameters.setParametersPerPhase(false);
			l.add(parameters);
		}
		return l;
	}

	public void setIsParametersPerPhase(boolean perPhase) {
		groupDatabase.getGroups().get(0).setIsParametersPerPhase(perPhase);
	}

	public void clearAll() {
		for (Group g : groupDatabase.getGroups())
			g.clearAll();
		groupDatabaseDisplay.setGroupDatabase(groupDatabase);
	}

	public void export(File file) throws IOException {
		excelFile.setFile(file);
	}

	public int getNoOfGroups() {
		return groupDatabase.getGroups().size();
	}

	public void cancelCalculation() {
		experimentCalculation.cancelCalculation();
	}

	public void setExtension(boolean e) {
		this.extension = e;
	}

	public String getPairs() {
		StringBuilder sb = new StringBuilder();

		// --------------------------------------

		for (Group g : groupDatabase.getGroups()) {
			int pNo = 1;
			for (Phase p : g.getPhases()) {
				List<String> allStimuli = new ArrayList<>();
				String[] trials = p.getExperiment().toUpperCase().split("/");
				for (String trial : trials) {
					StringBuilder stimulusName = new StringBuilder();
					for (char c : trial.toCharArray()) {
						if (c >= 'A' && c <= 'Z')
							stimulusName.append(c);
					}
					if (!allStimuli.contains(new String(stimulusName)))
						allStimuli.add(new String(stimulusName));
				}
				sb.append(g.getGroupName() + " Phase " + pNo++ + "\n");
				String phase = new String(" Phase " + pNo);
				int separatorLength = g.getGroupName().length() + phase.length();
				for (int i = 0; i < 1.5 * separatorLength; i++)
					sb.append("-");
				sb.append("\n");
				// --------------------------------------

				for (String stimulus : allStimuli) {
					List<String> l = new ArrayList<>();
					char[] c = stimulus.toCharArray();
					for (int i = 0; i < c.length; i++)
						for (int j = i + 1; j < c.length; j++) {
							String s = new String();
							if (c[j] > c[i])
								s = new String((char) c[i] + "" + (char) c[j]);
							else
								s = new String((char) c[j] + "" + (char) c[i]);
							if (c[j] == c[i] || l.contains(s))
								continue;
							l.add(s);
						}
					sb.append(stimulus + " contains: " + l.toString() + "\n");
				}

				for (int a = 0; a < allStimuli.size(); a++) {
					for (int b = a + 1; b < allStimuli.size(); b++) {

						if (allStimuli.get(a).equals(allStimuli.get(b))) {
							sb.append(allStimuli.get(a) + " s " + allStimuli.get(b) + " = " + " 1" + "\n");
							System.out.println("asd");
							return new String(sb);
						}

						char[] c1 = allStimuli.get(a).toCharArray();
						char[] c2 = allStimuli.get(b).toCharArray();
						Arrays.sort(c1);
						Arrays.sort(c2);

						String string1 = new String(c1);
						String string2 = new String(c2);

						if (string1.equals(string2)) {
							sb.append(allStimuli.get(a) + " s " + allStimuli.get(b) + " = " + " 1" + "\n");
							return new String(sb);
						}

						List<String> l1 = new ArrayList<>();
						List<String> l2 = new ArrayList<>();
						List<String> l3 = new ArrayList<>();

						for (int i = 0; i < c1.length; i++)
							for (int j = i + 1; j < c1.length; j++) {
								String s = new String();
								if (c1[j] > c1[i])
									s = new String((char) c1[i] + "" + (char) c1[j]);
								else
									s = new String((char) c1[j] + "" + (char) c1[i]);
								if (c1[j] == c1[i] || l1.contains(s))
									continue;
								l1.add(s);
							}

						for (int i = 0; i < c2.length; i++)
							for (int j = i + 1; j < c2.length; j++) {
								String s = new String();
								if (c2[j] > c2[i])
									s = new String((char) c2[i] + "" + (char) c2[j]);
								else
									s = new String((char) c2[j] + "" + (char) c2[i]);
								if (c2[j] == c2[i] || l2.contains(s))
									continue;
								l2.add(s);
							}

						double common = 0;
						for (String s1 : l1)
							for (String s2 : l2)
								if (s1.equals(s2)) {
									common++;
									l3.add(s1);
								}

						if (common != 0) {
							sb.append(allStimuli.get(a) + " / " + allStimuli.get(b) + " common pairs are: "
									+ l3.toString() + "\n");
							sb.append(allStimuli.get(a) + " s " + allStimuli.get(b) + " = " + (int) common + "/"
									+ l1.size() + " * " + (int) common + "/" + l2.size() + " = "
									+ (int) (common * common) + "/" + (int) (l1.size() * l2.size()) + " = "
									+ common / l1.size() * common / l2.size() + "\n");
						}

					}
				}
				sb.append("\n");
				// --------------------------------------
			}
		}

		// --------------------------------------
		return new String(sb);
	}
}
