package application.core.eventpicture.service;

import application.model.Event;
import application.model.EventPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventPictureService {

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

    /**
     * Given an event's id and it's pictures, add a new event picture entry
     * and resize the picture and save it into our data base.
     *
     * @param eventId Event's id.
     * @param images List of images.
     * @throws Exception Error inserting or writing the pictures in our destination.
     */
    void insertEventPictures(Long eventId, List<MultipartFile> images) throws Exception;

    /**
     * Given an entry's id, delete all it's pictures.
     *
     * @param eventId Event's id.
     * @throws Exception Error with any of the IO operations.
     */
    void deleteEventPictures(Long eventId) throws Exception;
}
