package timetabling.operators;

import java.util.Random;

import timetabling.core.Timetable;
import timetabling.helpers.TwoTimetables;

public abstract class Crossover {
	
	public TwoTimetables apply(Timetable parent1, Timetable parent2) {
		Timetable child1 = makeChild(parent1, parent2, null);
		Timetable child2 = makeChild(parent2, parent1, null);

		return new TwoTimetables(child1, child2);
	}
	
	abstract protected Timetable makeChild(Timetable parent1, Timetable parent2, Random random);
}
