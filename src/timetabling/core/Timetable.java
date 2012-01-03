package timetabling.core;


public class Timetable {

	public Integer[][] slots;
	
	public Timetable(int nrRooms, int nrTimeslots) {
		slots = new Integer[nrRooms][nrTimeslots];
	}
	
	public void print() {
		for(Integer[] ii : slots) {
			for (Integer i : ii) {
				System.out.print(i);
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public void printITCFormatted() {
		int maxEvents = slots.length * slots[0].length;
		Integer [] timeslots = new Integer[maxEvents];
		Integer [] rooms = new Integer[maxEvents];
		
		Integer event;
		for (int room = 0; room < slots.length; room++) {
			for (int timeslot = 0; timeslot < slots[room].length; timeslot++) {
				event = slots[room][timeslot];
				if (event != null) {
					timeslots[event] = timeslot;
					rooms[event] = room;
				}
			}
		}
		
		Integer e = 0;
		while( timeslots[e] != null ) {
			System.out.print(timeslots[e]);
			System.out.print(' ');
			System.out.println(rooms[e]);
			e++;
		}
		
	}
}
