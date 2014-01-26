package com.zchen.sdp.dao.impl;

import com.zchen.sdp.bean.SDPDoc;
import com.zchen.sdp.dao.DocDao;
import com.zchen.sdp.utils.Utils;
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

    @Override
    public int count() {
        String sql = "select count(*) from doc";
        return jdbcTemplate.queryForInt(sql, Utils.emptyMap());
    }

    @Override
    public List<SDPDoc> query(SDPDoc SDPDoc, int start, int limit) {
        StringBuilder sb = new StringBuilder("select * from doc  where 1=1 ");
        if (StringUtils.isNotEmpty(SDPDoc.getName())) {
            SDPDoc.setName("%" + SDPDoc.getName() + "%");
            sb.append("and name like :name");
        }
        if (StringUtils.isNotEmpty(SDPDoc.getPath())) {
            SDPDoc.setPath(SDPDoc.getPath() + "%");
            sb.append(" and  path like :path");
        }
        sb.append(String.format(" limit %d,%d ", start, limit));
        return jdbcTemplate.query(sb.toString(), new BeanPropertySqlParameterSource(SDPDoc), new DocMapper());
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
