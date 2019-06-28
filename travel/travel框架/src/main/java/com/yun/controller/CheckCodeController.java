package com.yun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CheckCodeController {
    @RequestMapping("/checkCode")
    public void  checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 创建图片对象
        int width = 80;
        int height = 35;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 2. 获取画笔对象
        Graphics g = image.getGraphics();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体", Font.BOLD, 26));
        String checkCode = getRandomCode();
        request.getSession().setAttribute("checkCode", checkCode);
        g.drawString(checkCode, 10, 30);

        //3. 写到响应的流中
        ImageIO.write(image, "png", response.getOutputStream());
    }
    private String getRandomCode() {
       List<Character> chs = new ArrayList<>();
        for (char i = 'a'; i <='z'; i++) {
            chs.add(i);
        }
        for (char i = 'A'; i <='Z'; i++) {
            chs.add(i);
        }
        for (char i = '0'; i <='9'; i++) {
            chs.add(i);
        }

        StringBuilder sb = new StringBuilder();
        int size = chs.size();
        for (int i = 0; i < size; i++) {
            int randomIndex = new Random().nextInt(size);
            String strCh = chs.get(randomIndex) + "";
            if (sb.length() < 4 && !sb.toString().contains(strCh)) {
                sb.append(strCh);
            }
        }
        return sb.toString();
    }
}
