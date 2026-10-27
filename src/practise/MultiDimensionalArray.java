package practise;
import java.util.*;
public class MultiDimensionalArray {

	public static void main(String[] args) {

		int[][] numbers = {{1,2,3}, {4,5}};
		
		int[][] numbers1 = numbers.clone();
		
		int[][] numbers2 = new int[numbers.length][];
		
		
		for(int i=0; i<numbers.length; i++) {
			numbers2[i] = new int[numbers[i].length];
			for(int j = 0; j < numbers[i].length; j++) {
				numbers2[i][j] = numbers[i][j];
			}
		}
		
		System.out.println(Arrays.deepToString(numbers2));
	}

}
