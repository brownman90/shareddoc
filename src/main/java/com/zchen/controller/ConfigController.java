package com.zchen.controller;

import com.zchen.bean.Config;
import com.zchen.bean.Dir;
import com.zchen.service.ConfigService;
import com.zchen.utils.ResponseMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @RequestMapping("/settings")
    public
    @ResponseBody
    ResponseMap settings() throws FileNotFoundException {
        Config config = new Config();
        config.setLocation("D:/shared_doc");
        config.setLimit(new Long("30000000000"));
        config.setFreeSpace(configService.getFreeSpace("D:/shared_doc"));
        config.setExceed(85);
        return ResponseMap.get().success().setData(config);
    }


    @RequestMapping("/space")
    public
    @ResponseBody
    ResponseMap space(String path) {
        long freeSize;
        try {
            freeSize = configService.getFreeSpace(path);
        } catch (FileNotFoundException e) {
            return ResponseMap.get().failure(e.getMessage());
        }
        return ResponseMap.get().success().setData(freeSize);
    }

    @RequestMapping("/directoryList")
    public
    @ResponseBody
    Dir directoryList(String node) {
        Dir root = new Dir();
        File[] files;
        if (node.equals("root")) {
            files = File.listRoots();
        } else {
            FileFilter ff = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            };
            files = new File(node).listFiles(ff);
        }
        List<Dir> children = new ArrayList<>();
        for (File file : files) {
            String dirName = file.getName().equals("") ? file.getAbsolutePath() : file.getName();
            Dir dir = new Dir(file.getAbsolutePath(), dirName);
            children.add(dir);
        }
        root.setChildren(children);

        return root;
    }


}
