package com.zchen.controller;

import com.zchen.bean.Doc;
import com.zchen.service.DocService;
import com.zchen.utils.ResponseGrid;
import com.zchen.utils.ResponseMap;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    public
    @ResponseBody
    ResponseGrid list(Doc doc, int start, int limit) {
        return docService.query(doc, start, limit);
    }

    @RequestMapping("/upload")
    public
    @ResponseBody
    ResponseMap upload(Doc doc) {
        try {
            docService.upload(doc);
        } catch (FileExistsException | FileUploadBase.FileSizeLimitExceededException e) {
            return ResponseMap.get().failure("Upload failed. " + e.getMessage());
        }
        return ResponseMap.get().success();
    }

    @RequestMapping("delete")
    public
    @ResponseBody
    ResponseMap delete(Doc doc) {
        docService.delete(doc);
        return ResponseMap.get().success();
    }

    @RequestMapping("deletes")
    public
    @ResponseBody
    ResponseMap deletes(int[] idList) {
        for (int id : idList) {
            docService.delete(new Doc(id));
        }
        return ResponseMap.get().success();
    }


}