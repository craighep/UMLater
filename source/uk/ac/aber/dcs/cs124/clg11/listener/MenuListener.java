package uk.ac.aber.dcs.cs124.clg11.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel;
import uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel;

/**
 * Listener for menu bar in {@link uk.ac.aber.dcs.cs124.clg11.frame.UMLFrame} to functions
 * to be performed when menu items are clicked.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */

public class MenuListener implements ActionListener{
	
	private CanvasPanel canvasPane;
	private ButtonPanel buttonPane;

	/**
	 * Initialises links to panels to allow functions to be performed
	 * @param bp - {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel} link to allow listening to
	 * take place on control buttons
	 * @param cp - {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} link to allow methods in that class to be run
	 * 
	 */
	public MenuListener(CanvasPanel cp, ButtonPanel bp) {
		this.canvasPane = cp;
		this.buttonPane = bp;
	}
	
	/** Listens for user input to allow interactive components to be used.
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Choose Background Colour")) {
			
			//Set initial colour as current background colour of CanvasPanel
			Color initialBackground = canvasPane.getBackground();
			
			//Display JColorChooser dialog
			Color background = JColorChooser.showDialog(null,"UMLator | Select Background Colour", initialBackground);
		        
			if (background != null) {
			
				//Set CanvasPanel colour as selected colour from dialog
		          canvasPane.setBackground(background);
		          
		        }
			
		} else if (actionCommand.equals("Choose Class Colour")) {
			
			Color initialBackground = canvasPane.getBackground();
			Color classColour = JColorChooser.showDialog(null,"UMLator | Select Class Colour", initialBackground);
			
		        if (classColour != null) {
		        
		        //Set class diagram background colour as selected colour from dialog
		          canvasPane.changeColour(classColour);
		          
		        }
		        
		} else if (actionCommand.equals("Choose Class Text Colour")) {
			
			Color initialBackground = canvasPane.getBackground();
			Color textColour = JColorChooser.showDialog(null,"UMLator | Select Text/Line Colour", initialBackground);
			
		        if (textColour != null) {
		        
		        //Set class diagram text/line colour as selected colour from dialog
		          canvasPane.changeTextColour(textColour);
		          
		        }
		        
		} else if (actionCommand.equals("New Class")) {
			
			canvasPane.setStatus("Click position for new class");
			buttonPane.addVar = true;
			
		} else if (actionCommand.equals("New Relationship")) {
			
			buttonPane.addRel = true;
			
		} else if (actionCommand.equals("Clear Canvas")) {
			
			canvasPane.clearCanvas();
			canvasPane.setStatus("Canvas Cleared Successfully");
			
		} else if (actionCommand.equals("Save Project")) {
			
			canvasPane.saveFile();
			
		} else if (actionCommand.equals("Load Project")) {
			
			canvasPane.loadFile();
			
		} else if (actionCommand.equals("Export Java Code")) {
			
			canvasPane.exportClass();
			
		} else if (actionCommand.equals("Exit")) {
			
			canvasPane.exitProgram();
			
		} else if (actionCommand.equals("Undo")) {
			
			canvasPane.undoAction();
			
		} else if (actionCommand.equals("About")) {
			
			//Display 'About' dialog
			canvasPane.about();
		}
		
	}

}
