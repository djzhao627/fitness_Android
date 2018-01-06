package com.lilei.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lilei.entity.Comments;
import com.lilei.entity.NewsListItem;
import com.lilei.utils.JdbcUtils;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月5日 上午7:20:35
 */
public class CommentsDao {

	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	
	public List<NewsListItem> getCommentsList(String userId) {
		try {
			sql= "SELECT DISTINCT c.newsId, title, username FROM `user` a, news b, comments c WHERE a.userId = b.userId AND b.newsId = c.newsId AND a.userId = ?;";
			return queryRunner.query(sql, new BeanListHandler<NewsListItem>(NewsListItem.class), userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Comments> getCommentsByNewsId(String newsId) {
		try {
			sql= "SELECT commentId, username, replyUser, `comment`, commentTime FROM `user` a, comments b WHERE a.userId = b.userId AND newsId = ? ORDER BY commentTime DESC;";
			return queryRunner.query(sql, new BeanListHandler<Comments>(Comments.class), newsId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean addNewComment(String userId, String newsId, String comment, String replyUser) {
		try {
			sql= "INSERT INTO comments (newsId, userId, comment, replyUser) VALUES (?, ?, ?, ?);";
			return queryRunner.update(sql, newsId, userId, comment, replyUser) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
