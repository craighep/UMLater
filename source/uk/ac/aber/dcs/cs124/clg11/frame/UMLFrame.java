package uk.ac.aber.dcs.cs124.clg11.frame;

import java.awt.BorderLayout;

/**
 * Main program frame that contains the main panels used to draw, and display the UML class diagram.
 * 
 * Includes menu bar, drawable panel {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}, and control panel {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel}.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (???), Craig Hepinsthall (???)
 *
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import uk.ac.aber.dcs.cs124.clg11.listener.*;
import uk.ac.aber.dcs.cs124.clg11.panel.*;

/**
 * Main JFrame for displaying program GUI. Contains {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel},
 * {@link uk.ac.aber.dcs.cs124.clg11.panel.ButtonPanel}, and {@link uk.ac.aber.dcs.cs124.clg11.panel.StatusPanel}.
 * 
 * Also creates and initialises menu bar and action listeners for all panels.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class UMLFrame extends JFrame{
	
	private ButtonPanel btPanel;
	private StatusPanel stPanel;
	private CanvasPanel cvPanel;
	private MouseInterListener mIL;
	private MousePositionListener mPL;
	private MenuListener mL;
	private String directEnd;
	
	private JMenuBar mb = new JMenuBar();
	
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu helpMenu = new JMenu("Help");
	
	private JMenuItem newClass = new JMenuItem("New Class");
	private JMenuItem newRel = new JMenuItem("New Relationship");
	private JMenuItem resetCanvas = new JMenuItem("Clear Canvas");
	private JMenuItem saveProj = new JMenuItem("Save Project");
	private JMenuItem loadProj = new JMenuItem("Load Project");
	private JMenuItem exportCode = new JMenuItem("Export Java Code");
	private JMenuItem exitProg = new JMenuItem("Exit");
	
	private JMenuItem backCol = new JMenuItem("Choose Background Colour");
	private JMenuItem classCol = new JMenuItem("Choose Class Colour");
	private JMenuItem classTxtCol = new JMenuItem("Choose Class Text Colour");
	private JMenuItem undoAction = new JMenuItem("Undo");
	
	private JMenuItem about = new JMenuItem("About");
	
	
	/**
	 * Initialises and displays main program frame, and all panels that are required so that program is usable
	 */
	public UMLFrame() {

		 //Locate and set frame icon
		this.setIconImage((new ImageIcon(getClass().getResource("/logo.png"))).getImage());

		
		//Initialise and set up frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("UMLator - CS124 Project | By Sam Jackson, Craig Heptinstall & Connor Goddard");
		
		//Set frame menu bar
		this.setJMenuBar(mb);
	
		//Create new StatusPanel
		stPanel = new StatusPanel(cvPanel);
		stPanel.setPreferredSize(new Dimension(this.getWidth(),25));
		
		//Create new CanvasPanel
		cvPanel = new CanvasPanel(stPanel);
		
		//Create new ButtonPanel
		btPanel = new ButtonPanel(cvPanel);
		btPanel.setPreferredSize(new Dimension(155,this.getHeight()));
		
		//Set panel positions and add to frame
		add(btPanel,BorderLayout.WEST); 
		add(cvPanel,BorderLayout.CENTER);
		add(stPanel,BorderLayout.SOUTH);
		
		
		//Fit frame to ensure all panels/components are visible
		pack();
		
		//Create interaction listeners and add to relevant panels
		mIL = new MouseInterListener(cvPanel, btPanel);
		cvPanel.addMouseListener(mIL);
		mPL = new MousePositionListener(cvPanel, btPanel);
		cvPanel.addMouseMotionListener(mPL);
		mL = new MenuListener(cvPanel, btPanel);
		
		//Create and initialise keystroke shortcut commands for menu bar
		KeyStroke keyNewClass = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyNewRel = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyLoadProj = KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keySaveProj = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyExport = KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyQuit = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		KeyStroke keyUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		
		//Add menus, and menu items to menu bar
		mb.add(fileMenu);
		fileMenu.add(newClass);
		
		//Add action listener to menu item to allow interactivity
		newClass.addActionListener(mL);
		
		//Add relevant keyboard shortcut accelerator to menu item
		newClass.setAccelerator(keyNewClass);
		
		fileMenu.add(newRel);
		newRel.addActionListener(mL);
		newRel.setAccelerator(keyNewRel);
		fileMenu.add(loadProj);
		loadProj.addActionListener(mL);
		loadProj.setAccelerator(keyLoadProj);
		fileMenu.add(saveProj);
		saveProj.addActionListener(mL);
		saveProj.setAccelerator(keySaveProj);
		fileMenu.add(resetCanvas);
		resetCanvas.addActionListener(mL);
		fileMenu.add(exportCode);
		exportCode.addActionListener(mL);
		exportCode.setAccelerator(keyExport);
		fileMenu.add(exitProg);
		exitProg.addActionListener(mL);
		exitProg.setAccelerator(keyQuit);
		
		mb.add(editMenu);
		editMenu.add(backCol);
		backCol.addActionListener(mL);
		editMenu.add(classCol);
		classCol.addActionListener(mL);
		editMenu.add(classTxtCol);
		classTxtCol.addActionListener(mL);
		editMenu.add(undoAction);
		undoAction.addActionListener(mL);
		undoAction.setAccelerator(keyUndo);
		
		mb.add(helpMenu);
		helpMenu.add(about);
		about.addActionListener(mL);
		
		//Determine centre of user's screen and position frame
		Toolkit k=Toolkit.getDefaultToolkit();
		Dimension d=k.getScreenSize();
		this.setLocation(d.width/2-this.getWidth()/2,d.height/2-this.getHeight()/2);
		
	}

	/**
	 * Display frame on screen with all relevant panels & components loaded
	 */
	public void showIt() {
		//Display frame on screen
		this.setVisible(true);
	}


}
