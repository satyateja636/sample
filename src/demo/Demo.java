package demo;
import java.util.*;
import java.util.stream.*;
public class Demo {

	public static void main(String[] args) {
		Map<Integer, String> sampleData = new HashMap<>();
		sampleData.put(58, "Linear Algebra");
		sampleData.put(94, "Art History");
		sampleData.put(94, "Operating Systems");
		sampleData.put(17, "Software Design");
		sampleData.put(58, "Mechanics");
		sampleData.put(58, "Economics");
		sampleData.put(17, "Political Science");
		sampleData.put(17, "Linear Algebra");
		sampleData.put(94, "Econimics");
		sampleData.put(25, "Econimics");
		sampleData.put(58, "Software Design");
		
		List<Integer> keys = new ArrayList<>(sampleData.keySet());
		List<List<Integer>> pairs = new ArrayList<>();
		
		for(int i = 0; i<keys.size() - 1; i++) {
			for(int j = i+1; j< keys.size(); j++) {
				List<Integer> l = Arrays.asList(keys.get(i),keys.get(j));
				pairs.add(l);	
			}
		}
		pairs.stream().forEach(System.out::println);

		Map<List<Integer>, List<String>> result = new HashMap<>();
		for(List lst : pairs) {
			List<String> temp = new ArrayList<>();
			temp.add(sampleData.get(lst.get(0)));
			temp.add(sampleData.get(lst.get(1)));
			result.put(lst, temp);
		}
		
		System.out.println(result);

	}
	

}
