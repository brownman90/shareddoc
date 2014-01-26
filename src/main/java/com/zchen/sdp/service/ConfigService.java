package com.zchen.sdp.service;

import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.SDPConfig;

import java.io.FileNotFoundException;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */
public interface ConfigService {

    public SDPConfig getSettings();

    public Long getFreeSpace(String path) throws FileNotFoundException;

    public DirectoryNode getFileSystemTree(String node);

    public String getFileSystemHome();

    public String getSDPCurrentPath();
}
