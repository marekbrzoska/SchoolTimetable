package timetabling.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


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
	
	public void writeITCFormatted(int fileNr) {
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
		
		NumberFormat formatter = new DecimalFormat("00");
		String fileNrString = formatter.format(fileNr);
		String path = "solutions/competition" + fileNrString + ".sln";
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			while( timeslots[e] != null ) {
				out.write(timeslots[e].toString());
				out.write(' ');
				out.write(rooms[e].toString());
				out.write("\n");
				e++;
			}
		  
		    out.close();
		} catch (IOException exception) {
			System.out.println("Nie udalo sie zapisac do pliku: " + path);
		}
	}
}
