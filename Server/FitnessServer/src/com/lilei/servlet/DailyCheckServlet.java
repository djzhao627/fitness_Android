package com.lilei.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月2日 下午10:00:56
 */
public class DailyCheckServlet extends BaseMobileServlet {

	private static final long serialVersionUID = 4495375668511646732L;

	/**
	 * 打卡
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String check(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		if (dailyCheckDao.isChecked(userId)) {
			return "您今日已经打过卡了，无需再次打卡";
		} else {
			if (dailyCheckDao.check(userId)) {
				return "success";
			} else {
				return "暂时无法打卡";
			}
		}
	}

	// 获得打卡记录
	public String getCheckedList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String checkedList = dailyCheckDao.getCheckedList(userId);
		if (checkedList == null) {
			return ERROR;
		} else {
			return checkedList;
		}
	}

	public String getHomepageTotalRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		int checkedDays = dailyCheckDao.getTotalCheckedDays(userId);
		int trainingDays = dailyCheckDao.getTotalTrainingDays(userId);
		return checkedDays + ":" + trainingDays;
	}

}
