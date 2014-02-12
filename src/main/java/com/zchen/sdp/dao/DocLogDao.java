package com.zchen.sdp.dao;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.extjsassistance.base.model.GridParams;
import com.zchen.sdp.bean.SDPDocLog;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
public interface DocLogDao {

    public void insert(SDPDocLog log);

    public GridLoad<SDPDocLog> query(SDPDocLog log, GridParams params);
}
