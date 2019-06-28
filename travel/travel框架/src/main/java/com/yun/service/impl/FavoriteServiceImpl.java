package com.yun.service.impl;

import com.github.pagehelper.PageHelper;
import com.yun.dao.FavoriteDao;
import com.yun.dao.RouteDao;
import com.yun.domain.Route;
import com.yun.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private RouteDao routeDao;
    @Override
    public void doFavority(int rid, int uid) {
        Date date = new Date();
        favoriteDao.save(rid,date,uid);

        //查询收藏次数
       int count =  favoriteDao.queryCountByRid(rid);
        routeDao.updateCountByRid(count,rid);
    }

    @Override
    public  List<Route> pageQueryFavorite(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
         return  favoriteDao.findAll();
    }

    @Override
    public List<Route> findMyFavorite(int uid ,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return favoriteDao.findMyFavorite(uid);
    }
}
