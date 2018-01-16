package controller;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultEditorKit;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import utils.CustomFileFilter;
import utils.FileExtension;
import view.About;
import view.ChartDisplayData;
import view.ChartFrame;
import view.ChartHelper;
import view.MainFrame;
import view.MainPanel;
import view.PairsDialog;
import view.ProgressDialog;
import view.RandomDiscriminabilityDialog;
import view.TaskListener;

/**
 * All the communications between the GUI components are made through this
 * class. It creates as well the Controller of the application through which the
 * view package interacts with the model package.
 * 
 * @author Alex
 *
 */
public class GUIController {

	private MainFrame mainFrame;
	private Controller controller;
	private MainPanel mainPanel;
	private List<ChartFrame> chartFrames;
	private List<String> phaseNames;
	private SetParametersPanelImplementer listenForsetParametersButton;
	private RunEventImplementer listenForRunButton;
	private ItemEventImplementer itemEventImplementer;
	private JFileChooser fileChooser;
	private RandomDiscriminabilityDialog randomDialog;
	private RandomDiscriminabilityDialog discriminabilityDialog;

	private ProgressDialog progressDialog;

	private About about;

	private PairsDialog pairs;

	public GUIController() {

		String os = System.getProperty("os.name");

		// map keyboard keys
		if (os.toLowerCase().contains("mac")) {
			InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);
		}

		mainFrame = new MainFrame();

		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.setDefaultCloseOperation(mainFrame.DO_NOTHING_ON_CLOSE);
				int action = JOptionPane.showConfirmDialog(mainFrame, "Do you want to quit the simulator ? ",
						"Confirm Quittinq", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION)
					System.exit(0);

			}
		});

		controller = new Controller();
		mainPanel = mainFrame.getMainPanel();
		mainPanel.setData(controller.getData());
		chartFrames = new ArrayList<>();
		phaseNames = new ArrayList<>();
		listenForsetParametersButton = new SetParametersPanelImplementer();
		listenForRunButton = new RunEventImplementer();
		itemEventImplementer = new ItemEventImplementer();
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new CustomFileFilter("prce"));

		//
		randomDialog = new RandomDiscriminabilityDialog("Random");
		randomDialog.setValueOfText("Set the value for random number of trials: ");
		randomDialog.setCurrentValueField(controller.getRandomFromGroup());

		//
		discriminabilityDialog = new RandomDiscriminabilityDialog("Discriminability");
		discriminabilityDialog.setValueOfText("Set the value of overall discriminability : ");
		discriminabilityDialog.setCurrentValueField(controller.getDiscriminabilityFromGroup());

		// set parameters
		mainFrame.setParametersFromPhase(controller.getParametersFromPhase());

		mainFrame.newItemNewButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				controller.reset();

				mainFrame.setVisible(false);
				mainFrame.dispose();

				if (about != null)
					about.setVisible(false);

				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				// execute code by EDT
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						GUIController guiController = new GUIController();
						guiController.getMainFrame().setVisible(true);
					}

				});
			}

		});

		mainFrame.openItemOpenButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						if (file != null) {
							if (file.exists()) {
								if (FileExtension.getFileExtension(file.getName()) != null
										&& FileExtension.getFileExtension(file.getName()).equals("prce")) {
									mainPanel.getTableModel().clearColumnNames();

									int i = controller.openFromFile(file);
									while (i > 0) {
										mainPanel.getTableModel().addPhase();
										i--;
									}

									randomDialog.setCurrentValueField(controller.getRandomFromGroup());
									discriminabilityDialog
											.setCurrentValueField(controller.getDiscriminabilityFromGroup());

									// set parameters after open a file
									if (controller.getParametersFromPhase().get(0).isParametersPerPhase())
										mainFrame.getmenuBar().getParameteresPerPhaseItem().setSelected(true);
									mainFrame.setParametersFromPhase(controller.getParametersFromPhase());

									singleClickTableEditable();

								} else {
									JOptionPane.showMessageDialog(mainFrame,
											"File \"" + file.getName() + "\" has unknown format. Cannot open it.",
											"Unknown format", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(mainFrame, "Could not open data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});

		mainFrame.saveItemSaveButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						if (file != null) {
							if (file.exists()) {
								if (FileExtension.getFileExtension(file.getName()) != null
										&& FileExtension.getFileExtension(file.getName()).equals("prce")) {
									int action = JOptionPane.showConfirmDialog(mainFrame,
											"File \" " + file.getName() + " \" exists. Do you want to overwite it?",
											"File exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
									if (action == JOptionPane.OK_OPTION) {
										controller.saveToFile(file);
									}
								} else {
									JOptionPane.showMessageDialog(mainFrame,
											"File \"" + file.getName() + "\" has unknown format. Cannot overwrite it.",
											"Unknown format", JOptionPane.OK_OPTION);
								}
							} else {
								if (FileExtension.getFileExtension(file.toString()) != null) {
									String f = FileExtension.trimExtension(file.toString());
									file = new File(f + ".prce");
								} else {
									file = new File(file.toString() + ".prce");
								}
								if (file.exists()) {
									int action = JOptionPane.showConfirmDialog(mainFrame,
											"File \" " + file.getName() + " \" exists. Do you want to overwite it?",
											"File exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
									if (action == JOptionPane.OK_OPTION) {
										controller.saveToFile(file);
									}
									return;
								} else {
									controller.saveToFile(file);
								}
							}
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(mainFrame, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}

		});

		mainFrame.setExportItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new CustomFileFilter("xlsx"));
				if (fc.showDialog(mainFrame, "Export") == JFileChooser.APPROVE_OPTION) {

					try {
						File file = fc.getSelectedFile();
						if (file != null) {
							if (file.exists()) {
								if (FileExtension.getFileExtension(file.getName()) != null
										&& FileExtension.getFileExtension(file.getName()).equals("xlsx")) {
									int action = JOptionPane.showConfirmDialog(mainFrame,
											"File \" " + file.getName() + " \" exists. Do you want to overwite it?",
											"File exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
									if (action == JOptionPane.OK_OPTION) {

										controller.export(file);

									}
								} else {
									JOptionPane.showMessageDialog(mainFrame,
											"File \"" + file.getName() + "\" has unknown format. Cannot overwrite it.",
											"Unknown format", JOptionPane.OK_OPTION);
								}
							} else {
								if (FileExtension.getFileExtension(file.toString()) != null) {
									String f = FileExtension.trimExtension(file.toString());
									file = new File(f + ".xlsx");
								} else {
									file = new File(file.toString() + ".xlsx");
								}
								if (file.exists()) {
									int action = JOptionPane.showConfirmDialog(mainFrame,
											"File \" " + file.getName() + " \" exists. Do you want to overwite it?",
											"File exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
									if (action == JOptionPane.OK_OPTION) {

										controller.export(file);

									}
									return;
								} else {

									controller.export(file);

								}
							}
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(mainFrame,
								"Could not export data to file. Probably the file is being used by another process.",
								"Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}

		});
		mainPanel.removeGroupButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeGroup();
				mainPanel.getTableModel().fireTableStructureChanged();
				// mainFrame.setAlphaStimuli(controller.getAlphaStimuli());

				singleClickTableEditable();
			}
		});

		mainPanel.addGroupButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addGroup();
				mainPanel.getTableModel().fireTableStructureChanged();
				singleClickTableEditable();
			}
		});

		mainPanel.removePhaseButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removePhase();
				mainPanel.getTableModel().removePhase();
				if (mainFrame.getmenuBar().getParameteresPerPhaseItem().isSelected())
					mainPanel.removeParametersTab();
				// mainFrame.setAlphaStimuli(controller.getAlphaStimuli());

				singleClickTableEditable();
			}
		});

		mainPanel.addPhaseButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addPhase();
				mainPanel.getTableModel().addPhase();
				if (mainFrame.getmenuBar().getParameteresPerPhaseItem().isSelected())
					mainPanel.addParametersPhaseTab();

				singleClickTableEditable();
			}
		});

		mainPanel.addGroupTableListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int rowIndex = e.getFirstRow();
				int columnIndex = e.getColumn();
				TableModel model = (TableModel) e.getSource();
				if (rowIndex > -1 && columnIndex > -1) {
					String cellValue = String.valueOf(model.getValueAt(rowIndex, columnIndex));
					if (columnIndex == 0) {
						controller.setGroupName(rowIndex, cellValue);
					} else if (columnIndex % 2 == 1) {
						controller.setExperimentSequence(rowIndex, columnIndex / 2, cellValue);
					} else {
						controller.setPhaseRandom(rowIndex, (columnIndex - 1) / 2, cellValue);
					}

				}
				// test
				// controller.printData();
			}
		});

		mainPanel.addSetParameteresButtonListener(listenForsetParametersButton);

		mainPanel.addRunButtonListener(listenForRunButton);

		mainPanel.addDisplayFiguresButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChartHelper chartHelper = new ChartHelper();
				chartHelper.setDataset(controller.getDataset());
				ChartFrame chartFrame = null;

				for (ChartDisplayData c : chartHelper.getDisplayDataByPhase()) {
					chartFrame = new ChartFrame(c.getChartTitle(), c.getData());

					chartFrames.add(chartFrame);
				}

				Map<String, List<Object>> series = new TreeMap<>();
				int index = chartFrames.size() - 1;
				ChartFrame chart = chartFrames.get(index);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getPlot().getRenderer();
				for (int i = 0; i < chart.getDataset().getSeriesCount(); i++) {
					String seriesKey = (String) chart.getDataset().getSeriesKey(i);
					Shape shape = renderer.getSeriesShape(i);
					Paint paint = renderer.getSeriesPaint(i);
					Stroke stroke = renderer.getSeriesStroke(i);
					List<Object> properties = new ArrayList<>();
					properties.add(shape);
					properties.add(paint);
					properties.add(stroke);
					series.put(seriesKey, properties);
					chart.pack();
					chart.setVisible(true);
					if (chart.getDataset().getSeries(i).isEmpty())
						renderer.setSeriesVisibleInLegend(i, false);
				}

				for (int i = index - 1; i >= 0; i--) {
					ChartFrame ch = chartFrames.get(i);
					XYLineAndShapeRenderer rend = (XYLineAndShapeRenderer) ch.getPlot().getRenderer();
					List<String> l = new ArrayList<>();
					for (int j = 0; j < ch.getDataset().getSeriesCount(); j++) {
						String seriesName = (String) ch.getDataset().getSeriesKey(j);
						l.add(seriesName);
						if (series.containsKey(seriesName)) {
							rend.setSeriesShape(j, (Shape) series.get(seriesName).get(0));
							rend.setSeriesPaint(j, (Paint) series.get(seriesName).get(1));
							rend.setSeriesStroke(j, (Stroke) series.get(seriesName).get(2));
						}
						if (ch.getDataset().getSeries(j).isEmpty())
							rend.setSeriesVisibleInLegend(j, false);
					}
					ch.pack();
					ch.setLocationByPlatform(true);
					ch.setVisible(true);
				}

				chartFrames.clear();

			}
		});

		mainFrame.getmenuBar().getParameteresPerPhaseItem().addItemListener(itemEventImplementer);

		mainFrame.setAlphaValuesItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					mainPanel.showAlphaPanel();
				if (e.getStateChange() == ItemEvent.DESELECTED)
					mainPanel.hideAlphaPanel();

			}

		});

		mainFrame.setExtensionItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					controller.setExtension(true);
					// mainFrame.inputPairsItemSetEnabled(true);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					controller.setExtension(false);
					mainFrame.inputPairsItemSetEnabled(false);
				}
			}

		});

		mainFrame.setInputPairsListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pairs != null)
					pairs.setVisible(false);
				pairs = new PairsDialog();
				pairs.append(controller.getPairs());
				pairs.setLocationRelativeTo(mainFrame);
				pairs.setVisible(true);

			}

		});

		mainFrame.setRandomItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				randomDialog.setLocationRelativeTo(mainFrame);
				randomDialog.setVisible(true);
			}

		});

		mainFrame.setDItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				discriminabilityDialog.setLocationRelativeTo(mainFrame);
				discriminabilityDialog.setVisible(true);
			}

		});

		randomDialog.setButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String random = randomDialog.getNewValueField();

				int i = 0;

				try {

					Integer rand = Integer.parseInt(random);

					if (rand >= 1 && rand <= 10000) {

						controller.setRandom(rand);

					} else {
						i = 1;
						throw new NumberFormatException();
					}

				} catch (NumberFormatException ex) {
					i = 1;
					mainPanel.displayErrorMessage("Random must be a number between 1 and 10 000.");
					randomDialog.setNewValueField("");
				}

				if (i == 0) {
					randomDialog.setCurrentValueField(random);
					randomDialog.setVisible(false);
				}
			}

		});

		randomDialog.cancelListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				randomDialog.setVisible(false);
			}

		});

		discriminabilityDialog.setButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String discriminability = discriminabilityDialog.getNewValueField();

				int i = 0;

				try {

					Integer d = Integer.parseInt(discriminability);

					if (d >= 1 && d <= 100) {

						controller.setDiscriminability(d);

					} else {
						i = 1;
						throw new NumberFormatException();
					}

				} catch (NumberFormatException ex) {
					i = 1;
					mainPanel.displayErrorMessage("Discriminability must be a number between 1 and 100.");
					discriminabilityDialog.setNewValueField("");
				}

				if (i == 0) {
					discriminabilityDialog.setCurrentValueField(discriminability);
					discriminabilityDialog.setVisible(false);
				}
			}

		});

		discriminabilityDialog.cancelListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				discriminabilityDialog.setVisible(false);
			}

		});

		mainFrame.addGetAlphaButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (correctSequence() != null) {
					mainFrame.setAlphaStimuli(controller.getAlphaStimuli());
				}
			}

		});

		mainFrame.addClearAllButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.clearAll();
				// mainPanel.setData(controller.getData());
				mainPanel.getTableModel().fireTableDataChanged();
				singleClickTableEditable();
			}

		});

		mainFrame.setAboutItemListner(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (about != null)
					about.setVisible(false);
				about = new About();
				about.setLocationRelativeTo(mainFrame);
				about.setVisible(true);
			}
		});

		mainFrame.setGuideItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String url = "https://www.cal-r.org/PearceSimulator/Pearce_Simulator1_Guide.pdf";

				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (IOException | URISyntaxException ex) {
						ex.printStackTrace();
					}
				} else {
					String osVersion = System.getProperty("os.name").toLowerCase();
					Runtime runTime = Runtime.getRuntime();
					try {
						if (osVersion.contains("win"))
							runTime.exec("cmd /c start " + url);
						else if (osVersion.contains("mac"))
							runTime.exec("open" + url);
						else
							;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		});

		// about.dispose();
		mainFrame.setVisible(true);
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public About getAbout() {
		return about;
	}

	// private

	private class ItemEventImplementer implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {

				createPerPhaseTabbedPane();

				controller.setIsParametersPerPhase(true);
				mainFrame.setParametersFromPhase(controller.getParametersFromPhase());

			}
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				mainPanel.createAllPhasesTabbedPane();
				controller.setIsParametersPerPhase(false);
				mainFrame.setParametersFromPhase(controller.getParametersFromPhase());
			}
		}
	}

	private class SetParametersPanelImplementer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (correctSequence() != null) {

				if (mainFrame.isExtensionItemSelected())
					mainFrame.inputPairsItemSetEnabled(true);

				boolean correctFormat = false;

				Map<String, String> stimulusAlphaMap = mainPanel.getStimulusAlphaMap();

				try {

					for (String s : stimulusAlphaMap.keySet()) {
						String alpha = stimulusAlphaMap.get(s);

						Double a = Double.parseDouble(alpha);

						if (a > 1 || a < 0) {
							throw new NumberFormatException();
						}
					}

					controller.setAlphaParameters(stimulusAlphaMap);
					correctFormat = true;
				} catch (NumberFormatException exception) {
					mainPanel.displayErrorMessage("Alpha must be a number between 0 and 1.");
					correctFormat = false;
					return;
				}

				List<List<String>> parameters = mainPanel.getParameters();
				if (parameters.size() == 1) {

					String betaPlus = parameters.get(0).get(0);
					String betaMinus = parameters.get(0).get(1);
					String lambdaPlus = parameters.get(0).get(2);
					String lambdaMinus = parameters.get(0).get(3);

					String errorString = new String("Parameters must be a numeric value.");

					try {

						Double bPlus = Double.parseDouble(betaPlus);
						Double bMinus = Double.parseDouble(betaMinus);
						Double lPlus = Double.parseDouble(lambdaPlus);
						Double lMinus = Double.parseDouble(lambdaMinus);

						if ((lMinus < -100 || lMinus > 100) || (lPlus < 0 || lPlus > 100)) {
							errorString = "Lambda must be a number between -100 and 100.";
							throw new NumberFormatException();
						} else if ((bPlus < 0 || bPlus > 1) || (bMinus < 0 || bMinus > 1)) {
							errorString = "Beta must be a number between 0 and 1.";
							throw new NumberFormatException();
						}

						controller.setParameters(betaPlus, betaMinus, lambdaPlus, lambdaMinus);
						correctFormat = true;
					} catch (NumberFormatException exception) {
						mainPanel.displayErrorMessage(errorString);
						correctFormat = false;
						return;
					}
				} else {
					int i = 0;
					for (List<String> l : parameters) {
						// String alpha = parameters.get(i).get(0);
						String betaPlus = parameters.get(i).get(0);
						String betaMinus = parameters.get(i).get(1);
						String lambdaPlus = parameters.get(i).get(2);
						String lambdaMinus = parameters.get(i).get(3);

						String errorString = new String("Parameters must be a number value.");

						try {

							Double bPlus = Double.parseDouble(betaPlus);
							Double bMinus = Double.parseDouble(betaMinus);
							Double lPlus = Double.parseDouble(lambdaPlus);
							Double lMinus = Double.parseDouble(lambdaMinus);

							if ((lMinus < 0 || lMinus > 100) || (lPlus < 0 || lPlus > 100)) {
								errorString = "Lambda must be a number between 0 and 100.";
								throw new NumberFormatException();
							} else if ((bPlus < 0 || bPlus > 1) || (bMinus < 0 || bMinus > 1)) {
								errorString = "Beta must be a number between 0 and 1.";
								throw new NumberFormatException();
							}

							controller.setParametersPerPhase(i, betaPlus, betaMinus, lambdaPlus, lambdaMinus);
							correctFormat = true;
						} catch (NumberFormatException exception) {
							mainPanel.displayErrorMessage(errorString);
							correctFormat = false;
							return;
						}
						i++;
					}
				}
				if (correctFormat) {
					mainPanel.getRunButton().setEnabled(true);
				} else {
					mainPanel.getRunButton().setEnabled(false);
				}
			}
		}
	}

	private String correctSequence() {
		TableModel model = mainPanel.getTableModel();
		String cellValue = "";
		Set<String> groups = new HashSet<>();
		String groupName = "";
		for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
			for (int columnIndex = 1; columnIndex < model.getColumnCount(); columnIndex += 2) {
				cellValue = String.valueOf(model.getValueAt(rowIndex, columnIndex));

				int errorCode = controller.isExperimentSequenceCorrectFormat(cellValue);
				groupName = String.valueOf(model.getValueAt(rowIndex, 0));

				if (groupName.isEmpty()) {
					mainPanel.displayErrorMessage("Groups must have a name.");
					return null;
				}

				// !! needs updates !!
				switch (errorCode) {
				case 0:
					mainPanel.displayErrorMessage("Experiment sequence in " + model.getColumnName(columnIndex) + " in "
							+ groupName + " is empty.");
					return null;
				case -1:
					mainPanel.displayErrorMessage("Experiment sequence in " + model.getColumnName(columnIndex) + " in "
							+ groupName + " contains spaces.");
					return null;
				case -2:
					mainPanel.displayErrorMessage("Experiment sequence in " + model.getColumnName(columnIndex) + " in "
							+ groupName + " is in wrong format.");
					return null;
				}

			}
			if (groups.add(groupName) == false) {
				mainPanel.displayErrorMessage("Groups must have different names.");
				mainPanel.getRunButton().setEnabled(false);
				return null;
			}
		}
		return cellValue;
	}

	private void createPerPhaseTabbedPane() {
		TableModel model = mainPanel.getTableModel();

		for (int i = 0; i < model.getColumnCount(); i++) {
			if (model.getColumnName(i).contains("Phase"))
				phaseNames.add(model.getColumnName(i));
		}
		mainPanel.createPerPhaseTabbedPane(phaseNames);
		phaseNames.clear();
	}

	private void singleClickTableEditable() {
		// single click to edit cells
		JTable groupTable = mainFrame.getMainPanel().getGroupTable();
		DefaultCellEditor dce = new DefaultCellEditor(mainPanel.getTableTextField());
		dce.setClickCountToStart(1);
		groupTable.getColumnModel().getColumn(0).setCellEditor(dce);
		for (int i = 1; i < groupTable.getColumnModel().getColumnCount(); i = i + 2) {
			groupTable.getColumnModel().getColumn(i).setCellEditor(dce);
			groupTable.getColumnModel().getColumn(i).setPreferredWidth(120);
			groupTable.getColumnModel().getColumn(i + 1).setPreferredWidth(60);
		}

	}

	private class RunEventImplementer implements ActionListener, PropertyChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			progressDialog = new ProgressDialog(mainFrame);

			progressDialog.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					controller.cancelCalculation();
				}
			});

			mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			mainFrame.inactive();
			mainPanel.getDisplayFiguresButton().setEnabled(false);
			progressDialog.setVisible(true);

			class Task extends SwingWorker<Void, Void> implements TaskListener {

				boolean cancel = false;

				@Override
				protected Void doInBackground() throws Exception {

					// Initialize progress property.
					setProgress(0);
					cancel = controller.runExperimentCalculation(this);
					return null;
				}

				@Override
				public void progress(int progress) {
					this.setProgress(progress);
				}

				/*
				 * Executed in event dispatching thread
				 */
				@Override
				public void done() {

					if (cancel) {
						mainFrame.active();
						mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						mainPanel.getDisplayFiguresButton().setEnabled(false);
						mainPanel.getRunButton().setEnabled(false);
						mainFrame.setDisabledSaveOpenExport();
					} else {

						progressDialog.setVisible(false);
						mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						mainFrame.active();

						mainPanel.getDisplayFiguresButton().setEnabled(true);
						mainPanel.getRunButton().setEnabled(false);
						mainFrame.setEnabledSaveOpenExport();
					}

				}
			}

			try {
				Task task = new Task();
				task.addPropertyChangeListener(listenForRunButton);
				task.execute();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if ("progress" == e.getPropertyName()) {
				int progress = (Integer) e.getNewValue();
				progressDialog.updateProgressBar(progress);
			}
		}
	}
}
