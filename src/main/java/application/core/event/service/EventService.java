package application.core.event.service;

import application.model.Event;
import application.model.EventWrapper;
import application.model.InsertEventWrapper;

import java.util.List;

public interface EventService {

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
     * Given a new event, insert it into our database.
     *
     * @param event Event that will be added.
     * @return Newly added event.
     * @throws Exception Error inserting.
     */
    Event insertNewEvent(Event event) throws Exception;

    /**
     * Given an entry id delete all it's corresponding information.
     *
     * @param id Entry's id.
     * @throws Exception Error deleting any of the event's information.
     */
    void deleteEvent(Long id) throws Exception;

    /**
     * Executes a native sql query, that will return all the points in a radius
     * of a given central coordinate.
     *
     * @param category Event category.
     * @param radius Radius from the given point.
     * @param lat Latitude of the central point.
     * @param lng Longitude of the central point.
     * @return List of events.
     */
    List<Event> getEventsInRadius(String category, Integer radius, String lat, String lng);
}
