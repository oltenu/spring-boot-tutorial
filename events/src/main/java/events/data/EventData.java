package events.data;

import events.model.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventData {
    private static final Map<Integer, Event> events = new HashMap<>();

    public static void add(Event event){
        events.put(event.getId(), event);
    }

    public static Event get(int id){
        return events.get(id);
    }

    public static Collection<Event> getAll(){
        return events.values();
    }

    public static void delete(int id){
        events.remove(id);
    }
}
