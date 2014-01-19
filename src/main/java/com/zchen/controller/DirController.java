package com.zchen.controller;

import com.zchen.bean.Dir;
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
    public @ResponseBody
    Dir dirTree(){
        return dirService.getDirTree();
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

}
