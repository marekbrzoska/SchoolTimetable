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
		
		Parser parser = new ITCParser("solutions/competition02.tim");
		int fileNr = 2;
		Constraints constraints = parser.parse();
		
		Timetable best = Evolution.run(100, 20, constraints, fileNr, 0.1, 0.8, 0.8);
	}
}
