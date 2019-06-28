package com.yun.travel.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yun.travel.dao.CategoryDao;
import com.yun.travel.domain.Category;
import com.yun.travel.domain.Route;
import com.yun.travel.util.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<Category> findAll() {
		String sql = "select * from tab_category";
		return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
	}
	@Override
	public int findTotalCount(int cid,String rname) {
		//String sql = "select count(*) from tab_route where cid=?";
		String sql = "select count(*) from tab_route where 1=1";
		StringBuilder sb = new StringBuilder(sql);
		List<Object> list = new ArrayList<>();
		if(cid!=0){
			sb.append(" and cid=?");
			list.add(cid);
		}
		if(rname!=null && rname.length()>0) {
			sb.append(" and rname like ?");
			list.add("%"+rname+"%");
		}
		sql=sb.toString();
		return template.queryForObject(sql, Integer.class,list.toArray());	 
	}
	@Override
	public List<Route> findPageRoutes(int cid, int start, int pageSize,String rname) {
		//String sql = "select * from tab_route where cid=? limit ?,?";
		String sql = "select * from tab_route where 1=1";
		StringBuilder sb = new StringBuilder(sql);
		List<Object> list = new ArrayList<>();
		if(cid!=0){
			sb.append(" and cid=?");
			list.add(cid);
		}
		if(rname!=null && rname.length()>0) {
			sb.append(" and rname like ?");
			list.add("%"+rname+"%");
		}
		sb.append(" limit ?,?");
		list.add(start);
		list.add(pageSize);
		List<Route> result = template.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
		return result;
	}
	@Override
	public Category findByCid(int cid) {
		String sql = "select * from tab_category where cid=?";
		Category category = null;
		try{
			category = template.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class),cid);
		}catch(Exception e){}
		return category;
	}

}
