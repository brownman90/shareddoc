package com.zchen.sdp.service;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDocLog;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
public interface DocLogService {

    public GridLoad<SDPDocLog> query(SDPDocLog log, GridPage page);

}
