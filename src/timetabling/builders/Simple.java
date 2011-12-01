package timetabling.builders;

import java.util.Set;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Simple {
	
	private Simple() {}
	
	@SuppressWarnings("unchecked")
	public static Timetable build(Constraints constraints) {
		boolean[][] roomsEvents = new boolean[constraints.nrRooms][constraints.nrEvents];
		Set<Slot>[] possibleSlots = new Set[constraints.nrEvents];
		
		
		// roomsEvents zostaje wypełnione informacją czy event może się odbyć w roomie
		// dla każdego eventu zapamiętujemy listę slotów gdzie można by go wcisnąć
		for (int roomNr = 0; roomNr < constraints.nrRooms; roomNr++) {
			for (int eventNr = 0; eventNr < constraints.nrEvents; eventNr++) {
				roomsEvents[roomNr][eventNr] = constraints.eventFitsInRoom(eventNr, roomNr);
				if (roomsEvents[roomNr][eventNr]) {
					for (int timeSlotNr = 0; timeSlotNr < constraints.nrTimeSlots; timeSlotNr++) {
						possibleSlots[eventNr].add(new Slot(roomNr, timeSlotNr));
					}
				}
			}
		}
		
		
		
		
		return null;
	}
}
