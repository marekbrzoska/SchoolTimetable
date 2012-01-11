package timetabling.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import timetabling.evaluators.ITCExternalEvaluator;


public class Timetable implements Cloneable, Comparable<Timetable> {

	public Integer[][] slots;
	private int fileNr;
	
	public Integer penalty;
	
	public Timetable(int nrRooms, int nrTimeslots, int fileNr) {
		slots = new Integer[nrRooms][nrTimeslots];
		this.fileNr = fileNr;
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
	
	public Timetable clone() {
		Timetable newTimetable = new Timetable(slots.length, slots[0].length, fileNr);
		for (int r = 0; r < slots.length; r++) {
			for (int ts = 0; ts < slots[0].length; ts++) {
				newTimetable.slots[r][ts] = slots[r][ts];
			}
		}
		newTimetable.penalty = this.penalty;
		
		return newTimetable;
	}
	
	@Override
	public int compareTo(Timetable that) {
		if (this.penalty.intValue() > that.penalty.intValue()) {
			return 1;
		} else if (this.penalty.intValue() == that.penalty.intValue()) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public void evaluate() {
		this.penalty = ITCExternalEvaluator.run(this, fileNr);
	}
}
