package com.zchen.sdp.dao.impl;

import com.zchen.sdp.bean.SDPConfig;
import com.zchen.sdp.dao.ConfigDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author Zhouce Chen
 * @version Feb 11, 2014
 */
@Repository
public class ConfigDaoImpl implements ConfigDao {


    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public SDPConfig getConfig() {
        String sql = "select * from config where id = 1";
        return jdbcTemplate.query(sql, new HashMap<String,String>(), new ConfigMapper()).get(0);
    }

    @Override
    public void setConfig(SDPConfig config) {
        String sql = "update config set location=:location, limit_size=:limit, exceed=:exceed where id = 1";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(config));
    }

    class ConfigMapper implements RowMapper<SDPConfig>{
        @Override
        public SDPConfig mapRow(ResultSet rs, int i) throws SQLException {
            SDPConfig config = new SDPConfig();
            config.setLocation(rs.getString("location"));
            config.setLimit(rs.getLong("limit_size"));
            config.setExceed(rs.getInt("exceed"));
            return config;
        }
    }

}
