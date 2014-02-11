package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.AjaxResult;
import com.zchen.extjsassistance.fs.ExtjsDirectoryAssistant;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;
import com.zchen.sdp.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @RequestMapping("/get")
    public
    @ResponseBody
    AjaxResult getConfig() throws FileNotFoundException {
        SDPConfig SDPConfig = configService.getConfig();
        return AjaxResult.get().success().setData(SDPConfig);
    }

    @RequestMapping("/set")
    public
    @ResponseBody
    AjaxResult setConfig(SDPConfig config){
        configService.setConfig(config);
        return AjaxResult.get().success();
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

    @RequestMapping("/fs/create")
    public
    @ResponseBody
    AjaxResult createDirectory(String id) {
        try {
            ExtjsDirectoryAssistant.createDirectory(id);
        }  catch (FileAlreadyExistsException e) {
            return AjaxResult.get().failure()
                    .setMessage("Create directory failed. Directory " + id + " has exists.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }

    @RequestMapping("/fs/delete")
    public
    @ResponseBody
    AjaxResult deleteDirectory(String id) {
        try {
            ExtjsDirectoryAssistant.deleteDirectory(id);
        } catch (DirectoryNotEmptyException e) {
            return AjaxResult.get().failure()
                    .setMessage("Delete directory failed. Directory " + id + " is not empty.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }


}
