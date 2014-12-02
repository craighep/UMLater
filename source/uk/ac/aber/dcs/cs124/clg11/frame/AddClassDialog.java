package uk.ac.aber.dcs.cs124.clg11.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData;
import uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag;
import uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel;

/**
 * Dialog to add a new class to the project that allows variables, methods and method parameters to be created.
 * Called by {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class AddClassDialog extends JFrame implements ActionListener, ItemListener{

	private JPanel dialogPanel = new JPanel();
	private JTextField methodText, objectText, className, objectOther, methodOther;
	private DefaultListModel listObject, listMethod;
	private JList obList, metList;
	private CanvasPanel canvasPanel;
	private int x, y;
	private JLabel newClass, newVar, newMet, obAcTitle, obTypeTitle, obOthTitle, metAcTitle, metTypeTitle, metOthTitle ;
	private JScrollPane scrollOb, scrollMeth;
	private JButton OK, CANCEL, addObject, removeObject, addMethod, removeMethod, addParam;
	private String[] accessModArr = { "Public", "Private", "Protected"};
	private String[] varType = { "String", "Int", "Bool", "Double", "Other" };
	private String[] methType = { "Void", "Int", "String", "Bool", "Double", "Other" };
	private JComboBox obModCombo, obTypeCombo, methModCombo, methTypeCombo;
	private SpringLayout layout;
	private JSeparator titleSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator objSep = new JSeparator(JSeparator.HORIZONTAL);
	private JSeparator metSep = new JSeparator(JSeparator.HORIZONTAL);

	/**
	 * Initialises/displays dialog and sets up variables used to set new class diagram position
	 * 
	 * @param cp - {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel} object to link back to CanvasPanel
	 * to allow the new class diagram to be drawn.
	 * @param x - X coordinate of clicked mouse position (X coordinate for new class diagram)
	 * @param y - Y coordinate of clicked mouse position (Y coordinate for new class diagram)
	 */
	public AddClassDialog(CanvasPanel cp, int x, int y) {

		//Set new layout for dialog panel
		layout = new SpringLayout();
		canvasPanel = cp;
		
		//Set panel size and layout manager
		dialogPanel.setLayout(layout);
		dialogPanel.setPreferredSize(new Dimension(320,680));
		this.x = x;
		this.y = y;

		this.setTitle("UMLator | Create New Class");
		this.setResizable(false);
		this.add(dialogPanel);
		
		//Determine centre of user's screen and position dialog
		Toolkit k=Toolkit.getDefaultToolkit();
		Dimension d=k.getScreenSize();
		this.setLocation(d.width/2-this.getWidth()/2,d.height/2-this.getHeight()/2 - 300);

		//Initialise & add components to dialogPanel
		initComponents();
		
		//Set the layout of the components on dialogPanel
		setLayout();

		//Set the frame size to fit all components in
		pack();
		
		//Display the dialog
		this.setVisible(true);

	}

	/**
	 * Initialise and add all required components to the Dialog Panel. 
	 * Add action listener to components that require interactivity (e.g. Buttons)
	 */
	public void initComponents() {

		newClass = new JLabel("Class Name:");
		className = new JTextField(10);

		listObject = new DefaultListModel();
		listMethod = new DefaultListModel();

		obList = new JList(listObject);
		metList = new JList(listMethod);

		scrollOb = new JScrollPane(obList);
		scrollMeth = new JScrollPane(metList);

		objectText = new JTextField(15);
		methodText = new JTextField(15);
		objectOther = new JTextField(15);
		objectOther.setEnabled(false);
		methodOther = new JTextField(15);
		methodOther.setEnabled(false);
		
		obOthTitle = new JLabel("Other:");
		metOthTitle = new JLabel("Other:");
		newVar = new JLabel("Variable Name:");
		newMet = new JLabel("Method Name:");
		obAcTitle = new JLabel("Access:");
		obTypeTitle = new JLabel("Type:");
		metAcTitle = new JLabel("Access:");
		metTypeTitle = new JLabel("Type:");

		addParam = new JButton("Add Parameter");
		addParam.addActionListener(this);
		OK = new JButton("OK");
		OK.addActionListener(this);
		CANCEL = new JButton("CANCEL");
		CANCEL.addActionListener(this);
		addObject = new JButton("Add Object");
		addObject.addActionListener(this);
		removeObject = new JButton("Remove Object");
		removeObject.addActionListener(this);
		addMethod = new JButton("Add Method");
		addMethod.addActionListener(this);
		removeMethod = new JButton("Remove Method");
		removeMethod.addActionListener(this);

		obModCombo = new JComboBox(accessModArr);
		obTypeCombo = new JComboBox(varType);
		obTypeCombo.addItemListener(this);
		methModCombo = new JComboBox(accessModArr);
		methTypeCombo = new JComboBox(methType);
		methTypeCombo.addItemListener(this);
		
		dialogPanel.add(newClass);
		dialogPanel.add(className);
		dialogPanel.add(scrollOb);
		dialogPanel.add(scrollMeth);
		dialogPanel.add(objectText);
		dialogPanel.add(methodText);
		dialogPanel.add(newVar);
		dialogPanel.add(newMet);
		dialogPanel.add(addParam);
		dialogPanel.add(OK);
		dialogPanel.add(CANCEL);
		dialogPanel.add(addObject);
		dialogPanel.add(removeObject);
		dialogPanel.add(addMethod);
		dialogPanel.add(removeMethod);
		dialogPanel.add(obModCombo);
		dialogPanel.add(obTypeCombo);
		dialogPanel.add(methModCombo);
		dialogPanel.add(methTypeCombo);
		dialogPanel.add(titleSep);
		dialogPanel.add(objSep);
		dialogPanel.add(metSep);
		dialogPanel.add(obAcTitle);
		dialogPanel.add(obTypeTitle);
		dialogPanel.add(metAcTitle);
		dialogPanel.add(metTypeTitle);
		dialogPanel.add(obOthTitle);
		dialogPanel.add(metOthTitle);
		dialogPanel.add(objectOther);
		dialogPanel.add(methodOther);

	}

	/**
	 * Sets up the 'SpringLayout' layout manager to organise all components on the Dialog Panel
	 * 
	 */
	public void setLayout() {

		//Set NORTH edge of 'newClass' label, 10 pixels down from NORTH edge of panel 
		layout.putConstraint(SpringLayout.NORTH,newClass,10,SpringLayout.NORTH, this); 
		
		//Set WEST edge of 'newClass' label, 60 pixels right from WEST edge of panel 
		layout.putConstraint(SpringLayout.WEST,newClass,60,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,className,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,className,5,SpringLayout.EAST, newClass);

		layout.putConstraint(SpringLayout.NORTH,titleSep,10,SpringLayout.SOUTH, className);
		layout.putConstraint(SpringLayout.WEST,titleSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, titleSep,-25,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,newVar,10,SpringLayout.SOUTH, titleSep);
		layout.putConstraint(SpringLayout.WEST,newVar,28,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,objectText,10,SpringLayout.SOUTH, titleSep);
		layout.putConstraint(SpringLayout.WEST,objectText,5,SpringLayout.EAST, newVar);

		layout.putConstraint(SpringLayout.NORTH,obAcTitle,13,SpringLayout.SOUTH, objectText);
		layout.putConstraint(SpringLayout.WEST,obAcTitle,31,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,obModCombo,10,SpringLayout.SOUTH, objectText);
		layout.putConstraint(SpringLayout.WEST,obModCombo,5,SpringLayout.EAST, obAcTitle);

		layout.putConstraint(SpringLayout.NORTH,obTypeTitle,13,SpringLayout.SOUTH, objectText);
		layout.putConstraint(SpringLayout.WEST,obTypeTitle,15,SpringLayout.EAST, obModCombo);

		layout.putConstraint(SpringLayout.NORTH,obTypeCombo,10,SpringLayout.SOUTH, objectText);
		layout.putConstraint(SpringLayout.WEST,obTypeCombo,5,SpringLayout.EAST, obTypeTitle);
		
		layout.putConstraint(SpringLayout.NORTH,obOthTitle,15,SpringLayout.SOUTH, obTypeTitle);
		layout.putConstraint(SpringLayout.WEST,obOthTitle,55,SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH,objectOther,15,SpringLayout.SOUTH, obTypeTitle);
		layout.putConstraint(SpringLayout.WEST,objectOther,5,SpringLayout.EAST, obOthTitle);

		layout.putConstraint(SpringLayout.NORTH,scrollOb,10,SpringLayout.SOUTH, obOthTitle);
		layout.putConstraint(SpringLayout.WEST,scrollOb,30,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,addObject,10,SpringLayout.SOUTH, scrollOb);
		layout.putConstraint(SpringLayout.WEST,addObject,50,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,removeObject,10,SpringLayout.SOUTH, scrollOb);
		layout.putConstraint(SpringLayout.WEST,removeObject,5,SpringLayout.EAST, addObject);

		layout.putConstraint(SpringLayout.NORTH,objSep,10,SpringLayout.SOUTH, removeObject);
		layout.putConstraint(SpringLayout.WEST,objSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, objSep,-25,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,newMet,10,SpringLayout.SOUTH, objSep);
		layout.putConstraint(SpringLayout.WEST,newMet,28,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,methodText,10,SpringLayout.SOUTH, objSep);
		layout.putConstraint(SpringLayout.WEST,methodText,5,SpringLayout.EAST, newMet);

		layout.putConstraint(SpringLayout.NORTH,metAcTitle,13,SpringLayout.SOUTH, methodText);
		layout.putConstraint(SpringLayout.WEST,metAcTitle,28,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,methModCombo,10,SpringLayout.SOUTH, methodText);
		layout.putConstraint(SpringLayout.WEST,methModCombo,5,SpringLayout.EAST, metAcTitle);

		layout.putConstraint(SpringLayout.NORTH,metTypeTitle,13,SpringLayout.SOUTH, methodText);
		layout.putConstraint(SpringLayout.WEST,metTypeTitle,15,SpringLayout.EAST, methModCombo);

		layout.putConstraint(SpringLayout.NORTH,methTypeCombo,10,SpringLayout.SOUTH, methodText);
		layout.putConstraint(SpringLayout.WEST,methTypeCombo,5,SpringLayout.EAST, metTypeTitle);
		
		layout.putConstraint(SpringLayout.NORTH,metOthTitle,15,SpringLayout.SOUTH, metTypeTitle);
		layout.putConstraint(SpringLayout.WEST,metOthTitle,55,SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH,methodOther,15,SpringLayout.SOUTH, metTypeTitle);
		layout.putConstraint(SpringLayout.WEST,methodOther,5,SpringLayout.EAST, metOthTitle);

		layout.putConstraint(SpringLayout.NORTH,scrollMeth,10,SpringLayout.SOUTH, metOthTitle);
		layout.putConstraint(SpringLayout.WEST,scrollMeth,30,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,addMethod,10,SpringLayout.SOUTH, scrollMeth);
		layout.putConstraint(SpringLayout.WEST,addMethod,45,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,removeMethod,10,SpringLayout.SOUTH, scrollMeth);
		layout.putConstraint(SpringLayout.WEST,removeMethod,5,SpringLayout.EAST, addMethod);

		layout.putConstraint(SpringLayout.NORTH,addParam,10,SpringLayout.SOUTH, removeMethod);
		layout.putConstraint(SpringLayout.WEST,addParam,50,SpringLayout.WEST, addMethod);

		layout.putConstraint(SpringLayout.NORTH,metSep,10,SpringLayout.SOUTH, addParam);
		layout.putConstraint(SpringLayout.WEST,metSep,5,SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, metSep,-25,SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH,CANCEL,10,SpringLayout.SOUTH, metSep);
		layout.putConstraint(SpringLayout.WEST,CANCEL,85,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,OK,10,SpringLayout.SOUTH, metSep);
		layout.putConstraint(SpringLayout.WEST,OK,5,SpringLayout.EAST, CANCEL);
	}


	/** Listener for actions on dialog to allow interactive elements (such as Buttons) to be used on Button Panel
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from components on Button Panel that require an action to be performed
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		String actionCommand = evt.getActionCommand();

		if (actionCommand.equals("Add Object")) {
			
			String sign = null;
			String type = null;
			
			//Retrieve variable access modifer & type from combo boxes 
			String result = (String) obModCombo.getSelectedItem();
			String result1 = (String) obTypeCombo.getSelectedItem();

			//Check if an object name has been entered
			if (objectText.getText().isEmpty() == true) {
				
				//If it has not, display an error message box asking for a name to be entered
				JOptionPane.showMessageDialog(null, "Please enter a variable name","UMLator | Error", JOptionPane.ERROR_MESSAGE);
				
			} else { //Otherwise if a name has been entered
				
				//Check selected access modifier selected and convert to symbol equivalent
			
				if ("Public".equals(result)) {
					sign = "+";
				}
				if ("Private".equals(result)) {
					sign = "-";
				}
				if ("Protected".equals(result)) {
					sign = "#";
				}
				if ("No Modifier".equals(result)) {
					sign = " ";
				}
				
				//Check selected type and convert to UML type
				
				if ("String".equals(result1)) {
					type = ": String";
				}
				if ("Int".equals(result1)) {
					type = ": int";
				}
				if ("Bool".equals(result1)) {
					type = ": Bool";
				}
				if ("Double".equals(result1)) {
					type = ": double";
				}
				if ("Other".equals(result1)) {
					type = ": " + this.objectOther.getText();
				}

				//Add new variable to listObject listbox in set format
				listObject.add(0, sign + " " + objectText.getText() + " " + type);
				
				clearInput();
			}
		} else if (actionCommand.equals("Add Method")) {

			String sign = null;
			String type = null;
			String result = (String) methModCombo.getSelectedItem();
			String result1 = (String) methTypeCombo.getSelectedItem();

			if (methodText.getText().isEmpty() == true) {
				JOptionPane.showMessageDialog(null, "Please enter a method name","UMLator | Error", JOptionPane.ERROR_MESSAGE);	
			} else {
				if ("Public".equals(result)) {
					sign = "+";
				}
				if ("Private".equals(result)) {
					sign = "-";
				}
				if ("Protected".equals(result)) {
					sign = "#";
				}
				if ("No Modifier".equals(result)) {
					sign = " ";
				}
				if ("String".equals(result1)) {
					type = ": String";
				}
				if ("Int".equals(result1)) {
					type = ": int";
				}
				if ("Bool".equals(result1)) {
					type = ": Bool";
				}
				if ("Double".equals(result1)) {
					type = ": double";
				}
				if ("Void".equals(result1)) {
					type = ": void";
				}
				if ("Other".equals(result1)) {
					type = ": " + this.methodOther.getText();
				}

				listMethod.add(0, sign + " " + methodText.getText() + "()" + " " + type);
				clearInput();
				
			}

		} else if (actionCommand.equals("Remove Method")) {

			//Check for no methods
			if (listMethod.size() == 0) {
				
				//If no methods have been created, show error message box
				JOptionPane.showMessageDialog(null, "No methods available to remove","UMLator | Error", JOptionPane.ERROR_MESSAGE);

			} else { //Otherwise if methods have been created
				
				//Remove the selected method from the listbox
				listMethod.remove(metList.getSelectedIndex());
			}

		} else if (actionCommand.equals("Remove Object")) {

			if (listObject.size() == 0) {

				JOptionPane.showMessageDialog(null, "No variables available to remove", "UMLator | Error", JOptionPane.ERROR_MESSAGE);

			} else {

				listObject.remove(obList.getSelectedIndex());

			}

		} else if (actionCommand.equals("Add Parameter")) {

			//Check if any methods have been created
			if (!(listMethod.size() == 0)) {

				//If they have, display the parameter editor dialog with the selected method
				ParamWindow paramWindow = new ParamWindow(this,metList.getSelectedIndex());

			} else {
				
				//Otherwise show error message box
				JOptionPane.showMessageDialog(null, "Please select a method to edit", "UMLator | Error", JOptionPane.WARNING_MESSAGE);
			}

		} else if (actionCommand.equals("OK")) {

			//Add the completed new class to the Data Model and GUI classes
			this.addClass();
			
			//Close the dialog
			this.dispose();

		} else if (actionCommand.equals("CANCEL")) {

			this.dispose();

		}
	}

	/**
	 * Adds a newly created class to the Data Model and GUI layer classes to allow class data to be saved and drawn on 
	 * {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public void addClass() {

		//Retrieve number of variables/methods to be created
		int varNo = listObject.getSize();
		int opNo = listMethod.getSize();

		//Create new data model
		ClassDiagData cDD = new ClassDiagData();

		//Set the class name in the data model
		cDD.setClassName(className.getText());

		//Add all created variables to ArrayList in data model
		for (int i = 0; i < varNo; i++) {

			cDD.addInstanceVar(listObject.get(i).toString()); 
		}

		//Add all created variables to ArrayList in data model
		for (int i = 0; i < opNo; i++) {
			cDD.addMethod(listMethod.get(i).toString()); 
		}

		//Add class access modifier to data model
		cDD.setAccessModifier("+");

		//Create a new GUI layer of the new class
		ClassDiag e = new ClassDiag(x, y, varNo, opNo);

		//Load the new class data from the data model into the GUI layer to draw on screen
		e.setData(cDD); // Set the associated data with from the cDD.

		//Save the new GUI and data model layers to the VectorofDrawables
		canvasPanel.addDrawable(e);
		
		//Refresh the CanvasPanel to display the new class on screen
		canvasPanel.repaint();

	}

	/**
	 * Adds the parameter(s) created in the Parameter Editor dialog to the 
	 * selected method 
	 * @param params - String of all parameters created in the Parameter Editor ({@link uk.ac.aber.dcs.cs124.clg11.frame.ParamWindow})
	 * @param index - Position of selected method in method listbox
	 */
	public void addParams(String params, int index) {
		try {
			//Retrieve selected method
			String s = (String) listMethod.get(index);
			
			//Add parameters inside '()' in selected method
			StringTokenizer token = new StringTokenizer(s, ")");
			String output = token.nextToken() + params + ")" + token.nextElement();
			
			//Add this to the listMethod listbox
			listMethod.set(index, output);	
			
		} catch (ArrayIndexOutOfBoundsException e) {
		
			JOptionPane.showMessageDialog(new JFrame(), "No methods available - Parameter cannot be created", "UMLator | Error", JOptionPane.ERROR_MESSAGE);
			
		}

	}
	
	public void itemStateChanged(ItemEvent evt) {
      
        Object item = evt.getItem();

        if (item.equals("Other") && evt.getSource().equals(this.obTypeCombo)) {

            this.objectOther.setEnabled(true);
 
        } else if (item.equals("Other")  && evt.getSource().equals(this.methTypeCombo)) {
        	
            this.methodOther.setEnabled(true);
            
        }
    }
	
	public void clearInput() {
		
		//Clear variable name textbox
		this.objectText.setText("");
		
		//Clear method name textbox
		this.methodText.setText("");
		
		if (objectOther.isEnabled() == true) {
			objectOther.setText("");
			objectOther.setEnabled(false);
		}
		
		if (methodOther.isEnabled() == true) {
			methodOther.setText("");
			methodOther.setEnabled(false);
		}
		
	}
}