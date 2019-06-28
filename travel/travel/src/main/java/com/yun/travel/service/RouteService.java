package com.yun.travel.service;

import com.yun.travel.domain.Route;

public interface RouteService {

	Route findRoute(int  rid);

	boolean isRed(int rid, int uid);

}
