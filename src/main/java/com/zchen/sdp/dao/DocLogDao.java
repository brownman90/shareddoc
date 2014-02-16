package com.zchen.sdp.dao;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDocLog;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
public interface DocLogDao {

    public void insert(SDPDocLog log);

    public GridLoad<SDPDocLog> query(SDPDocLog log, GridPage page);
}
