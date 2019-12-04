package javaFiles;
import java.util.*;
import java.io.*;

public class NeuralNetUtil{
	/* Returns limit # of examples from penDigits file*/
	public static HashSet<Example> getNNPenData(String filename, int limit){
		if(filename == null) filename = "datasets/pendigits.txt";
		if(limit <= 0) limit = 100000;
		
		HashSet<Example> examples = new HashSet<Example>();
		
		try{
			Scanner data = new Scanner(new File(filename));
			int lineNum = 0;
			while(data.hasNextLine()){
				String line = data.nextLine();
				
				double[] inVec = new double[16];
				double[] outVec = new double[10];
				int count = 0;
				for(String val : line.split(",")){
					if(count == 16) 
						outVec[Integer.parseInt(val)] = 1;
					else
						inVec[count] = Integer.parseInt(val)/100.0;
					count++;
				}
				examples.add(new Example(inVec, outVec));
				lineNum++;
				if(lineNum >= limit) 
					break;
			}
		}
		catch(FileNotFoundException fnf){
			fnf.printStackTrace();
		}
		
		return examples;
	}
	
	/*
	Returns a training set for the pen data set
	*/
	public static HashSet<Example> getPenTrainingSet(int size){
		if(size<=0) size = 10000;
		return getNNPenData("datasets/pendigitsTrain.txt", (int) (.8*size));
	}
	
	/*
	Returns a test set for the pen data set
	*/
	public static HashSet<Example> getPenTestSet(int size){
		if(size<=0) size = 10000;
		return getNNPenData("datasets/pendigitsTest.txt", (int) (.2*size));
	}
		
	/*
	Executes a training and testing of the pen data set with the specified hidden layers. If hiddenLayers is null, it uses a single hidden layer of size 24.
	*/
	public static NeuralNet testPenData(int[] hiddenLayers){
		if(hiddenLayers == null)
			hiddenLayers = new int[] {24};
		
		HashSet<Example> penTrain = getPenTrainingSet(0);
		HashSet<Example> penTest = getPenTestSet(0);
		
		return NeuralNet.buildNeuralNet(penTrain, penTest, 16, hiddenLayers, 10, 200);
	}
	
	/* Calculates the average of a list of doubles */
	public static double average(double[] list){
		double total = 0;
		for(double d:list) total += d;
		return total/list.length;
	}
	
	/* Calculates the standard deviation of a list of doubles */
	public static double stDeviation(double[] list){
		double mean = average(list);
		double sumDiffSq = 0;
		for(double d:list){
			sumDiffSq += Math.pow(d-mean, 2);
		}
		return Math.sqrt(sumDiffSq/list.length);
	}
	
	/* Converts a primitive double array to a Wrapper Double array */
	public static ArrayList<Double> toWrapperList(double[] array){
		ArrayList<Double> list = new ArrayList<Double>(array.length);
		for(double d:array)
			list.add(d);
		return list;
	}
	
	/* Converts a Double ArrayList to a primitive double array */
	public static double[] toPrimitiveArray(ArrayList<Double> list){
		double[] array = new double[list.size()];
		for(int idx=0; idx<list.size(); idx++){
			array[idx] = list.get(idx);
		}
		return array;
	}
}