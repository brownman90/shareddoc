package com.zchen.sdp.service;

import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */
public interface ConfigService {

    public SDPConfig getConfig();

    public void setConfig(SDPConfig config);

    public Long getFreeSpace(String path) throws FileNotFoundException;

    public DirectoryNode getFileSystemTree(String node);

    public String getFileSystemHome();

    public String getSDPCurrentPath();

    public void deleteDirectory(String dirPath) throws IOException;
}
