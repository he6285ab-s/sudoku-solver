
package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import solver.Solver;
import solver.SudokuSolver;

public class SudokuController {
	
	public SudokuController() {
		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 600, 675));
	}
	
	private void createWindow(String title, int width, int height) {
		
		// --- Set up the window ---
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container masterPane = frame.getContentPane(); // Add elements to this pane
		
		
		SudokuSolver solver = new Solver();
		
		
		// --- Create the Sudoku grid elements ---
		JPanel gamePane = new JPanel();
		GridLayout gridFields = new GridLayout(9, 9, -1, -1);
		gamePane.setLayout(gridFields);
		
		JTextField[][] gameGrid = createGrid(gamePane);
		
		
		// --- Create the bottom interactive pane elements ---
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(width, (height - width)));
		GridLayout buttonLayout = new GridLayout(1, 2, 5, 5);
		bottomPanel.setLayout(buttonLayout);
		
		// Solve button
		JButton solve = new JButton("Solve");
		solve.addActionListener(e -> {
			String addToSolver = addNumsToSolver(solver, gameGrid);
			
			// Check if illegal numbers caused exceptions
			if (!addToSolver.equals("")) {
				JOptionPane.showMessageDialog(frame, 
						"The input at (" + addToSolver
						+ ") is illegal.\nOnly numbers 1-9 are permitted.",
						"Illegal input", 
						JOptionPane.ERROR_MESSAGE);
			
			// Otherwise show the solution, if it exists
			} else {
				
				if(solver.solve()) {
					addNumsToSudokuWindow(solver, gameGrid);
				} else {
					JOptionPane.showMessageDialog(frame, "There is no solution!",
							"Solution not found", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});

		
		// Clear button
		JButton clear = new JButton("Clear");
		clear.addActionListener(e -> {
			solver.clear();
			clearGrid(gameGrid);
		});
		
		
		// --- Add elements and style buttons ---
		bottomPanel.add(clear);
		bottomPanel.add(solve);	
		masterPane.add(gamePane);
		masterPane.add(bottomPanel, BorderLayout.SOUTH);
		
		Font buttonFont = new Font("Calibri", Font.PLAIN, 25);
		solve.setFont(buttonFont);
		clear.setFont(buttonFont);
		
		// --- Set up the frame ---
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	// --- Private auxillary methods ---
	
	// Creates the Sudoku grid and returns it
	private JTextField[][] createGrid(JPanel panel) {
		JTextField[][] grid = new JTextField[9][9];
		Font inputFont = new Font("Calibri", Font.PLAIN, 20);
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				
				JTextField cell = new JTextField();
				cell.setHorizontalAlignment(JTextField.CENTER);
				cell.setFont(inputFont);
				
				// For background styling purposes
				boolean isTopCorner = r < 3 && (c < 3 || 5 < c);		// top left/right
				boolean isBottomCorner = 5 < r && (c < 3 || 5 < c);		// middle
				boolean isMiddle = 2 < r && r < 6 && 2 < c && c < 6;	// bottom left/right
				
				if (isTopCorner || isBottomCorner || isMiddle) {
					cell.setBackground(new Color(255, 60, 0));
				}
				
				grid[r][c] = cell;
				panel.add(cell);
			}
		}
		return grid;
	}
	
	// Clears the grid entirely
	private void clearGrid(JTextField[][] grid) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				grid[r][c].setText("");
			}
		}
	}
	
	// Tries to add numbers from the GUI to the solver
	private String addNumsToSolver(SudokuSolver solver, JTextField[][] grid) {
		
		int[][] newGrid = new int[9][9];
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				
				String inputFromCell = grid[r][c].getText();
				
				if (inputFromCell.equals("")) {
					newGrid[r][c] = 0;

				} else {
					
					try {
						int inputNum = Integer.valueOf(inputFromCell);
						if (inputNum < 1 || inputNum > 9) {
							throw new IllegalArgumentException();
						}
						newGrid[r][c] = inputNum;
					} catch (Exception e) {
						return (r+1) + "," + (c+1);
					}
				}
				
			}
		}
		solver.setNumbers(newGrid);
		return "";
	}
	
	// Reads numbers from the solver and adds them to the GUI
	private void addNumsToSudokuWindow(SudokuSolver solver, JTextField[][] grid) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				grid[r][c].setText("" + solver.getNumber(r, c));
			}
		}
	}
	

}
