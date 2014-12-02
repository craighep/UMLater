package uk.ac.aber.dcs.cs124.clg11.panel;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

/**
 * Contained within {@link uk.ac.aber.dcs.cs124.clg11.frame.UMLFrame}.
 * Displays current status information about the program and the current mouse position coordinates
 * to the user as a 'status bar'.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class StatusPanel extends JPanel {

	private JLabel statusText = new JLabel();
	private JLabel mouseCoords = new JLabel();
	SpringLayout layout = new SpringLayout();
	private JSeparator statusSep = new JSeparator(JSeparator.VERTICAL);

	/**
	 * Initialises and displays the panel and all required components.
	 * @param cp - Link to {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} to allow status messages
	 * and mouse coordinates to be displayed to the user.
	 */
	public StatusPanel(CanvasPanel cp) {

		//Initialise and add components to panel
		this.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
		this.setLayout(layout);
		this.add(statusText);
		this.add(statusSep);
		this.add(mouseCoords);

		//Organise components using layout manager settings
		layout.putConstraint(SpringLayout.NORTH,statusText,2,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,statusText,5,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,statusSep,2,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH,statusSep,-2,SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST,statusSep,8,SpringLayout.EAST, statusText);

		layout.putConstraint(SpringLayout.NORTH,mouseCoords,2,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST,mouseCoords,-10,SpringLayout.EAST, this);

	}

	/**
	 * Displays the current system status. Retrieved from {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 * @param status - Current system status message to be displayed.
	 */
	public void setStatus(String status) {
		//Set the status label (statusText) to status string
		statusText.setText(status);
	}

	/**
	 * Displays the mouse position coordinates. Retrieved from {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 * @param x - 'X' coordinate of the current mouse position to be displayed
	 * @param y - 'Y' coordinate of the current mouse position to be diaplyed
	 */
	public void setCoords(int x, int y) {
		mouseCoords.setText("Mouse Coords: X: " + x + " | Y: " + y);
	}
}
