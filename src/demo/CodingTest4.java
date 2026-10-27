package demo;
import java.util.*;
public class CodingTest4 {

	public static void main(String[] args) {

		char[][] board1 = {
				{'+', '+', '+', '0', '+', '0', '0'},
				{'0', '0', '+', '0', '0', '0', '0'},
				{'0', '0', '0', '0', '+', '0', '0'},
				{'+', '+', '+', '0', '0', '+', '0'},
				{'0', '0', '0', '0', '0', '0', '0'}};
		
		System.out.println(findPossibleLanes(board1));
	}

	public static Map<String, Set<Integer>> findPossibleLanes(char[][] board){
		
		Map<String, Set<Integer>> rowsColumns = new LinkedHashMap<>();
		rowsColumns.put("Rows: ", new HashSet<>());
		rowsColumns.put("Columns: ", new HashSet<>());
		for(int i=0; i <board.length;i++) {
			boolean passable = true;
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] == '+') {
					passable = false;
					break;
				}
				
			}
			if(passable) {
				rowsColumns.get("Rows: ").add(i);
			}
		}
		
		for(int i=0; i <board[0].length;i++) {
			boolean passable = true;
			for(int j = 0; j < board.length; j++) {
				if(board[j][i] == '+') {
					passable = false;
					break;
				}
				
			}
			if(passable) {
				rowsColumns.get("Columns: ").add(i);
			}
		}
		
		return rowsColumns;
		
	}
}
