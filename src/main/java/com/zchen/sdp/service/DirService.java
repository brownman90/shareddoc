package com.zchen.sdp.service;

import com.zchen.extjsassistance.fs.model.DirectoryNode;
import com.zchen.sdp.bean.Dir;
import org.apache.commons.io.FileExistsException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DirService {

    public DirectoryNode getDirectoryTree(String node);

    public void create(Dir dir) throws FileExistsException;

    public void delete(Dir dir) throws FileExistsException;


}
