package com.yun.service;

import com.yun.domain.Route;

public interface RouteService {
    Route findRoute(int rid);

    boolean isRed(int rid, int uid);
}
