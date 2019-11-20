package csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class BacktrackingSearch extends SolutionStrategy {
	int searchType;

	/**
	 * @param csp - a csp that backtracking search is going to try to solve
	 *solve is the base part of backing search. it calls the recursive function
	 */
	@Override
	public Assignment solve(CSP csp) {
		this.searchType = 0;
		Assignment solution = backtrack(new Assignment(), csp);
		return solution;
	}
	
	private Assignment backtrack(Assignment ass, CSP csp) {
		if (ass.isComplete(csp.getVariables())){
			System.out.println("assignment is complete - map is 100% filled backtracking search style");
			return ass;
		}
		Variable var = null;
		//arbitrarily selecting next variable:
		for (Variable v: csp.getVariables()) {
			if (ass.hasAssignmentFor(v) == false){
				var = v;
				break;
			}
		}
		//MRV heuristic
		if (searchType == 1) { 	
			ArrayList<Variable> unassignedVariables = new ArrayList<Variable>();
			for (Variable v: csp.getVariables()) {
				if (ass.hasAssignmentFor(v) == false) {
					unassignedVariables.add(v);
				}
			}
			for (Variable v: unassignedVariables) {
				if (csp.getDomain(v).size() <= csp.getDomain(var).size()) {
					var = v;
				}
			}
		}
		System.out.println("TTrying to find assignment for:" + var); // printing all the variables for readability before entering the loop
		for (Object value: csp.getDomain(var)) {
			ass.setAssignment(var, value);
			if ((ass.isConsistent(csp.getConstraints(var))) == false && value != null) {  
				ass.removeAssignment(var); 
				continue;
			}
			ArrayList<Variable> neighbors = new ArrayList<Variable>();
			for (Constraint c: csp.getConstraints(var)) {
				Variable neighbor = csp.getNeighbor(var, c);
				neighbors.add(neighbor);
			}
			Hashtable<Variable, Domain> neighborsVariablesAndDomains = new Hashtable<Variable, Domain>();
			for (Variable n: neighbors) {
				neighborsVariablesAndDomains.put(n,csp.getDomain(n));
			}
			//-----------------start of inference ------------------------
			for (Variable n: neighbors) {
				if (csp.getDomain(n).size() == 0 && ass.hasAssignmentFor(n) == false) { // if the neighbor isn't colored, and it's domain is empty
					for (Variable j: neighbors) {
						csp.setDomain(j, neighborsVariablesAndDomains.get(j));
					}
					ass.removeAssignment(var);
					csp.removeValueFromDomain(var, value);
					break;
				}
				else {
					csp.removeValueFromDomain(n, value);
				}
			}
			//--------------end of inference --------------------
			Assignment result = backtrack(ass, csp);
			
			if (result != null){
				return result;
			}else {
			//failure - remove assignment
				ass.removeAssignment(var);
				for (Variable j: neighbors) {
					csp.setDomain(j, neighborsVariablesAndDomains.get(j));
				}
			}
		}
		System.out.println("backtracking....");
		return null;
	}
	
	/**
	 * makeMRV enables the mrv part of the backtrack by flipping a 0 to a 1.
	 */
	public void makeMRV() {
		this.searchType = 1;
	}

}
