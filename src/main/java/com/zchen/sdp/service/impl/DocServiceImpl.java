package com.zchen.sdp.service.impl;

import com.zchen.sdp.bean.SDPDoc;
import com.zchen.sdp.dao.DocDao;
import com.zchen.sdp.service.DocService;
import com.zchen.sdp.utils.ResponseGrid;
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
 * Implementation of SDP document operation
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
@Service
public class DocServiceImpl implements DocService {
    String rootAbsolutePath = "D:/shared_doc";
    @Resource
    private DocDao docDao;


    /**
     * Query document list
     * @param SDPDoc   Query condition
     * @param start  Start row of pager
     * @param limit  Limit rows of pager
     * @return  ResponseGrid    Document list with pager
     */
    @Override
    public ResponseGrid query(SDPDoc SDPDoc, int start, int limit) {
        return new ResponseGrid(docDao.query(SDPDoc, start, limit), docDao.count());
    }

    /**
     * Upload document
     * @param SDPDoc   The document to upload
     * @throws FileExistsException
     * @throws FileUploadBase.FileSizeLimitExceededException
     */
    @Override
    public void upload(SDPDoc SDPDoc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException {
        MultipartFile uploadedFile = SDPDoc.getFile();
        String fileName = uploadedFile.getOriginalFilename();
        File savedFile = new File(rootAbsolutePath + SDPDoc.getPath() + fileName);
        if (savedFile.exists()) {
            throw new FileExistsException(fileName + " exists in " + rootAbsolutePath + SDPDoc.getPath() + " .");
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
            SDPDoc.setName(fileName.substring(0, dotIndex));
            SDPDoc.setType(fileName.substring(dotIndex + 1));
        } else {
            SDPDoc.setName(fileName);
            SDPDoc.setType("");
        }
        SDPDoc.setSize(uploadedFile.getSize());
        SDPDoc.setCommitter("czc");
        SDPDoc.setTime(DateFormat.getDateInstance().format(new Date()));
        docDao.insert(SDPDoc);

    }

    /**
     * Download document
     * @param SDPDoc   The document to download
     * @param response  The HTTP response object
     * @throws IOException
     */
    public void download(SDPDoc SDPDoc, HttpServletResponse response)//TODO
            throws IOException {
        File srcFile = new File(rootAbsolutePath + SDPDoc.getPath() + SDPDoc.getFullName());
        InputStream inputStream = new FileInputStream(srcFile);
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + SDPDoc.getFullName());

        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();

    }

    @Override
    public void update(SDPDoc SDPDoc) {

    }

    /**
     * Delete document
     * @param SDPDoc The document to delete
     */
    @Override
    public void delete(SDPDoc SDPDoc) {
        SDPDoc = docDao.findById(SDPDoc);
        if (SDPDoc != null) {
            String fileName = rootAbsolutePath + SDPDoc.getPath() + "/" + SDPDoc.getName() + (StringUtils.isEmpty(SDPDoc.getType()) ? "" : "." + SDPDoc.getType());
            File file = new File(fileName);
            file.delete();

            docDao.delete(SDPDoc);
        }
    }

    /**
     * Get document by id
     * @param SDPDoc   The document object with id.
     * @return  The document to get
     */
    @Override
    public SDPDoc findById(SDPDoc SDPDoc) {
        return docDao.findById(SDPDoc);
    }
}
