package uk.ac.aber.dcs.cs124.clg11.fileIO;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import uk.ac.aber.dcs.cs124.clg11.diag.Drawable;

/**
 * Exports/imports entire project to file using data retrieved from all {@link uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData} & 
 * {@link uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData} objects stored in 
 * {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} in XML format.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */

public class FileIO {

	private XMLDecoder xmlDecode;
	private XMLEncoder xmlEncode;

	private File projectDir;
	private JFileChooser fc = new JFileChooser();

	/**
	 * Saves a completely new XML file containing project to directory selected by user using
	 * {@link javax.swing.JFileChooser}.
	 * 
	 * @see javax.swing.JFileChooser
	 * @param v - Vector of all 'Drawable' objects stored in {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}
	 */
	private void saveNewProject(Vector<Drawable> v) {

		//Display save dialog
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
		int returnVal = fc.showSaveDialog(null);

		//Check if user has approved save
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//Set filepath for save
			projectDir = fc.getSelectedFile();
			
			//Write the Vector v to file in XML
			writeObject(v);
		}
	}

	/**
	 * Overwrites an existing XML file containing project to directory selected by user
	 * 
	 * @param v - Vector of all 'Drawable' objects stored in {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}
	 */
	public void saveProject(Vector<Drawable> v) {	
		//If the filepath has not being determined yet
		if (projectDir == null) {
			//Save a new file (open dialog)
			saveNewProject(v);
			
		} else {
			//Overwrite existing XML file using same filepath
			writeObject(v);
		}
	}

	/**
	 * Populates new 'Drawable' vector with data loaded in from XML file
	 * 
	 * @return vec - Vector of 'Drawables' populated from data in XML file
	 * @throws ArrayIndexOutOfBoundsException - Caught by {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public Vector<Drawable> openProject() throws ArrayIndexOutOfBoundsException {
		
		//Display load dialog
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
		int returnVal = fc.showOpenDialog(null);

		//Initialise new vector of drawables
		Vector<Drawable> vec = new Vector<Drawable>();

		//Check if user has approved save
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			projectDir = fc.getSelectedFile();
			
			//Populate new vector of drawables with data from XML file
			vec = readObject();
		}

		return vec;
	}

	/**
	 * Writes project 'Drawable' data to XML file using selected file path.
	 * 
	 * @param obj - Object containing all {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} data
	 * to write to XML file
	 */
	private void writeObject(Object obj) {
		
		try {
			//Create new XMLEncoder using streams set to file path selected by user
			xmlEncode = new XMLEncoder (new BufferedOutputStream(new FileOutputStream(projectDir.getAbsoluteFile())));
			
			//Write the vector of drawables object to XML file
			xmlEncode.writeObject(obj);
			
		} catch (FileNotFoundException e) {
			
			//Display error message box if exception is thrown
			JOptionPane.showMessageDialog(null, e, "File Not Found", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			
			//Always make sure to close XMLEncoder, even if exception is thrown
			xmlEncode.close();
		}
	}

	/**
	 * Reads/loads project 'Drawable' data from XML file to new {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} 
	 * using selected file path.
	 * 
	 * @return obj - Object containing all {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables} data retrieved from XML file
	 * @throws ArrayIndexOutOfBoundsException - Caught by {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	private Vector<Drawable> readObject () throws ArrayIndexOutOfBoundsException {
		
		try {
			//Create new XMLDecoder using streams set to file path selected by user
			xmlDecode = new XMLDecoder(new BufferedInputStream(new FileInputStream(projectDir.getAbsolutePath())));
			
			//Populate vector of drawables 'obj' with all 'Drawable' data from XML file
			Vector<Drawable> obj = (Vector<Drawable>) xmlDecode.readObject();
			return obj;
			
		} catch(FileNotFoundException e) {
			
			//Display error message box if exception is thrown
			JOptionPane.showMessageDialog(null, e, "File Not Found Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			
			//Always make sure to close XMLEncoder, even if exception is thrown
			xmlDecode.close();
			
		}
		return null;
	}
}
