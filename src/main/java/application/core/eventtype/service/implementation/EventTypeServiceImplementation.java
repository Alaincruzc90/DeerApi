package application.core.eventtype.service.implementation;

import application.core.eventtype.dao.EventTypeDao;
import application.core.eventtype.service.EventTypeService;
import application.model.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventTypeServiceImplementation implements EventTypeService {

    @Autowired
    EventTypeDao eventTypeDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public EventType findById(Long id) {
        return this.eventTypeDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(EventType eventType) throws Exception {
        this.eventTypeDao.persist(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(EventType eventType) throws Exception {
        this.eventTypeDao.delete(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(EventType eventType) throws Exception {
        this.eventTypeDao.update(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventType> getAllEventTypes(String category) {
        return this.eventTypeDao.getAllEventTypes(category);
    }

}
