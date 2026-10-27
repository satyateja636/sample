package practise;

import java.util.Arrays;
import java.util.Scanner;

public class DeclaringAndIntializingArrays {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// 1. if we want to initialize arrays at run-time
		int[] nums = new int[5];
		for (int i = 0; i < nums.length; i++) {
			System.out.println("Enter the num to store in : "+nums[i]);
			nums[i] = Integer.parseInt(sc.nextLine());
		}
		System.out.println(Arrays.toString(nums));
		
		// 2. if we want to initialize arrays at the time of declaration
		int[] nums1 = new int[] { 2, 4, 6, 8, 9, 11 };
		
		

	}

}
