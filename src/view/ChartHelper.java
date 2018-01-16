package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Alex	
 */
import model.ExperimentResult;

/**
 * 
 * @author Alex
 *
 */
public class ChartHelper {

	private ExperimentResult[][] results;

	private List<ChartDisplayData> charts;

	public void setDataset(ExperimentResult[][] results) {
		this.results = results;
	}

	public void display() {
		int rows = results.length;
		int columns = results[0].length;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++) {
				System.out.println(results[i][j].getGroupName() + " " + results[i][j].getPhaseNumber());
				System.out.println(results[i][j].getStimulusTrialPrediction());
				System.out.println("-----------------------");
			}
	}

	// per Phase data set

	public List<ChartDisplayData> getDisplayDataByPhase() {

		int rows = results.length;
		int columns = results[0].length;

		charts = new ArrayList<>();

		for (int j = 0; j < columns; j++) {
			ChartDisplayData chartDisplayData = new ChartDisplayData();
			for (int i = 0; i < rows; i++) {
				List<Map<String, List<Double>>> listOfStimulusPrediction = new ArrayList<>();;
				chartDisplayData.setChartTitle(results[i][j].getPhaseNumber());
				listOfStimulusPrediction.add(results[i][j].getStimulusTrialPrediction());
				listOfStimulusPrediction.add(results[i][j].getStimulusTrialPlusPrediction());
				listOfStimulusPrediction.add(results[i][j].getStimulusTrialMinusPrediction());
				//System.out.println(listOfStimulusPrediction);
				chartDisplayData.setSeries(results[i][j].getGroupName(), listOfStimulusPrediction);
			}
			charts.add(chartDisplayData);
		}
		return charts;
	}
}
