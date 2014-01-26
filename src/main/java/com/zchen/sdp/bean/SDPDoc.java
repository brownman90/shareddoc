package com.zchen.sdp.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * The SDP document class
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class SDPDoc {

    /**
     * Document id
     */
    private Integer id;

    /**
     * Document name without file type
     */
    private String name;

    /**
     * Document file type
     */
    private String type;

    /**
     * Document name with file type
     */
    private String fullName;

    /**
     * Document file size
     */
    private Long size;

    /**
     * Uploading document time
     */
    private String time;

    /**
     * Document committer
     */
    private String committer;

    /**
     * Document storage location relative to SDP storage root directory
     */
    private String path;

    /**
     * Upload file object
     */
    private MultipartFile file;

    public SDPDoc() {
    }

    public SDPDoc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
