package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.AjaxResult;
import com.zchen.extjsassistance.fs.DirectoryConfig;
import com.zchen.extjsassistance.fs.DirectoryNodeFactory;
import com.zchen.extjsassistance.fs.ExtjsDirectoryAssistant;
import com.zchen.extjsassistance.fs.ExtjsFileFilter;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.service.DirService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Controller
@RequestMapping("/dir")
public class DirController {

    String rootAbsolutePath = "D:/shared_doc";
    @Resource
    private DirService dirService;

    @RequestMapping("/tree")
    public
    @ResponseBody
    DirectoryNode getDirectoryTree(String node) {
        ExtjsFileFilter fileFilter = new ExtjsFileFilter();
        fileFilter.setIgnoreFile(true);
        fileFilter.setIgnoreHidden(true);


        DirectoryConfig config = new DirectoryConfig();
        config.setFileFilter(fileFilter);
        config.setRootPath(rootAbsolutePath);
        config.setLevel(0);

        DirectoryNode top = DirectoryNodeFactory.build().newTopNode(node, rootAbsolutePath);
        return ExtjsDirectoryAssistant.getFileSystemTree(top, config);
    }


    @RequestMapping("/delete")
    public
    @ResponseBody
    AjaxResult delete(DirectoryNode directoryNode) {
        try {
            String path = rootAbsolutePath + directoryNode.getId();
            ExtjsDirectoryAssistant.deleteDirectory(path);
        } catch (DirectoryNotEmptyException e) {
            return AjaxResult.get().failure()
                    .setMessage("Delete directory failed. Directory " + directoryNode.getId() + " is not empty.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }

    @RequestMapping("/create")
    public
    @ResponseBody
    AjaxResult create(DirectoryNode directoryNode) {
        try {
            String path = rootAbsolutePath + directoryNode.getId();
            ExtjsDirectoryAssistant.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            return AjaxResult.get().failure()
                    .setMessage("Create directory failed. Directory " + directoryNode.getId() + " has exists.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.get().success();
    }

}
