package com.lilei.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.lilei.entity.Comments;
import com.lilei.entity.News;
import com.lilei.entity.NewsDetail;
import com.lilei.entity.NewsListForFound;


/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月5日 下午7:24:09
 */
public class NewsServlet extends BaseMobileServlet {

	private static final long serialVersionUID = -6810618231374558214L;

	public String releaseNewsWithImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1.创建工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 【设置单文件最大值：5M】
		upload.setFileSizeMax(5 * 1024 * 1024);

		// 【设置总文件最大值： 20M】
		upload.setSizeMax(20 * 1024 * 1024);

		upload.setHeaderEncoding("utf-8");

		News news = new News();

		try {
			// 4.遍历表单项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 普通表单项
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (name.equals("userId")) {
						news.setUserId(Integer.parseInt(value));
					} else if (name.equals("title")) {
						news.setTitle(value);
					} else if (name.equals("content")) {
						news.setContent(value);
					}
					System.out.println(name + " : " + value);
				} else {// 文件表单项
					// 文件名
					String fileName = item.getName();
					// 生成唯一文件名
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					news.setImage(fileName);

					// 获取上传路径：项目目录下的upload文件夹(先创建upload文件夹)
					String basePath = "C:\\DJZHAO\\WORK\\Workspaces\\FileUpload\\fitness";

					// 创建文件对象
					File file = new File(basePath, fileName);

					// 写文件（保存）
					item.write(file);

					// 删除临时文件
					item.delete();
				}
			}

			if (newsDao.releaseNews(news)) {
				return "success";
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String releaseNewsWithoutImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String userId = request.getParameter("userId");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			News news = new News();
			news.setContent(content);
			news.setTitle(title);
			news.setUserId(Integer.parseInt(userId));
			if (newsDao.releaseNews(news)) {
				return "success";
			}
		return "error";
	}
	
	public String getNewsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<NewsListForFound> newsList = newsDao.getNewsList(10);
		Gson gson = new Gson();
		String json = gson.toJson(newsList);
		return json;
	}
	
	public String getNewsDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsId = request.getParameter("newsId");
		NewsDetail newsDetail = newsDao.getNewsDetail(newsId);
		if (newsDetail != null) {
			List<Comments> comments = commentsDao.getCommentsByNewsId(newsId);
			newsDetail.setComments(comments);
			Gson gson = new Gson();
			String json = gson.toJson(newsDetail);
			return json;
		} else {
			return ERROR;
		}
	}
}
