package com.zchen.service.impl;

import com.zchen.bean.Dir;
import com.zchen.service.DirService;
import org.apache.commons.io.FileExistsException;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DirServiceImpl implements DirService {
    String rootAbsolutePath = "D:/shared_doc";

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
