package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Alex
 *
 */
public class BottomToolBar extends JPanel {

	private BufferedImage logo;

	public BottomToolBar() {
		
		this.setBackground(Color.WHITE);

		try {
			logo = ImageIO.read(getClass().getResource(("/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(new JLabel(new ImageIcon(logo)));
		
	}
}