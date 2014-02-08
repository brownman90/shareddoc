package com.zchen.sdp.controller;

import com.zchen.extjsassistance.base.model.FormSubmit;
import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.extjsassistance.base.model.GridParams;
import com.zchen.sdp.bean.SDPDoc;
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
    GridLoad<SDPDoc> list(SDPDoc sdpDoc, GridParams params) {
        return docService.query(sdpDoc, params.getStart(), params.getLimit());
    }

    @RequestMapping("/upload")
    public
    @ResponseBody
    FormSubmit upload(SDPDoc sdpDoc) {
        try {
            docService.upload(sdpDoc);
        } catch (FileExistsException | FileUploadBase.FileSizeLimitExceededException e) {
            return FormSubmit.get().failure().setMessage("Upload failed. " + e.getMessage());
        }
        return FormSubmit.get().success();
    }

    @RequestMapping("/download")
    public String download(HttpServletResponse response, SDPDoc sdpDoc, ModelMap model)
    throws Exception {
        SDPDoc file = docService.findById(sdpDoc);
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
            // docService.delete(sdpDoc);
            return "share/files";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("delete")
    public
    @ResponseBody
    ResponseMap delete(SDPDoc sdpDoc) {
        docService.delete(sdpDoc);
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