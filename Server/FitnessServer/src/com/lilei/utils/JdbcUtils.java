package com.lilei.utils;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 常用JDBC操作。工具类。
 * 
 */
public class JdbcUtils {

	/** 初始化连接池 */
	private static DataSource dataSource;
	static {
		dataSource = new ComboPooledDataSource();
	}
	
	/** 创建DbUtils常用工具类对象 */
	
	/**
	 * 获取queryRunner。
	 * 
	 * @return
	 */
	public static QueryRunner getQueryRunnner() {
		return new QueryRunner(dataSource);
	}
	
	/**
	 * 获取dataSource。
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
}
