package timetabling.operators;

import java.util.Random;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Repairer {
	
	public static boolean run (Timetable timetable, Constraints constraints, int eventNr, int roomNr, int timeSlotNr, double lastSlotDenialProbability, Random random) {
		for (int ts: timetable.slots[roomNr]) {
			if (
					ts != timeSlotNr &&
					tryPutting(timetable, constraints, eventNr, roomNr, ts, lastSlotDenialProbability, random)
					) {
				return true;
			}
		}
		
		for (int r = 0; r < constraints.nrRooms; r++) {
			if (r == roomNr || ! constraints.eventFitsInRoom(eventNr, roomNr)) {
				continue;
			}
			
			for (int ts : timetable.slots[r]) {
				if (tryPutting(timetable, constraints, eventNr, r, ts, lastSlotDenialProbability, random)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean tryPutting(Timetable timetable, Constraints constraints, int eventNr, int roomNr, int timeSlotNr, double lastSlotDenialProbability, Random random) {
		if (timetable.slots[roomNr][timeSlotNr] != null) 
			return false;
		
		if (timeSlotNr % 9 == 8 && random.nextDouble() < lastSlotDenialProbability) {
				return false;
		}
		
		for (int curRoomNr = 0; curRoomNr < constraints.nrRooms; curRoomNr++) {
			if (curRoomNr != roomNr && constraints.haveCommonStudents(eventNr, timetable.slots[curRoomNr][timeSlotNr]))
				return false;
		}
		
		timetable.slots[roomNr][timeSlotNr] = eventNr;
		return true;
	}
}
