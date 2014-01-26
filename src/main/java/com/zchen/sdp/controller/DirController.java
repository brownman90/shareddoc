package com.zchen.sdp.controller;

import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.Dir;
import com.zchen.sdp.service.DirService;
import com.zchen.sdp.utils.ResponseMap;
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
    DirectoryNode getDirectoryTree(String node) {
        return dirService.getDirectoryTree(node);
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
