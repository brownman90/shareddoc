package com.zchen.service;

import com.zchen.bean.Doc;
import com.zchen.utils.ResponseGrid;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileExistsException;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocService {

    public ResponseGrid query(Doc doc, int start, int limit);

    public void upload(Doc doc) throws FileExistsException, FileUploadBase.FileSizeLimitExceededException;

    public void download(Doc doc);

    public void update(Doc doc);

    public void delete(Doc doc);

}
