package timetabling.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Constraints {
	
	public int nrEvents;
	public int nrRooms;
	public int nrFeatures;
	public int nrStudents;	
	
	public int[] roomSizes;
	public int[][] studentsEvents;
	public int[][] roomsFeatures;
	public int[][] eventsFeatures;
	
	public List<Integer>[] eventsRequirements; // tablica list wymagań eventów
	public Set<Integer>[] eventsStudents; // tablica zbiorów studentów na eventach
	
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
		
		// dla każdego eventa policz listę jego wymagań
		for (int e = 0; e < nrEvents; e++) {
			eventsRequirements[e] = new LinkedList<Integer>();
			for (int r = 0; r < nrFeatures; r++) {
				if (eventsFeatures[e][r] == 1) {
					eventsRequirements[e].add(r);
				}
			}
		}
		
		// dla każdego eventa
		eventsStudents = new Set[nrEvents];
		for (int e = 0; e < nrEvents; e++) {
			eventsStudents[e] = new HashSet<Integer>();
			for (int s = 0; s < nrStudents; s++) {
				if (studentsEvents[s][e] == 1) {
					eventsStudents[e].add(s);	// policz zbiór studentów
				}
			}
		}
		
		
		
	}
	
	public boolean eventFitsInRoom(int eventNr, int roomNr) {
		// sprawdź czy wszyscy studenci się zmieszczą
		if (eventsStudents[eventNr].size() > roomSizes[roomNr]) {
			return false;
		}
		
		// sprawdź czy są wszystkie feature'y
		for (int requirement : eventsRequirements[eventNr]){
			if (roomsFeatures[roomNr][requirement] == 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean eventFitsInTimeSlot(int eventNr, int timeSlotNr, Timetable timetable) {
		for (int room = 0; room < timetable.slots.length; room++) {
			if (timetable.slots[room][timeSlotNr] == null || eventNr == timetable.slots[room][timeSlotNr]) {
				continue;
			}
			
			if (haveCommonStudents(eventNr, timetable.slots[room][timeSlotNr])) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean intersectionNonEmpty(Set<Integer> setA, Set<Integer> setB) {
	    for (Integer x : setA)
	      if (setB.contains(x))
	        return true;
	    return false;
	  }
	
	public boolean haveCommonStudents(int e1, int e2) {
		return intersectionNonEmpty(eventsStudents[e1], eventsStudents[e2]);
	}
}
