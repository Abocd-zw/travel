package com.yun.controller;

import com.yun.domain.Route;
import com.yun.domain.User;
import com.yun.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/route")
@Controller
public class RouteController {
    @Autowired
    private RouteService routeService;

    @RequestMapping("/findRoute")
    public @ResponseBody Route findRoute(@RequestParam(name = "rid") int rid){
        return  routeService.findRoute(rid);
    }
    @RequestMapping("/isRed")
    public @ResponseBody boolean isRed(@RequestParam(name = "rid") int rid, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        boolean isRed;
        if(user!=null){
            int uid = user.getUid();
            isRed = routeService.isRed(rid,uid);
        }else{
            isRed=true;
        }
        return isRed;
    }
}
