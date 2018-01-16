package view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenu extends JPopupMenu {

	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem cutItem;
	private JMenuItem selectAllItem;
	private JMenuItem clearItem;

	public PopupMenu() {
		copyItem = new JMenuItem("Copy");
		pasteItem = new JMenuItem("Paste");
		cutItem = new JMenuItem("Cut");
		selectAllItem = new JMenuItem("Select All");
		clearItem = new JMenuItem("Clear");

		this.add(selectAllItem);
		this.addSeparator();
		this.add(copyItem);
		this.add(pasteItem);
		this.add(cutItem);
		this.addSeparator();
		this.add(clearItem);
	}

	public void addCopyItemListener(ActionListener l) {
		copyItem.addActionListener(l);
	}
	
	public void addPasteItemListener(ActionListener l) {
		pasteItem.addActionListener(l);
	}
	
	public void addCutItemListener(ActionListener l) {
		cutItem.addActionListener(l);
	}
	
	public void addSelectAllItemListener(ActionListener l) {
		selectAllItem.addActionListener(l);
	}

	public void addClearItemListener(ActionListener l) {
		clearItem.addActionListener(l);
	}

	public String getTempText() {
		String s = new String("") ;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			s = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return s;
	}

	public void setTempText(String tempText) {
		StringSelection stringSelection = new StringSelection(tempText);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

}
