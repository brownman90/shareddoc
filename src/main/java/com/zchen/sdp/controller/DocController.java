package com.zchen.sdp.controller;

import com.zchen.sdp.bean.SDPDoc;
import com.zchen.sdp.utils.ResponseGrid;
import com.zchen.sdp.service.DocService;
import com.zchen.sdp.utils.ResponseMap;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/list")
    public
    @ResponseBody
    ResponseGrid list(SDPDoc SDPDoc, int start, int limit) {
        return docService.query(SDPDoc, start, limit);
    }

    @RequestMapping("/upload")
    public
    @ResponseBody
    ResponseMap upload(SDPDoc SDPDoc) {
        try {
            docService.upload(SDPDoc);
        } catch (FileExistsException | FileUploadBase.FileSizeLimitExceededException e) {
            return ResponseMap.get().failure("Upload failed. " + e.getMessage());
        }
        return ResponseMap.get().success();
    }

    @RequestMapping("/download")
    public String download(HttpServletResponse response, SDPDoc SDPDoc, ModelMap model)
            throws Exception {
        SDPDoc file = docService.findById(SDPDoc);
        if (file == null) {
            model.put("status", false);
            model.put("message", "\"该文件不存在，可能已被删除。\"");
            return "share/files";
        }
        try {
            docService.download(file, response);
        } catch (FileNotFoundException e) {
            model.put("status", false);
            model.put("message", "\"该文件不存在，可能已被删除。\"");
            // docService.delete(SDPDoc);
            return "share/files";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("delete")
    public
    @ResponseBody
    ResponseMap delete(SDPDoc SDPDoc) {
        docService.delete(SDPDoc);
        return ResponseMap.get().success();
    }

    @RequestMapping("deletes")
    public
    @ResponseBody
    ResponseMap deletes(int[] idList) {
        for (int id : idList) {
            docService.delete(new SDPDoc(id));
        }
        return ResponseMap.get().success();
    }


}