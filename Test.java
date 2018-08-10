package com.cdv.lada.common;

import java.io.IOException;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date startDate = new Date();
		DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
		try {
			pool.getFileWithThreadPool("http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3", "J:\\1.mp3", 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(new Date().getTime() - startDate.getTime());
	}

}
