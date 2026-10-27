package demo;
import java.util.*;
public class CodingTest5 {

	public static void main(String[] args) {
		
		 int[] endings1 = {6, 15, 21, 30};
		    int[][] choices1_1 = {
		      {3, 7, 8},
		      {9, 4, 2},
		    };
		    int[][] choices1_2 = {
		      {3, 14, 2},
		    };
		    int[][] choices1_3 = {
		      {5, 11, 28},
		      {9, 19, 29},
		      {14, 16, 20},
		      {18, 7, 22},
		      {25, 6, 30},
		    };
		    int[][] choices1_4 = {
		      {2, 10, 15},
		      {3, 4, 10},
		      {4, 3, 15},
		      {10, 3, 15},
		    };

		    int[] endings2 = {11};
		    int[][] choices2_1 = {
		      {2, 3, 4},
		      {5, 10, 2},
		    };
		    int[][] choices2_2 = {};

		    int[] endings3 = {4, 11};
		    int[][] choices3_1 = {
		      {10, 6, 8},
		    };

		    int[] endings4 = {20};
		    int[][] choices4_1 = {
		      {2, 6, 3},
		      {3, 1, 4},
		      {4, 10, 5},
		      {6, 3, 7}
		    };
		    
		    stories(endings1,choices1_1,1);
		    System.out.println(stories(endings1,choices1_3,1));
	}
	
	public static int stories(int[]endings,int[][]choices,int option) {
		Map<Integer, Integer> mapOfOption_1 = new HashMap<>();
		Map<Integer, Integer> mapOfOption_2 = new HashMap<>();
		
		for(int[] choice : choices) {
				int key = choice[0];
				int value1 = choice[1];
				int value2 = choice[2];
				mapOfOption_1.put(key, value1);
				mapOfOption_2.put(key, value2);				
			
		}
		
		int currentPage = 1;
		Set<Integer> endingsSet = new HashSet();
		for(int ending : endings) {
			endingsSet.add(ending);
		}
		Set<Integer> visitedPages = new HashSet<>();
		while(true) {
			if(endingsSet.contains(currentPage))
				return currentPage;
			
			if(visitedPages.contains(currentPage)) {
				return -1;
			}
			
			visitedPages.add(currentPage);
			
			if(option == 1) {
				if(mapOfOption_1.containsKey(currentPage)) {
					currentPage = mapOfOption_1.get(currentPage);
				}else {
					currentPage++;
				}
			}
			
			if(option == 2) {
				if(mapOfOption_2.containsKey(currentPage)) {
					currentPage = mapOfOption_2.get(currentPage);
				}else {
					currentPage++;
				}
			}
		}

	}
}

