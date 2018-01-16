package view;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.GUIController;

/**
 * 
 * @author Alex
 *
 */
public class PearceSimulator {
	
	public static void main (String[] args) {
		
		// use the cross-platform Java Look&Feel(Metal) that looks the same on all platforms 
		try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
            
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
			}
			
		});
	}
}
