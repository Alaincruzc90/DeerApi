package application.core.eventtype.service;

import application.model.EventType;

import java.util.List;

public interface EventTypeService {

    /**
     * Given an id, return it's corresponding event type.
     *
     * @param id Event's id.
     * @return Event type corresponding to the given id.
     */
    EventType findById(Long id);

    /**
     * Given an event type, persist it.
     *
     * @param eventType New event type.
     * @throws Exception Error persisting.
     */
    void persist(EventType eventType) throws Exception;

    /**
     * Given an event type, delete it.
     *
     * @param eventType Event type that will be deleted.
     * @throws Exception Error deleting.
     */
    void delete(EventType eventType) throws Exception;

    /**
     * Given an event type, update it.
     *
     * @param eventType Event type that will be updated.
     * @throws Exception Error updating.
     */
    void update(EventType eventType) throws Exception;

    /**
     * Get all event types. filtered by type.
     *
     * @param category Category to which the event belongs to.
     * @return List of event types corresponding to a category, or all, if this category is null.
     */
    List<EventType> getAllEventTypes(String category);
}
