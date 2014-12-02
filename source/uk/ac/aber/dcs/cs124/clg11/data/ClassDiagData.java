package uk.ac.aber.dcs.cs124.clg11.data;

import java.util.ArrayList;

/**
 * Data Model layer used to contain information about a particular
 * class created by a user. 
 * 
 * Stored of type 'ObjectData' @see uk.ac.aber.dcs.cs124.clg11.data.ObjectData
 * in VectorOfDrawables
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Heptinstall (crh13)
 *
 */

public class ClassDiagData implements ObjectData {
	
	private String className;
	private boolean isAbstract;
	private String classAccessModifer;
	private ArrayList<String> instanceVariables = new ArrayList<String>();
	private ArrayList<String> associatedInstanceVariables = new ArrayList<String>();
	private ArrayList<String> methods = new ArrayList<String>();
	private String parentName;
	
	/**
	 *
	 * @return parentName - Name of parent class 
	 *
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 *
	 * @param parentName Name of parent class to be set
	 *
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	/**
	 *
	 * @param var Class variable being added to ArrayList of variables
	 *
	 */
	public void addInstanceVar(String var) {
		getInstanceVariables().add(var);
	}
	
	/**
	 *
	 * @param var Relationship variable being added to ArrayList of associated variables
	 *
	 */
	public void addAssocInstanceVar(String var) {
		getAssociatedInstanceVariables().add(var);
	}

	/**
	 *
	 * @param meth Class method being added to ArrayList of methods
	 *
	 */
	public void addMethod(String meth) {
		getMethods().add(meth);
	}

	/**
	 *
	 * @return instanceVariables - ArrayList of class variables
	 *
	 */
	public ArrayList<String> getInstanceVariables() {
		return instanceVariables;
	}

	/**
	 *
	 * @return getAssociatedInstanceVariables - ArrayList of associated variables
	 *
	 */
	public ArrayList<String> getAssocInstanceVariables() {
		return getAssociatedInstanceVariables();
	}

	/**
	 *
	 * @return methods - ArrayList of class methods
	 *
	 */
	public ArrayList<String> getMethods () {
		return methods;
	}

	/**
	 *
	 * @return className - The name of the class
	 *
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 *
	 * @param className - Name of the class to be set
	 *
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 *
	 * @return isIsAbstract - If class is abstract or not
	 *
	 */
	public boolean isAbstract() {
		return isIsAbstract();
	}
	
	/**
	 *
	 * @param isAbstract - Sets if class is abstract or not
	 *
	 */
	public void setAbstract(boolean isAbstract) {
		this.setIsAbstract(isAbstract);
	}
	
	/**
	 *
	 * @return getClassAccessModifier - The access modifier for the class
	 *
	 */
	public String getAccessModifier() {
		return getClassAccessModifer();
	}
	
	/**
	 *
	 * @param accessModifier - The access modifier for the class
	 *
	 */
	public void setAccessModifier(String accessModifier) {
		this.setClassAccessModifer(accessModifier);
	}

	/**
	 * @return the isAbstract
	 */
	 public boolean isIsAbstract() {
		return isAbstract;
	}

	/**
	 * @param isAbstract - the isAbstract to set
	 */
	 public void setIsAbstract(boolean isAbstract) {
		 this.isAbstract = isAbstract;
	 }

	 /**
	  * @return the classAccessModifer
	  */
	 public String getClassAccessModifer() {
		 return classAccessModifer;
	 }

	 /**
	  * @param classAccessModifer - the classAccessModifer to set
	  */
	 public void setClassAccessModifer(String classAccessModifer) {
		 this.classAccessModifer = classAccessModifer;
	 }

	 /**
	  * @param instanceVariables - the instanceVariables to set
	  */
	 public void setInstanceVariables(ArrayList<String> instanceVariables) {
		 this.instanceVariables = instanceVariables;
	 }

	 /**
	  * @return the associatedInstanceVariables
	  */
	 public ArrayList<String> getAssociatedInstanceVariables() {
		 return associatedInstanceVariables;
	 }

	 /**
	  * @param associatedInstanceVariables - the associatedInstanceVariables to set
	  */
	 public void setAssociatedInstanceVariables(ArrayList<String> associatedInstanceVariables) {
		 this.associatedInstanceVariables = associatedInstanceVariables;
	 }

	 /**
	  * @param methods - the methods to set
	  */
	 public void setMethods(ArrayList<String> methods) {
		 this.methods = methods;
	 }

}
