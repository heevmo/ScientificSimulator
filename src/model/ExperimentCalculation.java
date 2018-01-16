package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import view.TaskListener;

/**
 * 
 * @author Alex
 *
 */
public class ExperimentCalculation {

	private Group group;
	private ExperimentResult[][] results;
	private int i;
	private Map<Stimulus, List<Double>> temporaryStimulusTrialPrediction;
	private Map<Stimulus, List<Double>> temporaryStimulusTrialCuPrediction;
	private Map<Stimulus, List<Double>> randomStimulusTrialPrediction;
	private Map<String, List<Double>> hatStimulusPlusTrialPrediction;
	private Map<String, List<Double>> hatStimulusMinusTrialPrediction;
	private List<StringBuilder> experimentToList;
	private int random;
	private int discriminability;

	private int noOfGroups;

	private double progress;

	private boolean cancel;

	// US parameter
	double alpha;

	// CS parameters
	double betaPlus;
	double betaMinus;
	double lambdaPlus;
	double lambdaMinus;

	private ExcelFileDataSheet excelFileDataSheet;

	private AveragePrediction averagePrediction;

	private int noOfPhases;

	private boolean extension;

	public ExperimentCalculation(int rows, int columns) {

		temporaryStimulusTrialPrediction = new TreeMap<>();
		temporaryStimulusTrialCuPrediction = new TreeMap<>();
		results = new ExperimentResult[rows][columns];
		i = -1;
		experimentToList = new ArrayList<>();

		randomStimulusTrialPrediction = new TreeMap<>();
		hatStimulusPlusTrialPrediction = new TreeMap<>();
		hatStimulusMinusTrialPrediction = new TreeMap<>();

		extension = false;

	}

	public boolean calculate(TaskListener task) {

		noOfPhases = group.getPhases().size();

		if (cancel)
			return true;

		i++;
		int j = -1;

		// excel file data sheet
		excelFileDataSheet = new ExcelFileDataSheet(group.getGroupName());
		excelFileDataSheet.addDiscriminability(group.getDiscriminability());

		for (Phase phase : group.getPhases()) {

			//
			excelFileDataSheet.addRandom(phase.isExperimentSequenceRandom());
			excelFileDataSheet.addSequence(phase.getExperiment());

			random = group.getRandom();
			discriminability = group.getDiscriminability();

			for (Stimulus keyStimulus : temporaryStimulusTrialPrediction.keySet())
				temporaryStimulusTrialPrediction.get(keyStimulus).clear();

			for (String keyStimulus : hatStimulusPlusTrialPrediction.keySet())
				hatStimulusPlusTrialPrediction.get(keyStimulus).clear();

			for (String keyStimulus : hatStimulusMinusTrialPrediction.keySet())
				hatStimulusMinusTrialPrediction.get(keyStimulus).clear();

			for (Stimulus keyStimulus : temporaryStimulusTrialCuPrediction.keySet())
				temporaryStimulusTrialCuPrediction.get(keyStimulus).clear();

			double base = (double) 100 / random / noOfGroups / noOfPhases;

			if (phase.isExperimentSequenceRandom()) {

				this.experimentToList(phase);

				averagePrediction = new AveragePrediction();

				randomStimulusTrialPrediction = new TreeMap<>();

				for (Stimulus s : temporaryStimulusTrialPrediction.keySet()) {
					Stimulus s2 = new Stimulus(s.getName());
					s2.setAlpha(s.getAlpha());
					s2.setCuPrediction(s.getCuPrediction());
					s2.setLearning(s.getLearning());
					s2.setLevelOfActivation(s.getLevelOfActivation());
					s2.setPatternPrediction(s.getPatternPrediction());
					List<Double> l = new ArrayList<>();
					randomStimulusTrialPrediction.put(s2, l);
				}

				for (int p = 1; p <= random; p++) {

					if (cancel)
						return true;

					progress += base;
					task.progress((int) progress);

					this.shuffle(phase);

					algorithm(phase, task);

					// sum
					averagePrediction.addStimulusListPrediction(temporaryStimulusTrialPrediction);

					if (!hatStimulusPlusTrialPrediction.isEmpty())
						averagePrediction.addStimulusPlusListPrediction(hatStimulusPlusTrialPrediction);

					if (!hatStimulusMinusTrialPrediction.isEmpty())
						averagePrediction.addStimulusMinusListPrediction(hatStimulusMinusTrialPrediction);

					temporaryStimulusTrialPrediction.clear();
					hatStimulusPlusTrialPrediction.clear();
					hatStimulusMinusTrialPrediction.clear();

					for (Stimulus s : randomStimulusTrialPrediction.keySet()) {
						Stimulus s2 = new Stimulus(s.getName());
						s2.setAlpha(s.getAlpha());
						s2.setCuPrediction(s.getCuPrediction());
						s2.setLearning(s.getLearning());
						s2.setLevelOfActivation(s.getLevelOfActivation());
						s2.setPatternPrediction(s.getPatternPrediction());
						List<Double> l = new ArrayList<>();
						temporaryStimulusTrialPrediction.put(s2, l);
					}

				}

				randomStimulusTrialPrediction.clear();
				hatStimulusPlusTrialPrediction.clear();
				hatStimulusMinusTrialPrediction.clear();

				// average

				averagePrediction.average(random);

				averagePrediction.averagePlus(random);

				averagePrediction.averageMinus(random);

				temporaryStimulusTrialPrediction.putAll(averagePrediction.getAverage());
				hatStimulusPlusTrialPrediction.putAll(averagePrediction.getAveragePlus());
				hatStimulusMinusTrialPrediction.putAll(averagePrediction.getAverageMinus());

			} else {

				algorithm(phase, task);

			}

			ExperimentResult er = new ExperimentResult();
			er.setGroupName(group.getGroupName());
			int phaseNumber = group.getPhases().indexOf(phase);
			er.setPhaseNumber("Phase " + ++phaseNumber);

			er.setStimulusTrialPrediction(temporaryStimulusTrialPrediction);
			er.setStimulusTrialPlusPrediction(hatStimulusPlusTrialPrediction);
			er.setStimulusTrialMinusPrediction(hatStimulusMinusTrialPrediction);

			j++;

			results[i][j] = er;

			// excel file
			Map<String, List<Double>> excelMap = new TreeMap<>();
			for (Stimulus s : temporaryStimulusTrialPrediction.keySet()) {
				String stimulus = new String(s.getName());
				excelMap.put(stimulus, temporaryStimulusTrialPrediction.get(s));
			}
			for (String s : hatStimulusPlusTrialPrediction.keySet()) {
				String stimulus = new String(s);
				excelMap.put(stimulus, hatStimulusPlusTrialPrediction.get(s));
			}
			for (String s : hatStimulusMinusTrialPrediction.keySet()) {
				String stimulus = new String(s);
				excelMap.put(stimulus, hatStimulusMinusTrialPrediction.get(s));
			}
			excelFileDataSheet.setDataSheet(excelMap);
			Parameters parameters = new Parameters(betaPlus, betaMinus, lambdaPlus, lambdaMinus);
			excelFileDataSheet.addCsParameter(parameters);

		} // end phase

		temporaryStimulusTrialPrediction.clear();
		hatStimulusPlusTrialPrediction.clear();
		hatStimulusMinusTrialPrediction.clear();

		return false;

	} // end calculate

	// algorithm
	private void algorithm(Phase phase, TaskListener task) {

		// stimulus per group
		Stimulus stimulus = new Stimulus("0");

		// hat
		String hatStimulusPlus = new String();
		String hatStimulusMinus = new String();

		alpha = 1;
		betaPlus = phase.getBetaPlus();
		betaMinus = phase.getBetaMinus();
		lambdaPlus = phase.getLambdaPlus();
		lambdaMinus = phase.getLambdaMinus();

		String[] trials = phase.getExperiment().toUpperCase().split("/");

		if (phase.isExperimentSequenceRandom()) {
			trials = phase.getRandomExperiment().toUpperCase().split("/");
		}

		for (String trial : trials) {

			int noOfTrials = 1;
			StringBuilder stimulusName = new StringBuilder();
			boolean isReinforced = false;
			StringBuilder noOfTrialsString = new StringBuilder();

			for (char c : trial.toCharArray()) {
				if (c >= '0' && c <= '9') {
					noOfTrialsString.append(c);
					noOfTrials = Integer.parseInt(noOfTrialsString.toString());
				} else if (c >= 'A' && c <= 'Z') {
					stimulusName.append(c);
				} else if (c == '+') {
					isReinforced = true;
				}
			}

			boolean hat = false;
			stimulus = new Stimulus(stimulusName.toString());
			stimulus.setHat(hat);
			if (trial.contains("^")) {
				hat = true;
				hatStimulusPlus = new String(stimulus.getName() + "^[" + stimulus.getName() + "+" + "]");
				hatStimulusMinus = new String(stimulus.getName() + "^[" + stimulus.getName() + "-" + "]");
			} else {
				hat = false;
			}

			stimulus.setReinforced(isReinforced);

			if (group.getStimulusAlphaMap().containsKey(stimulusName.toString()))
				alpha = group.getStimulusAlphaMap().get(stimulusName.toString());

			//
			excelFileDataSheet.addParameterAlpha(stimulus.getName(), new Double(alpha));

			// 1
			for (Stimulus keyStimulus : temporaryStimulusTrialPrediction.keySet()) {

				if (extension) {

					double levelOfActivation = isConstituentExtension(stimulus.getName(), keyStimulus.getName());

					stimulus.setLevelOfActivation(levelOfActivation);
					stimulus.sumPrediction(keyStimulus.getCuPrediction());

				} else {
					double nC = isConstituent(stimulus.getName(), keyStimulus.getName());
					double nI = stimulus.getName().length();
					double nJ = keyStimulus.getName().length();
					double levelOfActivation = Math.sqrt(nC / nI * nC / nJ);

					for (int i = 1; i < discriminability; i++)
						levelOfActivation *= levelOfActivation;

					stimulus.setLevelOfActivation(levelOfActivation);
					stimulus.sumPrediction(keyStimulus.getCuPrediction());
				}

			}

			// 3
			for (Stimulus keyStimulus : temporaryStimulusTrialPrediction.keySet())

				if (keyStimulus.equals(stimulus)) {

					if (isReinforced) {

						double patternPrediction = keyStimulus.getCuPrediction() + stimulus.getSumPrediction();
						//patternPrediction = new Double(String.format("%.6f", patternPrediction));
						for (int i = 1; i <= noOfTrials; i++) {

							temporaryStimulusTrialPrediction.get(keyStimulus).add(patternPrediction);

							// hat
							// =====================================================================================
							if (hat) {
								for (String s : hatStimulusPlusTrialPrediction.keySet())
									if (s.equals(hatStimulusPlus))
										hatStimulusPlusTrialPrediction.get(hatStimulusPlus).add(patternPrediction);
								if (hatStimulusPlusTrialPrediction.isEmpty()
										|| !hatStimulusPlusTrialPrediction.containsKey(hatStimulusPlus)) {
									List<Double> hatPlusTrialPatternPrediction = new ArrayList<>();
									hatPlusTrialPatternPrediction.add(patternPrediction);
									hatStimulusPlusTrialPrediction.put(hatStimulusPlus, hatPlusTrialPatternPrediction);
								}
							}
							// =====================================================================================

							double learning = alpha * betaPlus * (lambdaPlus - patternPrediction);

							double cuPrediction = keyStimulus.getCuPrediction() + learning;
							keyStimulus.setCuPrediction(cuPrediction);

							patternPrediction = cuPrediction + stimulus.getSumPrediction();

							keyStimulus.setPatternPrediction(patternPrediction);
						}
					} else {
						double patternPrediction = keyStimulus.getCuPrediction() + stimulus.getSumPrediction();
						//patternPrediction = new Double(String.format("%.6f", patternPrediction));
						for (int i = 1; i <= noOfTrials; i++) {

							temporaryStimulusTrialPrediction.get(keyStimulus).add(patternPrediction);

							// hat
							// =====================================================================================
							if (hat) {
								for (String s : hatStimulusMinusTrialPrediction.keySet())
									if (s.equals(hatStimulusMinus))
										hatStimulusMinusTrialPrediction.get(hatStimulusMinus).add(patternPrediction);

								if (hatStimulusMinusTrialPrediction.isEmpty()
										|| !hatStimulusMinusTrialPrediction.containsKey(hatStimulusMinus)) {
									List<Double> hatMinusTrialPatternPrediction = new ArrayList<>();
									hatMinusTrialPatternPrediction.add(patternPrediction);
									hatStimulusMinusTrialPrediction.put(hatStimulusMinus,
											hatMinusTrialPatternPrediction);
								}
							}
							// =====================================================================================

							double learning = alpha * betaMinus * (lambdaMinus - patternPrediction);

							double cuPrediction = keyStimulus.getCuPrediction() + learning;
							keyStimulus.setCuPrediction(cuPrediction);

							patternPrediction = cuPrediction + stimulus.getSumPrediction();
							keyStimulus.setPatternPrediction(patternPrediction);
						}
					}
				}

			// 2
			if (temporaryStimulusTrialPrediction.isEmpty() || !temporaryStimulusTrialPrediction.containsKey(stimulus)) {
				// For every new stimulus
				List<Double> trialPatternPrediction = new ArrayList<>();
				temporaryStimulusTrialPrediction.put(stimulus, trialPatternPrediction);

				//
				List<Double> trialCuPrediction = new ArrayList<>();
				temporaryStimulusTrialCuPrediction.put(stimulus, trialCuPrediction);

				// Hat stimuli
				if (hat) {
					List<Double> hatPlusTrialPatternPrediction = new ArrayList<>();
					List<Double> hatMinusTrialPatternPrediction = new ArrayList<>();
					hatStimulusPlusTrialPrediction.put(hatStimulusPlus, hatPlusTrialPatternPrediction);
					hatStimulusMinusTrialPrediction.put(hatStimulusMinus, hatMinusTrialPatternPrediction);
				}

				// at the beginning of the first trial

				if (isReinforced) {

					double patternPrediction = stimulus.getSumPrediction();
					//patternPrediction = new Double(String.format("%.6f", patternPrediction));
					double cuPrediction = stimulus.getCuPrediction();

					for (int i = 1; i <= noOfTrials; i++) {

						trialPatternPrediction.add(patternPrediction);

						// hat
						// =====================================================================================
						if (hat)
							hatStimulusPlusTrialPrediction.get(hatStimulusPlus).add(new Double(patternPrediction));

						trialCuPrediction.add(cuPrediction);

						double learning = alpha * betaPlus * (lambdaPlus - patternPrediction);

						cuPrediction = stimulus.getCuPrediction() + learning;
						stimulus.setCuPrediction(cuPrediction);

						patternPrediction = cuPrediction + stimulus.getSumPrediction();
						stimulus.setPatternPrediction(patternPrediction);
						// =====================================================================================
					}

				} else {

					double patternPrediction = stimulus.getSumPrediction();
					//patternPrediction = new Double(String.format("%.6f", patternPrediction));
					for (int i = 1; i <= noOfTrials; i++) {

						trialPatternPrediction.add(patternPrediction);

						// hat
						// =====================================================================================
						if (hat)
							hatStimulusMinusTrialPrediction.get(hatStimulusMinus).add(new Double(patternPrediction));
						// =====================================================================================

						double learning = alpha * betaMinus * (lambdaMinus - patternPrediction);

						double cuPrediction = stimulus.getCuPrediction() + learning;
						stimulus.setCuPrediction(cuPrediction);

						patternPrediction = cuPrediction + stimulus.getSumPrediction();
						stimulus.setPatternPrediction(patternPrediction);
					}
				}
			}

			if (!phase.isExperimentSequenceRandom()) {
				if (noOfGroups > noOfPhases) {
					progress += (double) 100 / noOfGroups / noOfPhases / trials.length;
				} else {
					progress += (double) 100 / noOfPhases / noOfGroups / trials.length;
				}
				task.progress((int) progress);
			}

		} // end trial
	}

	private int isConstituent(String p1, String p2) {
		if (p1.equals(p2))
			return 0;

		char[] c1 = p1.toCharArray();
		char[] c2 = p2.toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);

		String string1 = new String(c1);
		String string2 = new String(c2);

		if (string1.equals(string2))
			return 0;

		int i = 0;
		boolean[] alphabet = new boolean[26];
		for (char c : p1.toCharArray()) {
			int index = c - 'A';
			alphabet[index] = true;
		}
		for (char c : p2.toCharArray()) {
			int index = c - 'A';
			if (alphabet[index]) {
				i++;
				alphabet[index] = false;
			}
		}
		return i;
	}

	private double isConstituentExtension(String p1, String p2) {
		if (p1.equals(p2))
			return 0;

		char[] c1 = p1.toCharArray();
		char[] c2 = p2.toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);

		String string1 = new String(c1);
		String string2 = new String(c2);

		if (string1.equals(string2))
			return 0;

		Set<String> l1 = new HashSet<>();
		Set<String> l2 = new HashSet<>();
		String s = new String("");

		for (int i = 0; i < c1.length; i++)
			for (int j = i + 1; j < c1.length; j++) {
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
				}

		double levelOfActivation = Math.sqrt(common / l1.size() * common / l2.size());

		for (int i = 1; i < discriminability; i++)
			levelOfActivation *= levelOfActivation;

		return levelOfActivation;

	}

	private void experimentToList(Phase phase) {

		experimentToList.clear();

		String[] trials = phase.getExperiment().toUpperCase().split("/");

		for (String trial : trials) {

			int noOfTrials = 1;
			StringBuilder stimulusName = new StringBuilder();
			StringBuilder noOfTrialsString = new StringBuilder();

			for (char c : trial.toCharArray()) {
				if (c >= '0' && c <= '9') {
					noOfTrialsString.append(c);
					noOfTrials = Integer.parseInt(noOfTrialsString.toString());
				} else if (c >= 'A' && c <= 'Z') {
					stimulusName.append(c);
				} else if (c == '+') {
					stimulusName.append('+');
				} else if (c == '^') {
					stimulusName.append('^');
				}

				else {
					stimulusName.append('-');
				}
			}

			for (int i = 0; i < noOfTrials; i++) {
				experimentToList.add(stimulusName);
			}

		}
	}

	private void shuffle(Phase phase) {

		Collections.shuffle(experimentToList);

		String phaseShuffled = new String();
		StringBuilder s = new StringBuilder();
		for (StringBuilder sb : experimentToList) {

			s.append(sb);
			s.append('/');
		}

		s.deleteCharAt(s.length() - 1);

		phaseShuffled = new String(s);

		phase.setRandomExperiment(phaseShuffled);
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public ExperimentResult[][] getDataset() {
		return results;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public ExcelFileDataSheet getExcelFileDataSheet() {
		return excelFileDataSheet;
	}

	public void setNoOfGroups(int i) {
		this.noOfGroups = i;
	}

	public void cancelCalculation() {
		cancel = true;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}
}
