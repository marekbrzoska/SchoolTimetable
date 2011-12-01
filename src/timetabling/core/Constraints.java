package timetabling.core;

import java.util.List;

public class Constraints {
	
	public int nrEvents;
	public int nrRooms;
	public int nrFeatures;
	public int nrStudents;	
	
	public int[] roomSizes;
	public int[][] studentsEvents;
	public int[][] roomsFeatures;
	public int[][] eventsFeatures;
	
	public List<Integer>[] eventsRequirements; 
	
	public Constraints(int nrEvents, int nrRooms, int nrFeatures, int nrStudents, 
			int[] roomSizes, int[][] studentsEvents, int[][] roomsFeatures, int[][] eventsFeatures) {
		
		this.nrEvents = nrEvents;
		this.nrRooms = nrRooms;
		this.nrFeatures = nrFeatures;
		this.nrStudents = nrStudents;
		this.roomSizes = roomSizes;
		this.studentsEvents = studentsEvents;
		this.roomsFeatures = roomsFeatures;
		this.eventsFeatures = eventsFeatures;
	}
	
	public boolean eventFitsInRoom(int eventNr, int roomNr) {
		for (int i = 0; i < nrFeatures; i++) {
			
		}
	}
}
