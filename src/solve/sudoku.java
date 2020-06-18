package solve;

import java.util.Random;

public class sudoku {
	int n;
	int[][] grid;
	int[][] soluGrid;
	Random ran = new Random();
//	static int count;
	
	public sudoku(int size) {
		n = size;
		grid = new int[n][n];
		soluGrid = new int[n][n];
	}
	
	public void createPuzzle() {
		setSolutionGrid(Puzzle.getQuiz());

		int process = ran.nextInt(4)+1;
		
		factory(process);
		
		int bonusDif = ran.nextInt(5);
		int count = 47 + bonusDif;
		setGrid(getSolution());

		while(count > 0) {
			int col = ran.nextInt(9);
			int row = ran.nextInt(9);
			
			if(grid[row][col] != 0) {
				grid[row][col] = 0;
				count--;
			}
		}
	}
	
	// Change the seed puzzle
	private void factory(int pr) {
		while(pr > 0) {
			int choice = ran.nextInt(3); // choice = [0, 1, 2]
			int[][] puz = getSolution();
			
			if(choice == 0) {
				int pick = ran.nextInt(3); // pick = [0, 1, 2]
								
				if(pick == 0) {		// Rotate 90 degree to left
					for(int i = 0; i < n; i++)
						for(int j = 0; j < n; j++)
							soluGrid[j][i] = puz[i][j];
				}
				else if(pick == 1) {		// Rotate 90 degree to right
					for(int i = 0; i < n; i++)
						for(int j = 0; j < n; j++)
							soluGrid[j][n-1-i] = puz[i][j];
				}
				else {		// Rotate 180 degree
					for(int i = 0; i < n; i++)
						for(int j = 0; j < n; j++)
							soluGrid[n-1-i][n-1-j] = puz[i][j];
				}
			}
			
			if(choice == 1) {
				int pick = ran.nextInt(2); // pick = [0, 1]
								
				if(pick == 0) {		// Reverse horizontal
					for(int i = 0; i < n; i++)
						for(int j = 0; j < n; j++)
							soluGrid[i][n-1-j] = puz[i][j];
				}
				else {		// Reverse vertical
					for(int i = 0; i < n; i++)
						for(int j = 0; j < n; j++)
							soluGrid[n-1-i][j] = puz[i][j];
				}
			}
			
			if(choice == 2) {
				int pick = ran.nextInt(4); // pick = [0, 1, 2, 3]
				
				if(pick == 0) {		// Change columns
					for(int k = 0; k < 3; k++) {	// Divide columns in 3 units: 123|456|789
						int colToChange1 = ran.nextInt(3) + k*3;
						int colToChange2 = ran.nextInt(3) + k*3;
						
						for(int i = 0; i < n; i++) {
							int tmp = soluGrid[i][colToChange2];
							soluGrid[i][colToChange2] = soluGrid[i][colToChange1];
							soluGrid[i][colToChange1] = tmp;
						}
					}
				}
				else if(pick == 1) {		// Change rows
					for(int k = 0; k < 3; k++) {	// Divide rows in 3 units
						int rowToChange1 = ran.nextInt(3) + k*3;
						int rowToChange2 = ran.nextInt(3) + k*3;
						
						for(int i = 0; i < n; i++) {
							int tmp = soluGrid[rowToChange2][i];
							soluGrid[rowToChange2][i] = soluGrid[rowToChange1][i];
							soluGrid[rowToChange1][i] = tmp;
						}
					}
				}
				else if(pick == 2) {		// Change units horizontal
					int unitToChange1 = ran.nextInt(3)*3;
					int unitToChange2;
					do {
						unitToChange2 = ran.nextInt(3)*3;
					} while(unitToChange2 == unitToChange1);
					
					for(int k = 0; k < 3; k++) {
						for(int i = 0; i < n; i++) {
							int tmp = soluGrid[i][unitToChange2];
							soluGrid[i][unitToChange2] = soluGrid[i][unitToChange1];
							soluGrid[i][unitToChange1] = tmp;
						}
						
						unitToChange1++;
						unitToChange2++;
					}
				}
				else {		// Change units vertical
					int unitToChange1 = ran.nextInt(3)*3;
					int unitToChange2;
					do {
						unitToChange2 = ran.nextInt(3)*3;
					} while(unitToChange2 == unitToChange1);
					
					for(int k = 0; k < 3; k++) {
						for(int i = 0; i < n; i++) {
							int tmp = soluGrid[unitToChange2][i];
							soluGrid[unitToChange2][i] = soluGrid[unitToChange1][i];
							soluGrid[unitToChange1][i] = tmp;
						}
						
						unitToChange1++;
						unitToChange2++;
					}
				}
			}
			
			pr--;
		}
	}

	public void print() {
		System.out.println("Grid: ");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++)
				System.out.print(grid[i][j] + "  ");
			System.out.println();
		}

		System.out.println("Solution: ");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++)
				System.out.print(soluGrid[i][j] + "  ");
			System.out.println();
		}
	}
	
	private boolean isValid(int[][] grid, int x, int i, int j) {
		for(int row = 0; row < n; row++)
			if(grid[row][j] == x || grid[i][row] == x)
				return false;
		
		int a = i/3, b = j/3;
	    for(int k = 3*a; k < 3*a+3; k++){
	        for(int q = 3*b; q < 3*b+3; q++){
	            if(grid[k][q] == x)
					return false;
	        }
	    }
		
		return true;
	}
	
	public void sudoku_solve(int[][] grid, int i, int j) {
		if(j == 9) {
			if(i == 8) {
//				count++;
				setSolutionGrid(getGrid());
				return;
			}
			else
				sudoku_solve(grid, i+1, 0);
		}
		else if(grid[i][j] != 0) {
			sudoku_solve(grid, i, j+1);
		}
		else {
			for(int x = 1; x < 10; x++)
				if(isValid(grid, x, i, j)) {
					grid[i][j] = x;
					sudoku_solve(grid, i, j+1);
					
					grid[i][j] = 0;
				}
		}
	}
	
	public int getN() {
		return n;
	}

	public int[][] getGrid() {
		return grid;
	}
	
	public int[][] getSolution() {
		return soluGrid;
	}

	public void setGrid(int[][] arr) {
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				grid[i][j] = arr[i][j];
	}
	
	public void setSolutionGrid(int[][] arr) {
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				soluGrid[i][j] = arr[i][j];
	}
}
