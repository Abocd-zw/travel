package com.yun.dao;

import com.yun.domain.Route;
import org.apache.ibatis.annotations.*;

public interface RouteDao {

    @Select("select * from tab_route where rid=#{rid}")
    @Results(id="routeMap",value = {
            @Result(id = true,column = "rid",property = "rid"),
            @Result(column = "rname",property = "rname"),
            @Result(column = "routeIntroduce",property = "routeIntroduce"),
            @Result(column = "price",property = "price"),
            @Result(column = "rflag",property = "rflag"),
            @Result(column = "rdate",property = "rdate"),
            @Result(column = "isThemeTour",property = "isThemeTour"),
            @Result(column = "count",property = "count"),
            @Result(column = "cid",property = "category", one = @One(select = "com.yun.dao.CategoryDao.findById")),
            @Result(column = "sid",property = "seller" ,one = @One(select ="com.yun.dao.SellerDao.findBySid")),
            @Result(column = "sourceId",property = "sourceId"),
            @Result(column = "rid",property = "routeImgList" , many = @Many(select = "com.yun.dao.RouteImgDao.findByRid")),
    })
    Route findRoute(int rid);

    @Update("update tab_route set count=#{count} where rid=#{rid}")
    void updateCountByRid(@Param("count") int count, @Param("rid")int rid);
}
