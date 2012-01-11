package timetabling.helpers;

import java.util.Comparator;
import java.util.Random;

public class RandomComparator<T> implements Comparator<T> {
	
	private Random random;
	
	public RandomComparator() {
		random = new Random();
	}
	
	public int compare(T o1, T o2) {
		return random.nextInt(2) - 1;
	}
}
