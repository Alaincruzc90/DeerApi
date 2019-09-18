package application.core.eventtype.dao.implementation;

import application.core.eventtype.dao.EventTypeDao;
import application.model.EventType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventTypeDaoImplementation implements EventTypeDao {

    // We are gonna use a session-per-request pattern, for each dta access object (dao).
    // In order to use it, we need an session factory, that will provide us with sessions for each request.
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public EventType findById(Long id) {
        return sessionFactory.getCurrentSession().get(EventType.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(EventType eventType) throws Exception {
        sessionFactory.getCurrentSession().persist(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(EventType eventType) throws Exception {
        sessionFactory.getCurrentSession().delete(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(EventType eventType) throws Exception {
        sessionFactory.getCurrentSession().update(eventType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventType> getAllEventTypes(String category) {
        List<EventType> eventTypes = null;
        try {

            // Current session.
            Session session = sessionFactory.getCurrentSession();

            //  Build our query.
            String hql = "from EventType e ";
            if (category != null && !category.equals("")) hql += " where e.category = :category";

            // Create query and set the corresponding parameters.
            Query query = session.createQuery(hql);
            if (category != null && !category.equals("")) query.setParameter("category", category);

            eventTypes = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventTypes;
    }


}
