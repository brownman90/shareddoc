package com.zchen.controller;

import com.zchen.extjsassistance.fs.ExtjsDirectoryAssistant;
import com.zchen.extjsassistance.fs.ExtjsDirectoryNodeFactory;
import com.zchen.extjsassistance.fs.ExtjsFileFilter;
import com.zchen.extjsassistance.fs.model.ExtjsDirectoryNode;
import com.zchen.utils.ResponseMap;
import com.zchen.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zhouce Chen
 * @version Jan 23, 2014
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/dir")
    public
    @ResponseBody
    ExtjsDirectoryNode list(String node) {
        ExtjsDirectoryNode directoryNode = ExtjsDirectoryNodeFactory.build().newNodeInstance(node);
        ExtjsFileFilter fileFilter = new ExtjsFileFilter();
        fileFilter.setIgnoreFile(true);
        fileFilter.setIgnoreHidden(true);

        return ExtjsDirectoryAssistant.getFileSystemTree(directoryNode, fileFilter);
    }


    @RequestMapping("/dir/home")
    public
    @ResponseBody
    ResponseMap home() {
        return ResponseMap.get()
                .success()
                .setData(ExtjsDirectoryAssistant.getUserHomePath());
    }

    @RequestMapping("/dir/current")
    public
    @ResponseBody
    ResponseMap current() {
        String home = "D:/shared_doc";
        return ResponseMap.get()
                .success()
                .setData(Utils.slashExchange(home));
    }

}
