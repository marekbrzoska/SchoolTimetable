import java.io.IOException;


import timetabling.builders.Simple;
import timetabling.core.Constraints;
import timetabling.core.Timetable;
import timetabling.evaluators.ITCExternalEvaluator;
import timetabling.parsers.ITCParser;
import timetabling.parsers.Parser;


public class Test {
	
	public static void main(String[] args) throws IOException {
		
		Parser parser = new ITCParser("solutions/competition01.tim");
		
		Constraints constraints = parser.parse();

		Timetable timetable = Simple.build(constraints);
		
		//timetable.printITCFormatted();
		System.out.println(ITCExternalEvaluator.run(timetable, 1));
	}
}
