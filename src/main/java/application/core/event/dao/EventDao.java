package application.core.event.dao;

import application.model.Event;
import application.model.EventWrapper;

import java.util.List;

public interface EventDao {

    /**
     * Given an id, return the corresponding event.
     *
     * @param id Event's id.
     * @return Event.
     */
    Event findById(Long id);

    /**
     * Given an event, persist it.
     *
     * @param event New event.
     * @throws Exception Error persisting.
     */
    void persist(Event event) throws Exception;

    /**
     * Given an event, delete it.
     *
     * @param event Event that will be deleted.
     * @throws Exception Error deleting.
     */
    void delete(Event event) throws Exception;

    /**
     * Given an event, update it.
     *
     * @param event Event that will be updated.
     * @throws Exception Error updating.
     */
    void update(Event event) throws Exception;

    /**
     * Executes a native sql query, that will return all the points in a radius
     * of a given central coordinate.
     *
     * @param category Event category.
     * @param radius Radius from the given point.
     * @param pointText Well form point text that represents a coordinate.
     * @return List of events.
     */
    List<Event> getEventsInRadius(String category, Integer radius, String pointText);

}
