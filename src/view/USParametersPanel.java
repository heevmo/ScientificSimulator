package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Parameters;

/**
 * 
 * @author Alex
 *
 */
public class USParametersPanel extends JPanel {

	private JButton setParametersButton;
	private JTextField alphaTextField;
	private JTextField betaPlusTextField;
	private JTextField betaMinusTextField;
	private JTextField lambdaPlusTextField;
	private JTextField lambdaMinusTextField;

	public USParametersPanel() {

		JPanel helpPanel = new JPanel();
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		this.add(p, BorderLayout.NORTH);
		this.add(helpPanel, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(575, 51));
		this.setBorder(BorderFactory.createEtchedBorder());
		// this.add(new JLabel("CS \u03B1"));
		//alphaTextField = new JTextField(2);
		// this.add(alphaTextField);
		//alphaTextField.setText("1");
		// helpPanel.add(Box.createRigidArea(new Dimension(45, 0)));

		helpPanel.add(new JLabel("US  \u03B2+ ="));
		// helpPanel.setBackground(Color.white);
		betaPlusTextField = new TextField(4);
		betaPlusTextField.setText("0.5");
		betaPlusTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		helpPanel.add(betaPlusTextField);
		helpPanel.add(Box.createRigidArea(new Dimension(22, 0)));

		helpPanel.add(new JLabel("US  \u03B2- ="));
		betaMinusTextField = new TextField(4);
		betaMinusTextField.setText("0.45");
		betaMinusTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		helpPanel.add(betaMinusTextField);
		helpPanel.add(Box.createRigidArea(new Dimension(22, 0)));

		helpPanel.add(new JLabel("US  \u03BB+ ="));
		lambdaPlusTextField = new TextField(4);
		lambdaPlusTextField.setText("1");
		lambdaPlusTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		helpPanel.add(lambdaPlusTextField);
		helpPanel.add(Box.createRigidArea(new Dimension(22, 0)));

		helpPanel.add(new JLabel("US  \u03BB- ="));
		lambdaMinusTextField = new TextField(4);
		lambdaMinusTextField.setText("0");
		lambdaMinusTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		helpPanel.add(lambdaMinusTextField);

		// etParametersButton = new JButton("Set parameters");
		// this.add(setParametersButton);

	}

	public JButton getSetParametersButton() {
		return setParametersButton;
	}

	public void setSetParametersButton(JButton setParametersButton) {
		this.setParametersButton = setParametersButton;

	}

	public JTextField getAlphaTextField() {
		return alphaTextField;
	}

	public void setAlphaTextField(JTextField alphaTextField) {
		this.alphaTextField = alphaTextField;
	}

	public JTextField getBetaPlusTextField() {
		return betaPlusTextField;
	}

	public void setBetaPlusTextField(JTextField betaPlusTextField) {
		this.betaPlusTextField = betaPlusTextField;
	}

	public JTextField getBetaMinusTextField() {
		return betaMinusTextField;
	}

	public void setBetaMinusTextField(JTextField betaMinusTextField) {
		this.betaMinusTextField = betaMinusTextField;
	}

	public JTextField getLambdaPlusTextField() {
		return lambdaPlusTextField;
	}

	public void setLambdaPlusTextField(JTextField lambdaPlusTextField) {
		this.lambdaPlusTextField = lambdaPlusTextField;
	}

	public JTextField getLambdaMinusTextField() {
		return lambdaMinusTextField;
	}

	public void setLambdaMinusTextField(JTextField lambdaMinusTextField) {
		this.lambdaMinusTextField = lambdaMinusTextField;
	}

	public void setParametersFromPhase(Parameters p) {
		betaPlusTextField.setText(p.getBetaPlus());
		betaMinusTextField.setText(p.getBetaMinus());
		lambdaPlusTextField.setText(p.getLambdaPlus());
		lambdaMinusTextField.setText(p.getLambdaMinus());
	}
	
	public void inactive() {
		betaPlusTextField.setEditable(false);
		betaMinusTextField.setEditable(false);
		lambdaPlusTextField.setEditable(false);
		lambdaMinusTextField.setEditable(false);
		betaPlusTextField.setBackground(Color.WHITE);
		betaMinusTextField.setBackground(Color.WHITE);
		lambdaPlusTextField.setBackground(Color.WHITE);
		lambdaMinusTextField.setBackground(Color.WHITE);

	}

	public void active() {
		betaPlusTextField.setEditable(true);
		betaMinusTextField.setEditable(true);
		lambdaPlusTextField.setEditable(true);
		lambdaMinusTextField.setEditable(true);
	}
}