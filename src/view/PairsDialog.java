package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PairsDialog extends JDialog {

	private BufferedImage iconImage;

	private JTextArea textArea;
	
	public PairsDialog() {

		try {
			iconImage = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setIconImage(iconImage);
		this.setResizable(false);
		this.setTitle("Input pairs");
		this.setSize(596, 447);
		
		textArea = new JTextArea();
		//textArea.setEditable(false);
		this.add(new JScrollPane(textArea));
	}
	
	public void append(String text) {
		textArea.append(text);
	}

}
