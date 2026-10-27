//package demo;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CodingTest2 {
//
//	public static void main(String[] argv) throws IOException {
//		testLogFile();
//		testLogEntry();
//	}
//
//	public static void testLogFile() throws IOException {
//		System.out.println("Running testLogFile");
//		try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_small.log"));) {
//			LogFile logFile = new LogFile(reader);
//			assertEquals(13, logFile.size());
//			for (LogEntry entry : logFile.logEntries) {
//				assert (entry instanceof LogEntry);
//			}
//		}
//	}
//
//	public static void testLogEntry() {
//		System.out.println("Running testLogEntry");
//		String logLine = "44776.619 KTB918 310E MAINROAD";
//		LogEntry logEntry = new LogEntry(logLine);
//		assertEquals(44776.619f, logEntry.getTimestamp(), 0.0001);
//		assertEquals("KTB918", logEntry.getLicensePlate());
//		assertEquals(310, logEntry.getLocation());
//		assertEquals("EAST", logEntry.getDirection());
//		assertEquals("MAINROAD", logEntry.getBoothType());
//		logLine = "52160.132 ABC123 400W ENTRY";
//		logEntry = new LogEntry(logLine);
//		assertEquals(52160.132f, logEntry.getTimestamp(), 0.0001);
//		assertEquals("ABC123", logEntry.getLicensePlate());
//		assertEquals(400, logEntry.getLocation());
//		assertEquals("WEST", logEntry.getDirection());
//		assertEquals("ENTRY", logEntry.getBoothType());
//	}
//
//}
//
//class LogEntry {
//
//	/**
//	 * Represents an entry from a single log line. Log lines look like this in the
//	 * file:
//	 *
//	 * 34400.409 SXY288 210E ENTRY
//	 *
//	 * Where: * 34400.409 is the timestamp in seconds since the software was
//	 * started. * SXY288 is the license plate of the vehicle passing through the
//	 * toll booth. * 210E is the location and traffic direction of the toll booth.
//	 * Here, the toll booth is at 210 kilometers from the start of the tollway, and
//	 * the E indicates that the toll booth was on the east-bound traffic side.
//	 * Tollbooths are placed every ten kilometers. * ENTRY indicates which type of
//	 * toll booth the vehicle went through. This is one of "ENTRY", "EXIT", or
//	 * "MAINROAD".
//	 **/
//
//	private final float timestamp;
//	private final String licensePlate;
//	private final String boothType;
//	private final int location;
//	private final String direction;
//
//	public LogEntry(String logLine) {
//		String[] tokens = logLine.split(" ");
//		this.timestamp = Float.parseFloat(tokens[0]);
//		this.licensePlate = tokens[1];
//		this.boothType = tokens[3];
//		this.location = Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 1));
//		String directionLetter = tokens[2].substring(tokens[2].length() - 1);
//		if (directionLetter.equals("E")) {
//			this.direction = "EAST";
//		} else if (directionLetter.equals("W")) {
//			this.direction = "WEST";
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}
//
//	public float getTimestamp() {
//		return timestamp;
//	}
//
//	public String getLicensePlate() {
//		return licensePlate;
//	}
//
//	public String getBoothType() {
//		return boothType;
//	}
//
//	public int getLocation() {
//		return location;
//	}
//
//	public String getDirection() {
//		return direction;
//	}
//
//	@Override
//	public String toString() {
//		return String.format("<LogEntry timestamp: %f  license: %s  location: %d  direction: %s  booth type: %s>",
//				timestamp, licensePlate, location, direction, boothType);
//	}
//}
//
//class LogFile {
//
//	/*
//	 * Represents a file containing a number of log lines, converted to LogEntry
//	 * objects.
//	 */
//
//	List<LogEntry> logEntries;
//
//	public LogFile(BufferedReader reader) throws IOException {
//		this.logEntries = new ArrayList<>();
//		String line = reader.readLine();
//		while (line != null) {
//			LogEntry logEntry = new LogEntry(line.strip());
//			this.logEntries.add(logEntry);
//			line = reader.readLine();
//		}
//	}
//
//	public LogEntry get(int index) {
//		return this.logEntries.get(index);
//	}
//
//	public int size() {
//		return this.logEntries.size();
//	}
//
//	public int countJourneys() {
//		int count = 0;
//		for(LogEntry logEntry : logEntries) {
//			if(logEntry.getBoothType() == "ENTRY" && logEntry.getBoothType() == "EXIT") {
//				count++;
//			}
//		}
//		
//		return count;
//	}
//}
