package demo;
import java.util.*;

public class Demo2 {

	public static void main(String[] args) {
		String[][] enrollment = {
				{"58", "Linear Algebra"},
				{"94", "Art History"},
				{"94", "Operating Systems"},
				{"17", "Software Design"},
				{"58", "Mechanics"},
				{"58", "Economics"},
				{"17", "Linear Algebra"},
				{"17", "Political Science"},
				{"94", "Economics"},
				{"25", "Economics"},
				{"58", "Software Design"},
		};
		
		for(Map.Entry<String, List<String>> entry : findPairs(enrollment).entrySet()) {
			System.out.println(entry.getKey() + " -> "+entry.getValue());
		}
		
		
	}
	
	public static Map<String, List<String>> findPairs(String[][] enrollment){
		
		Map<String, List<String>> result = new HashMap<>();
		Map<String, Set<String>> studentCourses = new HashMap<>();
		for(String[] s: enrollment) {
			
			String studentId = s[0];
			String course = s[1];
			
			if(!studentCourses.containsKey(studentId)) {
				studentCourses.put(studentId, new HashSet<>());
			}
			studentCourses.get(studentId).add(course);
		}
		
		List<String> studentIds = new ArrayList<>(studentCourses.keySet());
		for(int i =0; i< studentIds.size();i++) {
			for(int j = i+1; j< studentIds.size();j++) {
				String s1 = studentIds.get(i);
				String s2 = studentIds.get(j);
				Set<String> shared = new HashSet<>(studentCourses.get(s1));
				shared.retainAll(studentCourses.get(s2));
				
				result.put(s1+","+s2, new ArrayList<>(shared));
			}
		}
		
		return result;
	}
}
