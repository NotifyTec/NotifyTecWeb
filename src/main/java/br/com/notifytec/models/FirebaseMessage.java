package br.com.notifytec.models;

import java.util.List;

public class FirebaseMessage {
    private String collapse_key;
    private int time_to_live;
    private boolean delay_while_idle;
    private Object data;
    private String to;
    private List<String> registration_ids;
    private FirebaseNotification notification;

    public FirebaseNotification getNotification() {
        return notification;
    }

    public void setNotification(FirebaseNotification notification) {
        this.notification = notification;
    }

    
    
    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public int getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(int time_to_live) {
        this.time_to_live = time_to_live;
    }

    public boolean isDelay_while_idle() {
        return delay_while_idle;
    }

    public void setDelay_while_idle(boolean delay_while_idle) {
        this.delay_while_idle = delay_while_idle;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    
}
