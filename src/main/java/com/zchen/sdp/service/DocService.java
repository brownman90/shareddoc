package com.zchen.sdp.service;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.sdp.bean.SDPDoc;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocService {

    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, int start, int limit);

    public void upload(SDPDoc sdpDoc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException;

    public void download(SDPDoc sdpDoc, HttpServletResponse response) throws IOException;

    public void update(SDPDoc sdpDoc);

    public void delete(SDPDoc sdpDoc);

    public SDPDoc findById(SDPDoc sdpDoc);
}
