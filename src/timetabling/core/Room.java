package timetabling.core;

import java.util.Set;

import timetabling.core.Feature;


public class Room {
	private int nr;
	private int size;
	private Set<Feature> features;
	
	public Room(int nr, int size, Set<Feature> features){
		this.features = features;
		this.size = size;
		this.nr = nr;
	}

	public int getSize(){
		return this.size;
	}
	
	public int getNr(){
		return this.nr;
	}
	public boolean hasFeature(Feature f){
		return this.features.contains(f);
	}
}
