package timetabling.operators;

import java.util.Random;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class SectorCrossover extends Crossover {

	@Override
	protected Timetable makeChild(Timetable parent1, Timetable parent2, Constraints constraints, double lastSlotDenialProbability, Random random) {
		Timetable child = parent1.clone();
		
		int room1 = random.nextInt(parent1.slots.length);
		int room2 = random.nextInt(parent1.slots.length);
		
		if (room1 > room2) {
			room2 += room1;
			room1 = room2 - room1;
			room2 = room2 - room1;
		}
		
		int ts1 = random.nextInt(parent1.slots[0].length);
		int ts2 = random.nextInt(parent1.slots[0].length);
		
		if (ts1 > ts2) {
			ts2 += ts1;
			ts1 = ts2 - ts1;
			ts2 = ts2 - ts1;
		}
		
		for (int r = room1; r <= room2; r++) {
			for (int ts = ts1; ts <= ts2; ts++) {
				tryPutting(parent2.slots[r][ts], r, ts, child, constraints, lastSlotDenialProbability, random);
			}
		}
		
		return child;
	}

}
