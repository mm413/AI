package javaFiles;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/**
	Class to represent a single Perceptron in the network
*/

public class Perceptron{
	public int inSize; // Number of perceptrons feeding into this one
	public double[] weights;
	
	/**
	Constructor. Weights will be assigned randomly.
	@param inSize Number of perceptrons feeding into this one
	*/
	public Perceptron(int inSize){
		this.inSize = inSize+1; // Add one for bias
		this.weights = new double[this.inSize];
		this.setRandomWeights();
	}
	
	/**
	Constructor
	@param inSize Number of perceptrons feeding into this one
	@param weights The weights of previous layers into this one
	*/
	public Perceptron(int inSize, double[] weights){
		this.inSize = inSize+1;
		this.weights = weights;
	}
	
	/**
	Returns the sum of the input weighted by the weights
	@param inActs Input values, same length as inSize
	@return The weighted sum
	*/
	public double getWeightedSum(double[] inActs){
		double total = 0;
		if(inActs.length != inSize)
			throw new IllegalArgumentException("Array of inputs is of incorrect size");
		for(int i=0; i<inSize; i++){
			total += inActs[i]*weights[i];
		}
		return total;
	}
	
	/**
	Returns the value of a sigmoid function
	@param value The value to get sigmoid for
	@return The output of the sigmoid function for the parameter
	*/
	//I found the one liner for this equation in this blog post: 
	//http://chronicles.blog.ryanrampersad.com/2009/02/sigmoid-function-in-java/
	public double sigmoid(double value){
		return (1/(1 + Math.pow(Math.E,(-1*value)))); 
	}
	
	
	
	/**
	Returns the activation value of this perceptron with the given input.
	
	Same as g(z) in book. Remember to add 1 to the start of inActs for the bias input.
	
	@param inActs Input values, not including bias
	@return The value of the sigmoid of the weighted input
	*/
	public double sigmoidActivation(double[] inActs){
		ArrayList<Double> inacts = NeuralNetUtil.toWrapperList(inActs);
		inacts.add(0, (double) 1);
		double[] newInActs = NeuralNetUtil.toPrimitiveArray(inacts);
		double weightedSum = getWeightedSum(newInActs);
		return sigmoid(weightedSum);
	}
	
	
	
	/**
	Returns the value of the derivative of a sigmoid function.
	@param value The value to get sigmoid for
	@return The output of the derivative of a sigmoid function for the given value.
	*/
	public double sigmoidDeriv(double value){
		return 0; /*REPLACE WITH YOUR CODE */
	}
	
	/**
	Returns the derivative of the activation of this Perceptron with the given input.
	
	Same as g'(z) in book (note that this is not rounded). Remember to add 1 to the start of inActs for the bias input.
	
	@param inActs Input values, not including bias
	@return The derivative of the sigmoid of the weighted input
	*/
	public double sigmoidActivationDeriv(double[] inActs){
		return 0; /*REPLACE WITH YOUR CODE */
	}
	
	/**
	Updates the weights for this perceptron given the input delta. Remember to add 1 to the start of inActs for the bias input.
	@param inActs Input values, not including bias
	@param alpha The learning rate
	@param delta If this is an output, then g'(z)*error. If this is a hidden unit, the the as defined g'(z)*sum over weight*delta for the next layer
	@return The total modification of all the weights (sum of each abs(modification))
	*/
	public double updateWeights(double[] inActs, double alpha, double delta){
		double totalModification = 0;
		/* INSERT YOUR CODE HERE */
		return totalModification;
	}
	
	// Generates random input weights between -1 and 1
	private void setRandomWeights(){
		Random r = new Random();
		for(int i=0; i<this.inSize; i++){
			double d = r.nextDouble();
			int sign = r.nextInt(2); //Produces either 0 or 1
			d = (sign==1)? d : -d;
			this.weights[i] = d;
		}
	}
	
	public String toString(){
		String outStr = "";
		outStr += "Perceptron with "+this.inSize+" inputs\n";
		outStr += "Node input weights "+Arrays.toString(this.weights)+"\n";
		return outStr;
	}
}