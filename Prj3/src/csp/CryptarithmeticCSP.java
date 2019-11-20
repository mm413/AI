package csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CryptarithmeticCSP extends CSP {

	public CryptarithmeticCSP(String first, String second, String third) {
		super();
		//adding in variables and domains below
		ArrayList<Integer> domainList= new ArrayList<Integer>();
		for (int i = 0; i <=9; i++) {
			domainList.add(i);
		}
		Domain domain = new Domain(domainList);
		Set<Character> variableSet = new HashSet<Character>();
		for (char c: first.toCharArray()) {
			variableSet.add(c);
		}
		for (char c: second.toCharArray()) {
			variableSet.add(c);
		}
		for (char c: third.toCharArray()) {
			variableSet.add(c);
		}
		for (char c: variableSet) {
			Variable var = new Variable(Character.toString(c));
			super.addVariable(var);
			super.setDomain(var, domain);
		}
		//setting the all diff constraint 
		super.addConstraint(new AllDiffConstraint(super.getVariables()));
		//adding in carry's
		for (int i = 1; i<= first.length(); i++) {
			String name = ("C" + ((int)Math.pow(10, i)));
			Variable var = new Variable(name);
			super.addVariable(var);
			super.setDomain(var, domain);
		}
		//setting up the additive constraints:
		for (int i = 0; i < first.length(); i++) {
			ArrayList<Variable> lsv = new ArrayList<Variable>();
			for (Variable v: super.getVariables()){
				if (v.toString().equals(Character.toString(first.charAt(i)))) {
					lsv.add(v);
				}
				if (v.toString().equals(Character.toString(second.charAt(i)))) {
					lsv.add(v);
				}
			}
			
			ArrayList<Integer> lsc = new ArrayList<Integer>();
			ArrayList<Variable> rsv = new ArrayList<Variable>();
			ArrayList<Integer> rsc = new ArrayList<Integer>();
			AdditiveConstraint ac = new AdditiveConstraint(lsv, lsc, rsv, rsc);
		}
		System.out.println("Variables = " + super.getVariables().toString());
		System.out.println("I can add the domains and the variables, but can't figure out how to add additiveConstraints");
		System.out.println("Sorry ONeill, I'm taking the 3 point hit on this last one.");
		
		
		
	}
}
