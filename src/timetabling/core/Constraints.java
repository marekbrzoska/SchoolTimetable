package timetabling.core;

import java.util.LinkedList;
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
	
	public final int nrTimeSlots = 45;
	
	@SuppressWarnings("unchecked")
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
		
		this.eventsRequirements = new LinkedList[nrEvents];
		
		for (int i = 0; i < nrEvents; i++) {
			eventsRequirements[i] = new LinkedList<Integer>();
			for (int j = 0; j < nrFeatures; j++) {
				if (eventsFeatures[i][j] == 1) {
					eventsRequirements[i].add(j);
				}
			}
		}
		
	}
	
	public boolean eventFitsInRoom(int eventNr, int roomNr) {
		for (int requirement : eventsRequirements[eventNr]){
			if (roomsFeatures[roomNr][requirement] == 0) {
				return false;
			}
		}
		return true;
	}
}
