package com.zchen.bean;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class Dir {
    private String id;
    private String name;
    private List<Dir> children;

    public Dir() {
    }

    public Dir(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dir> getChildren() {
        return children;
    }

    public void setChildren(List<Dir> children) {
        this.children = children;
    }
}
