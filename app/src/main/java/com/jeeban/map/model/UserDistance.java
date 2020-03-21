package com.jeeban.map.model;

import java.io.Serializable;

public class UserDistance implements Serializable {

    private String userId;
    private Integer distanceId;
    private String distance;
    private String createdAt;
    private String updatedAt;

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

    public Integer getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(Integer distanceId) {
        this.distanceId = distanceId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDistance{" +
                "userId='" + userId + '\'' +
                ", distanceId=" + distanceId +
                ", distance='" + distance + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
