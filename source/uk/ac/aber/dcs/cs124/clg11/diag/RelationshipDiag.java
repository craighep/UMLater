package uk.ac.aber.dcs.cs124.clg11.diag;

import java.awt.Graphics;
import uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData;
import uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData;

/**
 * GUI layer used to output/draw relationship diagram
 * to the {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}. 
 * 
 * Retrieves data about classes and relationship from {@link uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData} &  
 * {@link uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData} objects. 
 * 
 * Stored of type 'RelationshipDiag' in VectorOfDrawables
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class RelationshipDiag extends Drawable {

	private int diamondX, diamondY;

	private String arrowDir;

	/**
	 * RelationshipDiagData object used to store data relating to the relationship diagram
	 */
	private RelationshipDiagData reld = new RelationshipDiagData();

	public RelationshipDiag() {

	}

	/**
	 * Determines and sets the two classes to have the relationship drawn between including the relationship type and multiplicities
	 * 
	 * @param a - First 'ClassDiag' object to draw relationship from
	 * @param b - Second 'ClassDiag' object to draw relationship to
	 * @param assocVar - Associated variable for relationship used in classes
	 * @param classAMulti - Multiplicity value for first class
	 * @param classBMulti - Multiplicity value for second class
	 * @param relType - Relationship type (e.g. Inheritance, Aggregation etc..)
	 */
	public RelationshipDiag(ClassDiag a,ClassDiag b, String assocVar, String classAMulti, String classBMulti, String relType) {

		reld.setClassA(a);
		reld.setClassB(b);

		ClassDiagData d = (ClassDiagData) reld.getClassA().getData();
		d.addAssocInstanceVar(assocVar);

		reld.setAssocVar(assocVar);
		reld.setRelType(relType);
		reld.setClassAMulti(classAMulti);
		reld.setClassBMulti(classBMulti);
	}

	/**
	 * Determines whether the {@link uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData} object contains a reference to the selected classes
	 * @param d
	 * @return contains - Boolean
	 */
	public boolean contains(Drawable d) {
		if (getReld().getClassA().equals(d) || getReld().getClassB().equals(d)) {
			return true;
		}
		return false;
	}

	/** 
	 * Determines what relationship type has been selected, and draws the relationship diagram between the two classes, using data 
	 * retrieved from {@link uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData})
	 * 
	 * Includes automatic line drawing algorithm that allows relationship diagram to be automatically redrawn 
	 * depending on the positions of the two related classes at any given time.
	 * 
	 * @see uk.ac.aber.dcs.cs124.clg11.diag.Drawable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		/** 
		 * x1 - X position coordinate of first class
		 * x2 - X position coordinate of second class
		 * y1 - Y position coordinate of first class
		 * y2 - Y position coordinate of second class
		 * w1 - Width of first class
		 * w2 - Width of second class
		 * h1 - Height of first class
		 * h2 - Height of second class
		 * acx - Centre 'X' position of first class
		 * bcx - Centre 'X' position of second class
		 * acy - Centre 'Y' position of first class
		 * bcy - Centre 'Y' position of second class
		 */
		int x1 = getReld().getClassA().getX(), y1 = getReld().getClassA().getY(), x2 = getReld().getClassB().getX(), y2 = getReld().getClassB().getY();
		int w1 = getReld().getClassA().getWidth(), h1 = getReld().getClassA().getHeight(), w2 = getReld().getClassB().getWidth(), h2 = getReld().getClassB().getHeight(); //change 180 to getWidth
		int acx = x1+w1/2, acy = y1+h1/2, bcx = x2+w2/2, bcy = y2+h2/2;

		/* This algorithm is used to determine where the two classes that are linked by the particular relationship diagram 
		 * are positioned in relation to each other, using the X/Y and centre X/Y coordinates of both classes. It then draws lines between
		 * them, in the shortest distance possible but ENSURES that NO LINES OVERLAP EITHER OF THE CLASSES and will 
		 * automatically re-draw the lines as the user moves the two classes around the CanvasPanel. 
		 */
		if (Math.abs(acx-bcx) > Math.abs(acy-bcy)) { // Check if first class is above second class
			if (bcy < y1) {

				g.drawLine(acx, y1, acx, bcy); //Draw line from first class

				if(x2 > x1) { //Side 1
					System.out.println("Side 1");
					this.setDiamondX(x2 - 20); //Set the diamond X value to position any diamonds that will need to be drawn
					this.setDiamondY(bcy - 10); //Set the diamond Y value to position any diamonds that will need to be drawn
					setArrowDir("EAST"); //Set the arrow direction for inheritance

					if (getReld().getRelType().equals("ASSOCIATION")) { //Check the relationship type

						g.drawLine(acx, bcy, x2, bcy); /*If it is INHERITANCE, draw the line right up to the edge 
														of the second class */

					} else { 

						g.drawLine(acx, bcy, x2 - 20, bcy); //Otherwise draw the line 20 pixels back to make room for the diamond, filled arrow
					}

					g.drawString(getReld().getClassBMulti(), x2 - 40, bcy + 15); //Draw second class multiplicity
					g.drawString(getReld().getClassAMulti(), acx + 10, y1 - 15); //Draw first class multiplicity
					g.drawString(getReld().getAssocVar(), acx + 10, y1 - 50); //Draw associated variable

					//This algorithm is repeated for ALL potential positions of the two classes...

				} else { //Side 2
					this.setDiamondX(x2 + w2); 
					this.setDiamondY(bcy - 10);
					setArrowDir("WEST");

					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(acx, bcy, x2 + w2, bcy); 

					} else {

						g.drawLine(acx, bcy, x2 + w2 + 20, bcy); 

					}

					g.drawString(getReld().getClassBMulti(), x2 + getReld().getClassB().getWidth() + 20, bcy + 20); 
					g.drawString(getReld().getClassAMulti(), acx + 10, y1 - 15);
					g.drawString(getReld().getAssocVar(), acx + 10, y1 - 55);
				}

			} else if (bcy > y1 && bcy < y1 + h1) {

				if(x2 > x1) { //Side 3
					this.setDiamondX(x2 - 20); //2nd class left
					this.setDiamondY(bcy - 10);
					setArrowDir("EAST");

					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(x1 + w1, bcy, x2, bcy);

					} else {

						g.drawLine(x1 + w1, bcy, x2 - 20, bcy);	

					}

					g.drawString(getReld().getClassBMulti(), x2 - 40, bcy + 15); 
					g.drawString(getReld().getClassAMulti(), x1 + getReld().getClassA().getWidth() + 10, bcy + 15);
					g.drawString(getReld().getAssocVar(), x1 + getReld().getClassA().getWidth() + 10, bcy + 35); 

				} else { //Side 4
					this.setDiamondX(x2 + w2); 
					this.setDiamondY(bcy - 10);
					setArrowDir("WEST");

					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(x1, bcy, x2 + w2, bcy);

					} else {

						g.drawLine(x1, bcy, x2 + w2 + 20, bcy);

					}

					g.drawString(getReld().getClassBMulti(), x2 + getReld().getClassB().getWidth() + 30, bcy + 20);
					g.drawString(getReld().getClassAMulti(), x1 - 30, bcy + 20);
					g.drawString(getReld().getAssocVar(), x1 - 200, bcy - 20); 

				}

			} else if (bcy > y1 + h1) {

				g.drawLine(acx, y1 + h1, acx, bcy);

				if(x2 > x1) { //Side 5
					this.setDiamondX(x2 - 20); 
					this.setDiamondY(bcy - 10);
					setArrowDir("EAST");

					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(acx, bcy, x2, bcy);

					} else {

						g.drawLine(acx, bcy, x2 - 20, bcy);

					}

					g.drawString(getReld().getClassBMulti(), x2 - 40, bcy + 15); 
					g.drawString(getReld().getClassAMulti(), acx + 10, y1 + getReld().getClassA().getHeight() + 20);
					g.drawString(getReld().getAssocVar(), acx + 10, y1 + getReld().getClassA().getHeight() + 60); 

				} else { //Side 6
					this.setDiamondX(x2 + w2); 
					this.setDiamondY(bcy - 10);
					setArrowDir("WEST");
					System.out.println("Side 6");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(acx, bcy, x2 + w2, bcy);

					} else {

						g.drawLine(acx, bcy, x2 + w2 + 20, bcy);

					}

					g.drawString(getReld().getClassBMulti(), x2 + getReld().getClassB().getWidth() + 30, bcy + 20); 
					g.drawString(getReld().getClassAMulti(), acx + 10 ,y1 + getReld().getClassA().getHeight() + 20);
					g.drawString(getReld().getAssocVar(), acx + 10, y1 + getReld().getClassA().getHeight() + 60); 
				}
			}

		} else {

			if (bcx < x1) {

				g.drawLine(x1, acy, bcx, acy);

				if(y2 > y1) { //Side 7
					this.setDiamondX(bcx - 10);
					this.setDiamondY(y2 - 20);
					setArrowDir("SOUTH");

					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, acy, bcx, y2);

					} else {

						g.drawLine(bcx, acy, bcx, y2 - 20);		

					}

					g.drawString(getReld().getClassBMulti(), bcx + 10, y2 - 30); 
					g.drawString(getReld().getClassAMulti(), x1 - 20, acy + 20);
					g.drawString(getReld().getAssocVar(), x1 - 30 , y1 + getReld().getClassA().getHeight() + 20); 

				} else { //Side 8

					this.setDiamondX(bcx - 10);
					this.setDiamondY(y2 + h2);
					setArrowDir("NORTH");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, acy, bcx, y2 + h2);

					} else {

						g.drawLine(bcx, acy, bcx, y2 + h2 + 20); 

					}

					g.drawString(getReld().getClassBMulti(), bcx + 10, y2 + getReld().getClassB().getHeight() + 30); 
					g.drawString(getReld().getClassAMulti(), x1 - 30, acy - 10);
					g.drawString(getReld().getAssocVar(), bcx + 10, y2 + getReld().getClassB().getHeight() + 80); 
				}

			} else if (bcx > x1 && bcx < x1 + w1) {

				if(y2 > y1) { //Side 9

					this.setDiamondX(bcx - 10); 
					this.setDiamondY(y2 - 20);
					setArrowDir("SOUTH");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, y1 + h1, bcx, y2);

					} else {

						g.drawLine(bcx, y1 + h1, bcx, y2 - 20);

					}

					g.drawString(getReld().getClassBMulti(), bcx + 10, y2 - 20);
					g.drawString(getReld().getClassAMulti(), bcx + 10, y1 + getReld().getClassA().getHeight() + 20);
					g.drawString(getReld().getAssocVar(), bcx + 10, y1 + getReld().getClassA().getHeight() + 60); 

				} else { //Side 10

					this.setDiamondX(bcx - 10);
					this.setDiamondY(y2 + h2);
					setArrowDir("NORTH");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, y1, bcx, y2 + h2);

					} else {

						g.drawLine(bcx, y1, bcx, y2 + h2 + 20);

					}

					g.drawString(getReld().getClassBMulti(), bcx + 10,  y2 + getReld().getClassB().getHeight() + 30);
					g.drawString(getReld().getClassAMulti(), acx + 10, y1 - 10);
					g.drawString(getReld().getAssocVar(), bcx + 10, y2 + getReld().getClassB().getHeight() + 80); 
				}

			} else if (bcx > x1 + w1) {

				g.drawLine(x1+ w1, acy, bcx, acy);

				if(y2 > y1) { //Side 11

					this.setDiamondX(bcx - 10);
					this.setDiamondY(y2 - 20);
					setArrowDir("SOUTH");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, acy, bcx, y2);

					} else {

						g.drawLine(bcx, acy, bcx, y2 - 20);	

					}
					g.drawString(getReld().getClassBMulti(), bcx + 10, y2 - 25); 
					g.drawString(getReld().getClassAMulti(), x1 + getReld().getClassA().getWidth() + 10, acy - 10);
					g.drawString(getReld().getAssocVar(), x1 + getReld().getClassA().getWidth() + 30, y1 + 140); 

				} else { //Side 12

					this.setDiamondX(bcx - 10);
					this.setDiamondY(y2 + h2);
					setArrowDir("NORTH");
					if (getReld().getRelType().equals("ASSOCIATION")) {

						g.drawLine(bcx, acy, bcx, y2 + h2); 

					} else {

						g.drawLine(bcx, acy, bcx, y2 + h2 + 20);

					}

					g.drawString(getReld().getClassBMulti(), bcx + 10, y2 + getReld().getClassB().getHeight() + 30); 
					g.drawString(getReld().getClassAMulti(), x1 + getReld().getClassA().getWidth() + 10, acy - 10);
					g.drawString(getReld().getAssocVar(), x1 + getReld().getClassA().getWidth() + 50, y1 + 20); 
				}
			}
		}

		/** 
		 * x1 -'X' position of the left side of the diamond
		 */
		x1 = this.getDiamondX();

		/** 
		 * x2 -'X' position of the right side of the diamond
		 */
		x2 = this.getDiamondX() + 20;

		/** 
		 * y1 -'Y' position of the top side of the diamond
		 */
		y1 = this.getDiamondY();

		/** 
		 * y2 -'Y' position of the bottom side of the diamond
		 */
		y2 = this.getDiamondY() + 20;

		/** 
		 * startx - Start centre position of diamond on X axis
		 */
		int startx = (x1+x2)/2;  

		/** 
		 * starty - Start centre position of diamond on Y axis
		 */
		int starty = (y1+y2)/2;  

		if (getReld().getRelType().equals("AGGREGATION")) { //Determine relationship type

			//Draw un-filled diamond at end of line (edge of class 2)
			g.drawLine(x1, starty , startx , y1);  
			g.drawLine(startx , y1, x2, starty );  
			g.drawLine(x2, starty , startx , y2);  
			g.drawLine(startx , y2, x1, starty ); 

		} else if (getReld().getRelType().equals("ASSOCIATION")) {

			if (getArrowDir().equals("NORTH")) {

				//Draw arrow point facing required direction
				g.drawLine(startx , y1, x2, starty ); 
				g.drawLine(x1, starty , startx , y1); 

			} else if (getArrowDir().equals("SOUTH")) {



				g.drawLine(x2, starty , startx , y2);  
				g.drawLine(startx , y2, x1, starty ); 

			} else if (getArrowDir().equals("EAST")) {



				g.drawLine(startx , y1, x2, starty );  
				g.drawLine(x2, starty , startx , y2);  

			} else if (getArrowDir().equals("WEST")) {


				g.drawLine(x1, starty , startx , y1);
				g.drawLine(startx , y2, x1, starty ); 


			} 

		} else if (getReld().getRelType().equals("COMPOSITION")) {

			//Draw filled diamond at end of line (edge of class 2)
			int [] xpoints = { x1, startx, x2, startx, x1 };
			int [] ypoints = { starty, y1, starty, y2, starty };
			g.fillPolygon(xpoints, ypoints, xpoints.length);

		} else if (getReld().getRelType().equals("INHERITANCE")) {

			if (getArrowDir().equals("NORTH")) {

				//Draw closed arrow point facing required direction
				g.drawLine(startx , y1, x2, starty ); 
				g.drawLine(x1, starty , startx , y1); 
				g.drawLine(x1,starty, x2, starty);
				g.drawLine(startx,starty, startx, y2);

			} else if (getArrowDir().equals("SOUTH")) {

				g.drawLine(x2, starty , startx , y2);  
				g.drawLine(startx , y2, x1, starty ); 
				g.drawLine(x1,starty, x2, starty);
				g.drawLine(startx,starty, startx, y1);

			} else if (getArrowDir().equals("EAST")) {

				g.drawLine(startx , y1, x2, starty );  
				g.drawLine(x2, starty , startx , y2);
				g.drawLine(startx,y1, startx, y2);
				g.drawLine(startx,starty, x1, starty);

			} else if (getArrowDir().equals("WEST")) {

				g.drawLine(x1, starty , startx , y1);
				g.drawLine(startx , y2, x1, starty );
				g.drawLine(startx,y1, startx, y2);
				g.drawLine(startx,starty, x2, starty);

			} 

		}

	}

	/**
	 * @return diamondX - 'X' position of the left side of the diamond
	 */
	public int getDiamondX() {
		return diamondX;
	}

	/**
	 * @param diamondX - 'X' position of the left side of the diamond to be set
	 */
	public void setDiamondX(int diamondX) {
		this.diamondX = diamondX;
	}

	/**
	 * @return diamondY - 'Y' position of the top side of the diamond
	 */
	public int getDiamondY() {
		return diamondY;
	}

	/**
	 * @param diamondY - 'Y' position of the top side of the diamond to be set
	 */
	public void setDiamondY(int diamondY) {
		this.diamondY = diamondY;
	}

	/**
	 * @return arrowDir - The direction that the arrow (for Inheritance) is facing
	 */
	public String getArrowDir() {
		return arrowDir;
	}

	/**
	 * @param arrowDir - The direction that the arrow (for Inheritance) is facing to be set
	 */
	public void setArrowDir(String arrowDir) {
		this.arrowDir = arrowDir;
	}

    /**
     * @return the reld
     */
    public RelationshipDiagData getReld() {
	return reld;
    }

    /**
     * @param reld the reld to set
     */
    public void setReld(RelationshipDiagData reld) {
	this.reld = reld;
    }

}
