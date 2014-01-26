package com.zchen.sdp.bean;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */
public class SDPConfig {

    /**
     * SDP storage location
     */
    private String location;

    /**
     *  The maximum SDP storage space size
     */
    private long limit;

    /**
     *  The free space size of the disk SDP storage location is at
     */
    private long freeSpace;

    /**
     *  The percentage of storage capacity exceeding warning
     */
    private int exceed;

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

    public int getExceed() {
        return exceed;
    }

    public void setExceed(int exceed) {
        this.exceed = exceed;
    }
}
