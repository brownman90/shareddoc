package com.zchen.sdp.dao;

import com.zchen.sdp.bean.SDPDoc;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocDao {
    public void insert(SDPDoc SDPDoc);

    public void delete(SDPDoc SDPDoc);

    public void update(SDPDoc SDPDoc);

    public int count();

    public List<SDPDoc> query(SDPDoc SDPDoc, int start, int limit);

    public SDPDoc findById(SDPDoc SDPDoc);
}
