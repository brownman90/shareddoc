package com.zchen.sdp.dao;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDoc;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocDao {
    public void insert(SDPDoc sdpDoc);

    public void delete(SDPDoc sdpDoc);

    public void update(SDPDoc sdpDoc);

    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, GridPage page, List<GridSort> sorts);

    public SDPDoc findById(SDPDoc sdpDoc);
}
