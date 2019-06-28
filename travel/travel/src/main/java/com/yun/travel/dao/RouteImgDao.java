package com.yun.travel.dao;

import java.util.List;

import com.yun.travel.domain.RouteImg;

public interface RouteImgDao {

	List<RouteImg> findImgByRid(int rid);

}
