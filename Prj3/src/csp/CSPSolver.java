package csp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSPSolver {


	/**
	 * @param args
	 * @throws FileNotFoundException
	 * main function. Acts as the driver/ input line interpreter.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File adjList = new File("usa-adj-list.txt");
		
		if (args[1].equals("usa")) {
			adjList = new File("usa-adj-list.txt");
			
		}
		
		if (args[1].equals("crypt")) {
			CryptarithmeticCSP csp = new CryptarithmeticCSP(args[2], args[3], args[4]);
		}
		
		if (args[1].equals("aus")) {
			adjList = new File("australia-adj-list.txt");
		}
		
		//All of this below is creating an adjacency list for the Map Coloring constructor
		List<List<Constraint>> allConstraints = new LinkedList<List<Constraint>>();
		//reading in lines from file
		Scanner scanner = new Scanner(adjList);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] states = line.split(",");
			boolean first = true;
			List<Constraint> constraintsForOneVar = new ArrayList<Constraint>();
			Variable CurrentVar = null;
			for (String state: states) {
				if (first) {
					first = false;
					CurrentVar = new Variable(state);
				} else {
					
					NotEqualConstraint constraint = new NotEqualConstraint(CurrentVar, new Variable(state));
					constraintsForOneVar.add(constraint);
				}
			}
			allConstraints.add(constraintsForOneVar);
		}
		
		int colors = 0;
		if (args[2].length() <=2) {
			colors = Integer.parseInt(args[2]);
		}
		
		
		if (args[0].equals("backtrack") && args[1].equals("crypt") == false) {
			MapColoringCSP csp = new MapColoringCSP(allConstraints, colors);
			if (args[1].equals("aus")) {
				csp.changeFile();
			}
			csp.build();
			BacktrackingSearch bs = new BacktrackingSearch();
			if (args.length == 4){
				if (args[3].equals("mrv")) {
					bs.makeMRV();
				}
			}
			System.out.println(bs.solve(csp));
		}
		
		
		if (args[0].equals("minconflicts") && args[1].equals("crypt") == false) {
			MapColoringCSP csp2 = new MapColoringCSP(allConstraints, colors);
			if (args[1].equals("aus")) {
				csp2.changeFile();
			}
			csp2.build();
			MinConflictsSearch mcs = new MinConflictsSearch(csp2, 100000);
		}

	}

}
