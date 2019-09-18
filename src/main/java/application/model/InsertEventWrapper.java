package application.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class InsertEventWrapper {

    private Event event;
    private List<MultipartFile> images;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
