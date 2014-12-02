package uk.ac.aber.dcs.cs124.clg11.vector;

import java.awt.*;
import java.util.*;

import uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag;
import uk.ac.aber.dcs.cs124.clg11.diag.Drawable;
import uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag;

/**
 * Contains all {@link uk.ac.aber.dcs.cs124.clg11.diag.Drawable} type objects ({@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag}
 * & {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag}) in a Vector, to allow universal use by the system as required.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */

public class VectorOfDrawables extends Vector<Drawable> {

	private Vector<Drawable> drawable;

	/**
	 * Initialises new {@link uk.ac.aber.dcs.cs124.clg11.diag.Drawable} type vector.
	 */
	public VectorOfDrawables() {
		//Create new 'Drawable' type vector
		drawable = new Vector<Drawable>();
	}

	/**
	 * Adds new {@link uk.ac.aber.dcs.cs124.clg11.diag.Drawable} object 
	 * to 'drawable' vector.
	 * @param c - Drawable object to be added to vector 
	 */
	public void addDrawable(Drawable c) {
		//Add new 'Drawable' object to vector
		getDrawable().add(c);
	}

	/**
	 * Draws all {@link uk.ac.aber.dcs.cs124.clg11.diag.Drawable} object in
	 * vector to {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}.
	 * 
	 * Called by: {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}.
	 * @param g - PaintComponent
	 */
	public void drawAll(Graphics g) {
		Drawable currentObject;

		//Check if vector is empty
		if (getDrawable().size() > 0) {

			//If not, loop through vector
			for(int i=0; i < getDrawable().size(); i++) {

				//Set currentObject as next 'Drawable' object in vector
				currentObject = (Drawable)(getDrawable().get(i));

				//Draw that object to canvas
				currentObject.paint(g);
			}
		}
	}

	/**
	 * @return drawable - Entire 'Drawable' type vector
	 */
	public Vector<Drawable> getDrawable() {
		return drawable;
	}

	/** 
	 * Returns the current size of the vector
	 * @see java.util.Vector#size()
	 */
	public int size() {
		return getDrawable().size();
	}

	/** 
	 * Returns the next 'Drawable' object in the vector
	 * @see java.util.Vector#get(int)
	 */
	public Drawable get(int i) {
		return getDrawable().get(i);
	}


	/**
	 * Locates the 'closest' {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} object from
	 * the vector using the current mouse position coordinates and returns it.
	 * @param x - 'X' coordinate of the current mouse position 
	 * @param y - 'Y' coordinate of the current mouse position
	 * @return Drawable
	 */
	public Drawable findNearestClass(int x, int y) {

		//Set the local variables required for the comparison
		Drawable d = null;
		double minDist = Double.MAX_VALUE;
		int minDistIndex = -1;

		//Loop through the vector
		for(int i=0; i < getDrawable().size(); i++) {

			//Set 'd' as the next 'Drawable' object
			d = (Drawable)(getDrawable().get(i));

			//Compare the distance between the object's X/Y and mouse X/Y 
			//against the current minDist
			if(d.distanceTo(x,y) < minDist) {

				//If it's less, set the minDist to that value
				minDist = d.distanceTo(x,y);

				//Set the index of the item with the smallest distance to 'i'
				minDistIndex = i;
			}
		}
		d = getDrawable().get(minDistIndex);
		if(d instanceof ClassDiag) {
		   ClassDiag cd = (ClassDiag) d;
		    //Set the distance away from the mouse click before a class is registered
		    if(cd.getX() < x && cd.getX() + cd.getWidth()  > x && cd.getY() < y && cd.getY() + cd.getWidth() > y) {

			    //If the mouse position is with the threshold, return the 'Drawable' object with the smallest distance
			    return cd;

		    }
		}
		//Otherwise return nothing..
		return null;
	} 

	public void removeNearestClass(int x, int y) {

		Drawable d = null;
		double minDist = Double.MAX_VALUE;
		int minDistIndex = -1;

		for(int i=0; i < getDrawable().size(); i++) {

			d = (Drawable)(getDrawable().get(i));

			if(d.distanceTo(x,y) < minDist) {

				minDist = d.distanceTo(x,y);
				minDistIndex = i;
			}
		}
		
		d = getDrawable().get(minDistIndex);
		if(d instanceof ClassDiag) {
		   ClassDiag cd = (ClassDiag) d;
		    if(cd.getX() < x && cd.getX() + cd.getWidth()  > x && cd.getY() < y && cd.getY() + cd.getWidth() > y) {

			    d = drawable.get(minDistIndex);

			    //Loop through the vector
			    for(int i=0; i< drawable.size(); i++) {

				    //Check if the object is a 'RelationshipDiag' object
				    if (drawable.get(i) instanceof RelationshipDiag) {

					    //If it is linked the 'ClassDiag' object selected
					    if(((RelationshipDiag) drawable.get(i)).contains(d)) {

						    //Remove it (removes the relationship link if a class is removed)
						    drawable.remove(i);
					    }
				    }
			    }
			    //Remove the 'ClassDiag' object
			    drawable.remove(d);			
		    } 
		}
	} 

	/**
	 * Clears the entire vector of all objects.
	 */
	public void clearAll() {
		//Clear the vector
		getDrawable().clear();
	}

	/**
	 * @param drawable - Drawable vector to set
	 */
	public void setDrawable(Vector<Drawable> drawable) {
		this.drawable = drawable;
	}

	/**
	 *Removes the last object to be added to the vector 
	 */
	public void undoLast() {
		//Remove the last object to be added to the vector
		drawable.remove(drawable.size() - 1);
	}


}
