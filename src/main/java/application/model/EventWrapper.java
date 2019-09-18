package application.model;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import java.util.Date;

// Same as an event, but without the sets.
public class EventWrapper {

    private Long eventId;
    private String description;
    private Date eventDate;
    private String localName;
    private String state;
    private Long createdBy;
    private Date dateCreated;
    private Long approvedBy;
    private Date dateApproved;
    private String country;
    private String province;
    private String district;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point point;

    public EventWrapper(Long eventId,
                        String description,
                        Date eventDate,
                        String localName,
                        String state,
                        Long createdBy,
                        Date dateCreated,
                        Long approvedBy,
                        Date dateApproved,
                        Point point,
                        String country,
                        String province,
                        String district) {
        this.eventId = eventId;
        this.description = description;
        this.eventDate = eventDate;
        this.localName = localName;
        this.state = state;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.approvedBy = approvedBy;
        this.dateApproved = dateApproved;
        this.point = point;
        this.country = country;
        this.province = province;
        this.district = district;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
