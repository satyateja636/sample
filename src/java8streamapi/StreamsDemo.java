package java8streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import practise.Employee;
import practise.Person;

public class StreamsDemo {

	public static void main(String[] args) {
		
		//Streams with collection example
		List<String> names = Arrays.asList("Alice", "Bob");
		names.stream().map(s -> s.toUpperCase()).forEach(System.out::println);
		
		//Streams with arrays example
		String[] namesArr = {"Java", "C++", "Python","GoLang"};
		Arrays.stream(namesArr).filter( s-> s.length() > 3).forEach(System.out::println);
		
		//reduce example(reduce is a terminal operation)
		List<Integer> nums = Arrays.asList(1,2,3,4,5,6);
		int reduceSum = nums.stream().reduce(0,(a,b) -> a+b);
		int reduceMultiply = nums.stream().reduce(1, (a,b) -> a*b);
		System.out.println(reduceSum);
		System.out.println(reduceMultiply);
		
		//flatMap - to handle nested Lists
		List<List<Integer>> listOfLists = Arrays.asList(
				Arrays.asList(1,2,3),
				Arrays.asList(4,5,6),
				Arrays.asList(7,8,9)
				);
		
		int finalResult = listOfLists.stream()
				.flatMap(list -> list.stream())
				.reduce(0, (a, b) -> a+b);
		System.out.println(finalResult);
		
		//peek - to debug the elements
		nums.stream().peek(System.out::println).collect(Collectors.toList());
		
		//distinct - gives unique elements
		List<Integer> nums1 = Arrays.asList(1,2,3,4,5,6,5,3,2,7,2,4);
		nums1.stream().distinct().forEach(System.out::println);
		
		//sorted
		List<Integer> nums2 = Arrays.asList(12,200,32,4,5,6);
		//sorting in asc
		nums2.stream().sorted().forEach(System.out::println);
		//sorting in desc
		nums2.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
		
		List<Person> persons = Arrays.asList(
				new Person("TT", 27),
				new Person("SS", 32),
				new Person("PP", 22),
				new Person("LN", 54),
				new Person("MM", 45)
				);
		//in asc
		persons.stream()
		.sorted(Comparator.comparingInt(s -> s.getAge()))
		.forEach(System.out::println);
		
		//in desc
		persons.stream()
		.sorted(Comparator.comparingInt((Person p) -> p.getAge()).reversed())
		.forEach(System.out::println);
		
		
		//skip
		nums.stream().skip(2).forEach(System.out::println);
		
		//limit
		nums.stream().limit(3).forEach(System.out::println);
		
		//groupingBy
		Map<Integer, List<Person>> map = persons.stream().collect(Collectors.groupingBy(p -> p.getAge()));
		map.forEach((age, person) -> {System.out.println("Age : "+age);
				System.out.println(" Persons : "+person);});
		System.out.println(map);
		
		//partitioningBy
		Map<Boolean, List<Person>> map1 = persons.stream().collect(Collectors.partitioningBy( p -> p.getAge() > 32));
		System.out.println(map1);
		
		//min & max
		Optional<Person> minPerson = persons.stream().min(Comparator.comparingInt(p -> p.getAge()));
		Optional<Person> maxPerson = persons.stream().max(Comparator.comparingInt(p -> p.getAge()));
		System.out.println("minPerson : "+minPerson);
		System.out.println("maxPerson : "+maxPerson);
		
		//findFirst
		Optional<Person> firstAgedPerson = persons.stream().sorted(Comparator.comparingInt((Person p) -> p.getAge()).reversed()).findFirst();
		System.out.println(firstAgedPerson);
		
		//anyMatch
		boolean anyMatch = nums.stream().anyMatch(n -> n %2 == 0);
		System.out.println("anyMatch : "+anyMatch);
		
		//allMatch
		boolean allMatch = Arrays.asList(2,4,66,78).stream().allMatch(n -> n % 2 ==0);
		System.out.println("allMatch : "+allMatch);
		
		//count
		long count = nums.stream().filter(n -> n %2 == 0).count();
		System.out.println("count : "+count);
		
		//sum
		int sum = nums.stream().mapToInt(Integer :: intValue).sum();
		System.out.println("Sum : "+sum);
		
		//average of all elements
		double avg = nums.stream().mapToInt(Integer :: intValue).average().orElse(0.0);
		System.out.println("avd : "+avg);
		
		//joining all the elements
		String namesJoined = names.stream().collect(Collectors.joining());
		System.out.println(namesJoined);
		//if want commma-Seperated value
		String commaSeperated = names.stream().collect(Collectors.joining(",","[","]"));
		System.out.println(commaSeperated);
		
		
		//Interview question make the list firstly it should contains  0's and sort rem elements
		List<Integer> ls = Arrays.asList(2,7,0,-2, 0, 0, 0,9);
		List<Integer> zeros = ls.stream().filter(n -> n == 0).collect(Collectors.toList());
		List<Integer> nonZeros = ls.stream().filter(n -> n!=0).sorted().collect(Collectors.toList());
		//merging two lists in to one	
		List<Integer> mergedList = Stream.concat(zeros.stream(), nonZeros.stream()).collect(Collectors.toList());
		System.out.println("mergedList : "+ mergedList);
		
		//count the occurrences of the elements
		Map<String, Long> occurrences = Arrays.asList("Alice", "Bob","Bob", "Cat", "Joey", "Alice", "Bob").stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println("occurrences : "+occurrences);
		
		//averagingDouble
		Map<String, Double> avgDouble = persons.stream()
				.collect(
						Collectors.groupingBy(p -> p.getName(), Collectors.averagingDouble(p -> p.getAge())));
		System.out.println("avgDouble : "+avgDouble);

		
		List<Employee> employees = Arrays.asList(
				new Employee("Alice", "HR", 45000),
				new Employee("Bob", "HR", 25000),
				new Employee("Charles", "IT", 50000),
				new Employee("David", "Finance", 35000),
				new Employee("Einstein", "Finance", 55000),
				new Employee("Frank", "IT", 65000),
				new Employee("Gustavo", "Finance", 75000));
		
		//find the highest paid employee in each department
		Map<String, Optional<Employee>> collect = employees.stream()
				.collect(Collectors.groupingBy(e -> e.getDepartment(), 
						Collectors.maxBy(Comparator.comparingDouble(e -> e.getSalary()))));
		System.out.println(collect);
		
		//find all departments with more than 2 employees
		Map<String, Long> departmentCount= employees.stream().collect(Collectors.groupingBy(e->e.getDepartment(), Collectors.counting()));
		List<String> departments = departmentCount.entrySet().stream().filter(e -> e.getValue() > 2).map(Map.Entry::getKey).collect(Collectors.toList());
		System.out.println(departments);
		
		//find department highest average salary
		Map<String, Double> departmentSalaryAvg = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
		Map.Entry<String, Double> StringDoubleEntry = departmentSalaryAvg.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
		System.out.println("StringDoubleEntry : "+StringDoubleEntry);
		
		//find most repeating character in string
		String sampleString = "banana";
		Map<Character, Long>charCountMap = sampleString.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Map.Entry<Character, Long> letter = charCountMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
		System.out.println("letter : "+letter);
		
		
		//find first non-repeating character in String
		Map<Character, Long> charCount = sampleString.chars().mapToObj( c -> (char)c).collect(
		Collectors.groupingBy(Function.identity(), LinkedHashMap::new,Collectors.counting()));
		Optional<Map.Entry<Character, Long>> first = charCount.entrySet().stream().filter(e -> e.getValue() ==1).findFirst();
		
		//reverse each word in a string
		String str = "Java Streams are powerful";
		String[] strArr = str.split(" ");
		String reversedString = Arrays.stream(strArr).map(word -> new StringBuilder(word).reverse().toString()).collect(Collectors.joining(" "));
		System.out.println(reversedString);
		
		//find highest paid employee in the IT department
		Employee highestPainEmployeeInIT = employees.stream().filter(e -> e.getDepartment().equalsIgnoreCase("IT")).max(Comparator.comparing(e -> e.getSalary())).orElse(null);
		System.out.println("highestPainEmployeeInIT : "+highestPainEmployeeInIT);
		
		//given the list of integers. find the frequency of each element
		List<Integer> numbers = Arrays.asList(1,4,2,7,2,1,1,1,9,0,4,3,0,6,2,9,0,2,0,5,5,5,3,3);
		Map<Integer, Long> frequencyMap = numbers.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println("frquencyMap : "+frequencyMap);
		//want to filter the elemnts that are reqpeated more than 3 times
		Map<Integer, Long> freqfiltered = frequencyMap.entrySet().stream().filter(e -> e.getValue()>3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println("freqfiltered : "+ freqfiltered);
	}

}
