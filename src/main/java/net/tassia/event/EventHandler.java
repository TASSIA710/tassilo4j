package net.tassia.event;

import java.util.ArrayList;
import java.util.Collection;

public class EventHandler<T extends Event> {
	private final Collection<EventListener<T>> listeners;

	public EventHandler() {
		this.listeners = new ArrayList<>();
	}



	public void addListener(EventListener<T> listener) {
		listeners.add(listener);
	}

	public void removeListener(EventListener<T> listener) {
		listeners.remove(listener);
	}

	public void removeListeners() {
		listeners.clear();
	}



	protected void dispatchEvent(T event) {
		for (EventListener<T> listener : listeners) {
			listener.onEvent(event);
		}
	}

}
