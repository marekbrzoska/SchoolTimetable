package timetabling.builders;

public class Slot {
	public int roomNr;
	public int timeSlotNr;
	
	public Slot(int roomNr, int timeSlotNr) {
		this.roomNr = roomNr;
		this.timeSlotNr = timeSlotNr;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == this) {
			return true;
		} else if ( ! (that instanceof Slot) ) {
			return false;
		} else if ( this.roomNr == ((Slot) that).roomNr && this.timeSlotNr == ((Slot) that).timeSlotNr) {
			return true;
		}
		return false;
	}
	  @Override
	   public int hashCode() {
	       return 100*roomNr + timeSlotNr;
	   }
}
