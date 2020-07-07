package net.tassia.event;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
	private final Map<String, EventHandler<? extends Event>> handlers;

	public EventManager() {
		handlers = new HashMap<>();
	}



	protected <T extends Event> EventHandler<T> getHandler(Class<T> eventClass) {
		return (EventHandler<T>) handlers.get(eventClass.getName());
	}

	protected <T extends Event> void setHandler(Class<T> eventClass, EventHandler<T> handler) {
		handlers.put(eventClass.getName(), handler);
	}



	public <T extends Event> void registerEvent(Class<T> eventClass) {
		setHandler(eventClass, new EventHandler<T>());
	}

	public <T extends Event> void dispatchEvent(Class<T> eventClass, T event) {
		getHandler(eventClass).dispatchEvent(event);
	}

}
