package com.yun.travel.dao;

import java.util.List;

import com.yun.travel.domain.Category;
import com.yun.travel.domain.Route;

public interface CategoryDao {

	List<Category> findAll();

	int findTotalCount(int cid,String rname);

	List<Route> findPageRoutes(int cid, int start, int pageSize,String rname);

	Category findByCid(int cid);

}
