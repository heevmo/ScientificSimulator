package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Parameters;

/**
 * The main frame of the application which holds the other GUI components.
 * 
 * @author Alex
 *
 */
public class MainFrame extends JFrame {

	private MenuBar menuBar;
	private TopToolBar topToolBar;
	private MainPanel mainPanel;
	private BottomToolBar bottomToolBar;
	private PopupMenu popupMenu;
	private BufferedImage icon;

	public MainFrame() {
		super("Pearce Model Simulator \u00a9 Version 1.0 2017");

		try {
			icon = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(icon);

		menuBar = new MenuBar();
		topToolBar = new TopToolBar();
		mainPanel = new MainPanel();
		bottomToolBar = new BottomToolBar();
		popupMenu = new PopupMenu();

		this.setLayout(new BorderLayout());
		this.setJMenuBar(menuBar);
		this.add(topToolBar, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(bottomToolBar, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(760, 650));
		this.setResizable(false);

		// Position the window in the centre of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}
	
	public void inactive() {
		
		for (Component c : menuBar.getComponents())
			c.setEnabled(false);
		
		topToolBar.inactive();
		
		mainPanel.inactive();
	}
	
	public void active() {
		
		for (Component c : menuBar.getComponents())
			c.setEnabled(true);
		
		topToolBar.active();
		
		mainPanel.active();
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public MenuBar getmenuBar() {
		return menuBar;
	}

	public void newItemNewButtonListener(ActionListener listener) {
		menuBar.newItemListener(listener);
		topToolBar.newButtonListener(listener);
	}

	public void openItemOpenButtonListener(ActionListener listener) {
		menuBar.openItemListener(listener);
		topToolBar.openButtonListener(listener);
	}

	public void saveItemSaveButtonListener(ActionListener listener) {
		menuBar.saveItemListener(listener);
		topToolBar.saveButtonListener(listener);
	}

	public void setRandomItemListener(ActionListener listener) {
		menuBar.setRandomItemListener(listener);
	}
	
	public void setDItemListener(ActionListener listener) {
		menuBar.setDItemListener(listener);
	}

	public void setAlphaValuesItemListener(ItemListener listener) {
		menuBar.setAlphaValuesItemListener(listener);
	}
	
	public void setExtensionItemListener(ItemListener listener) {
		menuBar.setExtensionItemListener(listener);
	}
	
	public void setInputPairsListener(ActionListener listener) {
		menuBar.setInputPairsListener(listener);
	}
	
	public void inputPairsItemSetEnabled(boolean b) {
		menuBar.inputPairsItemSetEnabled(b);
	}

	public void setParameteresPerPhaseItemListener(ItemListener listener) {
		menuBar.setAlphaValuesItemListener(listener);
	}

	public void addGetAlphaButtonListener(ActionListener listenForDisplayFiguresButton) {
		mainPanel.addGetAlphaButtonListener(listenForDisplayFiguresButton);
	}
	
	public void closeDisplayFiguresButtonListener(ActionListener listenForDisplayFiguresButton) {
		mainPanel.closeDisplayFiguresButtonListener(listenForDisplayFiguresButton);
	}

	public void setAlphaStimuli(Map<String, Double> stimuli) {
		mainPanel.setAlphaStimuli(stimuli);
	}

	public void setParametersFromPhase(List<Parameters> l) {
		mainPanel.setParametersFromPhase(l);
	}

	public void addClearAllButtonListener(ActionListener listenForClearAllButton) {
		mainPanel.addClearAllButtonListener(listenForClearAllButton);
	}

	public void setExportItemListener(ActionListener listener) {
		menuBar.setExportItemListener(listener);
		topToolBar.setExportItemListener(listener);
	}

	public void setEnabledSaveOpenExport() {
		menuBar.setEnabledSaveOpenExport();
		topToolBar.setEnabledSaveOpenExport();
	}

	public void setAboutItemListner(ActionListener listener) {
		menuBar.setAboutItemListner(listener);
	}
	
	public void setGuideItemListener(ActionListener listener) {
		menuBar.setGuideItemListener(listener);
	}
	
	public PopupMenu getPopupMenu() {
		return popupMenu;
	}
	
	public void setDisabledSaveOpenExport() {
		menuBar.setDisabledSaveOpenExport();
		topToolBar.setDisabledSaveOpenExport();
	}
	
	public boolean isExtensionItemSelected() {
		return menuBar.isExtensionItemSelected();
	}

}
