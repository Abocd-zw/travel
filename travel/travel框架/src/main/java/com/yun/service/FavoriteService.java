package com.yun.service;

import com.yun.domain.Route;

import java.util.List;

public interface FavoriteService {
    void doFavority(int rid, int uid);

    List<Route> pageQueryFavorite(int pageNum, int pageSize);

    List<Route> findMyFavorite(int uid ,int pageNum,int pageSize);
}
