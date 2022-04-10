package com.example.mpd_coursework_iainlang;

import java.io.Serializable;
import java.util.Date;

public class EventClass implements Serializable {
    private String title;
    private String description;
    private String link;
    private String publication;
    private String lat;
    private String lon;

    //declare Dates
    public Date setStartDate;
    public Date setEndDate;
    public String startDate;
    public String endDate;

    private String works;
    private String trafficManagement;
    private String diversionInfo;
    private String delayInformation;

    public EventClass(String title, String description, String link, String pubDate, String lat, String lon)
    {
        this.title = title;
        this.description = description;
        this.link = link;
        this.publication = pubDate;
        this.lat = lat;
        this.lon = lon;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    //pubDate
    public String getDate() {
        return "Date Published: " + publication;
    }

    public void setDate(String pubDate) {
        this.publication = pubDate;
    }

    //Latitude
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    //Longitude
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLatLon() {
        return "Latitude is: " + lat + "" + "" + "" + "" + "Longitude is: " + lon;
    }


    public Date getStartDate() {
        return setStartDate;
    }

    public void setSetStartDate(Date setDate) {
        this.setStartDate = setDate;
    }

    public Date getEndDate() {
        return setEndDate;
    }

    public void setEndDate(Date setDate) {
        this.setEndDate = setDate;
    }

    public int calculateDays(Date date1, Date date2)
    {
        if (date1 != null && date2 != null)
        {
            int days = ((int) ((date2.getTime()/(24*60*60*1000))- (int) (date1.getTime()/(24*60*60*1000))));
            return days;
        }
        else
        {
            return 0;
        }
    }

    public int getDurationInDays()
    {
        int days = calculateDays(setStartDate, setEndDate);
        return days;
    }

    public String getWorksAndTrafficDescription()
    {
        return "Current Works: " + works + "\n" + "\n" + "Traffic Management: " + trafficManagement + "\n";
    }

    public String getRoadworksDescription()
    {
        return "Current Works: " + works + "\n" + "\n" + "Traffic Management: " + trafficManagement + "\n" + "\n" + "Diversion Information: " + diversionInfo;
    }

    public String getDelayInformation()
    {
        return "Delay Information " + delayInformation;
    }

    public String getStartEndDate()
    {
        return ("Start date: " + startDate + " " + " " + " " + "End Date: " + endDate
                + "\n" + "Estimated Time of Works: " + getDurationInDays() + " days");
    }

    public String returnCurrentIncidentDescription()
    {
        return title;
    }
}


