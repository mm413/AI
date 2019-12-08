package javaFiles;

import java.io.*;
import java.util.*;

public class Prj4Main {

	public static void main(String[] args) throws FileNotFoundException {
//		HashSet<Example> testHashSet = new HashSet<Example>();
//		Scanner sc = new Scanner(new File("pendigitsTest.txt"));
//		while (sc.hasNextLine()) {
//			String line = sc.nextLine();
//		}
//		
//		HashSet<Example> trainHashSet = new HashSet<Example>();
		int[] zero = new int[1];
		zero[0] = 0;
		int[] five = new int[1];
		five[0] = 5;
		int[] ten = new int[1];
		ten[0] = 10;
		int[] fifteen = new int[1];
		fifteen[0] = 15;
		int[] twenty = new int[1];
		twenty[0] = 20;
		int[] twentyFive = new int[1];
		twentyFive[0] = 25;
		int[] thirty = new int[1];
		thirty[0] = 30;
		int[] thirtyFive = new int[1];
		thirtyFive[0] = 35;
		int[] fourty = new int[1];
		fourty[0] = 40;
		
		//running 5 tests of testPenData with default parameters, reporting max, average,
		//standard deviation of the accuracy
		for (int i = 1; i <=5; i++) {	
			System.out.println(i + "th" + " itteration:");
			System.out.println("zero next:");
			NeuralNet nn1 = NeuralNetUtil.testPenData(zero);
			System.out.println("five next:");
			NeuralNet nn2 = NeuralNetUtil.testPenData(five);
			System.out.println("ten next:");
			NeuralNet nn3 = NeuralNetUtil.testPenData(ten);
			System.out.println("fifteen next:");
			NeuralNet nn4 = NeuralNetUtil.testPenData(fifteen);
			System.out.println("twenty next:");
			NeuralNet nn5 = NeuralNetUtil.testPenData(twenty);
			System.out.println("25 next:");
			NeuralNet nn6 = NeuralNetUtil.testPenData(twentyFive);
			System.out.println("30 next:");
			NeuralNet nn7 = NeuralNetUtil.testPenData(thirty);
			System.out.println("35 next:");
			NeuralNet nn8 = NeuralNetUtil.testPenData(thirtyFive);
			System.out.println("40 next:");
			NeuralNet nn9 = NeuralNetUtil.testPenData(fourty);
		}
	}

}
