package com.yun.travel.service;

import java.util.List;

import com.yun.travel.domain.Category;
import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;

public interface CategoryService {

	List<Category> findAll();

	PageBean<Route> getPageBean(int cid, int currentPage, int pageSize,String rname);

}
