package com.zchen.controller;

import com.zchen.bean.Config;
import com.zchen.service.ConfigService;
import com.zchen.utils.ResponseMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.FileNotFoundException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @RequestMapping("/settings")
    public
    @ResponseBody
    ResponseMap settings() throws FileNotFoundException {
        Config config = new Config();
        config.setLocation("D:/shared_doc");
        config.setLimit(new Long("30000000000"));
        config.setFreeSpace(configService.getFreeSpace("D:/shared_doc"));
        config.setExceed(85);
        return ResponseMap.get().success().setData(config);
    }


    @RequestMapping("/space")
    public
    @ResponseBody
    ResponseMap space(String path) {
        long freeSize;
        try {
            freeSize = configService.getFreeSpace(path);
        } catch (FileNotFoundException e) {
            return ResponseMap.get().failure(e.getMessage());
        }
        return ResponseMap.get().success().setData(freeSize);
    }


}
