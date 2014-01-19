package com.zchen.service;

import com.zchen.bean.Dir;
import org.apache.commons.io.FileExistsException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DirService {

    public Dir getDirTree();

    public void create(Dir dir);

    public void delete(Dir dir) throws FileExistsException;


}
