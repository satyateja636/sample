package demo;

import java.util.*;

public class CodingTest8 {

}

class Course {
	/* Data about a particular course. */
	public String title; // The name of the obstacle course
	public int obstacleCount; // The number of obstacles in the course

	public Course(String courseTitle, int obstacles) {
		title = courseTitle;
		obstacleCount = obstacles;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Course)) {
			return false;
		}
		Course c = (Course) o;
		return c.title == this.title && c.obstacleCount == this.obstacleCount;
	}

	@Override
	public int hashCode() {
		return (title == null ? 0 : title.hashCode()) * obstacleCount;
	}
}

class Run {
	/* Data and methods about a single run of the obstacle course */
	public Course course; // The Course object this run is for
	public boolean complete; // true if the run is a full run of the course
								// false if the run is in progress or was aborted
	public List<Integer> obstacleTimes; // The times it took to complete each obstacle

	public Run(Course runCourse) {
		course = runCourse;
		complete = false;
		obstacleTimes = new ArrayList<>();
	}

	public void addObstacleTime(int obstacleTime) {
		// When an obstacle is completed, add the time to the current run.
		// Params:
		// obstacleTime: the time in seconds it took to complete the obstacle
		if (complete) {
			throw new IllegalStateException("Cannot add obstacle to complete run");
		}
		obstacleTimes.add(obstacleTime);
		if (obstacleTimes.size() == course.obstacleCount) {
			complete = true;
		}
	}

	public int getRunTime() {
		// Returns the total time this run has taken.
		// If the run is not complete, it returns the time taken so far.
		return obstacleTimes.stream().mapToInt(Integer::intValue).sum();
	}
}

class RunCollection {
	public Course course; // the Course this RunCollection is for
	public List<Run> runs; // the Run objects for this particular course

	public RunCollection(Course collectionCourse) {
		course = collectionCourse;
		runs = new ArrayList<>();
	}

	public int getNumRuns() {
		// Returns the number of runs in this collection
		return runs.size();
	}

	public void addRun(Run run) {
		if (run == null) {
			throw new IllegalArgumentException("Run cannot be null");
		}

		if (!sameCourse(this.course, run.course)) {
			throw new IllegalArgumentException("Run is for different course");
		}
		
		runs.add(run);

	}

	public boolean sameCourse(Course c1, Course c2) {
		if (c1 == c2) {
			return true;
		}

		if (c1 == null || c2 == null) {
			return false;
		}

		return c1.obstacleCount == c2.obstacleCount && Objects.equals(c1.title, c2.title);

	}

}
