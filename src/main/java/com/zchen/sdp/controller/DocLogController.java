package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.extjsassistance.base.model.GridParams;
import com.zchen.sdp.bean.SDPDocLog;
import com.zchen.sdp.service.DocLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Zhouce Chen
 * @version Feb 12, 2014
 */
@Controller
@RequestMapping("/log")
public class DocLogController {

    @Resource
    private DocLogService docLogService;

    @RequestMapping("/doc/list")
    public
    @ResponseBody
    GridLoad<SDPDocLog> list(SDPDocLog log, GridParams params) {
        return docLogService.query(log, params);
    }

}
