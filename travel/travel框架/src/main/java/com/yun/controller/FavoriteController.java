package com.yun.controller;


import com.github.pagehelper.PageInfo;
import com.yun.domain.Route;
import com.yun.domain.User;
import com.yun.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/doFavorite")
    public @ResponseBody boolean doFavorite(@RequestParam(name = "rid") int rid, @RequestParam(name = "uid") int uid){
        favoriteService.doFavority(rid,uid);
        return false;
    }
    @RequestMapping("/pageQueryFavorite")
    public @ResponseBody PageInfo<Route> pageQueryFavorite(@RequestParam(name = "pageNum") int pageNum,@RequestParam(defaultValue = "10")int pageSize){

        List<Route> routes = favoriteService.pageQueryFavorite(pageNum, pageSize);
        PageInfo<Route> pageInfo = new PageInfo<>(routes);
        pageInfo.setTotal(100);
        pageInfo.setPages(10);
        return pageInfo;
    }
    @RequestMapping("/findMyFavorite")
    public @ResponseBody PageInfo<Route> findMyFavorite(HttpServletRequest request ,@RequestParam(name = "pageNum") int pageNum,@RequestParam(defaultValue = "12") int pageSize){
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        List<Route> myFavorite = favoriteService.findMyFavorite(uid,pageNum,pageSize);
        PageInfo<Route> pageInfo = new PageInfo<>(myFavorite);
        return pageInfo;
    }
}
