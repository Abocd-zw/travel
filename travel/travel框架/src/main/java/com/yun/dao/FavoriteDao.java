package com.yun.dao;

import com.yun.domain.Favorite;
import com.yun.domain.Route;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface FavoriteDao {
    @Select("select * from tab_favorite where rid=#{rid} and uid=#{uid} ")
    Favorite findByRidAndUid(@Param("rid") int rid, @Param("uid")int uid);

    @Insert("insert into tab_favorite values(#{rid},#{date},#{uid})")
    void save(@Param("rid")int rid, @Param("date") Date date, @Param("uid")int uid);

    @Select("select count(*) from tab_favorite where rid=#{rid}")
    int queryCountByRid(int rid);

    @Select("select * from tab_route order by count desc")
    List<Route> findAll();

    @Select("select * from tab_route where rid in (select rid from tab_favorite where uid=#{uid} order by date desc)")
    List<Route> findMyFavorite(int uid);
}
