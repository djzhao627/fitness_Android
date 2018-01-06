package com.lilei.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月6日 下午9:39:55
 */
public class TrainServlet extends BaseMobileServlet {

	private static final long serialVersionUID = -4111383568403919681L;

	public String addNewTrainRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String duration = request.getParameter("duration");
		if (trainDao.addNewTrainRecord(userId, duration)) {
			return "success";
		} else {
			return "error";
		}
	}


}
