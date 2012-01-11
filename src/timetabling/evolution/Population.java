package timetabling.evolution;

import java.util.Arrays;
import java.util.Random;

import timetabling.builders.Simple;
import timetabling.core.Constraints;
import timetabling.core.Timetable;
import timetabling.helpers.RandomComparator;
import timetabling.helpers.TwoTimetables;
import timetabling.operators.Mutation;
import timetabling.operators.SectorCrossover;

public class Population {
	
	public Timetable[] individuals;
	private int fileNr;
	private int eliteSize;
	private Constraints constraints;
	private double lastSlotDenialProbability;
	private double mutationProbability;
	private Random random;
	private RandomComparator<Timetable> randomComparator;
	
	public Population(int populationSize, Constraints constraints, int fileNr, double elitism, double lastSlotDenialProbability, double mutationProbability) {
		this.random = new Random();
		this.constraints = constraints;
		this.lastSlotDenialProbability = lastSlotDenialProbability;
		this.mutationProbability = mutationProbability;
		this.fileNr = fileNr;
		
		this.individuals = new Timetable[populationSize];
		
		this.randomComparator = new RandomComparator<Timetable>();
		
		eliteSize = (int)(elitism * populationSize);
		if (eliteSize % 2 == 1) {
			eliteSize--;
		}
		if (eliteSize == 0) {
			System.out.println("Brak elity");
		}
		
		Simple simple = new Simple(fileNr);
		
		for (int i = 0; i < populationSize; i++) {
			individuals[i] = simple.build(constraints);
		}
	}
	
	public void evaluation() {
		for (Timetable individual : individuals) {
			individual.evaluate();
		}
	}
	
	public void newGeneration() {
		Timetable[] newIndividuals = new Timetable[individuals.length];
		TwoTimetables children;
		
		for (int i = 0; i < eliteSize; i++) {
			newIndividuals[i] = individuals[i];
		}
		Arrays.sort(individuals, randomComparator);
		
		SectorCrossover operator = new SectorCrossover();
		for (int i = 0; i < individuals.length - eliteSize; i += 2) {
			children = operator.apply(individuals[i], individuals[i+1], constraints, lastSlotDenialProbability, random);
			newIndividuals[i + eliteSize] = children.first;
			newIndividuals[i + eliteSize + 1] = children.second;
		
			//newIndividuals[i + eliteSize] = individuals[i].clone();
			//newIndividuals[i + eliteSize + 1] = individuals[i+1].clone();
		}
		
		this.individuals = newIndividuals;
		
		for (Timetable individual : individuals) {
			if (random.nextDouble() < mutationProbability) {
				Mutation.apply(individual, constraints, random);
			}
		}
	}
	
	public void sort() {
		Arrays.sort(individuals);
	}
	
	public Timetable first() {
		return individuals[0];
	}
}


