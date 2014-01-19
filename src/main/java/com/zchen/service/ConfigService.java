package com.zchen.service;

import java.io.FileNotFoundException;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */
public interface ConfigService {

    public Long getFreeSpace(String path) throws FileNotFoundException;
}
