package uk.ac.aber.dcs.cs124.clg11.listener;

import java.awt.event.*;
import uk.ac.aber.dcs.cs124.clg11.panel.*;
import uk.ac.aber.dcs.cs124.clg11.listener.*;
import javax.swing.*;

/**
 * Listener for {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel} to allow program
 * control buttons to perform functions when clicked.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class ButtonListener implements ActionListener {
	
	private CanvasPanel canvasPane;
	private ButtonPanel buttonPane;
	
	/**
	 * Initialises links to panels to allow functions to be performed
	 * @param bp - {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel} link to allow listening to
	 * take place on control buttons
	 * @param cP - {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} link to allow methods in that class to be run
	 * 
	 */
	public ButtonListener(ButtonPanel bp, CanvasPanel cP) {
		canvasPane = cP;
		buttonPane = bp;
	}
	
	/** Listens for user input to allow interactive components to be used.
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		
		if (actionCommand.equals("New Class")) {
			
			//Display current status in program status bar
			canvasPane.setStatus("Click position for new class");
			
			//Set relevant boolean for MouseInterListener
			buttonPane.addVar = true;
			buttonPane.addRel = false;
			buttonPane.delItem = false;
			
		} else if (actionCommand.equals("Remove Class")) {
			
			buttonPane.delItem = true;
			buttonPane.addVar = false;;
			buttonPane.addRel = false;
			
		} else if (actionCommand.equals("Reset Canvas")) {
			
			//Clear the CanvasPanel
			canvasPane.clearCanvas();
			
		} else if (actionCommand.equals("Add Relationship")) {
			
			buttonPane.addRel = true;
			buttonPane.addVar = false;
			buttonPane.delItem = false;
			
		} else if (actionCommand.equals("Export Java")) {
			
			//Export the project as Java code
			canvasPane.exportClass();
			
		} else if (actionCommand.equals("Save Project")) {
			
			//Save the project as an XML file
			canvasPane.saveFile();
			
		} else if (actionCommand.equals("Load Project")) {
			
			//Load a project from an XML file
		    canvasPane.loadFile();
		    
		} else if (actionCommand.equals("Undo")) {
			
			//Undo the last diagram action
			canvasPane.undoAction();
			canvasPane.setStatus("Action un-done successfully");
			
		} else if (actionCommand.equals("Exit Program")) {
			
			//Close the program
			canvasPane.exitProgram();
		}
		
	}


}
