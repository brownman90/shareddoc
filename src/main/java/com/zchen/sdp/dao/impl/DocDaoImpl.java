package com.zchen.sdp.dao.impl;

import com.zchen.extjsassistance.model.grid.GridPage;
import com.zchen.extjsassistance.model.SQLHelper;
import com.zchen.extjsassistance.model.grid.GridLoad;
import com.zchen.extjsassistance.model.grid.GridSort;
import com.zchen.sdp.bean.SDPDoc;
import com.zchen.sdp.dao.DocDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */

@Repository
public class DocDaoImpl implements DocDao {


    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insert(SDPDoc sdpDoc) {
        String sql = "insert into doc(name, type, size, time, committer, path) values(:name, :type, :size, :time, :committer, :path) ";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(sdpDoc));
    }

    @Override
    public void delete(SDPDoc sdpDoc) {
        String sql = "delete from doc where id=:id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(sdpDoc));
    }

    @Override
    public void update(SDPDoc sdpDoc) {

    }

    public int count(StringBuilder where, SDPDoc sdpDoc) {
        StringBuilder sb = new StringBuilder(" select count(*) from doc where 1=1  ");
        sb.append(where);
        return jdbcTemplate.queryForInt(sb.toString(), new BeanPropertySqlParameterSource(sdpDoc));
    }

    @Override
    public GridLoad<SDPDoc> query(SDPDoc sdpDoc, GridPage page, List<GridSort> sorts) {
        StringBuilder sb = new StringBuilder("select * from doc  where 1=1 ");
        StringBuilder where = new StringBuilder();
        if (StringUtils.isNotEmpty(sdpDoc.getName())) {
            sdpDoc.setName("%" + sdpDoc.getName() + "%");
            where.append(" and name like :name ");
        }
        if (StringUtils.isNotEmpty(sdpDoc.getPath())) {
            sdpDoc.setPath(sdpDoc.getPath() + "%");
            where.append(" and  path like :path ");
        }
        sb.append(where);
        sb.append(SQLHelper.sortsToSQL(sorts));
        sb.append(SQLHelper.pageToSQL(page));
        List<SDPDoc> list = jdbcTemplate.query(sb.toString(), new BeanPropertySqlParameterSource(sdpDoc), new DocMapper());
        int total = count(where, sdpDoc);
        return new GridLoad<>(list, total);
    }

    @Override
    public SDPDoc findById(SDPDoc sdpDoc) {
        String sql = "select * from doc where id=:id";
        List<SDPDoc> list = jdbcTemplate.query(sql, new BeanPropertySqlParameterSource(sdpDoc), new DocMapper());
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    class DocMapper implements RowMapper<SDPDoc> {
        @Override
        public SDPDoc mapRow(ResultSet rs, int rowNum) throws SQLException {
            SDPDoc sdpDoc = new SDPDoc();
            sdpDoc.setId(rs.getInt("id"));
            sdpDoc.setName(rs.getString("name"));
            sdpDoc.setType(rs.getString("type"));
            sdpDoc.setSize(rs.getLong("size"));
            sdpDoc.setTime(rs.getTimestamp("time"));
            sdpDoc.setCommitter(rs.getString("committer"));
            sdpDoc.setPath(rs.getString("path"));
            if (sdpDoc.getType().equals("")) {
                sdpDoc.setFullName(sdpDoc.getName());
            } else {
                sdpDoc.setFullName(sdpDoc.getName() + "." + sdpDoc.getType());
            }
            return sdpDoc;
        }
    }
}
