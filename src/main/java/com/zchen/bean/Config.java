package com.zchen.bean;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */
public class Config {

    private String location;

    private long limit;

    private long freeSpace;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }
}
