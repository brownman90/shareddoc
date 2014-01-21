package com.zchen.service.impl;

import com.zchen.bean.Doc;
import com.zchen.dao.DocDao;
import com.zchen.service.DocService;
import com.zchen.utils.ResponseGrid;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DocServiceImpl implements DocService {
    String rootAbsolutePath = "D:/shared_doc";
    @Resource
    private DocDao docDao;

    @Override
    public ResponseGrid query(Doc doc, int start, int limit) {
        return new ResponseGrid(docDao.query(doc, start, limit), docDao.count());
    }

    @Override
    public void upload(Doc doc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException {
        MultipartFile uploadedFile = doc.getFile();
        String fileName = uploadedFile.getOriginalFilename();
        File savedFile = new File(rootAbsolutePath + doc.getPath() + fileName);
        if (savedFile.exists()) {
            throw new FileExistsException(fileName + " exists in " + rootAbsolutePath + doc.getPath() + " .");
        }
        if (uploadedFile.getSize() > 20000000) {
            throw new FileUploadBase.FileSizeLimitExceededException(String.format("File size(%d) exceeds max upload limit(%d).", uploadedFile.getSize(), 20000000), uploadedFile.getSize(), 20000000);
        }
        try {
            uploadedFile.transferTo(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            doc.setName(fileName.substring(0, dotIndex));
            doc.setType(fileName.substring(dotIndex + 1));
        } else {
            doc.setName(fileName);
            doc.setType("");
        }
        doc.setSize(uploadedFile.getSize());
        doc.setCommitter("czc");
        doc.setTime(DateFormat.getDateInstance().format(new Date()));
        docDao.insert(doc);

    }

    public void download(Doc doc, HttpServletResponse response)
            throws IOException {
        File srcFile = new File(rootAbsolutePath + doc.getPath() + doc.getFullName());
        InputStream inputStream = new FileInputStream(srcFile);
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + doc.getFullName());

        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();

    }

    @Override
    public void update(Doc doc) {

    }

    @Override
    public void delete(Doc doc) {
        doc = docDao.findById(doc);
        if (doc != null) {
            String fileName = rootAbsolutePath + doc.getPath() + "/" + doc.getName() + (StringUtils.isEmpty(doc.getType()) ? "" : "." + doc.getType());
            File file = new File(fileName);
            file.delete();

            docDao.delete(doc);
        }
    }

    @Override
    public Doc findById(Doc doc) {
        return docDao.findById(doc);
    }
}
