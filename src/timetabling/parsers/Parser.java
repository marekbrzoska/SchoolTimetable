/**
 * 
 */
package timetabling.parsers;

import java.util.List;

import timetabling.core.Event;
import timetabling.core.Room;
import timetabling.core.Student;
import timetabling.core.Teacher;

/**
 * @author mbr
 *
 */
public interface Parser {
	public boolean parse();
	
	public List<Event>   getEvents();
	public List<Teacher> getTeachers();
	public List<Student> getStudent();
	public List<Room>    getRooms();
		
}
