package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * 
 * @author Alex
 *
 */
public class ChartFrame extends JFrame {

	private XYSeriesCollection dataset;

	private XYPlot plot;
	
	JFreeChart chart;
	
	private String s;
	
	private BufferedImage iconImage;

	public ChartFrame(String chartTile,
			List<Map<String, List<Map<String, XYSeries>>>> listOfGroupListOfStimulusSeries) {
		super(chartTile);
		
		
		try {
			iconImage = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(iconImage);
		
		s = chartTile;
		this.setLayout(new BorderLayout());
		dataset = new XYSeriesCollection();

		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));
		this.add(checkBoxPanel, BorderLayout.SOUTH);

		for (Map<String, List<Map<String, XYSeries>>> groupListOfStimulusPrediction : listOfGroupListOfStimulusSeries) {
			JPanel groupCheckBoxPanel = new JPanel(new FlowLayout());
			groupCheckBoxPanel.setBorder(BorderFactory.createEtchedBorder());
			final JPanel listCheckBoxStimulusPanel = new JPanel();
			for (final String group : groupListOfStimulusPrediction.keySet()) {
				JCheckBox checkBoxGroup = new JCheckBox(group);
				checkBoxGroup.setSelected(true);
				groupCheckBoxPanel.add(checkBoxGroup);
				checkBoxGroup.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						Component[] cs = (Component[]) listCheckBoxStimulusPanel.getComponents();
						if (e.getStateChange() == ItemEvent.DESELECTED) {
							for (Component c : cs)
								if (c instanceof JCheckBox)
									((JCheckBox) c).setSelected(false);
						}
						if (e.getStateChange() == ItemEvent.SELECTED) {
							for (Component c : cs)
								if (c instanceof JCheckBox)
									((JCheckBox) c).setSelected(true);
						}
					}

				});
				groupCheckBoxPanel.add(listCheckBoxStimulusPanel);
				checkBoxPanel.add(groupCheckBoxPanel);
				for (Map<String, XYSeries> stimulusPrediction : groupListOfStimulusPrediction.get(group)) {
					for (final String stimulus : stimulusPrediction.keySet()) {
						if (!stimulusPrediction.get(stimulus).isEmpty()) {
							JCheckBox checkBoxStimulus = new JCheckBox(stimulus);
							checkBoxStimulus.setSelected(true);
							listCheckBoxStimulusPanel.add(checkBoxStimulus);
							checkBoxStimulus.addItemListener(new ItemListener() {
								@Override
								public void itemStateChanged(ItemEvent e) {
									int index = dataset.getSeriesIndex(group + " : " + stimulus + "   ");
									XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
									if (e.getStateChange() == ItemEvent.DESELECTED) {
										renderer.setSeriesVisible(index, false);
									}
									if (e.getStateChange() == ItemEvent.SELECTED) {
										renderer.setSeriesVisible(index, true);
									}
								}

							});
						}
						dataset.addSeries(stimulusPrediction.get(stimulus));
					}
				}
			}
		}
		ChartPanel chartPanel = new ChartPanel(createChart(dataset));
		chartPanel.setPreferredSize(new Dimension(500, 270));
		this.add(chartPanel, BorderLayout.CENTER);
	}

	public JFreeChart createChart(XYSeriesCollection dataset) {
		 chart = ChartFactory.createXYLineChart("", //
				"Trial(n)", // X axis label
				"Associative Strength (V)", // Y axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
		);

		plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinePaint(Color.BLACK);

		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		for (int i = 0; i < dataset.getSeriesCount(); i++) {
			renderer.setSeriesStroke(i, new BasicStroke(1.5f));
			renderer.setSeriesShapesVisible(i, true);
			renderer.lookupSeriesShape(i);
			renderer.lookupSeriesPaint(i);
		}
		
		return chart;
	}

	public XYSeriesCollection getDataset() {
		return dataset;
	}

	public XYPlot getPlot() {
		return plot;
	}
	
	public String getChartTitle() {
		return s;
	}
}
