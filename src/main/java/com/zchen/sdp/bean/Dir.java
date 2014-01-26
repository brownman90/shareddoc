package com.zchen.sdp.bean;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class Dir {
    private String id;
    private String text;
    private List<Dir> children;
    private boolean expanded;
    private boolean leaf;
    private String iconCls;

    public Dir() {
    }

    public Dir(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Dir> getChildren() {
        return children;
    }

    public void setChildren(List<Dir> children) {
        this.children = children;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
