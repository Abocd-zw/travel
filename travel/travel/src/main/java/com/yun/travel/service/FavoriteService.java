package com.yun.travel.service;

import java.util.List;

import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;

public interface FavoriteService {

	void doFavorite(int rid, int uid);

	PageBean<Route> findMyFavorite(int uid,int currentPage,int PageSize);

	PageBean<Route> pageQueryFavorite(int currentPage,int pageSize);

}
