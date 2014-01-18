package com.zchen.controller;

import com.zchen.bean.Doc;
import com.zchen.service.DocService;
import com.zchen.utils.ResponseGrid;
import com.zchen.utils.ResponseMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "index";
    }

    @RequestMapping("/list")
    public @ResponseBody
    ResponseGrid list(Doc doc, int start, int limit){
        return docService.query(doc, start, limit);
    }

    @RequestMapping("/upload")
    public @ResponseBody
    ResponseMap upload(Doc doc){
        docService.upload(doc);

        return ResponseMap.get().success();
    }


}