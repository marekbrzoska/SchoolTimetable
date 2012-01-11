package timetabling.evolution;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Evolution {
	
	public static Timetable run(int maxIterations, int populationSize, 
			Constraints constraints, int fileNr, double elitism, 
			double lastSlotDenialProbability, double mutationProbability) {
		
		System.out.print("Rozmiar populacji: ");
		System.out.println(populationSize);
		
		System.out.print("Liczba iteracji: ");
		System.out.println(maxIterations);
		
		System.out.print("Nr testu: ");
		System.out.println(fileNr);
		
		System.out.print("Elityzm: ");
		System.out.println(elitism);
		
		System.out.print("Last slot denial probability: ");
		System.out.println(lastSlotDenialProbability);
		
		System.out.print("Prawdopodobieństwo mutacji: ");
		System.out.println(mutationProbability);
		
		Population population = new Population(populationSize, constraints, 
				fileNr, elitism, lastSlotDenialProbability, mutationProbability);
		
		System.out.print("Utworzono początkową populację: ");
		
		population.evaluation();
		population.sort();
		Timetable best = population.first().clone();
		System.out.println("najlepsza wartość to " + best.penalty.toString());
		
		Timetable populationBest;
		
		Integer oldBest;
		
		for (int iteration = 1; iteration < maxIterations; iteration++) {
			population.newGeneration();
			population.evaluation();
			population.sort();
			
			for (Timetable t : population.individuals) {
				System.out.print(t.penalty);
				System.out.print(" ");
			}
			System.out.println();
			
			populationBest = population.first();
			
			//System.out.print(populationBest.compareTo(best));
			
			if (populationBest.penalty.intValue() < best.penalty.intValue()) {
				oldBest = best.penalty;
				best = populationBest.clone();
				System.out.print("  Iteracja " + Integer.toString(iteration));
				System.out.println(": poprawiono wartość z " + oldBest.toString() + " na " + best.penalty.toString());
			}
			
		}
		
		return best;
	}
}
