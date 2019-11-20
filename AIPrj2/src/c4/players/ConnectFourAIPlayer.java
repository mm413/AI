package c4.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
import c4.mvc.ConnectFourModelInterface;

public class ConnectFourAIPlayer extends ConnectFourPlayer {
	ConnectFourModelInterface model;
	int depth = 0;
	
	/**
	 * @param model - ConnectFourModelInterface the model the game  is based on
	 * ConnectFourAIPlayer is a constructor for a connect4 ai player.
	 */
	public ConnectFourAIPlayer(ConnectFourModelInterface model) {
		this.model = model;
	}
	
	/**
	 * @param model - ConnectFourModelInterface the model the game  is based on
	 * @param depth - an integer represent how many states ahead the AI will consider.
	 * ConnectFourAIPlayer is a constructor for a connect4 ai player.
	 */
	public ConnectFourAIPlayer(ConnectFourModelInterface model, int depth) {
		this.model = model;
		this.depth = depth;
	}

	/**
	 * @return m - a move
	 * dumbGetMove was the old get move function. Serves no purpose.
	 */
	public int dumbGetMove() {
		boolean[] moves = model.getValidMoves();
		int m = 0;
		for (boolean move: moves) {
			if (move){
				return m;
			}
			m++;
		}
		return -1;
	}

	
	/**
	 * @param state - the current game board state
	 * @return results - an int[] representing all possible moves from the current state
	 * actions looks through all current possible moves (0-6) and returns them.
	 */
	public int[] actions(int[][] state) {
		//in his initialize in model, grid = new int[7][6]; 
		ArrayList<Integer> moves = new ArrayList<Integer>(); //all possible actions are [0,1,2,3,4,5,6]
		for (int i = 0; i < 7; i++) {
			if (state[i][0] != 1 && state[i][0] != 2) { //if the top slot of that column is empty
				moves.add(i);
			}
		}
		int[] results = new int[moves.size()];
		//System.out.println(moves.size());
		for(int i=0; i<results.length; i++) {
			results[i] = moves.get(i);
		}
		return results;
	}
	
	/**
	 * @param state - the current game board state
	 * @return - int representation of turn. 1 if the turn is player 1's, 2 if it's player 2's
	 * getTurn loops through the current game board, counting all player 1 and 2 pieces. returns the current 
	 * player's turn based off of the counts.
	 */
	public int getTurn(int[][] state) { //if the AIplayer is player1, returns 1. player2 = 2.
		int playerOneTurnCount = 0;
		int playerTwoTurnCount = 0;
		for(int col=0; col < 7; col++) {
			for(int row= 0; row < 6; row++) {
				if (state[col][row] == 1) {
					playerOneTurnCount++;
				}
				if (state[col][row] == 2) {
					playerTwoTurnCount++;
				}
			}
		}
		if (playerOneTurnCount == playerTwoTurnCount) { //I 100% know this is backwards, but this works (iff AI is player 2.)
			return 2;
		}else {
			return 1;
		}
	}
	
	/**
	 * @param state - the current game board
	 * @param action - a move that a player can make
	 * @return - a new game board with the action parameter added to the board
	 * result adds a player's action to the board, and returns the 'new' board.
	 */
	public int[][] result(int[][] state, int action) { 
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
		int myTurn = getTurn(state);
		possibleState [action][5-columnHeight] = myTurn;
		return possibleState;
		
	}
	
	
	/**
	 * @param state - the current game board state being tested
	 * @return boolean - true if the given state is terminal, false otherwise.
	 * returns true if the game is in a terminal state (someone won/lost, tie). False otherwise
	 */
	public boolean terminalTest(int[][] state) { //could optimize with less loops/ better checking
		boolean[] results = new boolean[5]; //we want this to be all false, else one of the checks returned a terminal state
		results[0] = verticalCheck(state);
		results[1] = horizontalCheck(state);
		results[2] = positiveDiagonalCheck(state);
		results[3] = negativeDiagonalCheck(state);
		results[4] = drawCheck(state);
		for (boolean result:results) {
			if (result == true) {
				return true;
			}
		}
		return false;	
	}
	
	

	private boolean negativeDiagonalCheck(int[][] state) { //all set
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
	

	private boolean horizontalCheck(int[][] state) { //all set
		for(int col=0; col<=3; col++){ 
			for(int row=0; row<6; row++){
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
	

	private boolean positiveDiagonalCheck(int[][] state) { //all set
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
	

	private boolean verticalCheck(int[][] state) { //all set
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
	

	private boolean drawCheck(int[][] state) {
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				if (state[col][row] == -1) {
					return false;
				}
			}
		}
		return true;
	}
	

	
	/**
	 * @param state - the current game board state being tested.
	 * @return int - the 'goodness' level of the state
	 * utility function tests a state that's given to it on it's level of 'goodness'. the higher the number returned,
	 * the better the state. The lower the number, the worse the state.
	 */
	public int utility(int [][] state) {
		int symbol = getTurn(state);
		int enemySymbol;
		System.out.println("AI Symbol: "+symbol);
		if (symbol == 2) {
			enemySymbol = 1; 
		}else {
			enemySymbol = 2;
		}
		System.out.println("Human Symbol: "+enemySymbol);
		//negative diagonal check
		for(int col=0; col<=3; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col+1][row+1] == symbol && state[col+2][row+2] == symbol && state[col+3][row+3] == symbol) {
					return 1000; 
				}
				if (state[col][row] == enemySymbol && state[col+1][row+1] == enemySymbol && state[col+2][row+2] == enemySymbol && state[col+3][row+3] == enemySymbol) {
					return -1000; 
				}
			}
		}
		//horizontal check
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
				if (state[col][row] == symbol && state[col+1][row] == symbol && state[col+2][row] == symbol && state[col+3][row] == symbol) {
					return 1000;
				}
				if (state[col][row] == enemySymbol && state[col+1][row] == enemySymbol && state[col+2][row] == enemySymbol && state[col+3][row] == enemySymbol) {
					return -1000;
				}
			}
		}
		//positive diagonal check
		for(int col=3; col<7; col++){
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col-1][row+1] == symbol && state[col-2][row+2] == symbol && state[col-3][row+3] == symbol) {
					return 1000;
				}
				if (state[col][row] == enemySymbol && state[col-1][row+1] == enemySymbol && state[col-2][row+2] == enemySymbol && state[col-3][row+3] == enemySymbol) {
					return -1000;
				}
			}
		}
		//vertical check
		for(int col=0; col<7; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col][row+1] == symbol && state[col][row+2] == symbol && state[col][row+3] == symbol) {
					return 1000;
				}
				if (state[col][row] == enemySymbol && state[col][row+1] == enemySymbol && state[col][row+2] == enemySymbol && state[col][row+3] == enemySymbol) {
					return -1000;
				}
			}
		}
		//draw check
		int neg1Count = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (state[i][j] == -1) {
					neg1Count++;
				}
			}
		}
		if (neg1Count == 0){
			return 0;
		}
		//negative diagonal check for 3 in a row
		for(int col=0; col<=3; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col+1][row+1] == symbol && state[col+2][row+2] == symbol && state[col+3][row+3] == -1) {
					return 600; 
				}
				if (state[col][row] == -1 && state[col+1][row+1] == symbol && state[col+2][row+2] == symbol && state[col+3][row+3] == symbol) {
					return 600; 
				}
				
				if (state[col][row] == enemySymbol && state[col+1][row+1] == enemySymbol && state[col+2][row+2] == enemySymbol && state[col+3][row+3] == -1) {
					return -600; 
				}
				if (state[col][row] == -1 && state[col+1][row+1] == enemySymbol && state[col+2][row+2] == enemySymbol && state[col+3][row+3] == enemySymbol) {
					return -600; 
				}
			}
		}
		
		//horizontal check for 3 in a row
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
				if (state[col][row] == symbol && state[col+1][row] == symbol && state[col+2][row] == symbol && state[col+3][row] == -1) {
					return 600;
				}
				if (state[col][row] == -1 && state[col+1][row] == symbol && state[col+2][row] == symbol && state[col+3][row] == symbol) {
					return 600;
				}
				if (state[col][row] == enemySymbol && state[col+1][row] == enemySymbol && state[col+2][row] == enemySymbol && state[col+3][row] == -1) {
					return -600;
				}
				if (state[col][row] == -1 && state[col+1][row] == enemySymbol && state[col+2][row] == enemySymbol && state[col+3][row] == enemySymbol) {
					return -600;
				}
			}
		}
		
		//positive diagonal check for 3 in a row
		for(int col=3; col<7; col++){
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col-1][row+1] == symbol && state[col-2][row+2] == symbol && state[col-3][row+3] == -1) {
					return 600;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == symbol && state[col-2][row+2] == symbol && state[col-3][row+3] == symbol) {
					return 600;
				}
				if (state[col][row] == enemySymbol && state[col-1][row+1] == enemySymbol && state[col-2][row+2] == enemySymbol && state[col-3][row+3] == -1) {
					return -600;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == enemySymbol && state[col-2][row+2] == enemySymbol && state[col-3][row+3] == enemySymbol) {
					return -600;
				}
			}
		}
		//vertical check for 3 in a row
		for(int col=0; col<7; col++){ 
			for(int row=0; row<=2; row++){
//				System.out.println("...");
//				System.out.println(enemySymbol);
//				System.out.println("new search, want -1,2,2,2");
//				System.out.println(state[col][row]);
//				System.out.println(state[col][row+1]);
//				System.out.println(state[col][row+2]);
//				System.out.println(state[col][row+3]);
				if (state[col][row] == -1 && state[col][row+1] == symbol && state[col][row+2] == symbol && state[col][row+3] == symbol) {
					System.out.println("I can win on next move");
					return 600;
				}
				if (state[col][row] == -1 && state[col][row+1] == enemySymbol && state[col][row+2] == enemySymbol && state[col][row+3] == enemySymbol) {
					System.out.println("we made it here. enemy can win on next move");
					return -600;
				}
				
			}
		}
		
		//negative diagonal check for 2 in a row
		for(int col=0; col<=3; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col+1][row+1] == symbol && state[col+2][row+2] == -1 && state[col+3][row+3] == -1) {
					return 100; //i win
				}
				if (state[col][row] == -1 && state[col+1][row+1] == symbol && state[col+2][row+2] == symbol && state[col+3][row+3] == -1) {
					return 150; //i win
				}
				if (state[col][row] == -1 && state[col+1][row+1] == -1 && state[col+2][row+2] == symbol && state[col+3][row+3] == symbol) {
					return 100; //i win
				}
				if (state[col][row] == enemySymbol && state[col+1][row+1] == enemySymbol && state[col+2][row+2] == -1 && state[col+3][row+3] == -1) {
					return -100; //enemy wins
				}
				if (state[col][row] == -1 && state[col+1][row+1] == enemySymbol && state[col+2][row+2] == enemySymbol && state[col+3][row+3] == -1) {
					return -150; //enemy wins
				}
				if (state[col][row] == -1 && state[col+1][row+1] == -1 && state[col+2][row+2] == enemySymbol && state[col+3][row+3] == enemySymbol) {
					return -100; //enemy wins
				}
			}
		}
		
		//horizontal check for 2 in a row
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
				if (state[col][row] == symbol && state[col+1][row] == symbol && state[col+2][row] == -1 && state[col+3][row] == -1) {
					return 100;
				}
				if (state[col][row] ==-1 && state[col+1][row] == symbol && state[col+2][row] == symbol && state[col+3][row] == -1) {
					return 100;
				}
				if (state[col][row] == -1 && state[col+1][row] == -1 && state[col+2][row] == symbol && state[col+3][row] == symbol) {
					return 100;
				}
				if (state[col][row] == enemySymbol && state[col+1][row] == enemySymbol && state[col+2][row] == -1 && state[col+3][row] == -1) {
					return -100;
				}
				if (state[col][row] == -1 && state[col+1][row] == enemySymbol && state[col+2][row] == enemySymbol && state[col+3][row] == -1) {
					return -100;
				}
				if (state[col][row] == -1 && state[col+1][row] == -1 && state[col+2][row] == enemySymbol && state[col+3][row] == enemySymbol) {
					return -100;
				}
			}
		}
		
		//positive diagonal check for 2 in a row
		for(int col=3; col<7; col++){
			for(int row=0; row<=2; row++){
				if (state[col][row] == symbol && state[col-1][row+1] == symbol && state[col-2][row+2] == -1 && state[col-3][row+3] == -1) {
					return 100;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == symbol && state[col-2][row+2] == symbol && state[col-3][row+3] == -1) {
					return 100;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == -1 && state[col-2][row+2] == symbol && state[col-3][row+3] == symbol) {
					return 100;
				}
				if (state[col][row] == enemySymbol && state[col-1][row+1] == enemySymbol && state[col-2][row+2] == -1 && state[col-3][row+3] == -1) {
					return -100;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == enemySymbol && state[col-2][row+2] == enemySymbol && state[col-3][row+3] == -1) {
					return -100;
				}
				if (state[col][row] == -1 && state[col-1][row+1] == -1 && state[col-2][row+2] == enemySymbol && state[col-3][row+3] == enemySymbol) {
					return -100;
				}
			}
		}
		
		//vertical check for 2 in a row
		for(int col=0; col<7; col++){ 
			for(int row=0; row<=2; row++){
				if (state[col][row] == -1 && state[col][row+1] == -1 && state[col][row+2] == symbol && state[col][row+3] == symbol) {
					return 100;
				}
				if (state[col][row] == -1 && state[col][row+1] == -1 && state[col][row+2] == enemySymbol && state[col][row+3] == enemySymbol) {
					return -100;
				}
			}
		}



		return 0; //state doesn't matter; move doesn't *really* matter 
	}
	
	
	/** @returns move - the move the AI is going to make
	 *
	 */
	@Override
	public int getMove(){
		int[][] state = model.getGrid();
		int move = alphaBetaSearch(state);
		return move;

	}
	
	/**
	 * @param state - the current game state
	 * @return action - the best action that the AI can see
	 * alphaBetaSearch takes the best of the minValues.
	 */
	public int alphaBetaSearch(int[][] state){
		int best = minValue(result(state,actions(state)[0]), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		int bestAction = actions(state)[0];
		int currentDepth = 0;
		for (int action: actions(state)) { 
			currentDepth = 0;
			int resultOfMin = minValue(result(state,action), Integer.MIN_VALUE, Integer.MAX_VALUE, currentDepth);
			if (resultOfMin > best) {
				best = resultOfMin;
				bestAction = action;
			}
		}
		return bestAction; 
	}
	
	/**
	 * @param state - the current game state
	 * @param alpha - the current best move for me
	 * @param beta - the current best move for my opponent
	 * @param currentDepth - how many moves ahead we are currently looking
	 * @return - the best state for me out of my opponents best state for themselves.
	 * maxValue returns the best state it can see, out of the worst states given to it.
	 */
	public int maxValue(int[][] state, int alpha, int beta, int currentDepth){
		currentDepth++;
		if (terminalTest(state)) {
			return utility(state);
		}
		if (currentDepth >= depth) {
			return utility(state);
		}
		int v = Integer.MIN_VALUE;
		for (int action: actions(state)) {
			v = Math.max(v, minValue(result(state, action), alpha, beta, currentDepth));
			if (v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;

	}
	
	/**
	 * @param state - the current game state
	 * @param alpha - the current best move for me
	 * @param beta - the current best move for my opponent
	 * @param currentDepth - how many moves ahead we are currently looking
	 * @return the best state for my opponent out of the best states for myself.
	 * minValue does the opposite of maxValue. Returns the worst state for me 
	 * out of the best states for me. 
	 */
	public int minValue(int[][] state, int alpha, int beta, int currentDepth){
		currentDepth++;
		if (terminalTest(state)) {
			return utility(state);
		}
		if (currentDepth >= depth) {
			return utility (state);
		}
		int v = Integer.MAX_VALUE;
		for (int action: actions(state)) {
			v = Math.min(v, maxValue(result(state, action), alpha, beta, currentDepth));
			if (v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}


}
