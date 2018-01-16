package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 * 
 * @author Alex
 *
 */

public class TextField extends JTextField implements MouseListener {

	private PopupMenu popupMenu;

	/*
	 * Constructor
	 */
	public TextField(int size) {
		super(size);
		this.setFont(new Font("Verdana", Font.PLAIN, 13));
		
		popupMenu = new PopupMenu();
		
		popupMenu.addCopyItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (TextField.this.getSelectedText() != null)
					popupMenu.setTempText(TextField.this.getSelectedText());

			}
		});

		popupMenu.addPasteItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = popupMenu.getTempText();
				int p = TextField.this.getCaretPosition();
				try {
					TextField.this.getDocument().insertString(p, s, null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		});

		popupMenu.addCutItemListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StringBuilder wholeText = new StringBuilder(TextField.this.getText());
				String cutText = TextField.this.getSelectedText();
				int start = TextField.this.getSelectionStart();
				int end = TextField.this.getSelectionEnd();
				wholeText.replace(start, end, "");
				TextField.this.setText(new String(wholeText));
				if (cutText != "")
					popupMenu.setTempText(cutText);
			}
			
		});
		
		popupMenu.addSelectAllItemListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextField.this.selectAll();
			}
			
		});
		
		popupMenu.addClearItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextField.this.setText("");
			}
			
		});
		
		this.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e) || e.isMetaDown() || e.isControlDown()) {
			popupMenu.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
