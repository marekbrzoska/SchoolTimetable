package timetabling.core;


public class Timetable {
	//Event[][][] timetable;
	public Event[][] timetable;
	
	private Room[] rooms;
	int[] lessons = {9,9,9,9,9}; //Lessons' number on Monday, Tuesday, Wednesday, Thursday, Friday
	
	
	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}
	
	public Room[] getRooms() {
		return rooms;
	}
	
	
}
