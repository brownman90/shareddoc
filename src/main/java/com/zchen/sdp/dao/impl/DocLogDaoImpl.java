package com.zchen.sdp.dao.impl;

import com.zchen.extjsassistance.base.model.GridLoad;
import com.zchen.extjsassistance.base.model.GridParams;
import com.zchen.sdp.bean.SDPDocLog;
import com.zchen.sdp.dao.DocLogDao;
import org.apache.commons.lang3.StringUtils;
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
 * @version Feb 12, 2014
 */
@Repository
public class DocLogDaoImpl implements DocLogDao {

    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insert(SDPDocLog log) {
        String sql = "insert into doc_log(name, path, operator, time, action) values(:name, :path, :operator, :time, :action)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(log));
    }

    public int count(StringBuilder where, SDPDocLog log) {
        StringBuilder sb = new StringBuilder(" select count(*) from doc_log where 1=1  ");
        sb.append(where);
        return jdbcTemplate.queryForInt(sb.toString(), new BeanPropertySqlParameterSource(log));
    }

    @Override
    public GridLoad<SDPDocLog> query(SDPDocLog log, GridParams params) {
        StringBuilder sb = new StringBuilder("select * from doc_log where 1=1");
        StringBuilder where = new StringBuilder();
        if (StringUtils.isNotEmpty(log.getName())) {
            log.setName("%" + log.getName() + "%");
            where.append(" and name like :name ");
        }
        if (StringUtils.isNotEmpty(log.getPath())) {
            log.setPath(log.getPath() + "%");
            where.append(" and  path like :path ");
        }
        sb.append(where);
        sb.append(String.format(" limit %d,%d ", params.getStart(), params.getLimit()));
        List<SDPDocLog> list = jdbcTemplate.query(sb.toString(), new BeanPropertySqlParameterSource(log), new DocLogMapper());
        int total = count(where, log);
        return new GridLoad<>(list, total);
    }

    class DocLogMapper implements RowMapper<SDPDocLog> {
        @Override
        public SDPDocLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            SDPDocLog log = new SDPDocLog();
            log.setId(rs.getInt("id"));
            log.setAction(rs.getString("action"));
            log.setName(rs.getString("name"));
            log.setPath(rs.getString("path"));
            log.setOperator(rs.getString("operator"));
            log.setTime(rs.getString("time"));
            return log;
        }
    }

}
