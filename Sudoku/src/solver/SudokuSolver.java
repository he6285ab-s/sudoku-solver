package solver;

public interface SudokuSolver {
	/**
	 * Sets the value of box row,col to number.
	 * 
	 * @param row    The row of the sudoku matrix
	 * @param col    The column of the sudoku matrix
	 * @param number The digit to insert in row, col
	 * 
	 * @exception IllegalArgumentException if number is outside [1..9] or row or col is
	 *                                  outside [0..8]
	 */
	void setNumber(int row, int col, int number);
	
	/**
	 * Checks if number can be inserted in the box row, col,
	 * return true if it's allowed according to game rules, otherwise false.
	 * 
	 * @param row    The row of the sudoku matrix
	 * @param col    The column of the sudoku matrix
	 * @param number The digit to try to insert in row, col
	 * @exception IllegalArgumentException if number is outside [1..9] or row or col is
	 *                                  outside [0..8]
	 */
	boolean trySetNumber(int row, int col, int number);
	
	/**
	 *  Returns the number in the box row, col.
	 *  
	 *  @param row The row of the sudoku matrix
	 *  @param col The column of the sudoku matrix
	 *  @exception IllegalArgumentException if row or col is outside [0..8]                           
	 */
	int getNumber(int row, int col);
	
	/**
	 * Removes the number in the box row, col.
	 * 
	 * @param row The row of the sudoku matrix
	 * @param col The column of the sudoku matrix
	 * @throws IllegalArgumentException if row or col is outside [0..8]
	 */
	void removeNumber(int row, int col);
	
	/** 
	 * Empties the sudoku completely.
	 */
	void clear();

	/** 
	 * Tries to solve the sudoku and returns true if it's solvable, otherwise false.
	 */
	boolean solve();
	
	/** 
	 * Returns the numbers in the sudoku. 
	 * 
	 * @return The numbers in the sudoku
	 */
	int[][] getNumbers();
	
	/**
	 * Sets the sudoku to the numbers in numbers.
	 * 
	 * @param numbers The numbers to update with
	 * @exception IllegalArgumentException if not all numbers in [1..9] 
	 */
	void setNumbers(int[][] numbers);
	
}



