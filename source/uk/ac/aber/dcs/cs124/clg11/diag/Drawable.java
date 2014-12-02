package uk.ac.aber.dcs.cs124.clg11.diag;

import java.awt.*;
import java.awt.print.Printable;

import uk.ac.aber.dcs.cs124.clg11.data.ObjectData;

/**
 * Contains all variables and methods which are common to BOTH the
 * Class and Relationship diagrams. Extended from by {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} &
 * {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag}
 * 
 * Stored in {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public abstract class Drawable {
	
	protected int x, y;
	
	/**
	 * Interface used to store 'Drawable' type objects that are extended/used by {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} &
	 * {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag}.
	 */
	protected ObjectData data;
	
	//Generalised abstract method must be implemented
	//Tells the program how to draw this drawable to the canvas
	
	/**
	 * Abstract 'paint' class used by {@link uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag} & {@link uk.ac.aber.dcs.cs124.clg11.diag.RelationshipDiag}
	 * to draw the 'Drawable'object for both class and relationship diagrams
	 */
	public abstract void paint(Graphics g);
	
	/**
	 * @param d - Interface used to hold all data of the 'Drawable' type
	 */
	public void setData(ObjectData d) {
		data = d;
	}
	
	/**
	 * @return data - Interface used to hold all data of the 'Drawable' type
	 */
	public ObjectData getData() {
		return data;
	}

	/**
	 * @return x - X position coordinate of the 'Drawable' object on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x - X position coordinate of the 'Drawable' object on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y - Y position coordinate of the 'Drawable' object on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y - Y position coordinate of the 'Drawable' object on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	/**
	 * Calculates the distance between the position that the user clicks on {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}, 
	 * and all 'Drawable' object positions to determine closest class diagram from mouse position.
	 * 
	 * @param x - X coordinate of mouse after user clicks on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 * @param y - Y coordinate of mouse after user clicks on the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 * @return Distance between the user's mouse position when clicked, and 'Drawable' object
	 */
	public double distanceTo(double x, double y) {
		return (Math.abs(this.x-x) + Math.abs(this.y-y));
	}
	
}
