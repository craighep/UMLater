package uk.ac.aber.dcs.cs124.clg11.fileIO;

import java.io.*;
import java.util.*;

/**
 * Exports Java code to file using data retrieved from all {@link uk.ac.aber.dcs.cs124.clg11.data.ClassDiagData} & 
 * {@link uk.ac.aber.dcs.cs124.clg11.data.RelationshipDiagData} objects stored in 
 * {@link uk.ac.aber.dcs.cs124.clg11.vector.VectorOfDrawables}.
 * 
 * @author Connor Goddard (clg11), Sam Jackson (slj11), Craig Heptinstall (crh13)
 *
 */

public class CodeExport {

	private String constructVar;
	private StringTokenizer token;
	private String output, access, rest, constName, constType, directEnd;
	private String[] paramArray;
	private int count;

	public CodeExport() {

	}

	/**
	 * Obtains selected directory path for file export before retrieving all class and relationship data and converting into Java code
	 * using string manipulation and concatination.
	 * 
	 * Java code is then saved as '.java' files in user specified directory.
	 *  
	 * @param filePath - File path for .java files selected by user using dialog
	 * @param varNo - Number of variables in classes to convert/output as Java code
	 * @param opNo - Number of methods in classes to convert/output as Java code
	 * @param asocNo - Number of associated variables used for relationships to convert/output as Java code
	 * @param className - The name of each class to be exported (Used in .java' filename)
	 * @param varArray - ArrayList of all variables in each class to be exported
	 * @param opArray - ArrayList of all methods in each class to be exported
	 * @param asocArray - ArrayList of all associated variables in each class to be exported
	 * @throws Exception - IO/FileNotFound exceptions that are thrown and dealt with by {@link uk.ac.aber.dcs.cs124.clg11.panel.CanvasPanel}
	 */
	public void printOut(String filePath, int varNo, int opNo, int asocNo, String className, ArrayList<String> varArray, ArrayList<String> opArray, ArrayList<String> asocArray) throws Exception{

		//Determine OS so that correct directory symbol can be used
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.indexOf("win") >= 0) {
			directEnd = "\\";
		} else {
			directEnd = "/";
		}
		
		//Create and initialise new PrintWriter with selected filepath
		PrintWriter out = new PrintWriter(new FileWriter(filePath + directEnd + className + ".java"));
		
		//Write out class Java code with current class name
		out.write("public class " + className + " {\r\n");
		out.write("\r\n"); //Perform carriage return

		//Loop through all variables in class
		for (int i = 0; i < varArray.size(); i++) {
			String s = varArray.get(i);
			String access = s.substring(0,1); //Retrieve access modifier from string
			String rest = s.substring(2);
			String output = access + " : " + rest; //Place a " : " between access modifier and rest of string for manipulation

			StringTokenizer token = new StringTokenizer(output, " : "); //Split string at all " : "
			String amod = token.nextToken(); //Set access modifier at first split section
			String name = token.nextToken(); //Set variable name at second split section
			String type = token.nextToken(); //Set variable type at final split section

			//Convert boolean type if required
			if (type.equals("Bool") || type.equals("bool")) {
				type = "boolean";
			}

			//Convert access modifier
			
			
			if (amod.equals("+")) {
				
				access = "public";
				
			} else if (amod.equals("-")) {
				
				access = "private";
			
			} else if (amod.equals("#")) {
				
				access = "protected";
				
			} else if (amod.equals(" ")) {
				
				access = " ";
				
			}
			
			//Write out variable in Java code format
			out.write("	 " + access + " " + type + " " + name + ";\r\n");
			out.write("\r\n");

		}

		//Write out all associated variables in Java code format
		for (int i = 0; i < asocArray.size(); i++) {
			out.write("	" + asocArray.get(i) + ";\r\n");
			out.write("\r\n");	
		}

		//Loop through all methods in class
		for (int i = 0; i < opArray.size(); i++) {

			String s = opArray.get(i);

			//Retrieve all text within '()' in string
			constructVar = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));

			//If there is any text within '()'
			if (constructVar.length() > 0) {

				//Check if there is more than one parameter
				if (constructVar.indexOf(",") > 0) {

					count = 0;
					
					//Split parameters at ","
					token = new StringTokenizer(constructVar, ",");

					//Add these to array
					paramArray = new String[token.countTokens()];

					while (token.hasMoreTokens() == true) {
						paramArray[count] = token.nextToken();
						count++;
					}

				} else { //Otherwise if there is only one parameter

					//Split the parameter into its name and type
					token = new StringTokenizer(constructVar, " : ");
					constName = token.nextToken();
					constType = token.nextToken();
				}

				//Remove the now extracted parameters and brackets from the original string
				String remove = s.substring(s.indexOf("("), s.lastIndexOf(")") + 1);

				String tmpString = s.replace(remove,""); 

				access = tmpString.substring(0,1);
				rest = tmpString.substring(2);
				output = access + " : " + rest;

			} else { //Otherwise if NO parameters at all exist

				//Continue as normal
				access = s.substring(0,1);
				rest = s.substring(2);
				output = access + " : " + rest;

			}

			//Same as variable
			token = new StringTokenizer(output, " : ");
			String amod = token.nextToken();
			String name = token.nextToken();
			String type = token.nextToken();


			if (type.equals("Bool") || type.equals("bool")) {
				type = "boolean";
			}

			
			if (amod.equals("+")) {
				
				access = "public";
				
			} else if (amod.equals("-")) {
				
				access = "private";
			
			} else if (amod.equals("#")) {
				
				access = "protected";
				
			} else if (amod.equals(" ")) {
				
				access = " ";
				
			}

			if (constructVar.length() > 0) {

				if (constructVar.indexOf(",") > 0) {

					//Add a '(' bracket
					String compParam = "(";

					//Loop through all parameters extracted
					for (int p = 0; p < paramArray.length; p++) {

						//Split using same process as variables/methods
						token = new StringTokenizer(paramArray[p], " : ");
						String paramName = token.nextToken();
						String paramType = token.nextToken();

						if (paramType.equals("Bool") || paramType.equals("bool")) {
							paramType = "boolean";
						}

						//Add all parameters to one string (compParam)
						compParam = compParam + paramType + " " + paramName + ", ";
					}

					//Remove the ", " from the final parameter in string
					compParam = compParam.substring(0, compParam.length()-2);

					//Write as to file as Java code
					out.write("\r\n");
					out.write("	" + access + " " + type + " " + name + " " + compParam + ")" + " {\r\n");
					out.write("\r\n");
					out.write("	}\r\n");

				} else { //Otherwise if there is only one parameter to export

					if (name.equals("main()")) { //Check if the method is a main method

						//Format in a special way
						name = name.replace("()","");

						out.write("\r\n");
						out.write("	public static void " + name + "(String[] args) {\r\n");
						out.write("\r\n");
						out.write("	}\r\n");

					} else {
						//Write out method with the one parameter
						out.write("\r\n");
						out.write("	" + access + " " + type + " " + name + " (" + constType + " " + constName + ")" + " {\r\n");
						out.write("\r\n");
						out.write("	}\r\n");
					}
				}

			} else { //Otherwise if there are no parameters

				if (name.equals("main()")) {

					name = name.replace("()","");

					out.write("\r\n");
					out.write("	public static void " + name + "(String[] args) {\r\n");
					out.write("\r\n");
					out.write("	}\r\n");

				} else {
					
					//Write methods with no parameters
					out.write("\r\n");
					out.write("	" + access + " " + type + " " + name + " {\r\n");
					out.write("\r\n");
					out.write("	}\r\n");
				}

			}
		}

		//Finish off closing class in Java code
		out.write("}");
		
		//Close PrintWriter
		out.close();
	}
}


