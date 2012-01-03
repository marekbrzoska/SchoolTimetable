package timetabling.builders;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import timetabling.core.Constraints;
import timetabling.core.Timetable;

public class Simple {
	
	private Simple() {}
	
	
	public static Timetable build(Constraints constraints) {
		Random random = new Random();
		Slot slot;
		Timetable timetable = new Timetable(constraints.nrRooms, constraints.nrTimeSlots);
		
		boolean[][] roomsEvents = new boolean[constraints.nrRooms][constraints.nrEvents];
		

		PriorityQueue<EventWithPossibleSlots> possibleSlots = 
				preparePossibleTimeSlots(constraints, roomsEvents);

		EventWithPossibleSlots e;
		while(! possibleSlots.isEmpty()){
			e = getOneEvent(possibleSlots, random);
			slot = pickBestSlot(e, possibleSlots, constraints, random);
			timetable.slots[slot.roomNr][slot.timeSlotNr] = e.eventNr;
			possibleSlots = updateQueue(possibleSlots, slot, e.eventNr, constraints);
		}
		
		
			
		
		
		return timetable;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static EventWithPossibleSlots getOneEvent(PriorityQueue<EventWithPossibleSlots> timeSlotsQueue, Random random) {
		// Ta funkcja zakłada, że timeSlotsQueue jest niepusta

		Set<EventWithPossibleSlots> possibleEventsSet = new HashSet();
		int possibleSlotsNr = timeSlotsQueue.peek().possibleSlots.size();
		// Wyjmij z kolejki wszystkie eventy, które można włożyć do tej samej liczby slotów
		while ( timeSlotsQueue.size() > 0 && timeSlotsQueue.peek().possibleSlots.size() == possibleSlotsNr ) {
			possibleEventsSet.add(timeSlotsQueue.poll());
		}
		
		EventWithPossibleSlots eventToReturn;
		// wylosuj numer eventu, spośród wybranych, który będziemy wkładać do Timetable'a
		int randint = random.nextInt(possibleEventsSet.size());
		int i = 0;
		for (EventWithPossibleSlots e : possibleEventsSet) {
			// dla wszyskiech eventów z których wylosowaliśmy jeden
			if (i == randint) {
				// jeśli to ten wylosowany to go zapamiętujemy
				eventToReturn = e;
				possibleEventsSet.remove(e);
				// a pozostałe wrzucamy z powrotem do kolejki
				for (EventWithPossibleSlots e2 : possibleEventsSet) {
					timeSlotsQueue.add(e2);
				}

				return eventToReturn;	
			}
			i++;	
		}
		System.out.println("Błąd: nie udało się wylosować odpowiedniego czegoś");
		return null;
	}

	@SuppressWarnings("unchecked")
	private static PriorityQueue<EventWithPossibleSlots> preparePossibleTimeSlots(Constraints constraints, boolean[][] roomsEvents) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Tworzenie możliwych timeslotów dla wszystkich eventów 
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Set<Slot>[] possibleSlotsArray = new Set[constraints.nrEvents];
		for (int i = 0; i < constraints.nrEvents; i++) {
			possibleSlotsArray[i] = new HashSet<Slot>();
		}
		PriorityQueue<EventWithPossibleSlots> possibleSlotsQueue = new PriorityQueue<EventWithPossibleSlots>();

		// dla każdego eventu
		for (int e = 0; e < constraints.nrEvents; e++) {
			// dla każdego pokoju
			for (int r = 0; r < constraints.nrRooms; r++) {
				// jeżeli event może się odbyć w tym pokoju
				if (constraints.eventFitsInRoom(e, r)) {
					// to dla wszystkich timeslotów w tym pokoju
					for (int t = 0; t < constraints.nrTimeSlots; t++) {
						// dodaj je do potencjalnych slotów dla tego eventa
						possibleSlotsArray[e].add(new Slot(r, t));
					}
				}
			}
		}
		
		// dla każdego eventa
		for (int e = 0; e < constraints.nrEvents; e++) {
			// dorzuć do kolejki event z możliwymi timeslotami
			possibleSlotsQueue.add(new EventWithPossibleSlots(e, possibleSlotsArray[e]));
		}
		
		
		// roomsEvents zostaje wypełnione informacją czy event może się odbyć w roomie
		for (int roomNr = 0; roomNr < constraints.nrRooms; roomNr++) {
			// dla każdego pokoju
			for (int eventNr = 0; eventNr < constraints.nrEvents; eventNr++) {
				// dla każdego eventu
				roomsEvents[roomNr][eventNr] = constraints.eventFitsInRoom(eventNr, roomNr);
				// zapisujemy, czy event może się tam odbyć
				if (roomsEvents[roomNr][eventNr]) {
					// jeżeli może
					for (int timeSlotNr = 0; timeSlotNr < constraints.nrTimeSlots; timeSlotNr++) {
						// to dla wszystkich timeslotów dodajemy je jako możliwe dla tego pokoju
						possibleSlotsArray[eventNr].add(new Slot(roomNr, timeSlotNr));
					}
				}
			}
		}
		
		return possibleSlotsQueue;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Slot pickBestSlot(EventWithPossibleSlots e, PriorityQueue<EventWithPossibleSlots> leftEvents, Constraints constraints, Random random){
		/////////////////////////////////////////////////////////////////////
		// Wybieramy najlepszy slot na event, czyli taki gdzie jest najmniej możliwości
		// włożenia innego eventu i najwięcej slotów o tej samej godzinie
		/////////////////////////////////////////////////////////////////////
		int[][] slots = new int[constraints.nrRooms][constraints.nrTimeSlots];
		//int[] timeslots = new int[constraints.nrTimeSlots];
		Set<Integer>[] timeslots = new Set[constraints.nrTimeSlots];
		for (int i = 0; i < timeslots.length; i++) {
			timeslots[i] = new HashSet<Integer>();
		}
		
		Set<Slot> bestSlots = new HashSet();
		int bestSlotQuality = -999999999; //minus nieskończoność
		
		for (EventWithPossibleSlots ews : leftEvents) {
			// dla każdego jeszcze nie przypisanego eventu
			for (Slot slot : ews.possibleSlots) {
				// dla każdego możliwego slotu tego eventu
				// zwiększamy liczbę eventów możliwych do włożenia do tego slotu
				slots[slot.roomNr][slot.timeSlotNr]++;
				// oraz zwiększamy liczbę eventów pasujących do tego timeslotu
				// UWAGA: to jest odrobinę inaczej niż w oryginalnym algorytmie:
				// tam jest liczona liczba pokoi, do których coś już jest włożone o tej godzinie
				// a my liczymy pokoje, do których mozna coś dać o tej godzinie
				timeslots[slot.timeSlotNr].add(slot.roomNr);
			}
		}
		int slotOccupation;
		int timeslotAvailability;
		int slotQuality;
		for (Slot slot : e.possibleSlots) {
			// dla potencjalnych slotów dla rozważanego eventa
			slotOccupation = slots[slot.roomNr][slot.timeSlotNr];
			timeslotAvailability = timeslots[slot.timeSlotNr].size();
			// liczymy jakość 
			slotQuality = timeslotAvailability - slotOccupation;
			if (slotQuality > bestSlotQuality) {
				// i jeżeli jest lepsza niż najlepsza do tej pory to zapamiętujemy to
				bestSlotQuality = slotQuality;
				bestSlots = new HashSet<Slot>();
				bestSlots.add(slot);
			} else if (slotQuality == bestSlotQuality) {
				bestSlots.add(slot);
			}
			
		}
		
		int rand = random.nextInt(bestSlots.size());
		// losujemy slot do zwrócenia
		return bestSlots.toArray(new Slot[bestSlots.size()])[rand];
	}
	
	private static PriorityQueue<EventWithPossibleSlots> updateQueue(PriorityQueue<EventWithPossibleSlots> queue, Slot slot, int eventNr, Constraints constraints) {
		PriorityQueue<EventWithPossibleSlots> newQueue = new PriorityQueue<EventWithPossibleSlots>();
		
		Set<Slot> newPossibleSlots = null;
		for (EventWithPossibleSlots ews : queue) {
			ews.possibleSlots.remove(slot);
			if (constraints.haveCommonStudents(eventNr, ews.eventNr)) {
				newPossibleSlots = new HashSet<Slot>();
				for (Slot s : ews.possibleSlots) {
					if (s.timeSlotNr != slot.timeSlotNr) {
						newPossibleSlots.add(new Slot(s.roomNr, s.timeSlotNr));
					}
				}
				newQueue.add(new EventWithPossibleSlots(ews.eventNr, newPossibleSlots));
			} else {
				newQueue.add(ews);
			}
		}
		return newQueue;
	}

}
