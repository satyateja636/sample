package demo;

import java.io.*;
import java.util.*;
import java.time.*;
//import javafx.util.Pair;
 
public class DEmo3 {
 
  public static void main(String[] argv) {
 
   String[][] songTimes1 = {
      {"Stairway to Heaven", "8:05"}, {"Immigrant Song", "2:27"},
      {"Rock and Roll", "3:41"}, {"Communication Breakdown", "2:29"},
      {"Good Times Bad Times", "2:48"}, {"Hot Dog", "3:19"},
      {"The Crunge", "3:18"}, {"Achilles Last Stand", "10:26"},
      {"Black Dog", "4:55"}
    };
    String[][] songTimes2 = {
      {"Stairway to Heaven", "8:05"}, {"Immigrant Song", "2:27"},
      {"Rock and Roll", "3:41"}, {"Communication Breakdown", "2:29"},
      {"Good Times Bad Times", "2:48"}, {"Black Dog", "4:55"},
      {"The Crunge", "3:18"}, {"Achilles Last Stand", "10:26"},
      {"The Ocean", "4:31"}, {"Hot Dog", "3:19"}
    };
    String[][] songTimes3 = {
      {"Stairway to Heaven", "8:05"}, {"Immigrant Song", "2:27"},
      {"Rock and Roll", "3:41"}, {"Communication Breakdown", "2:29"},
      {"Hey Hey What Can I Do", "4:00"}, {"Poor Tom", "3:00"},
      {"Black Dog", "4:55"}
    };
    String[][] songTimes4 = {
      {"Hey Hey What Can I Do", "4:00"}, {"Rock and Roll", "3:41"}, 
      {"Communication Breakdown", "2:29"}, {"Going to California", "3:30"}, 
      {"On The Run", "3:50"}, {"The Wrestler", "3:50"},
      {"Black Mountain Side", "2:11"}, {"Brown Eagle", "2:20"}
    };  
    String[][] songTimes5 = {
      {"Celebration Day", "3:30"}, {"Going to California", "3:30"},
      {"Take it easy", "3:30"}
    };
    String[][] songTimes6 = {
      {"Rock and Roll", "3:41"}, {"If I lived here", "3:59"},
      {"Day and night", "5:03"}, {"Tempo song", "1:57"}
    };
 
    System.out.println(findPairs(songTimes5));
  }
  
  
  public static List<List<String>> findPairs(String[][] songTimes){
	 
	  Map<String, Integer> songDurations = getSongDurationMap(songTimes);
	  List<String> songs = new ArrayList<>(songDurations.keySet());
 	  List<List<String>> pairsOfSongs = new ArrayList<>();
	  for(int i = 0; i< songs.size(); i++) {
		  for(int j = i+1; j < songs.size(); j++) {
			  if(songDurations.get(songs.get(i)) + songDurations.get(songs.get(j)) == 420) {
//				  pairOfSongs.add(songs.get(i));
//				  pairOfSongs.add(songs.get(j));
//				  break;
				  pairsOfSongs.add(Arrays.asList(songs.get(i), songs.get(j)));
			  }
		  }
	  }
	  
//	  System.out.println(pairOfSongs);
//	  return new ArrayList<>();
	  return pairsOfSongs;
  }
  
  public static Map<String, Integer> getSongDurationMap(String[][] songTimes){
	  
	  Map<String, Integer> songDurations = new LinkedHashMap<>();
	  
	  for(String[] songTime : songTimes) {
		  String song = songTime[0];
		  int duration = toSeconds(songTime[1]);
		  
		  songDurations.put(song, duration);
	  }
	  return songDurations;
  }
  
  public static int toSeconds(String duration) {
	  String[] parts = duration.split(":");
	  int minutes = Integer.parseInt(parts[0]);
	  int seconds = Integer.parseInt(parts[1]);
	  int totalSeconds = minutes * 60 + seconds;
	  return totalSeconds;
	  
  }
 
 
  
}
