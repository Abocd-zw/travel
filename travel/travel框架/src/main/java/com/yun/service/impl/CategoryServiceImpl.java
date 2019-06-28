package com.yun.service.impl;

import com.github.pagehelper.PageHelper;
import com.yun.dao.CategoryDao;
import com.yun.domain.Category;
import com.yun.domain.Route;
import com.yun.service.CategoryService;
import com.yun.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //从缓存中获取categorys数据
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //判断categorys是否为空
        if(categorys!=null && categorys.size()>0){
            list = new ArrayList<>();
            for (Tuple tuple : categorys) {
                Category c = new Category();
                c.setCname(tuple.getElement());
                c.setCid((int)tuple.getScore());
                list.add(c);
            }
        }else{
            System.out.println("从mysql中读取数据");
            list = categoryDao.findAll();
            for (Category category : list) {
                jedis.zadd("category", category.getCid(),category.getCname());
            }
        }
        return list;
    }

    @Override
    public List<Route> pageQueryRoutes(int cid, int pageNum, int pageSize,String rname) {
        PageHelper.startPage(pageNum, pageSize);
        if (rname == null) {
            return categoryDao.findRouteByCid(cid);
        }
        if (cid == 0) {
            return categoryDao.findRouteByRname(rname);
        }else{
            return categoryDao.findRouteByCidAndrname(cid,rname);
        }
    }

}
