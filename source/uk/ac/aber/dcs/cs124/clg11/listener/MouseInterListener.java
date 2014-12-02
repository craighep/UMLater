package uk.ac.aber.dcs.cs124.clg11.listener;

import java.awt.event.*;
import javax.swing.*;

import uk.ac.aber.dcs.cs124.clg11.frame.*;
import uk.ac.aber.dcs.cs124.clg11.panel.*;

/**
 * Listener for mouse click events in {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} to retrieve mouse position data
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall(crh13)
 *
 */
public class MouseInterListener implements MouseListener{

	private CanvasPanel canvasPane;

	private ButtonPanel buttonPane;

	private NewRelDialog relDialog;

	private int[] clickArray = new int[4];

	int count;
	int arrayCount = 0;

	/**
	 * Initialises links to panels to allow functions to be performed
	 * @param bp - {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} link to allow listening for mouse clicks
	 * 
	 * @param cp - {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel} link to allow access to function booleans
	 * 
	 */
	public MouseInterListener(CanvasPanel cp, ButtonPanel bp) {
		canvasPane = cp;
		buttonPane = bp;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	/** Listens for mouse click input to retrieve mouse position coordinate data.
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * @throws NullPointerException
	 */
	public void mouseClicked(MouseEvent e) throws NullPointerException {
		
		if (buttonPane.addVar == true) { //If user wants to add a class	
			
			//If left mouse button is being clicked
			if(SwingUtilities.isLeftMouseButton(e)) {

				//Display AddClassDialog window with link to CanvasPanel and mouse position coordinates
				new AddClassDialog(canvasPane, e.getX(), e.getY());
				
				//Disable boolean to stop 'add' functionality
				buttonPane.addVar = false;
				
				//Update status bar in UMLFrame
				canvasPane.setStatus("Class location confirmed");
			} 
		} else if (buttonPane.addRel == true) { //If user wants to add a relationship	

			if (count < 2) { //Check how many times user has clicked
				
				//If the user has clicked less than twice
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					
					//Add mouse position X coordinate to array
					clickArray[arrayCount] = e.getX();
					
					//Increment array
					arrayCount++;
					
					//Add mouse position Y coordinate to array
					clickArray[arrayCount] = e.getY();
					arrayCount++;
					
					//Increment number of times user has clicked
					count++;
				}
			}
			
			if (count == 2) { //If user has clicked twice
				
				//Open new 'NewRelDialog' window with both sets of mouse position coordinates for both "clicks"
				relDialog = new NewRelDialog(canvasPane, clickArray[0], clickArray[1], clickArray[2], clickArray[3]);
				
				//Disable boolean to stop 'add' functionality
				buttonPane.addRel = false;
				
				//Reset stats variables
				count = 0;
				arrayCount = 0;
				
			}
			
		} else if (buttonPane.delItem == true) { //If user wants to delete an item
			
			if(SwingUtilities.isLeftMouseButton(e)) {
				
				//Remove the class that has position coordinates closest to the position where the user clicked
				canvasPane.removeClass(e.getX(), e.getY());
				
				//Disable boolean to stop 'delete' functionality
				buttonPane.delItem = false;
			}
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}
