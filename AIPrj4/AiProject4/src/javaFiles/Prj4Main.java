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
		int[] hiddenLayers = new int[1];
		hiddenLayers[0] = 16;
		
		NeuralNetUtil.testPenData(hiddenLayers);

	}

}
