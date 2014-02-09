package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.AjaxResult;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;
import com.zchen.sdp.service.ConfigService;
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
    AjaxResult getSettings() throws FileNotFoundException {
        SDPConfig SDPConfig = configService.getSettings();
        return AjaxResult.get().success().setData(SDPConfig);
    }

    @RequestMapping("/space")
    public
    @ResponseBody
    AjaxResult getFreeSpace(String path) {
        long freeSize;
        try {
            freeSize = configService.getFreeSpace(path);
        } catch (FileNotFoundException e) {
            return AjaxResult.get().failure().setMessage(e.getMessage());
        }
        return AjaxResult.get().success().setData(freeSize);
    }

    @RequestMapping("/fs")
    public
    @ResponseBody
    DirectoryNode getFileSystemTree(String node) {
        return configService.getFileSystemTree(node);
    }

    @RequestMapping("/fs/home")
    public
    @ResponseBody
    AjaxResult getFileSystemHome() {
        return AjaxResult.get()
                .success()
                .setData(configService.getFileSystemHome());
    }

    @RequestMapping("/fs/current")
    public
    @ResponseBody
    AjaxResult getSDPCurrentLocation() {
        return AjaxResult.get()
                .success()
                .setData(configService.getSDPCurrentPath());
    }


}
