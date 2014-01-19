package com.zchen.dao;

import com.zchen.bean.Doc;

import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public interface DocDao {
    public void insert(Doc doc);

    public void delete(Doc doc);

    public void update(Doc doc);

    public int count();

    public List<Doc> query(Doc doc, int start, int limit);

    public Doc findById(Doc doc);
}
