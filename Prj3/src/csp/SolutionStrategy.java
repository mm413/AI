package csp;

/**
 * Base class for CSP solver implementations. Solving a CSP means finding an
 * assignment, which is consistent and complete with respect to a CSP. This
 * abstract class provides the central interface method.
 * 
 * @author Ruediger Lunde
 * @author Mike Stampone
 */
public abstract class SolutionStrategy {

	/**
	 * Returns a solution to the specified CSP, which specifies values for all
	 * the variables such that the constraints are satisfied.
	 * 
	 * @param csp
	 *            a CSP to solve
	 * 
	 * @return a solution to the specified CSP, which specifies values for all
	 *         the variables such that the constraints are satisfied.
	 */
	public abstract Assignment solve(CSP csp);
}
