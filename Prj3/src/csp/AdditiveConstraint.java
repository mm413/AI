package csp;

import java.util.ArrayList;
import java.util.List;

public class AdditiveConstraint implements Constraint {
	
	private List<Variable> leftSideVars = new ArrayList<Variable>();
	private List<Integer> leftSideCoefficients = new ArrayList<Integer>();
	private List<Variable> rightSideVars = new ArrayList<Variable>();
	private List<Integer> rightSideCoefficients = new ArrayList<Integer>();
	private List<Variable> scope = new ArrayList<Variable>();
	

	/**
	 * @param leftSideVars
	 * @param leftSideCoefficients
	 * @param rightSideVars
	 * @param rightSideCoefficients
	 * Additive Constraint is the constructor. It takes in lists of variables and coefs for both sides of an assignment, and creates the constraint object.
	 */
	public AdditiveConstraint(List<Variable> leftSideVars, List<Integer> leftSideCoefficients, List<Variable> rightSideVars, List<Integer> rightSideCoefficients) {
		this.leftSideVars = leftSideVars;
		this.leftSideCoefficients = leftSideCoefficients;
		this.rightSideVars = rightSideVars;
		this.rightSideCoefficients = rightSideCoefficients;
		for (Variable v: leftSideVars) {
			this.scope.add(v);
		}
		for (Variable v: rightSideVars) {
			this.scope.add(v);
		}
	}	

	/**
	 *getScope gives back the variables that this constraint touches
	 *@returns scope - a list of all variables touched by this constraint
	 */
	@Override
	public List<Variable> getScope() {
		return scope;
	}

	/**
	 *isSatisfiedWith tests to see if a constraint is satisfied in the current environment.
	 *@returns boolean - true if the assignment doesn't violate this constraint, false otherwise.
	 */
	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		int lhs = 0;
		for (Integer num: this.leftSideCoefficients) {
			lhs = lhs + num;
		}
		
		int rhs = rightSideCoefficients.get(0) + (rightSideCoefficients.get(1) * 10 );
		if (lhs == rhs){
			return true;
		}
		return false;
	}

}
