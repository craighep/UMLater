package uk.ac.aber.dcs.cs124.clg11.panel;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import uk.ac.aber.dcs.cs124.clg11.listener.*;

/**
 * Contained within {@link uk.ac.aber.dcs.cs124.clg11.frame.UMLFrame}.
 * Used to contain all system option and drawing tool buttons to allow user to interact with the program.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class ButtonPanel extends JPanel {

	private JLabel titleText = new JLabel("--System Menu--");
	private JLabel drawText = new JLabel("--Drawing Tools--");
	private JButton addButton, delButton, relButton, resetButton, exportButton, saveButton, loadButton, exitButton, undoButton;
	private JSeparator titleSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator menuSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator drawSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator topSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator bottomSep = new JSeparator(JSeparator.HORIZONTAL);
	
	public Boolean addVar = false; 
	public Boolean addRel = false;
	public Boolean delItem = false;
	private String directEnd;

	/**
	 * Initialises and displays the panel and all required components.
	 * @param cP - Link to {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public ButtonPanel(CanvasPanel cP) {

		//Initialises action listener for button components on panel
		ButtonListener buttonList = new ButtonListener(this, cP);
		
		//Set new layout for  panel
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		//Add visual components to panel
		this.add(titleText);
		this.add(drawText);
		this.add(titleSep);
		this.add(menuSep);
		this.add(drawSep);
		this.add(topSep);
		this.add(bottomSep);
	
	
		JLabel picLabel = new JLabel(new ImageIcon(getClass().getResource("/logo.png")));
		this.add(picLabel);

		
		//Set EAST border of panel 
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));

		
		//Initialise and add interactive components (buttons) to panel
		
		//Create new button
		addButton = new JButton("New Class");
		
		//Add button to panel
		this.add(addButton);
		
		//Set action listener to button to allow interaction
		addButton.addActionListener(buttonList);

		delButton = new JButton("Remove Class");
		this.add(delButton);
		delButton.addActionListener(buttonList);

		relButton = new JButton("Add Relationship");
		this.add(relButton);
		relButton.addActionListener(buttonList);

		resetButton = new JButton("Reset Canvas");
		this.add(resetButton);
		resetButton.addActionListener(buttonList);

		exportButton = new JButton("Export Java");
		this.add(exportButton);
		exportButton.addActionListener(buttonList);

		saveButton = new JButton("Save Project");
		this.add(saveButton);
		saveButton.addActionListener(buttonList);

		loadButton = new JButton("Load Project");
		this.add(loadButton);
		loadButton.addActionListener(buttonList);

		exitButton = new JButton("Exit Program");
		this.add(exitButton);
		exitButton.addActionListener(buttonList);

		undoButton = new JButton("Undo");
		this.add(undoButton);
		undoButton.addActionListener(buttonList);
		
		//Set up the 'SpringLayout' layout manager to organise all components on the Dialog Panel
		
		layout.putConstraint(SpringLayout.NORTH,topSep,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,topSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, topSep,-5,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,titleText,10,SpringLayout.NORTH, topSep);
		layout.putConstraint(SpringLayout.WEST,titleText,30,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, titleText,-30,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,titleSep,30,SpringLayout.NORTH, titleText);
		layout.putConstraint(SpringLayout.WEST,titleSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, titleSep,-5,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,exportButton,15,SpringLayout.NORTH, titleSep);
		layout.putConstraint(SpringLayout.WEST, exportButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, exportButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,saveButton,40,SpringLayout.NORTH, exportButton);
		layout.putConstraint(SpringLayout.WEST, saveButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, saveButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,loadButton,40,SpringLayout.NORTH, saveButton);
		layout.putConstraint(SpringLayout.WEST,loadButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, loadButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,exitButton,40,SpringLayout.NORTH, loadButton);
		layout.putConstraint(SpringLayout.WEST, exitButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, exitButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,menuSep,40,SpringLayout.NORTH, exitButton);
		layout.putConstraint(SpringLayout.WEST,menuSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, menuSep,-5,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,drawText,15,SpringLayout.NORTH, menuSep);
		layout.putConstraint(SpringLayout.WEST,drawText,25,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, drawText,-10,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,drawSep,30,SpringLayout.NORTH, drawText);
		layout.putConstraint(SpringLayout.WEST,drawSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, drawSep,-5,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,addButton,15,SpringLayout.NORTH, drawSep);
		layout.putConstraint(SpringLayout.WEST, addButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, addButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,relButton,40,SpringLayout.NORTH, addButton);
		layout.putConstraint(SpringLayout.WEST, relButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, relButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,delButton,40,SpringLayout.NORTH, relButton);
		layout.putConstraint(SpringLayout.WEST, delButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, delButton,-12,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,resetButton,40,SpringLayout.NORTH, delButton);
		layout.putConstraint(SpringLayout.WEST, resetButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, resetButton,-12,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,undoButton,40,SpringLayout.NORTH, resetButton);
		layout.putConstraint(SpringLayout.WEST, undoButton,12,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, undoButton,-12,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,bottomSep,40,SpringLayout.NORTH, undoButton);
		layout.putConstraint(SpringLayout.WEST,bottomSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, bottomSep,-5,SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH,picLabel,40,SpringLayout.SOUTH, bottomSep);
		layout.putConstraint(SpringLayout.WEST,picLabel,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, picLabel,-5,SpringLayout.EAST, this);
	}
}
