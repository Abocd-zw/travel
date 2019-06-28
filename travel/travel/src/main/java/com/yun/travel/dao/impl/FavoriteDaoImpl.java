package com.yun.travel.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yun.travel.dao.FavoriteDao;
import com.yun.travel.domain.Favorite;
import com.yun.travel.util.JDBCUtils;

public class FavoriteDaoImpl implements FavoriteDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public Favorite findByRidAndUid(int rid, int uid) {
		String sql = "select * from tab_favorite where rid=? and uid=?";
		Favorite f = null;
		try{
			f=template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
		}catch(Exception e){
			
		}
		return f;
	}
	@Override
	public void add(int rid, int uid) {
		String sql = "insert into tab_favorite values(?,?,?)";
		template.update(sql,rid,new Date(), uid);
	}
	@Override
	public int queryCountByRid(int rid) {
		String sql = "select count(*) from tab_favorite where rid=?";
		return template.queryForObject(sql, Integer.class,rid);
	}
	@Override
	public List<Integer> findByUid(int uid) {
		String sql = "select rid from tab_favorite where uid=?";
		return template.queryForList(sql,Integer.class ,uid);
	}

}
