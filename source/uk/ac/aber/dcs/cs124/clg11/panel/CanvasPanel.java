package uk.ac.aber.dcs.cs124.clg11.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData;
import uk.ac.aber.dcs.cs124.clg11.diag.*;
import uk.ac.aber.dcs.cs124.clg11.vector.*;
import uk.ac.aber.dcs.cs124.clg11.fileIO.*;

/**
 * Contained within {@link uk.ac.aber.dcs.cs124.clg11.frame.UMLFrame}.
 * Contains all drawn diagram objects created by user and allows those drawings to be interacted with by user. 
 * Also contains most methods relating to the use of those diagram objects once created. (e.g. exporting)
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class CanvasPanel extends JPanel {

	private VectorOfDrawables vod = new VectorOfDrawables();
	private CodeExport cEX = new CodeExport();
	private StatusPanel statusPane;
	private JFileChooser fc = new JFileChooser();
	private FileIO file = new FileIO();

	/**
	 * Initialises and displays the panel and all required components.
	 * @param sp - Link to {@link uk.ac.aber.dcs.cs124.clg11.panel.StatusPanel} to allow status messages
	 * and mouse coordinates to be displayed to the user.
	 */
	public CanvasPanel(StatusPanel sp) {

		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(1100,750)); //Set size of panel
		this.statusPane = sp;
		setStatus("Welcome to UMLator!");
	}

	/**
	 * Retrieves data for all diagram objects contained within itself and sends to {@link uk.ac.aber.dcs.cs124.clg11.fileIO.CodeExport}
	 * for exporting to Java code.
	 */
	public void exportClass() {

		//Check if there is any diagram objects to export
		if (vod.size() == 0) {

			//If not, display error message box
			JOptionPane.showMessageDialog(null, "No information available to export", "UMLator | Export Error", JOptionPane.ERROR_MESSAGE);

		} else { //Otherwise..

			//Display save dialog (Directories only)
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(null);

			//Check if user has approved save
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				//Loop through entire VectorOfDrawables
				for (int i = 0; i < vod.size(); i++) {

					//Obtain all instances of ClassDiag objects in VectorOfDrawables
					if (vod.get(i).getData() instanceof ClassDiagData) {

						//Load new ClassDiagData object with data from ClassDiagData object in VectorOfDrawables
						ClassDiagData d = (ClassDiagData) vod.get(i).getData();

						//Obtain all methods, variables and associated variables from data object
						ArrayList<String> opList = d.getMethods();
						ArrayList<String> varList = d.getInstanceVariables();
						ArrayList<String> asocVarList = d.getAssocInstanceVariables();

						try {

							//Set selected file path
							String fileDir = fc.getSelectedFile().getAbsolutePath();

							//Call to CodeExport to export convert and export Java code
							cEX.printOut(fileDir, varList.size(), opList.size(), asocVarList.size(), d.getClassName(), varList, opList, asocVarList);

							//Display message to use in status bar in UMLFrame
							setStatus("Java Export Complete");

							//Display message box confirming export
							JOptionPane.showMessageDialog(new JFrame(), "Java Exported Successfully", "UMLator | Code Export", JOptionPane.INFORMATION_MESSAGE);

						} catch (ArrayIndexOutOfBoundsException aI) {

							JOptionPane.showMessageDialog(new JFrame(), "Java Exported Failed - " + aI, "UMLator | Code Export", JOptionPane.ERROR_MESSAGE);
							setStatus("WARNING - IO Error");

						} catch (NullPointerException nP) {

							JOptionPane.showMessageDialog(new JFrame(), "Java Exported Failed - " + nP, "UMLator | Code Export", JOptionPane.ERROR_MESSAGE);
							setStatus("WARNING - IO Error");

						} catch (Exception e) {

							JOptionPane.showMessageDialog(new JFrame(), "Java Exported Failed - " + e, "UMLator | Code Export", JOptionPane.ERROR_MESSAGE);
							setStatus("WARNING - IO Error");

						}
					}

				}
			}
		}
	}

	/** Draws all diagram objects currently stored in {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}.
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Draw all objects in VectorOfDrawables
		vod.drawAll(g);
	}


	/**
	 * Locates the ClassDiag object within {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} 
	 * that has the nearest distance from it's own position, to the current mouse position.
	 * @param x - 'X' coordinate of the current mouse position 
	 * @param y - 'Y' coordinate of the current mouse position
	 * @return ClassDiag - Returns the closest 'ClassDiag' object
	 */
	public ClassDiag findNearestClass(int x, int y) {
		return (ClassDiag) (vod.findNearestClass(x, y));	
	}

	/**
	 * Removes all objects currently within {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} 
	 * and repaints CanvasPane. Therefore giving the effect of "clearing" the drawing canvas.
	 */
	public void clearCanvas() {
		//Remove all objects within VectorOfDrawables
		vod.clearAll();

		//Refresh the panel
		repaint();

		//Display message to user
		setStatus("Canvas Cleared Successfully");
	}

	/**
	 * Updates message displayed to user within  {@link uk.ac.aber.dcs.cs124.clg11.panel.StatusPanel} 
	 * @param status - String that is displayed to user in status bar
	 */
	public void setStatus(String status) {
		//Update status string
		statusPane.setStatus("Status: " + status);
	}


	/**
	 * Adds a new {@link uk.ac.aber.dcs.cs124.clg11.diag.Drawable} object of type 
	 * {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} or {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag} to
	 * {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}
	 * @param d - 'Drawable' type object to be added to VectorOfDrawables
	 */
	public void addDrawable(Drawable d) {
		//Add new 'Drawable' object to VectorOfDrawables
		vod.addDrawable(d);
	}

	/**
	 * Passes the entire {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} to 
	 * {@link uk.ac.aber.dcs.cs124.clg11.fileIO.FileIO} to saves the entire project as an XML file.
	 */
	public void saveFile() {
		//Save XML file using entire VectorOfDrawables
		file.saveProject(vod.getDrawable());
	}

	/**
	 * Loads XML data parsed from {@link uk.ac.aber.dcs.cs124.clg11.fileIO.FileIO}, into a 
	 * new {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} before refreshing the panel.
	 */
	public void loadFile() {

		try {
			//Load the data from XML file into VectorOfDrawables 
			vod.setDrawable(file.openProject());

		} catch (ArrayIndexOutOfBoundsException e) {
			//Display error message box
			JOptionPane.showMessageDialog(null, "File cannot be opened - " + e, "UMLator | Parser Error", JOptionPane.ERROR_MESSAGE);

		}

		//Refresh the panel
		repaint();
		setStatus("Successfully Loaded Project");
	}

	/**
	 * Removes the {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} object 
	 * (and related {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag} that is closest to the 
	 * current mouse position.
	 * @param x - 'X' coordinate of the current mouse position 
	 * @param y - 'Y' coordinate of the current mouse position
	 */
	public void removeClass(int x, int y) {

		//Check if there are any objects that could be removed
		if (vod.size() == 0) {
			//If not display error message
			JOptionPane.showMessageDialog(null, "No objects available to delete", "UMLator | Delete Error", JOptionPane.ERROR_MESSAGE);

		} else { //Otherwise..
			//Remove the nearest class from VectorOfDrawables
			vod.removeNearestClass(x, y);

			//Refresh the panel
			repaint();	
		}
	}

	/**
	 * Removes the last object to be stored in {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}/
	 */
	public void undoAction() {

		try {
			//Remove the last object stored in VectorOfDrawables
			vod.undoLast();

			//Refresh the panel
			repaint();

		} catch (ArrayIndexOutOfBoundsException ex) {

			//Show error message box
			JOptionPane.showMessageDialog(new JFrame(), "There are no actions to undo", "UMLator | Undo Error", JOptionPane.ERROR_MESSAGE);}
	}

	/**
	 * Changes the background colour of the ClassDiag objects using ColourChooser dialog.
	 * @param backColour - Colour selected by user
	 */
	public void changeColour(Color backColour) {

		//Check if there are any diagram objects
		if (vod.size() == 0) {

			//If not display error message
			JOptionPane.showMessageDialog(null, "No objects available to edit", "UMLator | Edit Error", JOptionPane.ERROR_MESSAGE);

		} else {

			//Loop through entire VectorOfDrawables
			for (int i = 0; i < vod.size(); i++) {

				//Select the current ClassDiag object
				ClassDiag selectClass = (ClassDiag) vod.get(i);

				//Set the new background colour
				selectClass.setBackColour(backColour);
			}

			//Refresh the panel
			repaint();
		}
	}

	/**
	 * Changes the text/line colour of the ClassDiag objects using ColourChooser dialog.
	 * @param textColour - Colour selected by user
	 */
	public void changeTextColour(Color textColour) {

		//Check if there are any diagram objects
		if (vod.size() == 0) {

			//If not display error message
			JOptionPane.showMessageDialog(null, "No objects available to edit", "UMLator | Edit Error", JOptionPane.ERROR_MESSAGE);

		} else {

			//Loop through entire VectorOfDrawables
			for (int i = 0; i < vod.size(); i++) {

				//Select the current ClassDiag object
				ClassDiag selectClass = (ClassDiag) vod.get(i);

				//Set the new text/line colour
				selectClass.setTextColour(textColour);
			}

			//Refresh the panel
			repaint();
		}
	}

	/**
	 * Displays dialog asking the user of they want to exit program. If they select "yes", the program terminates.
	 */
	public void exitProgram() {

		//Display question dialog
		int exit = JOptionPane.YES_OPTION;
		exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "UMLator | Exit", JOptionPane.YES_NO_OPTION);

		//If user selects "yes"
		if (exit == JOptionPane.YES_OPTION) {

			//Terminate the program
			System.exit(0);
		}
	}

	/**
	 * Updates mouse coordinates displayed to user within  {@link uk.ac.aber.dcs.cs124.clg11.panel.StatusPanel} 
	 * @param x - 'X' coordinate of the current mouse position 
	 * @param y - 'Y' coordinate of the current mouse position
	 */
	public void displayCoords(int x, int y) {
		//Update mouse coordinates display
		statusPane.setCoords(x, y);
	}

	/**
	 * Displays 'About' dialog
	 */
	public void about() {
		//Display the 'About' dialog
		JOptionPane.showMessageDialog(null, "Created by: Connor Goddard (clg11), Sam Jackson (slj11) & Craig Heptinstall (crh13) - Feb/March 2012","UMLator | About", JOptionPane.INFORMATION_MESSAGE);
	}
}