package com.zchen.sdp.service.impl;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDoc;
import com.zchen.sdp.bean.SDPDocLog;
import com.zchen.sdp.dao.DocDao;
import com.zchen.sdp.dao.DocLogDao;
import com.zchen.sdp.service.DocService;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

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

    @Resource
    private DocLogDao docLogDao;


    /**
     * Query document list
     * @param sdpDoc   Query condition
     * @param page  Page parameters for query docs
     * @param sort  Sort parameters for query docs
     * @return  ResponseGrid    Document list with pager
     */
    @Override
    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, GridPage page, List<GridSort> sorts) {
        return docDao.query(sdpDoc, page, sorts);
    }

    /**
     * Upload document
     * @param sdpDoc   The document to upload
     * @throws FileExistsException
     * @throws FileUploadBase.FileSizeLimitExceededException
     */
    @Override
    public void upload(SDPDoc sdpDoc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException {
        MultipartFile uploadedFile = sdpDoc.getFile();
        String fileName = uploadedFile.getOriginalFilename();
        File savedFile = new File(rootAbsolutePath + sdpDoc.getPath() + "/" + fileName);
        if (savedFile.exists()) {
            throw new FileExistsException(fileName + " exists in " + rootAbsolutePath + sdpDoc.getPath() + " .");
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
            sdpDoc.setName(fileName.substring(0, dotIndex));
            sdpDoc.setType(fileName.substring(dotIndex + 1));
        } else {
            sdpDoc.setName(fileName);
            sdpDoc.setType("");
        }
        sdpDoc.setSize(uploadedFile.getSize());
        sdpDoc.setCommitter("czc");
        Date current = new Date();
        sdpDoc.setTime(current);
        docDao.insert(sdpDoc);

        SDPDocLog log = new SDPDocLog();
        log.setName(sdpDoc.getFullName());
        log.setPath(sdpDoc.getPath());
        log.setOperator("czc");
        log.setTime(current);
        log.setAction("upload");
        docLogDao.insert(log);
    }

    /**
     * Download document
     * @param sdpDoc   The document to download
     * @param response  The HTTP response object
     * @throws IOException
     */
    public void download(SDPDoc sdpDoc, HttpServletResponse response)//TODO
    throws IOException {
        File srcFile = new File(rootAbsolutePath + sdpDoc.getPath() + "/" + sdpDoc.getFullName());
        InputStream inputStream = new FileInputStream(srcFile);
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + sdpDoc.getFullName());

        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();

    }

    @Override
    public void update(SDPDoc sdpDoc) {

    }

    /**
     * Delete document
     * @param sdpDoc The document to delete
     */
    @Override
    public void delete(SDPDoc sdpDoc) {
        sdpDoc = docDao.findById(sdpDoc);
        if (sdpDoc != null) {
            String fileName = rootAbsolutePath + sdpDoc.getPath() + "/" + sdpDoc.getName() + (StringUtils.isEmpty(sdpDoc.getType()) ? "" : "." + sdpDoc.getType());
            File file = new File(fileName);
            file.delete();

            docDao.delete(sdpDoc);

            SDPDocLog log = new SDPDocLog();
            log.setName(sdpDoc.getFullName());
            log.setAction("trash");
            log.setOperator("czc");
            log.setPath(sdpDoc.getPath());
            log.setTime(new Date());
            docLogDao.insert(log);
        }
    }

    /**
     * Get document by id
     * @param sdpDoc   The document object with id.
     * @return  The document to get
     */
    @Override
    public SDPDoc findById(SDPDoc sdpDoc) {
        return docDao.findById(sdpDoc);
    }
}
