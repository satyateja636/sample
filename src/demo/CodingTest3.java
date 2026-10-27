package demo;

import java.util.*;

public class CodingTest3 {

	public static void main(String[] args) {

		List<List<String>> instructions_1 = Arrays.asList(
								Arrays.asList("jasmin", "tulip"),
								Arrays.asList("lily", "tulip"),
								Arrays.asList("tulip", "tulip"),
								Arrays.asList("rose", "rose"),
								Arrays.asList("violet", "rose"),
								Arrays.asList("sunflower", "violet"),
								Arrays.asList("daisy", "violet"),
								Arrays.asList("iris", "violet")
								);
		
		List<String> treasure_rooms_1 = Arrays.asList("lily", "tulip", "violet", "rose");
		
		System.out.println(filterRooms(instructions_1, treasure_rooms_1));
		
		
	}
	
	public static List<String> filterRooms(List<List<String>> instructions, List<String> treasureRooms){
		
		Map<String, String> nextRooms = new HashMap<>();
		
		Map<String, Integer> indegree = new HashMap<>();
		
		List<String> result = new ArrayList<>();
		for(List<String> instruction : instructions) {
			String source = instruction.get(0);
			String destination = instruction.get(1);
			
			nextRooms.put(source, destination);
			
			if(!source.equalsIgnoreCase(destination)) {
				indegree.put(destination, indegree.getOrDefault(destination, 0)+1);
			}
		}
		
		for(String room : nextRooms.keySet()) {
			if(treasureRooms.contains(nextRooms.get(room)) && indegree.getOrDefault(room,0) >= 2) {
				result.add(room);
			}
		}
		
		return result;
	}

}
