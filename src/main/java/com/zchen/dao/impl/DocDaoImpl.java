package com.zchen.dao.impl;

import com.zchen.bean.Doc;
import com.zchen.dao.DocDao;
import com.zchen.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
    public void insert(Doc doc) {
        String sql = "insert into doc(name, type, size, time, committer, path) values(:name, :type, :size, :time, :committer, :path) ";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(doc));
    }

    @Override
    public void delete(Doc doc) {
        String sql = "delete from doc where id=:id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(doc));
    }

    @Override
    public void update(Doc doc) {

    }

    @Override
    public int count() {
        String sql = "select count(*) from doc";
        return jdbcTemplate.queryForInt(sql, Utils.emptyMap());
    }

    @Override
    public List<Doc> query(Doc doc, int start, int limit) {
        StringBuilder sb = new StringBuilder("select * from doc  where 1=1 ");
        if (StringUtils.isNotEmpty(doc.getName())) {
            doc.setName("%"+doc.getName()+"%");
            sb.append("and name like :name");
        }
        sb.append(String.format(" limit %d,%d ", start, limit));
        return jdbcTemplate.query(sb.toString(), new BeanPropertySqlParameterSource(doc), new DocMapper());
    }


    class DocMapper implements RowMapper<Doc> {
        @Override
        public Doc mapRow(ResultSet rs, int rowNum) throws SQLException {
            Doc doc = new Doc();
            doc.setId(rs.getInt("id"));
            doc.setName(rs.getString("name"));
            doc.setType(rs.getString("type"));
            doc.setSize(rs.getLong("size"));
            doc.setTime(rs.getString("time"));
            doc.setCommitter(rs.getString("committer"));
            doc.setPath(rs.getString("path"));

            return doc;
        }
    }
}
