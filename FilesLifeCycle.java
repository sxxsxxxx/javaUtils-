package com.cdv.lada.common.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

@Service("FilesLifeCycle")
public class FilesLifeCycle {

	@Value("${lada.filesLifeCycle.delShareFiles:false}")
	private boolean delShareFiles;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	String linuxDir = "/home/nsite/niste3/lada-";

	/**
	 * 对共享盘的文件扫描,把使用ASPERA下载方式腾讯云迁移到共享盘的文件做定时清除 扫描固定文件路径 每天对前一天的文件进行扫描删除
	 * 
	 * @param file
	 */

	@Scheduled(cron = "0 0/10 * * * ?") // 每天23点触发,删除前一天的文件
	private void delShareFiles() {

		/*
		 * if(!delShareFiles) { logger.info("生命周期删除回收站内稿件功能关闭!"); return; }
		 */
		// 拼接固定地址 绝对路径
		Calendar now = Calendar.getInstance();
		Date date = new Date();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		String month = (now.get(Calendar.MONTH) + 1 < 10) ? "0" + (now.get(Calendar.MONTH) + 1)
				: "" + (now.get(Calendar.MONTH) + 1);
		String day = (now.get(Calendar.DAY_OF_MONTH) + 1 < 10) ? "0" + now.get(Calendar.DAY_OF_MONTH)
				: "" + now.get(Calendar.DAY_OF_MONTH);
		String fileDir = "/home/nsite/nisite3/lada-" + now.get(Calendar.YEAR) + "-clips" + "/" + month + "-" + day;
		logger.debug("迁移文件删除文件地址生成{}", fileDir);
		// 删除文件
		java.io.File dirFile = new java.io.File(fileDir);
		if (dirFile.exists()) {
			logger.debug("{}目录下的迁移文件开始删除", fileDir);
			// 删除文件夹
			delFolder(fileDir);
			return;
		} else {
			logger.debug("{}目录地址不对", fileDir);
		}

	}

	private boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	private void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
