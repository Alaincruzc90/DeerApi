package application.core.event.service.implementation;

import application.core.event.dao.EventDao;
import application.core.event.service.EventService;
import application.core.eventpicture.service.EventPictureService;
import application.model.Event;
import application.model.EventWrapper;
import application.model.InsertEventWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventServiceImplementation implements EventService {

    @Autowired
    EventDao eventDao;

    @Autowired
    EventPictureService eventPictureService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Event findById(Long id) {
        return this.eventDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(Event event) throws Exception {
        this.eventDao.persist(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Event event) throws Exception {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Event event) throws Exception {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event insertNewEvent(Event event) throws Exception {

        // Set the missing information to our event.

        // TODO change to pending
        event.setState("approve");
        event.setDateCreated(new Date());

        // TODO change real user.
        event.setCreatedBy(1L);

        // Persist all the require event information.
        this.persist(event);
        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEvent(Long id) throws Exception {
        Event event = this.findById(id);
        this.eventPictureService.deleteEventPictures(id);
        this.delete(event);
    }

    @Override
    public List<Event> getEventsInRadius(String category, Integer radius, String lat, String lng) {
        String wellFormPointText = "POINT(" + lng + " " + lat + ")";
        return this.eventDao.getEventsInRadius(category, radius, wellFormPointText);
    }
}
