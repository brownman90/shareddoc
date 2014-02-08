package com.zchen.sdp.dao;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.sdp.bean.SDPDoc;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocDao {
    public void insert(SDPDoc SDPDoc);

    public void delete(SDPDoc SDPDoc);

    public void update(SDPDoc SDPDoc);

    public GridLoad<SDPDoc> query(SDPDoc SDPDoc, int start, int limit);

    public SDPDoc findById(SDPDoc SDPDoc);
}
