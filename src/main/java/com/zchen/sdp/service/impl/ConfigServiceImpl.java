package com.zchen.sdp.service.impl;

import com.zchen.extjsassistance.fs.*;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;
import com.zchen.sdp.service.ConfigService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Implementation of SDP system settings
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */

@Service
public class ConfigServiceImpl implements ConfigService {


    /**
     * Get SDP system settings
     * @return  The SDP system settings
     */
    @Override
    public SDPConfig getSettings() {
        SDPConfig SDPConfig = new SDPConfig();
        SDPConfig.setLocation("D:/shared_doc");
        SDPConfig.setLimit(new Long("30000000000"));
        try {
            SDPConfig.setFreeSpace(this.getFreeSpace("D:/shared_doc"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SDPConfig.setExceed(85);
        return SDPConfig;
    }

    /**
     * Get the size of disk free space by SDP storage location.
     * @param path The path of storage location
     * @return  The size of disk free space
     * @throws FileNotFoundException
     */
    @Override
    public Long getFreeSpace(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path + " doesn't exist.");
        }
        return file.getFreeSpace();
    }

    /**
     * Get directory tree of file system
     * @param node  The top directory node path
     * @return  The top directory
     */
    @Override
    public DirectoryNode getFileSystemTree(String node) {
        DirectoryNode directoryNode = DirectoryNodeFactory.build().newTopNode(node);

        ExtjsFileFilter fileFilter = new ExtjsFileFilter();
        fileFilter.setIgnoreFile(true);
        fileFilter.setIgnoreHidden(true);

        DirectoryConfig config = new DirectoryConfig();
        config.setFileFilter(fileFilter);
        config.setIconHandler(new IconHandler() {
            @Override
            public String getIconClass(DirectoryNode directoryNode) {
                File file = new File(directoryNode.getId() + "/.shareddoc");
                if (file.exists()) {
                    return "favicon";
                }
                return "";
            }
        });
        return ExtjsDirectoryAssistant.getFileSystemTree(directoryNode, config);
    }

    /**
     * Get the file system home directory path
     * @return  The file system home directory path
     */
    @Override
    public String getFileSystemHome() {
        return ExtjsDirectoryAssistant.getUserHomePath();
    }

    /**
     * Get SDP current storage location
     * @return  SDP current storage location
     */
    @Override
    public String getSDPCurrentPath() {
        return DirectoryUtils.slashExchange("D:/shared_doc");
    }
}
