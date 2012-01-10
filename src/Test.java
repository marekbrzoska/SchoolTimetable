import java.io.IOException;
import java.util.Random;


import timetabling.builders.Simple;
import timetabling.core.Constraints;
import timetabling.core.Timetable;
import timetabling.evaluators.ITCExternalEvaluator;
import timetabling.operators.Mutation;
import timetabling.parsers.ITCParser;
import timetabling.parsers.Parser;


public class Test {
	
	public static void main(String[] args) throws IOException {
		Random random = new Random();
		
		Parser parser = new ITCParser("solutions/competition01.tim");
		
		Constraints constraints = parser.parse();

		Timetable timetable = Simple.build(constraints);
		
		System.out.println(ITCExternalEvaluator.run(timetable, 1));
		
		Mutation.run(timetable, constraints, random);
		
		System.out.println(ITCExternalEvaluator.run(timetable, 1));
		
		//timetable.printITCFormatted();
		//System.out.println(ITCExternalEvaluator.run(timetable, 1));
	}
}
