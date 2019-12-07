package javaFiles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

/**
	Class to hold the net of perceptrons and implement functions for it
*/

public class NeuralNet{
	
	public int[] layerSize;	// Size of each layer, from input to output
	public ArrayList<Perceptron> outputLayer; // Output Layer
	public int numHiddenLayers; // Number of hidden layers
	public ArrayList<ArrayList<Perceptron>> hiddenLayers; // List of Hidden layers
	public int numLayers; // Number of layers
	public ArrayList<ArrayList<Perceptron>> layers; // List of all layers
	
	private static double lastWeightMod; // Special variable to track what the value of weightMod was when training last ended
	
	// Static variables for use with backPropLearning()
	public static final int AVERAGE_ERROR = 0;
	public static final int AVERAGE_WEIGHT_CHANGE = 1;
	
	/**
	Constructor
	@param layerSize An array representing the number of units in each layer, from input to output
	*/
	public NeuralNet(int[] layerSize){
		this.layerSize = layerSize;
		this.outputLayer = new ArrayList<Perceptron>();
		this.numHiddenLayers = layerSize.length - 2;
		this.hiddenLayers = new ArrayList<ArrayList<Perceptron>>();
		this.numLayers = this.numHiddenLayers+1;
		this.layers = new ArrayList<ArrayList<Perceptron>>();
		
		//Build hidden layers
		for(int h=0; h<numHiddenLayers; h++){
			hiddenLayers.add(h, new ArrayList<Perceptron>());
			for(int p=0; p < layerSize[h+1]; p++){
				Perceptron percep = new Perceptron(layerSize[h]);
				hiddenLayers.get(h).add(percep);
			}
		}
		
		//Build output layer
		for(int i=0; i<layerSize[layerSize.length-1]; i++){
			Perceptron percep = new Perceptron(layerSize[layerSize.length-2]);
			outputLayer.add(percep);
		}
		
		//Build layers list that holds all layers in order. Use this structure to implement back propagation.
		for(ArrayList<Perceptron> hl : hiddenLayers){
			layers.add(hl);
		}
		layers.add(outputLayer);
	}
	
	public String toString(){
		String outStr = "\n";
		for(int hiddenIndex=0; hiddenIndex < numHiddenLayers; hiddenIndex++){
			outStr += "\nHidden Layer #"+hiddenIndex;
			for(int index=0; index < hiddenLayers.get(hiddenIndex).size(); index++){
				outStr += "Percep #"+index+": "+hiddenLayers.get(hiddenIndex).get(index).toString();
			}
			outStr += "\n";
		}
		for(int i=0; i<outputLayer.size(); i++){
			outStr += "Output Percep #"+i+":"+outputLayer.get(i).toString();
		}
		return outStr;
	}
	
	/**
	Propogate input vector forward to calculate outputs
	@param inActs The input to the NN (an example)
	@return A list of lists. The first list is the input list, and the others are lists of the output values of all perceptrons in each layer.
	*/
	public ArrayList<ArrayList<Double>> feedForward(ArrayList<Double> inActs){
		ArrayList<ArrayList<Double>> listOfLists = new ArrayList<ArrayList<Double>>();
		listOfLists.add(inActs);
		for (ArrayList<Perceptron> layer : layers) {
			int previousLayer = 0;
			ArrayList<Double> layersOutputVals = new ArrayList<Double>();
			for (Perceptron percep : layer) {
				layersOutputVals.add(percep.sigmoidActivation(NeuralNetUtil.toPrimitiveArray(listOfLists.get(previousLayer))));
			}
			previousLayer++;
			System.out.println(layersOutputVals);
			listOfLists.add(layersOutputVals);
		}
		return listOfLists; 
	}
	
	/**
	Run a single iteration of backward propagation learning algorithm. See the text for pseudocode.
	@param examples A set of Examples to be used for learning
	@param alpha The learning rate to train with
	@return Two double values, the average error and average weight change, to be used as stopping conditions.
	
	averageError is the summed (error)^2/2 of all examples, divided by numExamples*numOutputs
	averageWeightChange is the summed absolute weight change of all perceptrons divided by the sum of their input sizes (the average weight change for a single perceptron).
	
	Use this class's static variables to access the error and weight change from the array returned by this method.
	*/
	public double[] backPropLearning(HashSet<Example> examples, double alpha){
		double averageError = 0;
		double averageWeightChange = 0;
		int numWeights = 0;
		
		for(Example e: examples){
			// Keep track of deltas to use in weight change
			ArrayList<ArrayList<Double>> deltas = new ArrayList<ArrayList<Double>>();
			
			// Neural net output list
			ArrayList<ArrayList<Double>> allLayerOutput = this.feedForward(e.inputs); //TODO - Replace null with neural net list computation 
			ArrayList<Double> lastLayerOutput = allLayerOutput.get(allLayerOutput.size()-1);
			
			// Iterate through all output layer neurons
			ArrayList<Double> outDelta = new ArrayList<Double>();
			for(int outputNum=0; outputNum < e.getOutputSize(); outputNum++){
				double gPrime = outputLayer.get(outputNum).sigmoidActivationDeriv(NeuralNetUtil.toPrimitiveArray(allLayerOutput.get(allLayerOutput.size()-2))); // TODO -- Replace null with correct argument
				double error = e.outputs.get(outputNum) - lastLayerOutput.get(outputNum); // TODO -- Replace 0 with correct value differece between outputted value and expected output value
				double delta = gPrime * error; // TODO -- Replace 0 with correct value
				averageError += error*error/2;
				outDelta.add(new Double(delta));
			}
			deltas.add(outDelta);
			
			// Backpropagate through all hidden layers, calculating and storing the deltas for each perceptron layer
			for(int layerNum=numHiddenLayers-1; layerNum >=0; layerNum--){
				ArrayList<Perceptron> layer = layers.get(layerNum);
				ArrayList<Perceptron> nextLayer = layers.get(layerNum+1);
				ArrayList<Double> hiddenDelta = new ArrayList<Double>();
				//Iterate through all neurons in this layer
				for(int neuronNum=0; neuronNum < layer.size(); neuronNum++){
					double gPrime = layer.get(neuronNum).sigmoidActivationDeriv(NeuralNetUtil.toPrimitiveArray(allLayerOutput.get(layerNum -1))); // TODO -- Replace null with correct argument
					double delta = gPrime*layer.get(neuronNum).getWeightedSum(NeuralNetUtil.toPrimitiveArray(outDelta)) ; // TODO -- Replace 0 with correct value delta
					hiddenDelta.add(new Double(delta));
				}
				deltas.add(0, hiddenDelta);
			}
			//Get output of all layers
			
			//Having aggregated all deltas, update the weights of the hidden and output layers accordingly
			for(int numLayer = 0; numLayer < numLayers; numLayer++){
				ArrayList<Perceptron> layer = layers.get(numLayer);
				for(int numNeuron = 0; numNeuron < layer.size(); numNeuron++){
					double weightMod = layer.get(numNeuron).updateWeights(NeuralNetUtil.toPrimitiveArray(allLayerOutput.get(numLayer)), alpha, deltas.get(numLayer).get(numNeuron)); // TODO -- Replace null and 0s with correct arguments
					averageWeightChange += weightMod;
					numWeights += layer.get(numNeuron).inSize;
				}
			}
		}
		
		averageError /= (examples.size() * outputLayer.size());
		averageWeightChange /= numWeights;
		
		double[] returnval = {averageError, averageWeightChange};
		return returnval;
	}
	
	/**
	Train a neural net for the given input, with default hidden layer, learning rates and threshold
	@param examplesTrain 			Training examples
	@param examplesTest 			Testing examples
	@param numInput 				The number of inputs to this network
	@param numOutput 				The number of outputs from this network
	*/
	public static NeuralNet buildNeuralNet(HashSet<Example> examplesTrain, HashSet<Example> examplesTest, int numInput, int numOutput){
		return buildNeuralNet(examplesTrain, examplesTest, 0.1, 0.00008, numInput, new int[] {1}, numOutput, Integer.MAX_VALUE, null);
	}
	
	/**
	Train a neural net for the given input, with default learning rates and threshold
	@param examplesTrain 			Training examples
	@param examplesTest 			Testing examples
	@param numInput 				The number of inputs to this network
	@param hiddenLayerList 			The number of hidden units in each layer
	@param numOutput 				The number of outputs from this network
	@param maxItr 					The maximum number of iterations to run
	*/
	public static NeuralNet buildNeuralNet(HashSet<Example> examplesTrain, HashSet<Example> examplesTest, int numInput, int[] hiddenLayerList, int numOutput, int maxItr){
		return buildNeuralNet(examplesTrain, examplesTest, 0.1, 0.00008, numInput, hiddenLayerList, numOutput, maxItr, null);
	}	
	
	/**
	Train a neural net for the given input
	
	@param examplesTrain 			Training examples
	@param examplesTest 			Testing examples
	@param alpha 					Learning rate
	@param weightChangeThreshold	The threshold to stop training at
	@param numInput 				The number of inputs to this network
	@param hiddenLayerList 			The number of hidden units in each layer
	@param numOutput 				The number of outputs from this network
	@param maxItr 					The maximum number of iterations to run
	@param startNNet 				A NeuralNet to train, or null if a new NeuralNet
	
	@return The trained neural network. The weight modification that it achieved (either when the threshold was reached, or when maxItr was reached) will be stored in this class's static variable lastWeightMod.
	*/
	public static NeuralNet buildNeuralNet(HashSet<Example> examplesTrain, HashSet<Example> examplesTest, double alpha, double weightChangeThreshold, int numInput, int[] hiddenLayerList, int numOutput, int maxItr, NeuralNet startNNet){
		
		if(startNNet != null){ //here if it's not a new neural net
			hiddenLayerList = new int[startNNet.numHiddenLayers];
			int i=0;
			for(ArrayList<Perceptron> layer:startNNet.hiddenLayers){
				hiddenLayerList[i] = layer.size();
				i++;
			}
		}
		
		System.out.println(String.format("Starting training with %d inputs, %d outputs, %s hidden layers, size of training set %d, and size of test set %d", numInput, numOutput, Arrays.toString(hiddenLayerList), examplesTrain.size(), examplesTest.size()));
		
		int[] layerList = new int[hiddenLayerList.length+2];
		layerList[0] = numInput;
		layerList[layerList.length-1] = numOutput;
		for(int i=0; i<hiddenLayerList.length; i++){
			layerList[i+1] = hiddenLayerList[i];
		}
		NeuralNet net = new NeuralNet(layerList);
		
		if(startNNet != null){
			net = startNNet;
		}
		
		int iteration = 0;
		double trainError = 0;
		double weightMod = 0;

		
		
		/* YOUR CODE HERE */
		int itterNum = 0;
		double[] backpropCall = net.backPropLearning(examplesTrain, alpha);
		while ((backpropCall[1] < weightChangeThreshold) && (itterNum < maxItr)) {
			itterNum++;
			if(iteration%10 == 0){
				System.out.println(String.format("On iteration %d; training error %f and weight change %f", iteration, trainError, weightMod));
			}
			else
				System.out.print(".");
		}
		/* Iterate for as long as it takes to reach weight modification threshold */
		
		
		System.out.println(String.format("Finished after %d iterations with training error %f and weight change %f", iteration, trainError, weightMod));
		
		/*
		Get the accuracy of your Neural Network on the test examples.
		For each test example, you should first feedforward to get the NN outputs. Then, round the list of outputs from the output layer of the neural net.
		If the entire rounded list from the NN matches with the known list from the test example, then add to testCorrect, else add to testError.
		*/ 
		
		int testError = 0;
		int testCorrect = 0;

		for (Example ex: examplesTest) {
			ArrayList<Double> output = net.feedForward(ex.inputs).get(net.feedForward(ex.inputs).size()-1);
			boolean same = true;
			for (int i = 0; i > output.size(); i++) {
				if (round(output.get(i)) != round(ex.getOutputs().get(i))) {
					same = false;
				}
			}
			if (same) {
				testCorrect++;
			}else {
				testError++;
			}
		}
		double testAccuracy = testCorrect / (testError+testCorrect); // (num correct / num total)
		
		System.out.println(String.format("Feed Forward Test correctly classified %d, incorrectly classified %d, test percent accurate %f", testCorrect, testError, testAccuracy));
		
		lastWeightMod = weightMod; // Store last value of weightMod before returning. Use getLastWeightMod() to retrieve value
		return net;
	}
	
	/**
	Retrieves the last weight modification value from the most recent training process
	@return Last weight modification value
	*/
	public static double getLastWeightMod(){
		return lastWeightMod;
	}
	
	
	// got the base of this from https://stackoverflow.com/questions/2654839/rounding-a-double-to-turn-it-into-an-int-java
	public static int round(double d){
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<0.5){
	        return  d<0 ? -i : i;            
	    }else{
	        return  d<0 ? -(i+1) : i+1;          
	    }
	}
}