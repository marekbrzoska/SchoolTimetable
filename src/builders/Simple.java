package builders;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Simple {
	
	private Simple() {}
	
	public static Timetable build(Constraints constraints) {
		boolean[][] roomsEvents = new boolean[constraints.nrRooms][constraints.nrEvents];
		
		for (int i = 0; i < constraints.nrRooms; i++) {
			for (int j = 0; j < constraints.nrEvents; j++) {
				roomsEvents[i][j] = true;
			}
		}
		
		return null;
	}
}
