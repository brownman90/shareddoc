package com.zchen.sdp.dao;

import com.zchen.sdp.bean.SDPConfig;

/**
 * @author Zhouce Chen
 * @version Feb 11, 2014
 */
public interface ConfigDao {

    public SDPConfig getConfig();

    public void setConfig(SDPConfig config);

}
