package com.lilei.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lilei.dao.CommentsDao;
import com.lilei.dao.DailyCheckDao;
import com.lilei.dao.FavorsDao;
import com.lilei.dao.NewsDao;
import com.lilei.dao.TrainDao;
import com.lilei.dao.UserDao;

public class BaseMobileServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6970143137677892748L;
	String ERROR = "{'info':'error'}";

	// 统一创建dao，由子类调用
	UserDao userDao = new UserDao();
	DailyCheckDao dailyCheckDao = new DailyCheckDao();
	CommentsDao commentsDao = new CommentsDao();
	FavorsDao favorsDao = new FavorsDao();
	NewsDao newsDao = new NewsDao();
	TrainDao trainDao = new TrainDao();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 设置编码
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			
			// 操作类型
			String methodName = request.getParameter("method");
			// 调用的子类
			Class clazz = this.getClass();
			// 通过反射执行方法
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 获取返回值
			Object returnValue = method.invoke(this, request, response);
			// 相应请求
			response.getWriter().write(returnValue.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(ERROR);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
