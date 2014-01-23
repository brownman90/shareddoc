package com.zchen.controller;

import com.zchen.bean.Dir;
import com.zchen.utils.ResponseMap;
import com.zchen.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

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
    Dir list(String node) {
        Dir root = new Dir();
        File[] files;
        if (node.equals("root")) {
            files = File.listRoots();
        } else {
            FileFilter ff = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return !pathname.isHidden() && pathname.isDirectory();
                }
            };
            files = new File(node).listFiles(ff);
        }
        List<Dir> children = new ArrayList<>();
        for (File file : files) {
            String dirName = file.getName().equals("") ? file.getAbsolutePath() : file.getName();
            String path = Utils.slashExchange(file.getAbsolutePath());
            Dir dir = new Dir(path, dirName);
            File flagFile = new File(path + "/.shareddoc");
            if (flagFile.exists()) {
                dir.setIconCls("favicon");
            }
            children.add(dir);
        }
        root.setChildren(children);

        return root;
    }


    @RequestMapping("/dir/home")
    public
    @ResponseBody
    ResponseMap home() {
        String home = System.getProperty("user.home");
        return ResponseMap.get().success().setData(Utils.slashExchange(home));
    }

}
