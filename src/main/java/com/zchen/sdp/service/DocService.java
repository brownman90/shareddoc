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

    public GridLoad<SDPDoc> query(SDPDoc SDPDoc, int start, int limit);

    public void upload(SDPDoc SDPDoc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException;

    public void download(SDPDoc SDPDoc, HttpServletResponse response) throws IOException;

    public void update(SDPDoc SDPDoc);

    public void delete(SDPDoc SDPDoc);

    public SDPDoc findById(SDPDoc SDPDoc);
}
