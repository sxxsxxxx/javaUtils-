package com.sunUtils.commos.file;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadFileWithThreadPool {

    public void getFileWithThreadPool(String urlLocation, String filePath, int poolLength) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        long len = getContentLength(urlLocation);
        System.out.println(len);
        for (int i = 0; i < poolLength; i++) {
            long start = i * len / poolLength;
            long end = (i + 1) * len / poolLength - 1;
            if (i == poolLength - 1) {
                end = len;
            }
            System.out.println(start+"---------------"+end);
            DownloadWithRange download = new DownloadWithRange(urlLocation, filePath, start, end);
            threadPool.execute(download);
        }
        threadPool.shutdown();
    }

    public static long getContentLength(String urlLocation) throws IOException {
        URL url = null;
        if (urlLocation != null) {
            url = new URL(urlLocation);
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        long len = conn.getContentLength();

        return len;
    }

    public static void main(String[] args) {
        Date startDate = new Date();
        DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
        try {
            pool.getFileWithThreadPool("http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3", "D:\\1.mp3", 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new Date().getTime() - startDate.getTime());
    }
}
