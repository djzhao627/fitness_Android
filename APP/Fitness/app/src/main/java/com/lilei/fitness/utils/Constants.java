package com.lilei.fitness.utils;

import java.io.File;
import java.util.List;

import android.os.Environment;

import com.lilei.fitness.entity.User;

public class Constants {
	/**
	 ******************************************* 参数设置信息开始 ******************************************
	 */

	// 用户对象
	public static User USER = new User();

	public static List<String> DAILYCHECKEDLIST;

	// 应用名称
	public static String APP_NAME = "";

	// 服务器地址
	public static String BASE_URL = "http://172.16.17.117:8080/FitnessServer/";

	// 图片路径
	public static final String IMAGE_URL = "http://58.211.5.34:8080/studioms/staticmedia/images/#";

	// 视频路径
	public static final String VIDEO_URL = "http://58.211.5.34:8080/studioms/staticmedia/video/#";

	// 保存参数文件夹名称
	public static final String SHARED_PREFERENCE_NAME = "fitness_prefs";

	// SDCard路径
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "com.lilei.fitness";

	// 图片存储路径
	public static final String BASE_PATH = SD_PATH + "/images";

	// 缓存图片路径
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "/cache";

	// 需要分享的图片
	public static final String SHARE_FILE = BASE_PATH + "/QrShareImage.png";

	// 手机IMEI号码
	public static String IMEI = "";

	// 手机号码
	public static String TEL = "";

	// 屏幕高度
	public static int SCREEN_HEIGHT = 800;

	// 屏幕宽度
	public static int SCREEN_WIDTH = 480;

	// 屏幕密度
	public static float SCREEN_DENSITY = 1.5f;

	// 分享成功
	public static final int SHARE_SUCCESS = 0X1000;

	// 分享取消
	public static final int SHARE_CANCEL = 0X2000;

	// 分享失败
	public static final int SHARE_ERROR = 0X3000;

	// 开始执行
	public static final int EXECUTE_LOADING = 0X4000;

	// 正在执行
	public static final int EXECUTE_SUCCESS = 0X5000;

	// 执行完成
	public static final int EXECUTE_FAILED = 0X6000;

	// 加载数据成功
	public static final int LOAD_DATA_SUCCESS = 0X7000;

	// 加载数据失败
	public static final int LOAD_DATA_ERROR = 0X8000;

	// 动态加载数据
	public static final int SET_DATA = 0X9000;

	// 未登录
	public static final int NONE_LOGIN = 0X10000;

	/**
	 ******************************************* 参数设置信息结束 ******************************************
	 */
}
