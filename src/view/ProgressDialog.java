package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

/**
 * 
 * @author Alex
 *
 */
public class ProgressDialog extends JDialog {

	private JProgressBar progressBar;
	private BufferedImage iconImage;

	public ProgressDialog(Window parent) {

		try {
			iconImage = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(iconImage);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize((new Dimension(200,25)));
		progressBar.setStringPainted(true);
		//progressBar.setMinimum(0);
		this.setSize(230, 85);
		this.setTitle("Running...");
		this.setLayout(new FlowLayout());
		this.add(progressBar);
		//this.pack();
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(parent);
	}
	
	public void updateProgressBar(int value) {
		progressBar.setValue(value);
	}

	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}

}
