package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class About extends JDialog {

	private BufferedImage about;
	private BufferedImage iconImage;
	
	public About() {
		
		try {
			about = ImageIO.read(getClass().getResource("/about.png"));
			iconImage = ImageIO.read(getClass().getResource(("/icon_32.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		this.setIconImage(iconImage);
		this.setResizable(false);
		
		this.add(new JLabel(new ImageIcon(about)));
		
		this.setSize(596, 447);
	}
	
}
