package com.zchen.utils;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class ResponseGrid {

    private List rows;

    private int count;

    public ResponseGrid(List rows, int count) {
        this.rows = rows;
        this.count = count;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
