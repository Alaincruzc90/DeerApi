package application.core.eventpicture.dao.implementation;

import application.core.eventpicture.dao.EventPictureDao;
import application.model.EventPicture;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventPictureDaoImplementation implements EventPictureDao {

    // We are gonna use a session-per-request pattern, for each dta access object (dao).
    // In order to use it, we need an session factory, that will provide us with sessions for each request.
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public EventPicture findById(Long id) {
        return sessionFactory.getCurrentSession().find(EventPicture.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(EventPicture eventPicture) throws Exception {
        sessionFactory.getCurrentSession().persist(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(EventPicture eventPicture) throws Exception {
        sessionFactory.getCurrentSession().delete(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(EventPicture eventPicture) throws Exception {
        sessionFactory.getCurrentSession().update(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventPicture> getAllByEventId(Long eventId) {
        List<EventPicture> eventPictures = null;
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("from EventPicture e where e.eventId = :eventId");
            query.setParameter("eventId", eventId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventPictures;
    }

}
