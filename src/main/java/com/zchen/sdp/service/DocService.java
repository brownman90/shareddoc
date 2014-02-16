package com.zchen.sdp.service;

import com.zchen.extjsassistance.model.grid.*;
import com.zchen.sdp.bean.SDPDoc;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocService {

    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, GridPage page, List<GridSort> sorts);

    public void upload(SDPDoc sdpDoc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException;

    public void download(SDPDoc sdpDoc, HttpServletResponse response) throws IOException;

    public void update(SDPDoc sdpDoc);

    public void delete(SDPDoc sdpDoc);

    public SDPDoc findById(SDPDoc sdpDoc);
}
