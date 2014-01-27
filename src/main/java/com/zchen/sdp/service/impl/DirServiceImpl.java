package com.zchen.sdp.service.impl;

import com.zchen.extjsassistance.fs.DirectoryConfig;
import com.zchen.extjsassistance.fs.ExtjsDirectoryAssistant;
import com.zchen.extjsassistance.fs.DirectoryNodeFactory;
import com.zchen.extjsassistance.fs.ExtjsFileFilter;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.Dir;
import com.zchen.sdp.service.DirService;
import org.apache.commons.io.FileExistsException;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Implementation of SDP document storage directories related operation
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DirServiceImpl implements DirService {
    String rootAbsolutePath = "D:/shared_doc";

    /**
     * Get the directory tree of SDP storage location
     * @param node  The top directory node path
     * @return  The top directory node
     */
    @Override
    public DirectoryNode getDirectoryTree(String node) {
        String rootPath = "D:/shared_doc";
        ExtjsFileFilter fileFilter = new ExtjsFileFilter();
        fileFilter.setIgnoreFile(true);
        fileFilter.setIgnoreHidden(true);


        DirectoryConfig config = new DirectoryConfig();
        config.setFileFilter(fileFilter);
        config.setRootPath(rootPath);
        config.setLevel(0);

        DirectoryNode top = DirectoryNodeFactory.build().newTopNode(node, rootPath);
        return ExtjsDirectoryAssistant.getFileSystemTree(top, config);
    }

    /**
     * Create new directory
     * @param dir  The directory to create
     * @throws FileExistsException
     */
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

    /**
     * Delete directory
     * @param dir The directory to delete
     * @throws FileExistsException
     */
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
