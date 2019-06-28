package com.yun.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yun.travel.dao.RouteImgDao;
import com.yun.travel.domain.RouteImg;
import com.yun.travel.util.JDBCUtils;

public class RouteImgDaoImpl implements RouteImgDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<RouteImg> findImgByRid(int  rid) {
		String sql = "select * from tab_route_img where rid=?";
		return  template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
	}

}
