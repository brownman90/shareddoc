package com.zchen.sdp.service.impl;

import com.zchen.extjsassistance.fs.*;
import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;
import com.zchen.sdp.dao.ConfigDao;
import com.zchen.sdp.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of SDP system settings
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */

@Service
public class ConfigServiceImpl implements ConfigService {

    public static final String SDP_FLAG_FILE = "/.shareddoc";

    @Resource
    private ConfigDao configDao;

    /**
     * Get SDP system settings
     * @return  The SDP system settings
     */
    @Override
    public SDPConfig getConfig() {
        SDPConfig SDPConfig = configDao.getConfig();
        try {
            SDPConfig.setFreeSpace(this.getFreeSpace("D:/shared_doc"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return SDPConfig;
    }

    @Override
    public void setConfig(SDPConfig config) {
        SDPConfig origin = configDao.getConfig();
        if(!origin.getLocation().equals(config.getLocation())){
            Path path = Paths.get(config.getLocation() + SDP_FLAG_FILE);
            if(Files.notExists(path)){
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        configDao.setConfig(config);
    }

    /**
     * Get the size of disk free space by SDP storage location.
     * @param path The path of storage location
     * @return  The size of disk free space
     * @throws FileNotFoundException
     */
    @Override
    public Long getFreeSpace(String path) throws FileNotFoundException {
        Path directory = Paths.get(path);
        if (Files.notExists(directory)) {
            throw new FileNotFoundException(path + " doesn't exist.");
        }
        Long size = null;
        try {
            size = Files.getFileStore(directory).getUsableSpace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
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
                File file = new File(directoryNode.getId() + SDP_FLAG_FILE);
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
        return DirectoryUtils.slashExchange(configDao.getConfig().getLocation());
    }

    @Override
    public void deleteDirectory(String path) throws IOException{
        Path flagFile = Paths.get(path + SDP_FLAG_FILE);
        //delete flag file if it exists
        boolean isSDP =  Files.deleteIfExists(flagFile);
        Path deletedPath = Paths.get(path);
        try {
            //delete directory
            Files.delete(deletedPath);
        } catch (DirectoryNotEmptyException e){
            if(isSDP){
                //if flag file is deleted, create it
                Files.createFile(flagFile);
            }
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
