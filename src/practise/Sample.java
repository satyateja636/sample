package practise;
import java.util.Scanner;

public class Sample {

	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		
		System.out.println("Enter the number: ");
		int num = sc.nextInt();
		System.out.println("Enter the name: ");
		String name = sc.next();
		
		System.out.println("name : "+name+"\n"+" age : "+ num);
		
		
		// we can also use this
		sc.nextLine();
		System.out.println("Enter the number: ");
		int a1 = Integer.parseInt(sc.nextLine());
		System.out.println(a1);
	}
}
