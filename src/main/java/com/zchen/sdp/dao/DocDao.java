package com.zchen.sdp.dao;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.sdp.bean.SDPDoc;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocDao {
    public void insert(SDPDoc sdpDoc);

    public void delete(SDPDoc sdpDoc);

    public void update(SDPDoc sdpDoc);

    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, int start, int limit);

    public SDPDoc findById(SDPDoc sdpDoc);
}
