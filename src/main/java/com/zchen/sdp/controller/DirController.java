package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.AjaxResult;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.Dir;
import com.zchen.sdp.service.DirService;
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
    AjaxResult delete(Dir dir) {
        try {
            dirService.delete(dir);
        } catch (FileExistsException e) {
            return AjaxResult.get().failure()
                    .setMessage("Delete directory failed. " + e.getMessage());
        }
        return AjaxResult.get().success();
    }

    @RequestMapping("/create")
    public
    @ResponseBody
    AjaxResult create(Dir dir) {
        try {
            dirService.create(dir);
        } catch (FileExistsException e) {
            return AjaxResult.get().failure()
                    .setMessage("Create directory failed. " + e.getMessage());
        }
        return AjaxResult.get().success();
    }

}
