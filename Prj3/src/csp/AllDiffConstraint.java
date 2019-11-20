package csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllDiffConstraint implements Constraint {
	
	private List<Variable> scope = new ArrayList<Variable>();
	
	/**
	 * @param variables - a list type of all current variables
	 * AllDiffConstraint is the constructor for this constraint
	 * Adds all variables given to it's scope.
	 */
	public AllDiffConstraint(List<Variable> variables) {
		for (Variable v: variables) {
			this.scope.add(v);
		}
	}

	/**
	 *getScope returns all variables this constraint monitors
	 *@returns - scope - list object of variables
	 */
	@Override
	public List<Variable> getScope() {
		return scope;
	}

	/**
	 *@param assignmet - a current assignment of variables
	 *isSatisfiedWith checks to see if this assignment is valid for this constraint
	 *@returns boolean - true if the assignment is valid, false otherwise.
	 */
	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		Set<Object> set = new HashSet<Object>();
		for (Variable v: scope) {
			set.add(assignment.getAssignment(v));
		}
		if(set.size() == scope.size()){
			return true;
		}
		return false;
	}

}
