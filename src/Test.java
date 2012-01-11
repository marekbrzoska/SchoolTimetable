import java.io.IOException;
import java.util.Random;

import timetabling.core.Constraints;
import timetabling.core.Timetable;
import timetabling.evolution.Evolution;
import timetabling.parsers.ITCParser;
import timetabling.parsers.Parser;


public class Test {
	
	public static void main(String[] args) throws IOException {
		Random random = new Random();
		
		for (int fileNr = 2; fileNr <= 2; fileNr++) {
			Parser parser = new ITCParser("solutions/competition0" + Integer.toString(fileNr) + ".tim");
			Constraints constraints = parser.parse();
		
			Timetable best = Evolution.run(30, 40, constraints, fileNr, 0.05, 0.5, 0.03);
			System.out.println("\n\nWynik końcowy: " + Integer.toString(best.penalty.intValue()));
			System.out.println("\n\n\n\n***************************************\n\n");
		}
	}
}
