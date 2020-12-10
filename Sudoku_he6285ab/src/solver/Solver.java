package solver;

public class Solver implements SudokuSolver {

	private int[][] board;
	
	/**
	 * Creates a new Sudoku solver object.
	 */
	public Solver() {
		board = new int[9][9];
	}
	
	
	@Override
	public void clear() {
		board = new int[9][9];
	}

	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public void setNumber(int row, int col, int number) {
		checkIndexException(row, col);
		checkNumberException(number, 1);
		board[row][col] = number;
	}
	
	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public boolean trySetNumber(int row, int col, int number) {
		checkIndexException(row, col);
		checkNumberException(number, 1);	
		return isValid(row, col, number);
	}

	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public int getNumber(int row, int col) {
		checkIndexException(row, col);
		return board[row][col];
	}


	@Override
	public int[][] getNumbers() {
		return board;
	}
	
	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public void removeNumber(int row, int col) {
		checkIndexException(row, col);		
		board[row][col] = 0;
	}
	

	@Override
	public boolean solve() {
		return solve(0, 0);
	}
	
	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public void setNumbers(int[][] numbers) {
		for (int r = 0; r < 9; r++) {
 			for (int c = 0; c < 9; c++) {
 				checkNumberException(numbers[r][c], 0);
 			}
 		}
 		// Update board only if no exception has been thrown (i.e. all input is in [1,9])
		board = numbers; 
	}

	
	
	// ----- Private helper methods -----
	
	/*
	 * Recursive method for checking if the board is solvable
	 * in its current state.
	 */
	private boolean solve(int row, int col) {
		
		// Handle row overflow
		if(col > 8) {
			col = 0;
			row++;
		}
		
		/* Base case - reached a solution
		 * i.e. has reached a solution
		 */
		if (row == 9) {
			return true;
		}
				
		// If box is empty 
		if (board[row][col] == 0) {
			
			// Try to set numbers 1-9
			for (int i = 1; i < 10; i++) {
				if (trySetNumber(row, col, i)) {
					setNumber(row, col, i);
					
					if(solve(row, col+1)) {
						return true;
					} else {
						removeNumber(row, col);
					}
					
				}
			}
			// No solution found
			return false; 
		
		// If the box has a value
		} else {
			if (isValid(row, col, getNumber(row, col))) {
				return solve(row, col+1);
			} else {
				return false;
			}
		}
		
	}
	

	/* Checks if the number is valid in row, col 
	 * (ignoring actual value in row,col) */
	private boolean isValid(int row, int col, int number) {
		
		for (int i = 0; i < 9; i++) {
			
			// Row
			if (i != row && number == getNumber(i, col)) {
				return false;
			}
			
			// Col
			if (i != col && number == getNumber(row, i)) {
				return false;
			}
			
		}
		
		// Square
		int boxStartRow = row - row%3; // Start row index of 3x3 box
		int boxStartCol = col - col%3; // Start col index of 3x3 box
					
		for (int r = boxStartRow; r < boxStartRow + 3; r++) {
			for (int c = boxStartCol; c < boxStartCol + 3; c++) {
				if (r != row && c != col && getNumber(r, c) == number) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Check for index out of bounds -> throw IllegalArgumentException
	private void checkIndexException(int row, int col) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Invalid indices");
		}
	}
	
	// Check for number outside Sudoku range -> throw IllegalArgumentException
	private void checkNumberException(int num, int lowerRange) {
		if (num < lowerRange || num > 9) {
			throw new IllegalArgumentException("Invalid number");
		}
	}
	
}
