package com.jeeban.map.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DistanceMatrix implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses = null;
    @SerializedName("origin_addresses")
    @Expose
    private List<String> originAddresses = null;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DistanceResponse{" +
                "destinationAddresses=" + destinationAddresses +
                ", originAddresses=" + originAddresses +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}
