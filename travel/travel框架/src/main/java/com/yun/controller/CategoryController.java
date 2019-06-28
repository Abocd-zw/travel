package com.yun.controller;

import com.github.pagehelper.PageInfo;
import com.yun.domain.Category;
import com.yun.domain.Route;
import com.yun.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {           //分类

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAll")
    public @ResponseBody List<Category> findAll(){
        List<Category> list = categoryService.findAll();
        return list;
    }

    @RequestMapping("/pageQueryRoutes")
    public @ResponseBody PageInfo pageQueryRoutes(@RequestParam(required = true,name = "cid")int cid,String  rname,@RequestParam(required = true,name = "currentPage",defaultValue = "1")int pageNum, @RequestParam(required = true,name = "pageSize",defaultValue = "5")int pageSize) throws UnsupportedEncodingException {
        if("null".equals(rname)){
            rname=null;
        }else{
            rname = new String (rname.getBytes("iso-8859-1"),"utf-8");
        }
        List<Route> list = categoryService.pageQueryRoutes(cid,pageNum,pageSize,rname);
        PageInfo<Route> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
