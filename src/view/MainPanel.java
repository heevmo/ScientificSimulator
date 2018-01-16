package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;

import org.apache.poi.util.StringUtil;

import model.Parameters;

/**
 * 
 * @author Alex
 *
 */
public class MainPanel extends JPanel {

	private JPanel trialsPanel;
	private JPanel buttonsTrialsPanel;
	private JButton removeGroupButton;
	private JButton addGroupButton;
	private JButton removePhaseButton;
	private JButton addPhaseButton;
	private JTable groupTable;
	private GroupsPhasesTableModel tableModel;
	private JPanel USPanel;
	private JPanel CSPanel;
	private JButton setParametersButton;
	private JPanel buttonsPanel;
	private JButton clearAllButton;
	private JButton runButton;
	private JButton displayFiguresButton;
	private JButton closeFiguresButton;
	private JButton getAlphaButton;
	private JPanel setParametersPanelHelper;
	private JTabbedPane USTabbedPane;
	private JTabbedPane CSTabbedPane;
	private List<List<String>> parameteres;
	private List<USParametersPanel> parametersPanels;
	private CSParametersPanel csParametersPanel;
	private JScrollPane scrollPane;

	private PopupMenu popupMenu;

	TextField tableTextField;

	public MainPanel() {

		parameteres = new ArrayList<>();
		parametersPanels = new ArrayList<>();

		// trials panel
		trialsPanel = new JPanel();
		trialsPanel.setBorder(BorderFactory.createTitledBorder("Trials"));
		trialsPanel.setLayout(new BoxLayout(trialsPanel, BoxLayout.Y_AXIS));

		buttonsTrialsPanel = new JPanel();
		removeGroupButton = new JButton("-");
		removeGroupButton.setFocusPainted(false);
		addGroupButton = new JButton("+");
		addGroupButton.setFocusPainted(false);
		removePhaseButton = new JButton("-");
		removePhaseButton.setFocusPainted(false);
		addPhaseButton = new JButton("+");
		addPhaseButton.setFocusPainted(false);
		buttonsTrialsPanel.add(new JLabel("Groups"));
		buttonsTrialsPanel.add(removeGroupButton);
		buttonsTrialsPanel.add(addGroupButton);
		buttonsTrialsPanel.add(Box.createRigidArea(new Dimension(440, 0)));
		buttonsTrialsPanel.add(new JLabel("Phases"));
		buttonsTrialsPanel.add(removePhaseButton);
		buttonsTrialsPanel.add(addPhaseButton);

		popupMenu = new PopupMenu();

		tableModel = new GroupsPhasesTableModel();

		groupTable = new JTable(tableModel) {

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);

				// set background white at click
				if (groupTable.isRowSelected(row) && groupTable.isColumnSelected(column))
					component.setBackground(Color.WHITE);
				else {
					component.setBackground(groupTable.getBackground());
				}

				// resize columns
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		groupTable.setRowHeight(24);

		groupTable.setFont(new Font("Verdana", Font.PLAIN, 13));
		groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		groupTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		trialsPanel.add(buttonsTrialsPanel);
		scrollPane = new JScrollPane(groupTable);
		trialsPanel.add(scrollPane);
		// groupTable.getTableHeader().setPreferredSize(new
		// Dimension(scrollPane.getWidth(), 22));
		groupTable.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 11));
		groupTable.getTableHeader().setReorderingAllowed(false);

		// center text in cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		groupTable.setDefaultRenderer(String.class, centerRenderer);

		// single click to edit cells
		tableTextField = new TextField(4);
		tableTextField.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		// ---------------------------------------------------------------------------------

		// ---------------------------------------------------------------------------------

		DefaultCellEditor dce = new DefaultCellEditor(tableTextField);
		dce.setClickCountToStart(1);
		groupTable.getColumnModel().getColumn(0).setCellEditor(dce);

		// int i = groupTable.getColumnModel().getColumnCount();
		for (int i = 1; i < groupTable.getColumnModel().getColumnCount(); i = i + 2) {
			groupTable.getColumnModel().getColumn(i).setCellEditor(dce);
			groupTable.getColumnModel().getColumn(i).setPreferredWidth(120);
			groupTable.getColumnModel().getColumn(i + 1).setPreferredWidth(60);
		}

		// parameters panel

		JPanel parametersPanel = new JPanel();
		parametersPanel.setBorder(BorderFactory.createTitledBorder("Parameteres"));
		USPanel = new JPanel();
		CSPanel = new JPanel();
		JPanel csHelperPanel = new JPanel();
		csHelperPanel.add(CSPanel);
		CSPanel.setVisible(false);
		parametersPanel.setLayout(new BorderLayout());
		parametersPanel.add(USPanel, BorderLayout.SOUTH);
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(0, 25));
		parametersPanel.add(centerPanel, BorderLayout.CENTER);
		parametersPanel.add(csHelperPanel, BorderLayout.NORTH);

		// CS Panel
		// CSPanel.setPreferredSize(new Dimension(500,100));
		CSPanel.setBorder(BorderFactory.createEtchedBorder());
		CSPanel.setVisible(false);
		CSTabbedPane = new JTabbedPane();
		CSPanel.add(CSTabbedPane);
		csParametersPanel = new CSParametersPanel();
		CSTabbedPane.add("CS \u03B1 | All Groups", csParametersPanel);
		JPanel selectionAndGetAlphaPanel = new JPanel(new BorderLayout());
		selectionAndGetAlphaPanel.setPreferredSize(new Dimension(130, 83));
		CSPanel.add(selectionAndGetAlphaPanel);
		getAlphaButton = new JButton("Get Stimuli");
		selectionAndGetAlphaPanel.add(getAlphaButton, BorderLayout.SOUTH);
		JRadioButton allGroups = new JRadioButton("CS \u03B1 | All groups");
		allGroups.setSelected(true);
		allGroups.setActionCommand("1");
		JRadioButton perGroup = new JRadioButton("CS \u03B1 | Per group");
		perGroup.setActionCommand("2");
		ButtonGroup group = new ButtonGroup();
		group.add(allGroups);
		group.add(perGroup);
		JPanel p = new JPanel(new GridLayout(2, 0));
		// p.add(allGroups);
		// p.add(perGroup);
		selectionAndGetAlphaPanel.add(p, BorderLayout.NORTH);

		setParametersButton = new JButton("Set Parameters");
		setParametersPanelHelper = new JPanel(new BorderLayout());
		setParametersPanelHelper.setPreferredSize(new Dimension(130, 77));
		setParametersPanelHelper.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		setParametersPanelHelper.add(setParametersButton, BorderLayout.SOUTH);
		USPanel.add(setParametersPanelHelper);

		//
		buttonsPanel = new JPanel();

		clearAllButton = new JButton("Clear All");
		buttonsPanel.add(clearAllButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(100, 0)));

		runButton = new JButton("R U N");
		runButton.setFont(new Font("Verdana", Font.BOLD, 15));
		runButton.setEnabled(false);
		buttonsPanel.add(runButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(100, 0)));

		displayFiguresButton = new JButton("Display figures");
		displayFiguresButton.setEnabled(false);
		buttonsPanel.add(displayFiguresButton);
		// buttonsPanel.add(Box.createRigidArea(new Dimension(35, 0)));

		closeFiguresButton = new JButton("Close figures");
		closeFiguresButton.setEnabled(false);
		// buttonsPanel.add(closeFiguresButton);

		// main panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(trialsPanel);
		this.createAllPhasesTabbedPane();
		this.add(parametersPanel);
		this.add(buttonsPanel);

		Border outsideBorder = BorderFactory.createEmptyBorder(0, 1, 1, 1);
		Border insideBorder = BorderFactory.createEtchedBorder();
		this.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

	}

	public void removeGroupButtonListener(ActionListener listenForRemoveGroupButton) {
		removeGroupButton.addActionListener(listenForRemoveGroupButton);
	}

	public void addGroupButtonListener(ActionListener listenForAddGroupButton) {
		addGroupButton.addActionListener(listenForAddGroupButton);
	}

	public void removePhaseButtonListener(ActionListener listenForRemovePhaseButton) {
		removePhaseButton.addActionListener(listenForRemovePhaseButton);
	}

	public void addPhaseButtonListener(ActionListener listenForAddPhaseButton) {
		addPhaseButton.addActionListener(listenForAddPhaseButton);
	}

	public void addGroupTableListener(TableModelListener listenForTableDataChange) {
		groupTable.getModel().addTableModelListener(listenForTableDataChange);
	}

	public void addSetParameteresButtonListener(ActionListener listenForsetParametersButton) {
		setParametersButton.addActionListener(listenForsetParametersButton);
	}

	public void addRunButtonListener(ActionListener listenForRunButton) {
		runButton.addActionListener(listenForRunButton);
	}

	public void addDisplayFiguresButtonListener(ActionListener listenForDisplayFiguresButton) {
		displayFiguresButton.addActionListener(listenForDisplayFiguresButton);
	}

	public void closeDisplayFiguresButtonListener(ActionListener listenForDisplayFiguresButton) {
		displayFiguresButton.addActionListener(listenForDisplayFiguresButton);
	}

	public void addGetAlphaButtonListener(ActionListener listenForDisplayFiguresButton) {
		getAlphaButton.addActionListener(listenForDisplayFiguresButton);
	}

	public void addClearAllButtonListener(ActionListener listenForClearAllButton) {
		clearAllButton.addActionListener(listenForClearAllButton);
	}

	public void addGroupTableMouseListener(MouseAdapter mouseAdapter) {
		groupTable.addMouseListener(mouseAdapter);
	}

	public JButton getRunButton() {
		return runButton;
	}

	public JButton getDisplayFiguresButton() {
		return displayFiguresButton;
	}

	public GroupsPhasesTableModel getTableModel() {
		return tableModel;
	}

	public void setData(List<List<Object>> data) {
		tableModel.setData(data);
	}

	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void createAllPhasesTabbedPane() {
		parametersPanels.clear();

		if (USPanel.getComponentCount() != 0)
			USPanel.removeAll();

		USTabbedPane = new JTabbedPane();
		USParametersPanel p = new USParametersPanel();
		parametersPanels.add(p);
		USTabbedPane.add("US \u03B2 \u03BB | All phases", p);
		USPanel.add(USTabbedPane);
		USPanel.add(setParametersPanelHelper);
		USTabbedPane.updateUI();

	}

	public void createPerPhaseTabbedPane(List<String> phaseNames) {
		parametersPanels.clear();
		if (USPanel.getComponentCount() != 0)
			USPanel.removeAll();
		USTabbedPane = new JTabbedPane();
		for (String phaseName : phaseNames) {
			USParametersPanel p = new USParametersPanel();
			parametersPanels.add(p);
			USTabbedPane.add("US \u03B2 \u03BB | " + phaseName, p);
		}
		USTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		USPanel.add(USTabbedPane);
		USPanel.add(setParametersPanelHelper);
		USTabbedPane.updateUI();
	}

	public void addParametersPhaseTab() {

		String tabTitle = "US \u03B2 \u03BB | Phase" + tableModel.getPhaseID();
		USParametersPanel p = new USParametersPanel();

		parametersPanels.add(p);
		USTabbedPane.add(tabTitle, p);

		USTabbedPane.updateUI();
	}

	public void removeParametersTab() {
		int index = USTabbedPane.getTabCount() - 1;
		if (index > 0) {
			parametersPanels.remove(index);
			USTabbedPane.removeTabAt(index);
		}
	}

	public List<List<String>> getParameters() {

		parameteres.clear();

		for (USParametersPanel p : parametersPanels) {

			List<String> parameteresValues = new ArrayList<>();

			parameteres.add(parameteresValues);

			// parameteresValues.add(csParametersPanel.getAlphaValue());
			parameteresValues.add(p.getBetaPlusTextField().getText());
			parameteresValues.add(p.getBetaMinusTextField().getText());
			parameteresValues.add(p.getLambdaPlusTextField().getText());
			parameteresValues.add(p.getLambdaMinusTextField().getText());

		}
		return parameteres;
	}

	public void showAlphaPanel() {
		CSPanel.setVisible(true);
	}

	public void hideAlphaPanel() {
		CSPanel.setVisible(false);

	}

	public void setAlphaStimuli(Map<String, Double> stimulusAlphaMap) {
		csParametersPanel.setAlphaStimuli(stimulusAlphaMap);
	}

	public void setParametersFromPhase(List<Parameters> l) {
		if (l.get(0).isParametersPerPhase()) {

			for (int i = 0; i < l.size(); i++) {
				Parameters p = l.get(i);
				parametersPanels.get(i).setParametersFromPhase(p);
			}

		} else {
			Parameters p = l.get(0);
			parametersPanels.get(0).setParametersFromPhase(p);
		}
	}

	public Map<String, String> getStimulusAlphaMap() {
		return csParametersPanel.getStimulusAlphaMap();
	}

	public JTable getGroupTable() {
		return groupTable;
	}

	public void inactive() {
		removeGroupButton.setEnabled(false);
		addGroupButton.setEnabled(false);
		removePhaseButton.setEnabled(false);
		addPhaseButton.setEnabled(false);
		groupTable.setEnabled(false);
		clearAllButton.setEnabled(false);
		getAlphaButton.setEnabled(false);
		runButton.setEnabled(false);
		csParametersPanel.inactive();
		// CSTabbedPane.setEnabled(false);
		setParametersButton.setEnabled(false);

		for (USParametersPanel p : parametersPanels) {
			p.inactive();
		}
	}

	public void active() {
		removeGroupButton.setEnabled(true);
		addGroupButton.setEnabled(true);
		removePhaseButton.setEnabled(true);
		addPhaseButton.setEnabled(true);
		groupTable.setEnabled(true);
		clearAllButton.setEnabled(true);
		getAlphaButton.setEnabled(true);
		csParametersPanel.active();
		setParametersButton.setEnabled(true);

		for (USParametersPanel p : parametersPanels) {
			p.active();
		}
	}

	public JButton getCloseFiguresButton() {
		return closeFiguresButton;
	}

	public PopupMenu getPopupMenu() {
		return popupMenu;
	}

	public JTextField getTableTextField() {
		return tableTextField;
	}

}
