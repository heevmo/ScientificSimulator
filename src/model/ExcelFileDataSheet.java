package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alex
 */

public class ExcelFileDataSheet {

	private String groupName;
	// stimulus / pattern prediction value
	private List<Map<String, List<Double>>> listMapStimulusPrediction;
	private Map<String, Double> mapStimulusAlpha;
	private List<Parameters> listCsParameter;
	private Double betaPlus;
	private Double betaMinus;
	private Double lambdaPlus;
	private Double lambdaMinus;
	private int discriminability;
	private List<Boolean> listRandom;
	private List<String> listSequence;

	public ExcelFileDataSheet(String groupName) {
		this.groupName = groupName;
		listMapStimulusPrediction = new ArrayList<>();
		listRandom = new ArrayList<>();
		listSequence = new ArrayList<>();
		listCsParameter = new ArrayList<>();
		mapStimulusAlpha = new TreeMap<>();
		discriminability = 2;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setDataSheet(Map<String, List<Double>> map) {

		Map<String, List<Double>> stimulusPrediction = new TreeMap<>();

		for (String s : map.keySet()) {
			List<Double> predictions = new ArrayList<>();
			predictions.addAll(map.get(s));
			if (!map.get(s).isEmpty())
				stimulusPrediction.put(s, predictions);
		}
		listMapStimulusPrediction.add(stimulusPrediction);

	}

	public void addCsParameter(Parameters csParameters) {
		listCsParameter.add(csParameters);
	}

	public List<Map<String, List<Double>>> getListMapStimulusPrediction() {
		return listMapStimulusPrediction;
	}

	public Double getBetaPlus() {
		return betaPlus;
	}

	public Double getBetaMinus() {
		return betaMinus;
	}

	public Double getLambdaPlus() {
		return lambdaPlus;
	}

	public Double getLambdaMinus() {
		return lambdaMinus;
	}

	public void addParameterAlpha(String stimulus, Double alpha) {
		mapStimulusAlpha.put(stimulus, alpha);
	}

	public Map<String, Double> getMapStimulusAlpha() {
		return mapStimulusAlpha;
	}

	public void addRandom(Boolean random) {
		listRandom.add(new Boolean(random));
	}

	public void addSequence(String sequence) {
		listSequence.add(new String(sequence.toUpperCase()));
	}

	public List<Boolean> getListRandom() {
		return listRandom;
	}

	public List<String> getListSequence() {
		return listSequence;
	}

	public List<Parameters> getListCsParameter() {
		return listCsParameter;
	}

	public void addDiscriminability(int d) {
		discriminability = d;
	}

	public int getDiscriminability() {
		return discriminability;
	}

}
