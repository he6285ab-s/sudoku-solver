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
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Invalid indices");
		}
		
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("Invalid number");
		}
		
		board[row][col] = number;
	}
	
	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public boolean trySetNumber(int row, int col, int number) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Invalid indices");
		}
		
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("Invalid number");
		}
		
		return isValid(row, col, number);
	}

	/**
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public int getNumber(int row, int col) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Invalid indices");
		}
		
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
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Invalid indices");
		}
		
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
 				if (numbers[r][c] < 0 || numbers[r][c] > 9) {
 					throw new IllegalArgumentException();
 				} 
 			}
 		}
		
 		// Update board only if no exception has been thrown (i.e. all input is in [1,9])
		board = numbers; 
	}

	
	
	// ----- Private auxillary methods -----
	
	/*
	 * Recursive method for checking if the board is solvable
	 * in its current state.
	 */
	private boolean solve(int row, int col) {
		
		/* Base case - overflowed to row that doesn't exist
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
					
					int[] nextPos = getNext(row, col);
					if(solve(nextPos[0], nextPos[1])) {
						return true;
					} else {
						removeNumber(row, col);
					}
					
				}
			}
			
			return false; // If no number is allowed in the box
		
		// If the box has a value
		} else {
			/*
			 *  If the cell is valid -> recursively check next cell,
			 *  otherwise return false
			 */
			if (isValid(row, col, getNumber(row, col))) {
				int[] nextPos = getNext(row, col);
				return solve(nextPos[0], nextPos[1]);
			} else {
				return false;
			}
		}
		
	}
	
	private int[] getNext(int row, int col) {
		int[] nextPos = new int[2];
		col++;
		if (col > 8) {
			row++;
			col = 0;
		}
		nextPos[0] = row;
		nextPos[1] = col;
		return nextPos;
	}
	
	private boolean isValid(int row, int col, int number) {
		boolean valid = true;
		
		for (int i = 0; i < 9; i++) {
			
			// Row
			if (i != row && number == getNumber(i, col)) {
				valid = false;
			}
			
			// Col
			if (i != col && number == getNumber(row, i)) {
				valid = false;
			}
			
		}
		
		// Square
		int row_shift = row % 3; // How far from a number divisible by 3 the row is shifted
		int col_shift = col % 3;
					
		for (int r = row - row_shift; r < row + 3 - row_shift; r++) {
			for (int c = col - col_shift; c < col + 3 - col_shift; c++) {
				if (r != row && c != col && getNumber(r, c) == number) {
					valid = false;
				}
			}
		}
		
		return valid;
	}

	
}
