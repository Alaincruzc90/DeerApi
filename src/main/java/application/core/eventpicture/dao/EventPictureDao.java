package application.core.eventpicture.dao;

import application.model.EventPicture;

import java.util.List;

public interface EventPictureDao {

    /**
     * Given an id, return it's corresponding event picture.
     *
     * @param id Event's picture id.
     * @return Event picture corresponding to the given id.
     */
    EventPicture findById(Long id);

    /**
     * Given an event picture, persist it.
     *
     * @param eventPicture New event picture.
     * @throws Exception Error persisting.
     */
    void persist(EventPicture eventPicture) throws Exception;

    /**
     * Given an event picture, delete it.
     *
     * @param eventPicture Event picture that will be deleted.
     * @throws Exception Error deleting.
     */
    void delete(EventPicture eventPicture) throws Exception;

    /**
     * Given an event picture, update it.
     *
     * @param eventPicture Event picture that will be updated.
     * @throws Exception Error updating.
     */
    void update(EventPicture eventPicture) throws Exception;

    /**
     * Given an event's id, return all it's pictures.
     *
     * @param eventId Id of the event.
     * @return List of event pictures.
     */
    List<EventPicture> getAllByEventId(Long eventId);

}
