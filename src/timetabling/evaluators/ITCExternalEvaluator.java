package timetabling.evaluators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import timetabling.core.Timetable;

public class ITCExternalEvaluator {
	
	public static int run(Timetable timetable, int fileNr) {
		NumberFormat formatter = new DecimalFormat("00");
		String fileNrString = formatter.format(fileNr);
		String result = null;
		
		timetable.writeITCFormatted(fileNr);
		try {
			Runtime rt = Runtime.getRuntime() ;
			Process p = rt.exec("src/timetabling/evaluators/a.exe solutions/competition" + fileNrString) ;
			InputStream in = p.getInputStream() ;
			//OutputStream out = p.getOutputStream ();
			//InputStream err = p.getErrorStream() ;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			result = br.readLine();
			br.close();
		}
		catch (IOException e) {
			System.out.println("Błąd wykonania a.exe");
			return -1;
		}
		
		try {
			return Integer.parseInt(result);
		}
		catch (NumberFormatException e) {
			return -6;
		}
	}
}
