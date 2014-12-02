package uk.ac.aber.dcs.cs124.clg11.listener;

import java.awt.event.*;
import uk.ac.aber.dcs.cs124.clg11.vector.*;
import uk.ac.aber.dcs.cs124.clg11.diag.*;
import uk.ac.aber.dcs.cs124.clg11.panel.*;
import javax.swing.*;

/**
 * Listener for mouse dragged events in {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} to retrieve mouse position data
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class MousePositionListener implements MouseMotionListener {

	private CanvasPanel canvasPane;
	/**
	 * Initialises links to panels to allow functions to be performed
	 * @param bp - {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} link to allow listening for mouse clicks
	 * 
	 * @param cp - {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel} link to allow access to function booleans
	 * 
	 */
	public MousePositionListener(CanvasPanel cp, ButtonPanel bp) {
		canvasPane = cp;
	}

	/** Listens for when the mouse is moved around the CanvasPanel
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {

		//Display the mouse position coordinates in the status bar in UMLFrame
		canvasPane.displayCoords(e.getX(), e.getY());

	}

	/** Listens for when the mouse is dragged around the CanvasPanel
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {

		//Determine the closest ClassDiag object from the current mouse position
		ClassDiag thisOne = canvasPane.findNearestClass(e.getX(),e.getY());

		if (thisOne !=null) {

			//Update the position of the ClassDiag object using the current mouse position
			thisOne.update(e.getX()-(thisOne.getWidth()/2), e.getY()-(thisOne.getHeight()/2));
			//Refresh the CanvasPanel to allow the class diagram to be 'dragged' around the panel
			canvasPane.repaint();
		}
	}
}
