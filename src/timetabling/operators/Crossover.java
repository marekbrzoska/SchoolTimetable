package timetabling.operators;

import java.util.Random;

import timetabling.core.Constraints;
import timetabling.core.Timetable;
import timetabling.helpers.TwoTimetables;

public abstract class Crossover {
	
	public TwoTimetables apply(Timetable parent1, Timetable parent2, Constraints constraints, double lastSlotDenialProbability, Random random) {
		Timetable child1 = makeChild(parent1, parent2, constraints, lastSlotDenialProbability, random);
		Timetable child2 = makeChild(parent2, parent1, constraints, lastSlotDenialProbability, random);

		return new TwoTimetables(child1, child2);
	}
	
	public void tryPutting(Integer eventNr, int roomNr, int timeSlotNr, Timetable timetable, Constraints constraints, double lastSlotDenialProbability, Random random) {
		if (eventNr == timetable.slots[roomNr][timeSlotNr]) {
			return;
		} else if (eventNr == null) {
			Integer oldEventNr = timetable.slots[roomNr][timeSlotNr];
			timetable.slots[roomNr][timeSlotNr] = null;

			if ( ! Repairer.run(timetable, constraints, oldEventNr, roomNr, timeSlotNr, lastSlotDenialProbability, random)) {
				timetable.slots[roomNr][timeSlotNr] = oldEventNr;
			}
			
		} else if (timetable.slots[roomNr][timeSlotNr] == null) {
			if (
					constraints.eventFitsInTimeSlot(eventNr, timeSlotNr, timetable) && 
					constraints.eventFitsInRoom(eventNr, roomNr)
					) {
				outerloop:
				for (int r = 0; r < timetable.slots.length; r++) {
					for (int ts = 0; ts < timetable.slots[0].length; ts++) {
						if (timetable.slots[r][ts] == eventNr) {
							timetable.slots[r][ts] = null;
							break outerloop;
						}
					}
				}
			
				timetable.slots[roomNr][timeSlotNr] = eventNr;
			}
		} else {
			if (
					constraints.eventFitsInTimeSlot(eventNr, timeSlotNr, timetable) && 
					constraints.eventFitsInRoom(eventNr, roomNr)
					) {
				int x = -1, y = -1;
				
				outerloop:
				for (int r = 0; r < timetable.slots.length; r++) {
					for (int ts = 0; ts < timetable.slots[0].length; ts++) {
						if (timetable.slots[r][ts] == eventNr) {
							timetable.slots[r][ts] = null;
							x = r;
							y = ts;
							break outerloop;
						}
					}
				}
			
				int oldEventNr = timetable.slots[roomNr][timeSlotNr];
				timetable.slots[roomNr][timeSlotNr] = eventNr;
				
				if ( ! Repairer.run(timetable, constraints, oldEventNr, roomNr, timeSlotNr, lastSlotDenialProbability, random)) {
					timetable.slots[roomNr][timeSlotNr] = oldEventNr;
					timetable.slots[x][y] = eventNr;
				}
			}
		}
	}
	
	abstract protected Timetable makeChild(Timetable parent1, Timetable parent2, Constraints constraints, double lastSlotDenialProbability, Random random);
}
