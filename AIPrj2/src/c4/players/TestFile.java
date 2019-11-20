package c4.players;

import java.util.*;

public class TestFile {

	public static void main(String[] args) {
		int[][] dummyBoard = new int[7][6];
		for(int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				dummyBoard[i][j] = -1;
			}
		}
		
		System.out.println(Arrays.deepToString(dummyBoard));
		int[][] firstSlot = result(dummyBoard, 1);
		int[][] secondSlot = result(firstSlot, 2);
		int[][] thirdSlot = result(secondSlot, 3);
		int[][] fourthSlot = result(thirdSlot, 4);
		int[][] fifthSlot = result(fourthSlot, 0);
		int[][] sixthSlot = result(fifthSlot, 0);
		int[][] lastSlot = result(sixthSlot, 6);
		System.out.println(Arrays.deepToString(fourthSlot));
		int[] actions = actions(fourthSlot);
		for (int action: actions) {
		}
		System.out.println("test for terminal: " + terminalTest(fourthSlot));
	}

	
	
	public static int[][] result(int[][] state, int action) { //works as expected.
		//grid = new int[7][6];
		int[][] possibleState = new int[7][6];
		int columnHeight = 0;
		//makes copy of the current game board state
		for(int col = 0; col < 7; col++) {
			for(int row = 0; row < 6; row++) {
				possibleState[col][row] = state[col][row];
			}
		}
		//gets the column height of the new point
		//we want to be in possibleState[action][find this height]
		for (int i = 0; i < 6; i++) {
			if (possibleState[action][i] == 1 || possibleState[action][i] == 2) {
				columnHeight++;
			}
		}
		int myTurn = 1; //making the turn always 1.
		possibleState [action][5-columnHeight] = myTurn;
		return possibleState;
		
	}

	
	
	
	public static int[] actions(int[][] state) {  //works as expected
		//in his initialize in model, grid = new int[7][6]; 
		ArrayList<Integer> moves = new ArrayList<Integer>(); //all possible actions are [0,1,2,3,4,5,6]
		for (int i = 0; i < 7; i++) {
			if (state[i][0] != 1 && state[i][0] != 2) { //if the top slot of that column is empty
				moves.add(i);
			}
		}
		int[] results = new int[moves.size()];
		for(int i=0; i<results.length; i++) {
			results[i] = moves.get(i);
		}
		return results;
	}
	
	
	
	
	
	
	//works as expected
	
	//returns true if the game is in a terminal state (someone won/lost, tie). False otherwise
	//logic for these functions was stolen from my other connect4 project in data structures.
	public static boolean terminalTest(int[][] state) { //could optimize with less loops/ better checking
		boolean[] results = new boolean[5]; //we want this to be all false, else one of the checks returned a terminal state
		results[0] = verticalCheck(state);
		results[1] = horizontalCheck(state);
		results[2] = positiveDiagonalCheck(state);
		results[3] = negativeDiagonalCheck(state);
		results[4] = drawCheck(state);
//		System.out.println("new terminal test");
		for (boolean result:results) {
			System.out.println(result);
			if (result == true) {
//				System.out.println("claims to be a terminal state below");
//				System.out.println(Arrays.deepToString(state));
				return true;
			}
		}
		return false;	
	}
	
	
	private static boolean negativeDiagonalCheck(int[][] state) { //all set
		for(int col=0; col<=3; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == 1 && state[col+1][row+1] == 1 && state[col+2][row+2] == 1 && state[col+3][row+3] == 1) {
					return true;
				}
				if (state[col][row] == 2 && state[col+1][row+1] == 2 && state[col+2][row+2] == 2 && state[col+3][row+3] == 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean horizontalCheck(int[][] state) { //all set
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
				if (state[col][row] == 1 && state[col+1][row] == 1 && state[col+2][row] == 1 && state[col+3][row] == 1) {
					return true;
				}
				if (state[col][row] == 2 && state[col+1][row] == 2 && state[col+2][row] == 2 && state[col+3][row] == 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean positiveDiagonalCheck(int[][] state) { //all set
		for (int col = 3; col < 7; col++) { 
			for (int row = 0; row <= 2; row++) { 
				if (state[col][row] == 1 && state[col-1][row+1] == 1 && state[col-2][row+2] == 1 && state[col-3][row+3] == 1) {
					return true;
				}
				if (state[col][row] == 2 && state[col-1][row+1] == 2 && state[col-2][row+2] == 2 && state[col-3][row+3] == 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean verticalCheck(int[][] state) { //all set
		for(int col=0; col<7; col++){
			for(int row=0; row<=2; row++){
				if (state[col][row] == 1 && state[col][row+1] == 1 && state[col][row+2] == 1 && state[col][row+3] == 1) {
					return true;
				}
				if (state[col][row] == 2 && state[col][row+1] == 2 && state[col][row+2] == 2 && state[col][row+3] == 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean drawCheck(int[][] state) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (state[i][j] == -1) {
					return false;
				}
			}
		}
		return true;
	}
	

}
