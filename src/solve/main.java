package solve;

public class main {

	public static void main(String[] args) {
		
		sudoku game = new sudoku(9);
		game.createPuzzle();
//		game.sudoku_solve(arr, 0, 0);
		game.print();

	}

}
