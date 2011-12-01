package timetabling.core;


import java.util.Set;

import timetabling.core.Feature;

public class Event {
	public final int nr;


	private Set<Feature> requiredFeatures;
	private Set<Student> students;
	
	Event(int nr){
		this.nr = nr;
	}

	public void setRequiredFeatures(Set<Feature> requiredFeatures) {
		this.requiredFeatures = requiredFeatures;
	}

	public Set<Feature> getRequiredFeatures() {
		return requiredFeatures;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<Student> getStudents() {
		return students;
	}
	
	public void addStudent(Student s){
		this.students.add(s);
	}
}
