package solve;

import java.util.Random;

public class Puzzle {
	static Random ran = new Random();
	
	static int[][] getQuiz() {
		int i = ran.nextInt(4) + 1; // 1, 2, 3, 4
		
		switch(i) {
		case 1:
			int[][]arr1 = {{3, 1, 6, 5, 7, 8, 4, 9, 2}, 
			         {5, 2, 9, 1, 3, 4, 7, 6, 8}, 
			         {4, 8, 7, 6, 2, 9, 5, 3, 1}, 
			         {2, 6, 3, 4, 1, 5, 9, 8, 7}, 
			         {9, 7, 4, 8, 6, 3, 1, 2, 5}, 
			         {8, 5, 1, 7, 9, 2, 6, 4, 3}, 
			         {1, 3, 8, 9, 4, 7, 2, 5, 6}, 
			         {6, 9, 2, 3, 5, 1, 8, 7, 4}, 
			         {7, 4, 5, 2, 8, 6, 3, 1, 9}};
			return arr1;
		case 2:
			int[][]arr2 = {{5, 3, 4, 6, 7, 8, 9, 1, 2}, 
			         {6, 7, 2, 1, 9, 5, 3, 4, 8}, 
			         {1, 9, 8, 3, 4, 2, 5, 6, 7}, 
			         {8, 5, 9, 7, 6, 1, 4, 2, 3},
			         {4, 2, 6, 8, 5, 3, 7, 9, 1}, 
			         {7, 1, 3, 9, 2, 4, 8, 5, 6}, 
			         {9, 6, 1, 5, 3, 7, 2, 8, 4}, 
			         {2, 8, 7, 4, 1, 9, 6, 3, 5}, 
			         {3, 4, 5, 2, 8, 6, 1, 7, 9}};
			return arr2;
		case 3:
			int[][]arr3 = {{6, 5, 1, 8, 7, 3, 2, 9, 4}, 
				    {7, 4, 3, 2, 5, 9, 1, 6, 8}, 
				    {9, 8, 2, 1, 6, 4, 3, 5, 7}, 
				    {1, 2, 5, 4, 3, 6, 8, 7, 9}, 
				    {4, 3, 9, 5, 8, 7, 6, 1, 2}, 
				    {8, 6, 7, 9, 1, 2, 5, 4, 3}, 
				    {5, 7, 8, 3, 9, 1, 4, 2, 6}, 
				    {2, 1, 6, 7, 4, 8, 9, 3, 5}, 
				    {3, 9, 4, 6, 2, 5, 7, 8, 1}};
			return arr3;
		case 4:
			int[][]arr4 = {{8, 1, 2, 7, 5, 3, 6, 4, 9},
					{9, 4, 3, 6, 8, 2, 1, 7, 5},
					{6, 7, 5, 4, 9, 1, 2, 8, 3},
					{1, 5, 4, 2, 3, 7, 8, 9, 6},
					{3, 6, 9, 8, 4, 5, 7, 2, 1},
					{2, 8, 7, 1, 6, 9, 5, 3, 4},
					{5, 2, 1, 9, 7, 4, 3, 6, 8},
					{4, 3, 8, 5, 2, 6, 9, 1, 7},
					{7, 9, 6, 3, 1, 8, 4, 5, 2}};
			return arr4;
		default:
			return null;
		}
	}
}