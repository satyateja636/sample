package practise;

public class ArraysDemo {

	public static void main(String[] args) {

		Person p1 = new Person("Tes1", 21);
		System.out.println(p1);

		Person p2 = new Person("Tes2", 22);
		
		changePerson(p1);
		System.out.println(p1);

		
	}
	

	public static void changePerson(Person p) {
		p = new Person("Tesww", 540);
	}
	
	

}
