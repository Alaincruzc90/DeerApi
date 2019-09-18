package application.controllers;

import application.core.event.service.EventService;
import application.core.eventpicture.service.EventPictureService;
import application.core.eventtype.service.EventTypeService;
import application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventTypeService eventTypeService;

    @Autowired
    EventService eventService;

    @Autowired
    EventPictureService eventPictureService;

    @GetMapping("")
    @ResponseBody
    public List<Event> test() {
        List<Event> events =
                this.eventService.getEventsInRadius("Amenaza Natural", 10, "9.9406155", "-84.1900355");
        return events;
    }

    /**
     * Given a set of parameters return all events that complies
     * with the given filters.
     *
     * @param category Events' category.
     * @param radius Radius from a given point.
     * @param lat Searched latitude.
     * @param lng Searched longitude.
     * @return List of events.
     */
    @GetMapping("/quick-event-search")
    @ResponseBody
    public List<Event> quickEventSearch(@RequestParam("category") String category,
                                        @RequestParam("radius") Integer radius,
                                        @RequestParam("lat") String lat,
                                        @RequestParam("lng") String lng) {
        return this.eventService.getEventsInRadius(category, radius, lat, lng);
    }

    /**
     * Add a new event and return it after persisted.
     *
     * @param event New event.
     * @return Added event.
     * @throws Exception Error inserting the event.
     */
    @PostMapping(value = "/add-event", consumes = "application/json")
    @ResponseBody
    public Event addEvent(@RequestBody Event event) throws Exception {

        // Check the point information.
        if (event.getPoint() == null) throw new Exception("Las coordenadas están vacías.");

        return this.eventService.insertNewEvent(event);
    }

    /**
     * Given an event's id and a list of images, add those images to the event.
     *
     * @param eventId Event's id.
     * @param images Images corresponding to the event.
     * @return The event will all it's corresponding information.
     * @throws Exception Error adding the images.
     */
    @PostMapping(value = "/add-event-images")
    @ResponseBody
    public Event addEventPictures(@RequestParam("eventId") Long eventId,
                                  @RequestParam("images") List<MultipartFile> images) throws Exception {

        // If there are no images to add, then simple return null.
        if (images == null || images.size() <= 0) return this.eventService.findById(eventId);;

        this.eventPictureService.insertEventPictures(eventId, images);
        return this.eventService.findById(eventId);
    }

    /**
     * Given a category, return a list of event types corresponding to it.
     *
     * @param category The category of the event types.
     * @return List of event types.
     */
    @GetMapping("/event-types")
    @ResponseBody
    public List<EventType> getAllEventTypes(@RequestParam(required = false) String category) {
        return this.eventTypeService.getAllEventTypes(category);
    }

}
