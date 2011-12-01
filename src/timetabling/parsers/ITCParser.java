package timetabling.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import timetabling.core.Event;
import timetabling.core.Room;
import timetabling.core.Student;


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
			input = null;
		}
	}
	

	@Override
	public boolean parse() {
		String strLine;
		int nrEvents;
		int nrRooms;
		int nrFeatures;
		int nrStudents;

		try {
			strLine = input.readLine();
			String[] firstLine = strLine.split(" ");
			
			while ((strLine = input.readLine()) != null)   {
				
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Event> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Room> getRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudent() {
		// TODO Auto-generated method stub
		return null;
	}



}
