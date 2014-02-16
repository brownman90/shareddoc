package com.zchen.sdp.service.impl;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDocLog;
import com.zchen.sdp.dao.DocLogDao;
import com.zchen.sdp.service.DocLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
@Service
public class DocLogServiceImpl implements DocLogService {

    @Resource
    private DocLogDao docLogDao;

    @Override
    public GridLoad<SDPDocLog> query(SDPDocLog log,GridPage page) {
        return docLogDao.query(log, page);
    }
}
