package com.gino.eventmanagertracker;

public class EventModel {
    int id = 0;
    String eventName = "";
    String eventDate = "";
    String eventLocation = "";
    String eventOrganiser = "";
    String eventType = "";
    String eventStatus = "";

    public EventModel(String eventName, String eventDate, String eventLocation, String eventOrganiser,
                      String eventType, String eventStatus) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventOrganiser = eventOrganiser;
        this.eventType = eventType;
        this.eventStatus = eventStatus;
    }

    public EventModel() {

    }
}