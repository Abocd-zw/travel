package com.yun.travel.dao;

import java.util.List;

import com.yun.travel.domain.Route;

public interface RouteDao {

	Route findByRid(int  rid);

	void updateCountByRid(int count, int rid);

	List<Route> pageQueryFavorite(int currentPage, int pageSize);

}
