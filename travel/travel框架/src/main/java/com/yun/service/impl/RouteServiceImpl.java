package com.yun.service.impl;

import com.yun.dao.FavoriteDao;
import com.yun.dao.RouteDao;
import com.yun.domain.Favorite;
import com.yun.domain.Route;
import com.yun.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;

    @Autowired
    private FavoriteDao favoriteDao;
    @Override
    public Route findRoute(int rid) {
        return routeDao.findRoute(rid);
    }

    @Override
    public boolean isRed(int rid, int uid) {
        Favorite f =  favoriteDao.findByRidAndUid(rid,uid);
        return f==null;
    }
}
