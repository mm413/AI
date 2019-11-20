package csp;

import java.io.*;
import java.util.*;

public class MapColoringCSP extends CSP {
	
	private boolean australianFile = false;
	private List<List<Constraint>> allConstraints;
	private int colors;
	
	/**
	 * @param allConstraints
	 * @param colors
	 * @throws FileNotFoundException
	 * Map coloring CSP is the constructor. assigns values, and called super's constructor. (CSP)
	 */
	public MapColoringCSP (List<List<Constraint>> allConstraints, int colors) throws FileNotFoundException {
		super();
		this.allConstraints = allConstraints;
		this.colors = colors;
		
	}
	/**
	 * @throws FileNotFoundException
	 * builds the CSP, meaning it fills the constraint, variable, and domain lists from a file.
	 */
	public void build() throws FileNotFoundException {
		//getting the colors in a list (4,3,2,1) for domain
		ArrayList<Integer> colorList = new ArrayList<Integer>();
		while (colors > 0) {
			colorList.add(colors);
			colors--;
		}
		Domain domain = new Domain(colorList);

		//for adding the variables in, and setting their domain
		File adjList = new File("usa-adj-list.txt");
		if (this.australianFile) {
			adjList = new File("australia-adj-list.txt");
		}
		Scanner scanner = new Scanner(adjList);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] states = line.split(",");
			boolean first = true;
			for (String state: states) {
				if (first) { //if it's the first state in a line
					first = false;
					Variable var = new Variable(state); //make it a variable
					super.addVariable(var); //add it to variable list
					super.setDomain(var, domain); //set its domain (colors)
				}
			}
		}
		//Lastly, adding the constraints in:
		for (List<Constraint> loc : allConstraints) {
			for (Constraint c : loc) {
				super.addConstraint(c);
			}
		}
	}
	
	/**
	 * change file switches from usa to aus.
	 * 
	 */
	public void changeFile() {
		if (this.australianFile == true) {
			this.australianFile = false;
		}else {
		this.australianFile = true;
		}
	}
}
