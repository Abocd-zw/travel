package com.yun.service;

import com.yun.domain.Category;
import com.yun.domain.Route;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    List<Route> pageQueryRoutes(int cid, int pageNum, int pageSize,String rname);
}
