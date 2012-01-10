package timetabling.helpers;

import timetabling.core.Timetable;

public class TwoTimetables {
	public Timetable first;
	public Timetable second;
	
	public TwoTimetables(Timetable t1, Timetable t2) {
		first = t1;
		second = t2;
	}
}
