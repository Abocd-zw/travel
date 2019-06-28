package com.yun.dao;

import com.yun.domain.RouteImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteImgDao {
    @Select("select * from tab_route_img where rid=#{rid} ")
    List<RouteImg> findByRid(int rid);
}
