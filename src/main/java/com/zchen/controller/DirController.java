package com.zchen.controller;

import com.zchen.bean.Dir;
import com.zchen.service.DirService;
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
}
