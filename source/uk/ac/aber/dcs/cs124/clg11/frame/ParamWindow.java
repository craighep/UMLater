
package uk.ac.aber.dcs.cs124.clg11.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 * Dialog to add parameters to a selected method being created for a new class.
 * Called by {@link uk.ac.aber.dcs.cs124.clg11.frame.AddClassDialog}
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */
public class ParamWindow extends JFrame implements ActionListener, ItemListener {
    private AddClassDialog parent;
    
    private JPanel listPanel = new JPanel();
    private JPanel btnPanel = new JPanel();
    private JPanel inputPanel= new JPanel();
    private JPanel radPanel = new JPanel();
    
    private JButton submit, cancel, add, remove;
    private JRadioButton radInt, radDouble, radFloat, radBool, radString, radOther;
    private JTextField name, other;
    private ButtonGroup types = new ButtonGroup();
    private JScrollPane scrollingList;
    private DefaultListModel listModel;
    private JList params;
    private int parentIndex = 0;
    
    /**
     * Initialises and displays dialog frame and panel and adds required components to panel.
     * @param parent - Link back to {@link uk.ac.aber.dcs.cs124.clg11.frame.AddClassDialog} to allow new parameters to be added to existing methods created
     * there
     * @param pI - Index of method in {@link uk.ac.aber.dcs.cs124.clg11.frame.AddClassDialog} that is currently being edited.
     */
    public ParamWindow(AddClassDialog parent, int pI) {
    	
        //Intialise dialog frame
        setTitle("UMLator | Parameter Editor");
        setLocation(200,200);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        //Set links back to AddClassDialog
        this.parent = parent;
        this.parentIndex = pI;
        this.setResizable(false);
        
        //Create JList and ListModel
        listModel = new DefaultListModel();
        params = new JList(listModel);
        params.setFixedCellWidth(400);
        scrollingList = new JScrollPane(params);
        
        //Create dialog panel
        listPanel.setSize(500, 200);
        listPanel.add(scrollingList);
       
        //Add panel to dialog frame
        add(listPanel, BorderLayout.NORTH);
        
        
        //Create required components and add to dialog panels
        name = new JTextField(10);
        other = new JTextField(10);
        other.setEnabled(false);
        
        radInt = new JRadioButton("Int");
        radDouble = new JRadioButton("Double");
        radBool = new JRadioButton("Bool");
        radString = new JRadioButton("String");
        radFloat = new JRadioButton("Float");
        radOther = new JRadioButton("Other:");
        
        radOther.addItemListener(this);
        radInt.setSelected(true);

        types.add(radInt);
        types.add(radDouble);
        types.add(radFloat);
        types.add(radBool);
        types.add(radString);
        types.add(radOther);
        
        radPanel.add(radInt);
        radPanel.add(radDouble);
        radPanel.add(radFloat);
        radPanel.add(radBool);
        radPanel.add(radString);
        radPanel.add(radOther);
        
        inputPanel.setLayout(new BorderLayout());
        
        inputPanel.add(name, BorderLayout.NORTH);
        inputPanel.add(radPanel, BorderLayout.CENTER);
        inputPanel.add(other, BorderLayout.SOUTH);
        
        add(inputPanel, BorderLayout.CENTER);
        
        //Create the button panel
        add = new JButton("Add");
        remove = new JButton("Remove");
        cancel = new JButton("Cancel");
        submit = new JButton("Submit");
        
        add.addActionListener(this);
        remove.addActionListener(this);
        cancel.addActionListener(this);
        submit.addActionListener(this);
        
        btnPanel.add(add);
        btnPanel.add(remove);
        btnPanel.add(cancel);
        btnPanel.add(submit);
        
        add(btnPanel, BorderLayout.SOUTH);
        
        //Fit frame to ensure all panels/components are visible
        pack();
    }
   
	/**
	 * Clears all input fields to allow new data to be inputted.
	 */
	public void clearFields() {
		
		//Clear all types
        types.clearSelection();
        
        //Reset to default radio button
        radInt.setSelected(true);
        
        //Disable 'Other' radio button
        other.setEnabled(false);
        
        //Clear input textboxes
        name.setText("");
        other.setText("");
        
    }
    
    /** Listens for user input to allow interactive components to be used.
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        if (e.getActionCommand().equals("Add")) {
        	
        	//Retrieve name of new parameter
            String n = name.getText();
            
            //Create and initialise new 'type' string
            String type = "";
            
            //Check selected parameter type and set 'type' string as that type
            if(radInt.isSelected()) { type = "int"; }
            else if (radDouble.isSelected()) { type = "double"; }
            else if (radBool.isSelected()) { type = "bool"; }
            else if (radFloat.isSelected()) { type = "float"; }
            else if (radString.isSelected()) { type = "String"; }
            else if (radOther.isSelected()) { type = other.getText(); }
            
            //Add the parameter name & type to the listbox in special format
            listModel.add(listModel.getSize(), n + " : "+ type);
            
            //Clear all input fields
            clearFields();
            
        } else if (e.getActionCommand().equals("Submit")) {
        	
        	//Create and initialise new 'parameter' string
            String parameters = "";
            
            //Loop through all parameter is listbox
            for (Object v : listModel.toArray()) {
            	
            	//Add each listbox entry to 'parameters' string with a "," separating each one
                parameters += v + ",";
            }
            
            //Remove the final "," from parameters string
            parameters = parameters.substring(0, parameters.length()-1);
          
            //Link back to AddClassDialog parent with 'parameters' string containing new parameters
            parent.addParams(parameters, parentIndex);
            
            //Clear all input fields
            clearFields();
            
            //Close the dialog
            dispose();
            
        } else if (e.getActionCommand().equals("Cancel")) {
        	
        	//Close the dialog
            dispose();
            
        } else if (e.getActionCommand().equals("Remove")) {
        	
        	//Remove selected parameter in listbox
            listModel.remove(params.getSelectedIndex());
            
        }
    }
    
    /** Checks if the 'Other' radio button has been selected, and displays the 'Other' input fields accordingly
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
    	
    	//If the 'Other' radio button has been selected
        if(e.getItem().equals(radOther)) {
        	
        	//Enable to 'Other' input text box
            other.setEnabled(!other.isEnabled());
        }
    }
}
