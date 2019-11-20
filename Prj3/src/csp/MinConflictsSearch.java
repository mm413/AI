package csp;

import java.util.ArrayList;
import java.util.Random;

public class MinConflictsSearch extends SolutionStrategy {
	int maxSteps;
	
	public MinConflictsSearch(CSP csp, int maxSteps) {
		this.maxSteps = maxSteps;
		solve(csp);
	}

	@Override
	public Assignment solve(CSP csp) {
		Random r = new Random();
		Assignment current = new Assignment();
		//assigning random value to each variable in domain below:
		for (Variable var: csp.getVariables()) {
			current.setAssignment(var, r.nextInt((csp.getDomain(var).size() - 1) +1) + 1);
		}
		for (int i = 0; i < maxSteps; i++) {
			if (current.isSolution(csp)){
				System.out.println("assignment is complete - map is 100% filled min conflicts style");
				return current;
			}
			Object val = null;
			//right now, need to make var a random variable from csp.getVariables
			int max = csp.getVariables().size() - 1;
			int randomNum = r.nextInt((max - 0) +1)+0;
			//System.out.println("State Index = " + randomNum);
			Variable var =  csp.getVariables().get((randomNum));
			//System.out.println(var);
			for(Constraint c: csp.getConstraints(var)) {
				if (c.isSatisfiedWith(current) == false) { //if this constraint is conflicted
					val = bestColorChoice(var, csp, current);
					current.setAssignment(var, val);
					break;
				}
			}
		}
		System.out.println("unable to make all assignments");
		return null;
	}

	private Object bestColorChoice(Variable var, CSP csp, Assignment current) {
		int leastConflicts = Integer.MAX_VALUE;
		Object currentObject = csp.getDomain(var).get(0);
		for (Object domainVal: csp.getDomain(var)) { //loop through all colors:
			//System.out.println(domainVal);
			//set as first color:
			current.setAssignment(var, domainVal);
			//get all conflicts associated with that color for val:
			int currentConflicts = 0;
			for (Constraint c: csp.getConstraints(var)) {
				if (c.isSatisfiedWith(current) == false){ //there's a conflict with this constraint
					currentConflicts++;
				}
			}
			if (currentConflicts <= leastConflicts) {
				leastConflicts = currentConflicts;
				currentObject = domainVal;
			}
		}
		return currentObject;
	}

}
