package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class CSParametersPanel extends JPanel {

	private JPanel displayPanel;
	private Map<String, JTextField> stimulAlphaMap;

	public CSParametersPanel() {
		this.setPreferredSize(new Dimension(563, 55));
		this.setBorder(BorderFactory.createEtchedBorder());

		stimulAlphaMap = new TreeMap<>();

		displayPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(displayPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(555, 45));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		this.add(scrollPane);

	}

	// public void setStimuli

	public void setAlphaStimuli(Map<String, Double> stimulusAlphaMap) {
		stimulAlphaMap.clear();
		for (String key : stimulusAlphaMap.keySet()) {
			JTextField field = new TextField(4);
			field.setFont(new Font("Verdana", Font.PLAIN, 13));
			field.setText(stimulusAlphaMap.get(key).toString());
			stimulAlphaMap.put(key, field);
		}
		addStimuli();
	}

	private void addStimuli() {

		if (displayPanel.getComponentCount() != 0)
			displayPanel.removeAll();

		for (String stimulus : stimulAlphaMap.keySet()) {

			displayPanel.add(new JLabel(stimulus + " \u03B1 " + " ="));
			displayPanel.add(stimulAlphaMap.get(stimulus));
			displayPanel.add(new JLabel("    "));
		}
		displayPanel.updateUI();
	}

	public Map<String, String> getStimulusAlphaMap() {
		Map<String, String> map = new TreeMap<>();
		for (String s : stimulAlphaMap.keySet())
			map.put(s, stimulAlphaMap.get(s).getText());
		return map;
	}

	public void inactive() {
		for (String k : stimulAlphaMap.keySet()) {
			stimulAlphaMap.get(k).setEditable(false);
			stimulAlphaMap.get(k).setBackground(Color.WHITE);
		}
	}

	public void active() {
		for (String k : stimulAlphaMap.keySet())
			stimulAlphaMap.get(k).setEditable(true);
	}
}
