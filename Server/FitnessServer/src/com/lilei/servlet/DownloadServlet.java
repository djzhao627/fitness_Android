package com.lilei.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月6日 下午12:46:02
 */
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -1120930992495743033L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取操作类型
		String method = request.getParameter("method");
		if ("getNewsImage".equals(method)) {
			getNewsImage(request, response);
		}
	}

	public void getNewsImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("imageName");
		// 获取文件上传路径
		String basePath = "C:\\DJZHAO\\WORK\\Workspaces\\FileUpload\\fitness";
		// 获取文件
		File file = new File(basePath, fileName);
		// 判断文件是否存在
		if (!file.exists()) {
			response.getWriter().write("error");
			return;
		}
		// 获取一个文件流
		InputStream in = new FileInputStream(file);
		// 文件名中文编码处理
		fileName = URLEncoder.encode(fileName.substring(fileName.lastIndexOf('#') + 1), "UTF-8");
		// 设置下载的响应头
		response.setHeader("content-disposition", "attachment;filename=" + fileName);
		// 获取response输出字节流
		OutputStream out = response.getOutputStream();
		byte[] buff = new byte[1024];
		int len = -1;
		while ((len = in.read(buff)) != -1) {
			out.write(buff, 0, len);
		}
		// 关闭流
		out.close();
		in.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
