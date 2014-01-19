package com.zchen.service.impl;

import com.zchen.service.ConfigService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Zhouce Chen
 * @version Jan 19, 2014
 */

@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public Long getFreeSpace(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path + " doesn't exist.");
        }
        return file.getFreeSpace();
    }
}
