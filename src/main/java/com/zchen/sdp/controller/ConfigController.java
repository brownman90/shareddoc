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
import java.io.IOException;
import java.nio.file.*;

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
    AjaxResult createDirectory(DirectoryNode node) {
        try {
            Path directory = Paths.get(node.getId());
            Files.createDirectory(directory);
        }  catch (FileAlreadyExistsException e) {
            return AjaxResult.get().failure()
                    .setMessage("Create folder failed. Folder '" + node.getId() + "' has exists.");
        } catch (InvalidPathException e) {
            return AjaxResult.get().failure()
                    .setMessage("Create folder failed. Invalid folder name '" + node.getText() + "'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }

    @RequestMapping("/fs/delete")
    public
    @ResponseBody
    AjaxResult deleteDirectory(DirectoryNode node) {
        try {
            configService.deleteDirectory(node.getId());
        } catch (DirectoryNotEmptyException e) {
            return AjaxResult.get().failure()
                    .setMessage("Delete folder failed. Folder '" + node.getId() + "' is not empty.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }


}
