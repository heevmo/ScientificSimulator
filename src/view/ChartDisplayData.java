package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.data.xy.XYSeries;

/**
 * 
 * @author Alex
 *
 */
public class ChartDisplayData {

	private String chartTitle;
	private String groupName;
	
	private List<Map<String, List<Map<String, XYSeries>>>> listOfGroupListOfStimulusPrediction;

	public ChartDisplayData() {
		listOfGroupListOfStimulusPrediction = new ArrayList<>();
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public void setSeries(String group, List<Map<String, List<Double>>> listOfStimulusPrediction) {
		// 1
		Map<String, List<Map<String, XYSeries>>> groupOfListOfStimulusSeries = new TreeMap<>();
		// 3
		List<Map<String, XYSeries>> listOfStimulusSeries = new ArrayList<>();
		// 4
		groupOfListOfStimulusSeries.put(group, listOfStimulusSeries);		
		// 2
		listOfGroupListOfStimulusPrediction.add(groupOfListOfStimulusSeries);
		
		for (Map<String, List<Double>> stimulusPrediction : listOfStimulusPrediction) {
			Map<String, XYSeries> stimulusSeries = new TreeMap<>();
			listOfStimulusSeries.add(stimulusSeries);
			for (String stimulus : stimulusPrediction.keySet()) {
				XYSeries series = new XYSeries(group + " : " + stimulus + "   ");
				stimulusSeries.put(stimulus, series);
				for (int i = 0; i < stimulusPrediction.get(stimulus).size(); i++) {
					int x = i + 1;
					double y = stimulusPrediction.get(stimulus).get(i);
					series.add(x,y);
				}
			}
		}
	}

	public List<Map<String, List<Map<String, XYSeries>>>> getData() {
		return listOfGroupListOfStimulusPrediction;
	}

}
