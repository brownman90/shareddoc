package com.zchen.sdp.service;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.extjsassistance.base.model.GridPage;
import com.zchen.extjsassistance.base.model.GridParams;
import com.zchen.sdp.bean.SDPDocLog;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
public interface DocLogService {

    public GridLoad<SDPDocLog> query(SDPDocLog log, GridPage page);

}
