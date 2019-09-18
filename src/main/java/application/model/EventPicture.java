package application.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_picture")
public class EventPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long pictureId;

    @Column(name = "path_small")
    private String pathSmall;

    @Column(name = "path_medium")
    private String pathMedium;

    @Column(name = "path_big")
    private String pathBig;

    @Column(name = "name")
    private String name;

    @Column(name = "event_id")
    private long eventId;

    @Column(name = "date_created")
    private Date dateCreated;

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }


    public String getPathSmall() {
        return pathSmall;
    }

    public void setPathSmall(String pathSmall) {
        this.pathSmall = pathSmall;
    }


    public String getPathMedium() {
        return pathMedium;
    }

    public void setPathMedium(String pathMedium) {
        this.pathMedium = pathMedium;
    }


    public String getPathBig() {
        return pathBig;
    }

    public void setPathBig(String pathBig) {
        this.pathBig = pathBig;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
