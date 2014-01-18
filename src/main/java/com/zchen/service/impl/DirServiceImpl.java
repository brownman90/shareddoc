package com.zchen.service.impl;

import com.zchen.bean.Dir;
import com.zchen.service.DirService;
import com.zchen.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DirServiceImpl implements DirService {
    String rootAbsolutePath = "D:/shared_doc";

    @Override
    public Dir getDirTree() {


        Dir root = new Dir("/", "");

        return fillChildren(root);
    }


    private Dir fillChildren(Dir dir){
        File[] childrenFiles =  new File(rootAbsolutePath + dir.getId()).listFiles();
        List<Dir> children = new ArrayList<>();
        for (File childFIle : childrenFiles) {
            if(childFIle.isDirectory()){
                String id = childFIle.getPath().substring(rootAbsolutePath.length());
                Dir childDir = new Dir(Utils.slashExchange(id), childFIle.getName());
                children.add(fillChildren(childDir));
            }
        }
        dir.setChildren(children);
        return dir;
    }



}
