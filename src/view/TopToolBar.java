package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 * 
 * @author Alex
 *
 */
public class TopToolBar extends JPanel {

	private Calendar now;
	private JTextField timeField;
	private JTextField dateField;
	private JButton newButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton exportButton;

	public TopToolBar() {

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		newButton = new JButton();
		newButton.setIcon(createIcon("/new.gif"));
		newButton.setToolTipText("New");
		toolBar.add(newButton);
		toolBar.addSeparator();

		openButton = new JButton();
		openButton.setIcon(createIcon("/open.gif"));
		openButton.setToolTipText("Open");
		toolBar.add(openButton);
		toolBar.addSeparator();

		saveButton = new JButton();
		saveButton.setIcon(createIcon("/save.gif"));
		saveButton.setToolTipText("Save");
		saveButton.setEnabled(false);
		toolBar.add(saveButton);
		toolBar.addSeparator();

		exportButton = new JButton();
		exportButton.setIcon(createIcon("/export.gif"));
		exportButton.setToolTipText("Export");
		exportButton.setEnabled(false);
		toolBar.add(exportButton);

		this.add(toolBar);

		this.add(Box.createRigidArea(new Dimension(350, 0)));

		Border outsideBorder = BorderFactory.createEmptyBorder(1, 1, 0, 1);
		Border insideBorder = BorderFactory.createEtchedBorder();
		this.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

		// Date and Time
		JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.add(timePanel);

		now = Calendar.getInstance();
		final SimpleDateFormat fdate = new SimpleDateFormat("EEE dd/MM/yyyy");
		final SimpleDateFormat ftime = new SimpleDateFormat("HH:mm:ss");

		timePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// timePanel.add(new JLabel("Date:"));
		dateField = new JTextField(10);
		dateField.setEditable(false);
		dateField.setText(fdate.format(now.getTime()));
		dateField.setBackground(Color.WHITE);
		// timePanel.add(dateField);

		// timePanel.add(new JLabel("Time:"));
		timeField = new JTextField(6);
		timeField.setText(ftime.format(now.getTime()));
		timeField.setEditable(false);
		timeField.setBackground(Color.WHITE);
		timePanel.add(timeField);
		timePanel.add(Box.createRigidArea(new Dimension(1, 0)));
		timePanel.add(dateField);

		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				now = Calendar.getInstance();
				dateField.setText(fdate.format(now.getTime()));
				timeField.setText(ftime.format(now.getTime()));
			}
		}).start();

	}

	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
			return null;
		}
		ImageIcon imageIcon = new ImageIcon(url);
		return imageIcon;
	}

	public void newButtonListener(ActionListener listener) {
		newButton.addActionListener(listener);
	}

	public void openButtonListener(ActionListener listener) {
		openButton.addActionListener(listener);
	}

	public void saveButtonListener(ActionListener listener) {
		saveButton.addActionListener(listener);
	}

	public void setExportItemListener(ActionListener listener) {
		exportButton.addActionListener(listener);
	}

	public void setEnabledSaveOpenExport() {
		saveButton.setEnabled(true);
		openButton.setEnabled(true);
		exportButton.setEnabled(true);
	}

	public void inactive() {

		newButton.setEnabled(false);
		openButton.setEnabled(false);
		saveButton.setEnabled(false);
		exportButton.setEnabled(false);
		
	}

	public void active() {

		newButton.setEnabled(true);
		openButton.setEnabled(true);
		saveButton.setEnabled(true);
		exportButton.setEnabled(true);
		
	}
	
	public void setDisabledSaveOpenExport() {
		saveButton.setEnabled(false);
		exportButton.setEnabled(false);
	}

}
