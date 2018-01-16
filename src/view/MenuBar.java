package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * 
 * @author Alex
 *
 */
public class MenuBar extends JMenuBar {

	private JMenuItem parameteresPerPhaseItem;
	private JMenuItem setDValuesItem;
	private JMenuItem setAlphaValuesItem;
	private JMenuItem guideItem; 
	private JMenuItem aboutItem; 
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem exportItem;
	private JMenuItem quitItem;
	private JMenuItem randomItem;
	private JMenuItem extensionItem;
	private JMenuItem inputPairsItem;

	public MenuBar() {

		// File
		JMenu fileMenu = new JMenu("File");
		newItem = new JMenuItem("New");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exportItem = new JMenuItem("Export");
		quitItem = new JMenuItem("Quit");

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem.setMnemonic(KeyEvent.VK_N);
		openItem.setMnemonic(KeyEvent.VK_O);
		saveItem.setMnemonic(KeyEvent.VK_S);
		exportItem.setMnemonic(KeyEvent.VK_X);
		quitItem.setMnemonic(KeyEvent.VK_Q);

		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

		saveItem.setEnabled(false);
		exportItem.setEnabled(false);

		quitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(view.MenuBar.this.getParent(),
						"Do you want to quit the simulator ? ", "Confirm Quitting", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);

				}
			}

		});

		this.add(fileMenu);

		// Settings
		JMenu settingsMenu = new JMenu("Settings");

		randomItem = new JMenuItem("Number of Random Trial Combinations");
		setDValuesItem = new JMenuItem("Overall discriminability");
		
		settingsMenu.add(randomItem);
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		randomItem.setMnemonic(KeyEvent.VK_R);
		randomItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		
		settingsMenu.add(setDValuesItem);
		setDValuesItem.setMnemonic(KeyEvent.VK_D);
		setDValuesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		
		parameteresPerPhaseItem = new JCheckBoxMenuItem("Set different US parameters per phase");

		settingsMenu.add(parameteresPerPhaseItem);
		parameteresPerPhaseItem.setMnemonic(KeyEvent.VK_P);
		parameteresPerPhaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));

		setAlphaValuesItem = new JCheckBoxMenuItem("Set alpha values");
		settingsMenu.add(setAlphaValuesItem);
		setAlphaValuesItem.setMnemonic(KeyEvent.VK_A);
		setAlphaValuesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
		
		extensionItem = new JCheckBoxMenuItem("Extension 2001 for structural discriminations");
		settingsMenu.add(extensionItem);
		extensionItem.setMnemonic(KeyEvent.VK_E);
		extensionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));
		
		inputPairsItem = new JMenuItem("Show input pairs");
		settingsMenu.add(inputPairsItem);
		inputPairsItem.setMnemonic(KeyEvent.VK_I);
		inputPairsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK));
		inputPairsItem.setEnabled(false);

		this.add(settingsMenu);

		// Help
		JMenu helpMenu = new JMenu("Help");
		guideItem = new JMenuItem("Guide");
		aboutItem = new JMenuItem("About");

		helpMenu.add(guideItem);
		helpMenu.add(aboutItem);

		helpMenu.setMnemonic(KeyEvent.VK_H);
		guideItem.setMnemonic(KeyEvent.VK_G);
		aboutItem.setMnemonic(KeyEvent.VK_A);

		guideItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		this.add(helpMenu);
	}

	public void newItemListener(ActionListener listener) {
		newItem.addActionListener(listener);
	}

	public void openItemListener(ActionListener listener) {
		openItem.addActionListener(listener);
	}

	public void saveItemListener(ActionListener listener) {
		saveItem.addActionListener(listener);
	}

	public void setAlphaValuesItemListener(ItemListener listener) {
		setAlphaValuesItem.addItemListener(listener);
	}
	
	public void setExtensionItemListener(ItemListener listener) {
		extensionItem.addItemListener(listener);
	}
	
	public void setInputPairsListener(ActionListener listener) {
		inputPairsItem.addActionListener(listener);
	}
	
	public void inputPairsItemSetEnabled(boolean b) {
		inputPairsItem.setEnabled(b);
		
	}

	public void setParameteresPerPhaseItemListener(ItemListener listener) {
		parameteresPerPhaseItem.addItemListener(listener);
	}

	public void setRandomItemListener(ActionListener listener) {
		randomItem.addActionListener(listener);
	}
	
	public void setDItemListener(ActionListener listener) {
		setDValuesItem.addActionListener(listener);
	}

	public void setExportItemListener(ActionListener listener) {
		exportItem.addActionListener(listener);
	}

	public JMenuItem getParameteresPerPhaseItem() {
		return parameteresPerPhaseItem;
	}
	
	public void setAboutItemListner(ActionListener listener) {
		aboutItem.addActionListener(listener);
	}
	
	public void setGuideItemListener(ActionListener listener) {
		guideItem.addActionListener(listener);
	}

	public void setEnabledSaveOpenExport() {
		saveItem.setEnabled(true);
		openItem.setEnabled(true);
		exportItem.setEnabled(true);
	}
	
	public void setDisabledSaveOpenExport() {
		saveItem.setEnabled(false);
		exportItem.setEnabled(false);
	}
	
	public boolean isExtensionItemSelected() {
		return extensionItem.isSelected();
	}
}
