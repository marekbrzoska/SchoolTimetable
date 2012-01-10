package timetabling.operators;

import java.util.Random;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Mutation {
	
	public static void run(Timetable timetable, Constraints constraints, Random random) {
		for (int i = 0; i < 1000; i++) {
			int r1 = random.nextInt(constraints.nrRooms);
			int r2 = random.nextInt(constraints.nrRooms);
		
			int ts1 = random.nextInt(constraints.nrTimeSlots);
			int ts2 = random.nextInt(constraints.nrTimeSlots);
			
			Integer e1 = timetable.slots[r1][ts1];
			Integer e2 = timetable.slots[r2][ts2];
			
			if (e1 == null || e2 == null) {
				continue;
			}
	
			if (
				constraints.eventFitsInRoom(e1, r2) &&
				constraints.eventFitsInRoom(e2, r1) &&
				constraints.eventFitsInTimeSlot(e1, ts2, timetable) &&
				constraints.eventFitsInTimeSlot(e2, ts1, timetable)
			) {
				timetable.slots[r1][ts1] = e2;
				timetable.slots[r2][ts2] = e1;
				break;
			}
		}
	}
}
