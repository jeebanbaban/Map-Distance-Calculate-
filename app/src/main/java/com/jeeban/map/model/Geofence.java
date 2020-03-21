package com.jeeban.map.model;

import java.io.Serializable;

public class Geofence implements Serializable {
    private int geofenceId;
    private String latitude;
    private String longitude;
    private String createdAt;
    private String updatedAt;

    public int getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(int geofenceId) {
        this.geofenceId = geofenceId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Location{" +
                "geofenceId=" + geofenceId +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
