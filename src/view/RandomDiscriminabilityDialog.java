package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * 
 * @author Alex
 *
 */

public class RandomDiscriminabilityDialog extends JDialog {
	
	private JLabel text;
	private JLabel currentValueLabel;
	private JTextField currentValueField;
	private JLabel newValueLabel;
	private JTextField newValueField;
	private JButton setButton;
	private JButton cancelButton;
	private JPanel panel;
	private BufferedImage iconImage;
	
	public RandomDiscriminabilityDialog(String t) {
		this.setTitle(t);
		
		try {
			iconImage = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(iconImage);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());
		this.add(panel);
		this.setSize(310, 135);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		text = new JLabel();
		JPanel helperText = new JPanel();
		helperText.add(text);
		panel.add(helperText, BorderLayout.NORTH);
		
		currentValueLabel = new JLabel("Current value: ");
		currentValueField = new JTextField(4);
		currentValueField.setEditable(false);
		currentValueField.setBackground(Color.WHITE);
		
		newValueLabel = new JLabel("New value: ");
		newValueField = new TextField(4);
		
		JPanel helperPanel = new JPanel();
		helperPanel.add(currentValueLabel);
		helperPanel.add(currentValueField);
		helperPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		helperPanel.add(newValueLabel);
		helperPanel.add(newValueField);
		
		panel.add(helperPanel, BorderLayout.CENTER);
		
		setButton = new JButton("Set");
		cancelButton = new JButton("Cancel");
		
		JPanel buttons = new JPanel();
		buttons.add(setButton);
		buttons.add(cancelButton);
		panel.add(buttons, BorderLayout.SOUTH);
	}
	
	public void setButtonListener (ActionListener listener) {
		setButton.addActionListener(listener);
	}
	
	public void cancelListener (ActionListener listener) {
		cancelButton.addActionListener(listener);
	}
	
	public void setCurrentValueField(String random) {
		currentValueField.setText(random);
		newValueField.setText("");
	}
	
	public String getNewValueField() {
		return newValueField.getText();
	}
	
	public void setNewValueField(String s) {
		newValueField.setText(s);
	}
	
	public void setValueOfText(String s) {
		text.setText(s);
	}
}
