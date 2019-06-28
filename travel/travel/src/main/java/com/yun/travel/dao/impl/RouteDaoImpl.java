package com.yun.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yun.travel.dao.RouteDao;
import com.yun.travel.domain.Route;
import com.yun.travel.util.JDBCUtils;

public class RouteDaoImpl implements RouteDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public Route findByRid(int rid) {
		String sql = "select * from tab_route where rid=?";
		Route route=null;
		try{
			route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
		}catch(Exception e){
			
		}
		return route;
	}
	@Override
	public void updateCountByRid(int count, int rid) {
		String sql = "update tab_route set count=? where rid=?";
		template.update(sql,count,rid);	
	}
	@Override
	public List<Route> pageQueryFavorite(int currentPage, int pageSize) {
		int start = (currentPage-1)*pageSize;
		String sql = "select * from tab_route order by count desc limit ?,?";
		List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),start,pageSize);
		return list;
	}

}
