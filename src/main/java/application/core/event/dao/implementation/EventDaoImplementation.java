package application.core.event.dao.implementation;

import application.core.event.dao.EventDao;
import application.model.Event;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EventDaoImplementation implements EventDao{

    // We are gonna use a session-per-request pattern, for all dta objects.
    // In order to use it, we need an session factory, that will provide us with sessions for each request.
    @Autowired
    SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public Event findById(Long id) {
        return sessionFactory.getCurrentSession().find(Event.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(Event event) throws Exception {
        sessionFactory.getCurrentSession().save(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Event event) throws Exception {
        sessionFactory.getCurrentSession().delete(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Event event) throws Exception {
        sessionFactory.getCurrentSession().update(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getEventsInRadius(String category, Integer radius, String pointText) {

        List<Object> filteredEvents;
        List<Event> events = null;
        try {

            // First, we want to get all the corresponding ids. Then, we fetch all the events.
            // Check the category. If the usar wants all, then is not needed
            // to filter the events.
            String hql;
            if (category.equals("ALL")) {
                hql = "SELECT event_id from (SELECT e.* ,(ST_Distance_Sphere( ST_GeomFromText(:point), e.point, 6373)) " +
                        "AS distance FROM event e " +
                        "ORDER BY distance) x WHERE x.distance <= :radius";

                NativeQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
                query.setParameter("point", pointText);
                query.setParameter("radius", radius);
                filteredEvents = query.list();

            } else {
                hql = "SELECT event_id from (SELECT e.* ,(ST_Distance_Sphere( ST_GeomFromText(:point), e.point, 6373)) " +
                        "AS distance FROM event e JOIN event_has_type t ON e.event_id = t.event_id " +
                        "JOIN event_type et ON t.type_id = et.type_id where et.category = :category " +
                        "ORDER BY distance) x WHERE x.distance <= :radius";

                NativeQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
                query.setParameter("point", pointText);
                query.setParameter("radius", radius);
                query.setParameter("category", category);
                filteredEvents = query.list();
            }

            // Now, for each event, fetch it's information. We received an array of objects
            // so it is needed to cast the required information.
            List<Long> eventIds = new ArrayList<>();
            for (Object o: filteredEvents) {
                eventIds.add( ((Integer) o).longValue() );
            }

            // Query to fetch all the events with their given information.
            String eventHql = "from Event e " +
                    "where e.eventId in (:eventIds) ";

            // Add the category filter, only if the category is needed.
            Query eventQuery = sessionFactory.getCurrentSession().createQuery(eventHql);
            eventQuery.setParameter("eventIds", eventIds);

            // Fetch the results.
            events = (List<Event>) eventQuery.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
