package com.zchen.service.impl;

import com.zchen.bean.Doc;
import com.zchen.dao.DocDao;
import com.zchen.service.DocService;
import com.zchen.utils.ResponseGrid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DocServiceImpl implements DocService {

    @Resource
    private DocDao docDao;

    @Override
    public ResponseGrid query(Doc doc, int start, int limit) {
        return new ResponseGrid(docDao.query(doc, start, limit), docDao.count());
    }

    @Override
    public void upload(Doc doc) {
        MultipartFile uploadedFile = doc.getFile();
        String fileName = uploadedFile.getOriginalFilename();
        File savedFile = new File("D:/shared_doc/"+fileName);
        try {
            uploadedFile.transferTo(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int dotIndex = fileName.lastIndexOf(".");
        String name = fileName.substring(0, dotIndex);
        String type = fileName.substring(dotIndex + 1);
        doc.setName(name);
        doc.setType(type);
        doc.setSize(uploadedFile.getSize());
        doc.setCommitter("czc");
        doc.setTime(DateFormat.getDateInstance().format(new Date()));
        docDao.insert(doc);
    }

    @Override
    public void download(Doc doc) {

    }

    @Override
    public void update(Doc doc) {

    }

    @Override
    public void delete(Doc doc) {

    }
}
