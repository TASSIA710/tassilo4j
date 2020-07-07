package net.tassia.event;

@FunctionalInterface
public interface EventListener<T extends Event> {
	void onEvent(T event);
}
