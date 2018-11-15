package com.sunUtils.commos.poi;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author dengtongcai Email: dengtongcai@9fbank.com.cn
 * @Title: PDFBoxUtil.java
 * @Description: 读取PDF文件工具类
 * @date 2017年1月4日 上午11:15:32
 */
public class PDFBoxUtil {
    private static Logger logger = LoggerFactory.getLogger(PDFBoxUtil.class);

    /**
     * 将pdf文件内容输出为String,去除前15行空白乱数据
     *
     * @param pdfpath
     * @return
     */
    public static String PDFtoString(String pdfpath) {
        PDDocument pd;
        try {
            File input = new File(pdfpath);
            pd = PDDocument.load(input);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pd);
            if (pd != null) {
                pd.close();
            }
            String seprators = subSeprators(text, 15);
            return text.replace(seprators, "");
        } catch (Exception e) {
            logger.error("{},文件读取失败! {}", pdfpath, e);
        }
        return "文件读取失败";
    }

    /**
     * 读取pdf不作处理直接返回
     *
     * @param pdfpath
     * @return
     */
    public static String PDFtoOrigin(String pdfpath) {
        PDDocument pd;
        try {
            File input = new File(pdfpath);
            pd = PDDocument.load(input);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pd);
            if (pd != null) {
                pd.close();
            }
            return text;
        } catch (Exception e) {
            logger.error("{},文件读取失败! {}", pdfpath, e);
        }
        return "文件读取失败";
    }

    /**
     * subSeprators:(这里用一句话描述这个方法的作用).
     *
     * @param str
     * @param n
     * @return
     * @since JDK 1.7
     */
    private static String subSeprators(String str, int n) {
        int i = 0;
        int s = 0;
        while (i++ < n) {
            s = str.indexOf(System.lineSeparator(), s + 1);
            if (s == -1) {
                return str;
            }
        }
        return str.substring(0, s);
    }

    /**
     * 北京电信去掉类似无用文本：用户号码：18911568334 下载时间：2017-03-23 17:50
     *
     * @param text
     * @param loginNum
     * @param downloadTime
     * @return
     */
    public static String splitExtra(String text, String loginNum, String downloadTime) {
        StringBuilder builder = new StringBuilder();
        String[] textArray = text.split(System.lineSeparator());
        ArrayList<String> origin = new ArrayList<>();
        for (String line : textArray) {
            if (!(line.contains(loginNum) && line.contains(downloadTime))) {
                origin.add(line);
            }
        }
        for (String line : origin) {
            builder.append(line + System.lineSeparator());
        }
        return builder.toString();
    }

    /**
     * 这是一个简单的PDF读取方法，后续需要对输出的文字格式进行编辑 输入：PDF路径 输出：PDF文件中读取的文字
     *
     * @throws IOException
     */
    public static String getTextFromPDF(String pdfFilePath) throws IOException {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            // 使用文件输入流读取PDF存放路径
            is = new FileInputStream(pdfFilePath);
            // 将输入流传入进行转换器PDFParser
            PDFParser parser = new PDFParser(new RandomAccessBuffer(is));
            parser.parse();
            // 从转换器获得PDDocument
            document = parser.getPDDocument();
            // 将PDDocument放入文件剥离器PDFTextStripper剥离文字
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置文本是否按照位置排序
            stripper.setSortByPosition(true);
            // 设置起始页
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            result = stripper.getText(document);
        } catch (IOException e) {
            throw new IOException("文件读取失败");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new IOException("文件读取失败");
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    throw new IOException("文件读取失败");
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String pdfpath = "C:\\Users\\dengtongcai\\Downloads\\20170323175017067-18911568334.pdf";
        String text = PDFtoOrigin(pdfpath);
        String pdFtoString = splitExtra(text, "登陆号码", "下载时间");
        System.out.println(pdFtoString);
    }
}
