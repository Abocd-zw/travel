package com.yun.travel.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.travel.domain.ResultInfo;
import com.yun.travel.domain.User;
import com.yun.travel.service.UserService;
import com.yun.travel.service.impl.UserServiceImpl;

public class UserServlet extends BaseServlet {

	/**
	 * 登录方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1,校验验证码
		String checkCode = (String) request.getSession().getAttribute("checkCode");
		String vCode = request.getParameter("check");
		ResultInfo info = new ResultInfo();
		ObjectMapper om = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		if (vCode != null && !vCode.equalsIgnoreCase(checkCode)) {
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			om.writeValue(response.getOutputStream(), info);
			return;
		}
		// 校验用户名和密码
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		UserService sevice = new UserServiceImpl();
		User u = sevice.login(user);
		if (u != null) {
			if ("Y".equals(u.getStatus())) {
				info.setFlag(true);
				request.getSession().setAttribute("user", u);
			} else {
				info.setFlag(false);
				info.setErrorMsg("用户未激活，请先激活");
			}
		} else {
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误");
		}
		String loginJson = om.writeValueAsString(info);
		response.getWriter().write(loginJson);
	}

	/**
	 * 注册方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 创建封装结果类,用于封装结果
		ResultInfo info = new ResultInfo();
		// 创建ObjectMapper,用于将结果类转换成json语句;
		ObjectMapper om = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		// 获取用户输入的校验码
		String vcode = request.getParameter("check");
		// 获取后台生成传给前台页面的校验码
		String checkCode = (String) request.getSession().getAttribute("checkCode");
		if (vcode != null && !vcode.equalsIgnoreCase(checkCode)) {
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			String infoJson = om.writeValueAsString(info);
			response.getWriter().write(infoJson);
			return;
		}
		// 接收用户数据
		Map<String, String[]> map = request.getParameterMap();
		// 将用户数据封装到user中
		User user = new User();

		try {
			BeanUtils.populate(user, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
		// 调用service的注册方法
		UserService service = new UserServiceImpl();
		boolean flag = service.regist(user);
		if (flag) {
			info.setFlag(flag);
		} else {
			info.setFlag(flag);
			info.setErrorMsg("用户名已存在");
		}
		String infoJson = om.writeValueAsString(info);
		response.getWriter().write(infoJson);
	}

	/**
	 * 激活用户
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 接收激活码
		String code = request.getParameter("code");
		// 调用service 的激活方法
		UserServiceImpl service = new UserServiceImpl();
		boolean flag;
		if (code != null) {
			flag = service.active(code);
			PrintWriter out = response.getWriter();
			if (flag) {
				out.write("激活成功,请<a href = 'http://192.168.43.181/travel/login.html'>登录</a>");
			} else {
				out.write("激活失败");
			}
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

		// 3. 写到响应的流中
		ImageIO.write(image, "png", response.getOutputStream());
	}

	private String getRandomCode() {
		List<Character> chs = new ArrayList<Character>();
		for (char i = 'a'; i <= 'z'; i++) {
			chs.add(i);
		}
		for (char i = 'A'; i <= 'Z'; i++) {
			chs.add(i);
		}
		for (char i = '0'; i <= '9'; i++) {
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

	/**
	 * 查找session中的用户
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo info = new ResultInfo();
		response.setContentType("application/json;charset=utf-8");
		if (user != null) {
			info.setData(user);
			info.setFlag(true);
		} else {
			info.setFlag(false);
		}
		ObjectMapper om = new ObjectMapper();
		String userJson = om.writeValueAsString(info);
		response.getWriter().write(userJson);
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		// 清除session
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

}
