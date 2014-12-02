package uk.ac.aber.dcs.cs124.clg11.data;

import uk.ac.aber.dcs.cs124.clg11.diag.ClassDiag;

/**
 * Storage class used to contain information about a particular
 * relationship created between two classes, created by a user. 
 * 
 * Stored of type 'ObjectData' in VectorOfDrawables
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */

public class RelationshipDiagData implements ObjectData {
	private ClassDiag a, b;
	private String assocVar;
	private String classAMulti;
	private String classBMulti;
	private String relType;
	

	/**
	 *
	 * @return relType Type of relationship 
	 */
	public String getRelType() {
		return relType;
	}

	/**
	 * @return a - Object of Class A
	 */
	public ClassDiag getClassA() {
		return a;
	}

	
	/**
	 * @param a - 'ClassDiag' object to set as Class A
	 */
	public void setClassA(ClassDiag a) {
		this.a = a;
	}

	/**
	 * @return b - Object of Class B
	 */
	public ClassDiag getClassB() {
		return b;
	}

	/**
	 * @param b - 'ClassDiag' object to set as Class B
	 */
	public void setClassB(ClassDiag b) {
		this.b = b;
	}

	/**
	 * @return assocVar - The associated variable name
	 */
	public String getAssocVar() {
		return assocVar;
	}

	/**
	 * @param assocVar - Variable to be set as associated variable for relationship
	 */
	public void setAssocVar(String assocVar) {
		this.assocVar = assocVar;
	}

	
	/**
	 * @return classAMulti - The multiplicity value for Class A
	 */
	public String getClassAMulti() {
		return classAMulti;
	}

	
	/**
	 * @param classAMulti - The multiplicity value set for Class A
	 */
	public void setClassAMulti(String classAMulti) {
		this.classAMulti = classAMulti;
	}

	/**
	 * @return classBMulti - The multiplicity value for Class B
	 */
	public String getClassBMulti() {
		return classBMulti;
	}

	/**
	 * @param classBMulti - The multiplicity value set for Class B
	 */
	public void setClassBMulti(String classBMulti) {
		this.classBMulti = classBMulti;
	}

	/**
	 * @param relType - Type of relationship 
	 */
	public void setRelType(String relType) {
		this.relType = relType;
	}
	
}
