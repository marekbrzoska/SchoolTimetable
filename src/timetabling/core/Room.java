package timetabling.core;

import java.util.Collection;
import java.util.Set;

import timetabling.core.Feature;


public class Room {
	public final int nr;
	public final int size;
	
	private Set<Feature> features;
	
	public Room(int nr, int size, Set<Feature> features){
		this.features = features;
		this.size = size;
		this.nr = nr;
	}

	public boolean hasFeature(Feature f){
		return this.features.contains(f);
	}
	
	public boolean hasFeatures(Collection<Feature> requiredFeatures){
		return this.features.containsAll(requiredFeatures);
	}
	
	public void addFeature(Feature f){
		this.features.add(f);
	}
}
