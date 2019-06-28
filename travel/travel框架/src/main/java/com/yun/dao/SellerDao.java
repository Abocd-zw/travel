package com.yun.dao;

import com.yun.domain.Seller;
import org.apache.ibatis.annotations.Select;

public interface SellerDao {
    @Select("select * from tab_seller where sid=#{sid}")
    Seller findBySid(int sid);
}
