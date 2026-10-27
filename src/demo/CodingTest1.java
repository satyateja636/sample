package demo;

import java.util.Arrays;
import java.util.*;
public class CodingTest1 {

	public static void main(String[] args) {

	 List<List<String>>enrollments1 = Arrays.asList(
			 Arrays.asList("58", "Linear Algebra"),
			 Arrays.asList("94", "Art History"),
			 Arrays.asList("94", "Operating Systems"),
			 Arrays.asList("17", "Software Design"),
			 Arrays.asList("58", "Mechanics"),
			 Arrays.asList("58", "Economics"),
			 Arrays.asList("17", "Linear Algebra"),
			 Arrays.asList ("17", "Political Science"),
			 Arrays.asList("94", "Economics"),
			 Arrays.asList("25", "Economics"),
			 Arrays.asList("58", "Software Design")
		              );
	 
	 for(Map.Entry<String, List<String>> entry: findPairsOfSharedCourses(enrollments1).entrySet()) {
		 System.out.println(entry.getKey()+" -> "+entry.getValue());
	 }
	}
	
	public static Map<String, List<String>> findPairsOfSharedCourses(List<List<String>> enrollment){
		Map<String, Set<String>> studentCoursesMap = getStudentCoursesMap(enrollment);
		Map<String, List<String>> SharedCourses = getSharedCourses(studentCoursesMap);
		return SharedCourses;
		
	}
	
	
	public static Map<String, Set<String>> getStudentCoursesMap(List<List<String>> enrollment){
		
		Map<String, Set<String>> studentCourses = new HashMap<>();
		for(List<String> s : enrollment) {
			String studentId = s.get(0);
			String course = s.get(1);
			
			if(!studentCourses.containsKey(studentId)) {
				studentCourses.put(studentId, new HashSet<>());
			}
			studentCourses.get(studentId).add(course);				
		}
		return studentCourses;

	}
	
	public static Map<String, List<String>> getSharedCourses(Map<String, Set<String>> studentCoursesMap){
		
		Map<String, List<String>> sharedCourses = new HashMap<>();
		List<String> studentIds = new ArrayList<>(studentCoursesMap.keySet());
		for(int i =0; i<studentCoursesMap.size(); i++) {
			for(int j = i+1; j < studentCoursesMap.size(); j++) {
				String student1 = studentIds.get(i);
				String student2 = studentIds.get(j);
				
				Set<String> shared = new HashSet<>(studentCoursesMap.get(student1));
				shared.retainAll(studentCoursesMap.get(student2));
				
				sharedCourses.put(student1+","+student2, new ArrayList<>(shared));
			}
		}
		
		return sharedCourses;
	}

}
