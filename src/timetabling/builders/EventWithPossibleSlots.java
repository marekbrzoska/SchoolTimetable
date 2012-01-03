package timetabling.builders;

import java.util.Set;


import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class EventWithPossibleSlots implements Comparable<EventWithPossibleSlots> {
	final int eventNr;
	public Set<Slot> possibleSlots;
	
	public EventWithPossibleSlots(int eventNr, Set<Slot> possibleSlots) {
		this.eventNr = eventNr;
		this.possibleSlots = possibleSlots;
	}
	
	public boolean canAssign(Slot slot, Timetable timetable, Constraints constraints) {
		
		Integer e;
		// dla każdego pokoju o zadanej godzinie
		for (int room = 0; room < constraints.nrRooms; room++) {
			// zobacz jaki tam jest event (e)
			e = timetable.slots[room][slot.timeSlotNr];
			// jeśli jest tam jakiś event (e)
			if (e != null) {
				// to dla każdego naszego studenta na evencie (eventNr)
				for (int s : constraints.eventsStudents[eventNr]) {
					// sprawdź czy ten student nie idzie na rozważany event (e)
					if (constraints.eventsStudents[e].contains(s)) {
						// jeśli idzie to nie może o tej samej godzinie iść gdzie indziej :P
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public int compareTo(EventWithPossibleSlots e) {
		if (this.possibleSlots.size() < e.possibleSlots.size()) {
			return -1;
		} else if (this.possibleSlots.size() == e.possibleSlots.size()) {
			return 0;
		} else {
			return 1;
		}
	}
	
}
