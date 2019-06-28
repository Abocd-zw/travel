package com.yun.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yun.travel.dao.SellerDao;
import com.yun.travel.domain.Seller;
import com.yun.travel.util.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public Seller findBySid(int sid) {
		String sql = "select * from tab_seller where sid=?";
		Seller seller  = null;
		try{
			seller  = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
		}catch(Exception e){}
		return seller;
	}

}
