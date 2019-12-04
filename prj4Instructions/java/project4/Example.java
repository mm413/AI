package project4;
import java.util.ArrayList;

/**
A class to represent a single example that will be used for training or testing a neural net.
*/

public class Example{
	public ArrayList<Double> inputs;	// A vector of inputs
	public ArrayList<Double> outputs;	// The corresponding vector of outputs
	
	/**
	Constructor
	@param in Input Vector
	@param out Output Vector
	*/
	public Example(double[] in, double[] out){
		inputs = new ArrayList<Double>(in.length);
		outputs = new ArrayList<Double>(out.length);
		for(double i:in)
			inputs.add(i);
		for(double o:out)
			outputs.add(o);
	}
	
	/**
	Returns the input vector
	@return Input vector
	*/
	public ArrayList<Double> getInputs(){
		return inputs;
	}
	
	/**
	Returns the output vector
	@return Output vector
	*/
	public ArrayList<Double> getOutputs(){
		return outputs;
	}
	
	/**
	Returns the size of the input vector
	@return Size of input vector
	*/
	public int getInputSize(){
		return inputs.size();
	}
	
	/**
	Returns the size of the output vector
	@return Size of the output vector
	*/
	public int getOutputSize(){
		return outputs.size();
	}
}