package com.yun.travel.dao;

import java.util.List;

import com.yun.travel.domain.Favorite;

public interface FavoriteDao {

	Favorite findByRidAndUid(int rid, int uid);

	void add(int rid, int uid);

	int queryCountByRid(int rid);

	List<Integer> findByUid(int uid);

}
