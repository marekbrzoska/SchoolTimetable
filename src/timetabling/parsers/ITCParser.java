package timetabling.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import timetabling.core.Constraints;


/**
 * Parser for International Timetabling Competition input format
 * http://www.idsia.ch/Files/ttcomp2002/oldindex.html
 */
public class ITCParser implements Parser{
	
	BufferedReader input;
	
	public ITCParser(String inputFileName){
		try {
			input = new BufferedReader(new FileReader(inputFileName)); 
		} 
		catch (FileNotFoundException e) {
			System.out.println("Nie znaleziono pliku: " + inputFileName);
			input = null;
		}
	}
	
	@Override
	public Constraints parse() throws IOException {
		String strLine;
		int nrEvents;
		int nrRooms;
		int nrFeatures;
		int nrStudents;	
		
		int[] roomSizes;
		int[][] studentsEvents;
		int[][] roomsFeatures;
		int[][] eventsFeatures;

		strLine = input.readLine();
		String[] firstLine = strLine.split(" ");
		nrEvents = Integer.parseInt(firstLine[0]);
		nrRooms = Integer.parseInt(firstLine[1]);
		nrFeatures = Integer.parseInt(firstLine[2]);
		nrStudents = Integer.parseInt(firstLine[3]);
		
		roomSizes = new int[nrRooms];
		roomsFeatures = new int[nrRooms][nrFeatures];
		eventsFeatures = new int[nrEvents][nrFeatures];
		studentsEvents = new int[nrStudents][nrEvents];	
		
		for (int i = 0; i < nrRooms; i++) {
			roomSizes[i] = Integer.parseInt(input.readLine());
		}
		
		for (int i = 0; i < nrStudents; i++) {
			for (int j = 0; j < nrEvents; j++) {
				studentsEvents[i][j] = Integer.parseInt(input.readLine());
			}
		}
		
		for (int i = 0; i < nrRooms; i++) {
			for (int j = 0; j < nrFeatures; j++) {
				roomsFeatures[i][j] = Integer.parseInt(input.readLine());
			}
		}
		
		for (int i = 0; i < nrEvents; i++) {
			for (int j = 0; j < nrFeatures; j++) {
				eventsFeatures[i][j] = Integer.parseInt(input.readLine());
			}
		}
			
		return new Constraints(nrEvents, nrRooms, nrFeatures, nrStudents, roomSizes, studentsEvents, roomsFeatures, eventsFeatures);
	}

}
