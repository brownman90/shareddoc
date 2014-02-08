package com.zchen.sdp.dao.impl;

import com.zchen.extjsassistance.base.model.GridLoad;
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
    public void insert(SDPDoc SDPDoc) {
        String sql = "insert into doc(name, type, size, time, committer, path) values(:name, :type, :size, :time, :committer, :path) ";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(SDPDoc));
    }

    @Override
    public void delete(SDPDoc SDPDoc) {
        String sql = "delete from doc where id=:id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(SDPDoc));
    }

    @Override
    public void update(SDPDoc SDPDoc) {

    }

    public int count(StringBuilder where, SDPDoc SDPDoc) {
        StringBuilder sb = new StringBuilder(" select count(*) from doc where 1=1  ");
        sb.append(where);
        return jdbcTemplate.queryForInt(sb.toString(), new BeanPropertySqlParameterSource(SDPDoc));
    }

    @Override
    public GridLoad<SDPDoc> query(SDPDoc SDPDoc, int start, int limit) {
        StringBuilder sb = new StringBuilder("select * from doc  where 1=1 ");
        StringBuilder where = new StringBuilder();
        if (StringUtils.isNotEmpty(SDPDoc.getName())) {
            SDPDoc.setName("%" + SDPDoc.getName() + "%");
            where.append(" and name like :name ");
        }
        if (StringUtils.isNotEmpty(SDPDoc.getPath())) {
            SDPDoc.setPath(SDPDoc.getPath() + "%");
            where.append(" and  path like :path ");
        }
        sb.append(where);
        sb.append(String.format(" limit %d,%d ", start, limit));
        List<SDPDoc> list = jdbcTemplate.query(sb.toString(), new BeanPropertySqlParameterSource(SDPDoc), new DocMapper());
        int total = count(where, SDPDoc);
        return new GridLoad<>(list, total);
    }

    @Override
    public SDPDoc findById(SDPDoc SDPDoc) {
        String sql = "select * from doc where id=:id";
        List<SDPDoc> list = jdbcTemplate.query(sql, new BeanPropertySqlParameterSource(SDPDoc), new DocMapper());
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    class DocMapper implements RowMapper<SDPDoc> {
        @Override
        public SDPDoc mapRow(ResultSet rs, int rowNum) throws SQLException {
            SDPDoc SDPDoc = new SDPDoc();
            SDPDoc.setId(rs.getInt("id"));
            SDPDoc.setName(rs.getString("name"));
            SDPDoc.setType(rs.getString("type"));
            SDPDoc.setSize(rs.getLong("size"));
            SDPDoc.setTime(rs.getString("time"));
            SDPDoc.setCommitter(rs.getString("committer"));
            SDPDoc.setPath(rs.getString("path"));
            if (SDPDoc.getType().equals("")) {
                SDPDoc.setFullName(SDPDoc.getName());
            } else {
                SDPDoc.setFullName(SDPDoc.getName() + "." + SDPDoc.getType());
            }
            return SDPDoc;
        }
    }
}
