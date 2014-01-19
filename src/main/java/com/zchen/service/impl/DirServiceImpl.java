package com.zchen.service.impl;

import com.zchen.bean.Dir;
import com.zchen.service.DirService;
import com.zchen.utils.Utils;
import org.apache.commons.io.FileExistsException;
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
        for (File childFile : childrenFiles) {
            if (childFile.isDirectory()) {
                String id = childFile.getPath().substring(rootAbsolutePath.length()) + "/";
                Dir childDir = new Dir(Utils.slashExchange(id), childFile.getName());
                children.add(fillChildren(childDir));
            }
        }
        dir.setChildren(children);
        return dir;
    }

    @Override
    public void create(Dir dir) throws FileExistsException {
        String path = rootAbsolutePath + dir.getId();
        File file = new File(path);
        if (file.exists()) {
            throw new FileExistsException("The directory exists.");
        }
        boolean result = file.mkdir();
        if (!result) {

        }
    }

    @Override
    public void delete(Dir dir) throws FileExistsException {
        String path = rootAbsolutePath + dir.getId();
        File file = new File(path);
        boolean result = file.delete();
        if (!result) {
            throw new FileExistsException("The directory is not empty.");
        }
    }
}
