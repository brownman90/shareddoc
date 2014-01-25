package com.zchen.controller;

import com.zchen.bean.Dir;
import com.zchen.extjsassistance.fs.ExtjsDirectoryAssistant;
import com.zchen.extjsassistance.fs.ExtjsDirectoryConfig;
import com.zchen.extjsassistance.fs.ExtjsDirectoryNodeFactory;
import com.zchen.extjsassistance.fs.ExtjsFileFilter;
import com.zchen.extjsassistance.fs.model.ExtjsDirectoryNode;
import com.zchen.service.DirService;
import com.zchen.utils.ResponseMap;
import org.apache.commons.io.FileExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Controller
@RequestMapping("/dir")
public class DirController {

    @Resource
    private DirService dirService;

    @RequestMapping("/tree")
    public
    @ResponseBody
    ExtjsDirectoryNode dirTree(String node) {
        ExtjsFileFilter fileFilter = new ExtjsFileFilter();
        fileFilter.setIgnoreFile(true);
        fileFilter.setIgnoreHidden(true);


        ExtjsDirectoryConfig config = new ExtjsDirectoryConfig();
        config.setFileFilter(fileFilter);
        config.setRootPath("D:/shared_doc");

        ExtjsDirectoryNode top = ExtjsDirectoryNodeFactory.build().newNodeInstance(node, config);
        return ExtjsDirectoryAssistant.getFileSystemTree(top, config);
    }


    @RequestMapping("/delete")
    public
    @ResponseBody
    ResponseMap delete(Dir dir) {
        try {
            dirService.delete(dir);
        } catch (FileExistsException e) {
            return ResponseMap.get().failure("Delete directory failed. " + e.getMessage());
        }
        return ResponseMap.get().success();
    }

    @RequestMapping("/create")
    public
    @ResponseBody
    ResponseMap create(Dir dir) {
        try {
            dirService.create(dir);
        } catch (FileExistsException e) {
            return ResponseMap.get().failure("Create directory failed. " + e.getMessage());
        }
        return ResponseMap.get().success();
    }

}
