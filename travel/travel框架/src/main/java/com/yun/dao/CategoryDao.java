package com.yun.dao;

import com.yun.domain.Category;
import com.yun.domain.Route;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {
    @Select("select * from tab_category")
    List<Category> findAll();
    @Select("select * from tab_category where cid=#{cid}")
    Category findById(int cid);

    @Select("select * from tab_route where cid=#{cid}")
    List<Route> findRouteByCid(int cid);

    @Select("select * from tab_route where rname like '%${value}%'}")
    List<Route> findRouteByRname(String rname);

    @Select("select * from tab_route where cid=#{cid} and rname like concat('%',#{rname},'%')")
    List<Route> findRouteByCidAndrname(@Param("cid")int cid, @Param("rname") String rname);
}
