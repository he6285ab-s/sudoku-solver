package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import solver.Solver;
import solver.SudokuSolver;

public class SolverTest {
	
	SudokuSolver solver;
	
	@BeforeEach
	void setUp() {
		solver = new Solver();
	}
	
	@AfterEach
	void tearDown() {
		solver = null;
	}
	
	@Test
	void testTrySetNumber() {
		solver.setNumber(0, 0, 1);
		assertTrue(solver.trySetNumber(0, 1, 2),
				"Adding a 2 beside a 1 should be allowed.");
		assertFalse(solver.trySetNumber(0, 1, 1),
				"Adding a 1 beside a 1 shouldn't be allowed.");
	}
	
	@Test
	void testTrySetNumberExceptions() {
		assertThrows(IllegalArgumentException.class, 
				() -> solver.trySetNumber(0, 1, -1),
				"Negative inputs should throw exception.");
		assertThrows(IllegalArgumentException.class, 
				() -> solver.trySetNumber(0, 1, 0),
				"Input 0 should throw exception.");
		assertThrows(IllegalArgumentException.class, 
				() -> solver.trySetNumber(0, 1, 10),
				"Input larger than 9 should throw exception.");
		
		assertThrows(IllegalArgumentException.class,
				() -> solver.trySetNumber(-1, 0, 5),
				"Negative row number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.trySetNumber(0, -1, 5),
				"Negative col number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.trySetNumber(10, 0, 5),
				"Row number larger than 9 should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.trySetNumber(0, -1, 5),
				"Col number larger than 9 should throw exception.");
	}
	
	
	@Test
	void testSetAndGetNumber() {
		assertEquals(0, solver.getNumber(0, 0),
				"Empty box should contain a 0.");
		solver.setNumber(0, 0, 1);
		assertEquals(1, solver.getNumber(0, 0),
				"Box should contain a 1 after setting it to 1.");
	}
	
	@Test
	void testSetNumberExceptions() {
		assertThrows(IllegalArgumentException.class, 
				() -> solver.setNumber(0, 1, -1),
				"Negative inputs should throw exception.");
		assertThrows(IllegalArgumentException.class, 
				() -> solver.setNumber(0, 1, 0),
				"Input 0 should throw exception.");
		assertThrows(IllegalArgumentException.class, 
				() -> solver.setNumber(0, 1, 10),
				"Input larger than 9 should throw exception.");
		
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumber(-1, 1, 5),
				"Negative row number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumber(1, -1, 5),
				"Negative col number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumber(10, 0, 5),
				"Row number larger than 9 should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumber(0, 10, 5),
				"Col number larger than 9 should throw exception.");
	}
	
	@Test
	void testGetNumberExceptions() {
		assertThrows(IllegalArgumentException.class,
				() -> solver.getNumber(-1, 1),
				"Negative row number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.getNumber(1, -1),
				"Negative col number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.getNumber(10, 1),
				"Row number larger than 9 should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.getNumber(0, 10),
				"Col number larger than 9 should throw exception.");
	}
	
	@Test
	void testClear() {
		solver.setNumber(1, 1, 5);
		solver.setNumber(3, 2, 2);
		solver.clear();
		assertEquals(0, solver.getNumber(1, 1),
				"Box should contain a 0.");
		assertEquals(0, solver.getNumber(3, 2),
				"Box should contain a 0.");
	}
	
	@Test
	void testGetNums() {
		int[][] board;
		
		board = solver.getNumbers();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(0, board[r][c]);
			}
		}
		
		solver.setNumber(0, 0, 1);
		board = solver.getNumbers();
		assertEquals(1, board[0][0]);
	}
	
	@Test
	void testRemoveNum() {
		solver.removeNumber(0, 0);
		assertEquals(0, solver.getNumber(0, 0));
		solver.setNumber(0, 0, 1);
		assertEquals(1, solver.getNumber(0, 0));
		solver.removeNumber(0, 0);
		assertEquals(0, solver.getNumber(0, 0));
	}
	
	@Test
	void testRemoveNumExceptions() {
		assertThrows(IllegalArgumentException.class,
				() -> solver.removeNumber(-1, 1),
				"Negative row number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.removeNumber(1, -1),
				"Negative col number should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.removeNumber(10, 1),
				"Row number larger than 9 should throw exception.");
		assertThrows(IllegalArgumentException.class,
				() -> solver.getNumber(0, 10),
				"Col number larger than 9 should throw exception.");
	}
	
	@Test
	void testSetNums() {
		int[][] emptyBoard = new int[9][9];
		solver.setNumbers(emptyBoard);
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				assertEquals(0, solver.getNumber(r, c));
			}
		}
		
		
		solver.setNumber(0, 0, 5);
		solver.setNumber(3, 3, 5);
		solver.setNumber(6, 6, 5);
		
		int[][] notEmptyBoard = new int[9][9];
		
		notEmptyBoard[0][0] = 1;
		notEmptyBoard[3][3] = 1;
		notEmptyBoard[6][6] = 1;
		
		solver.setNumbers(notEmptyBoard);
		assertEquals(1, solver.getNumber(0, 0),
				"Previous number should be overwritten.");
		assertEquals(1, solver.getNumber(3, 3),
				"Previous number should be overwritten.");
		assertEquals(1, solver.getNumber(6, 6),
				"Previous number should be overwritten.");
		
		solver.clear();
		
		int[][] illegalBoard1 = new int[9][9];
		illegalBoard1[0][0] = -1;
		illegalBoard1[1][1] = 1;
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumbers(illegalBoard1));
		assertEquals(0, solver.getNumber(0, 0),
				"No overwriting at all if one input is below 1.");
		assertEquals(0, solver.getNumber(1, 1),
				"No overwriting at all if one input is below 1.");
		
		solver.clear();
		
		int[][] illegalBoard2 = new int[9][9];
		illegalBoard2[0][0] = 10;
		illegalBoard2[1][1] = 1;
		assertThrows(IllegalArgumentException.class,
				() -> solver.setNumbers(illegalBoard2));
		assertEquals(0, solver.getNumber(0, 0),
				"No overwriting at all if one input is above 9.");
		assertEquals(0, solver.getNumber(1, 1),
				"No overwriting at all if one input is above 9.");
		
	}
	
	@Test
	void testSolveEmpty() {
		assertTrue(solver.solve());
	}
	
	@Test
	void testSolveInvalid() {
		solver.setNumber(0, 0, 1);
		solver.setNumber(0, 1, 1);
		assertFalse(solver.solve());
	}
	
	@Test
	void testSolveUnsolvableAndChangeToSolvable() {
		solver.setNumber(0, 0, 1);
		solver.setNumber(0, 1, 2);
		solver.setNumber(0, 2, 3);
		solver.setNumber(1, 0, 4);
		solver.setNumber(1, 1, 5);
		solver.setNumber(1, 2, 6);
		solver.setNumber(2, 3, 7);
		assertFalse(solver.solve());
		solver.removeNumber(2, 3);
		assertTrue(solver.solve());
	}
	
	@Test
	void testGivenSolvable() {
		int[][] givenGrid = {
				{0, 0, 8, 0, 0, 9, 0, 6, 2},
				{0, 0, 0, 0, 0, 0, 0, 0, 5},
				{1, 0, 2, 5, 0, 0, 0, 0, 0},
				{0, 0, 0, 2, 1, 0, 0, 9, 0},
				{0, 5, 0, 0, 0, 0, 6, 0, 0},
				{6, 0, 0, 0, 0, 0, 0, 2, 8},
				{4, 1, 0, 6, 0, 8, 0, 0, 0},
				{8, 6, 0, 0, 3, 0, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 4, 0, 0},
		};
		
		
		solver.setNumbers(givenGrid);
		assertTrue(solver.solve());
		
		// Test that 3rd and 7th row are correct at least
		int[] thirdRowSolution = {1, 9, 2, 5, 6, 3, 8, 7, 4};
		int[] seventhRowSolution = {4, 1, 5, 6, 9, 8, 2, 3, 7};
		for (int i = 0; i < 9; i++) {
			assertEquals(thirdRowSolution[i], solver.getNumber(2, i));
			assertEquals(seventhRowSolution[i], solver.getNumber(6, i));
		}
		
	}
	
	

}
