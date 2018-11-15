package com.sunUtils.commos.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 配置文件中读取变量
 *
 * @author 9f
 */
public class PropertyManager {

    private static Logger log = LoggerFactory.getLogger(PropertyManager.class);
    private static final String CONFIG = "config";
    /**
     * 将key和value缓存到内存里
     */
    private static final Map<String, String> PROPERTIES = new LinkedHashMap<>();

    /**
     * 读取默认的classpath下config.properties文件
     *
     * @param key key
     * @return value value
     */
    public static String getString(String key) {
        return getRB(CONFIG, key);
    }

    /**
     * 读取指定为rb.properties文件
     *
     * @param rb  property 文件，文件名为 rb.properties
     * @param key key
     * @return value 键值
     */
    public static String getRB(String rb, String key) {
        try {
            String rbKey = rb + "_" + key;
            if (PROPERTIES.containsKey(rbKey)) {
                return PROPERTIES.get(rbKey);
            }
            String value = ResourceBundle.getBundle(rb).getString(key);
            PROPERTIES.put(rbKey, value);
            return value;
        } catch (MissingResourceException e) {
            log.error("读取属性失败,目标文件:{},目标属性:{}", rb, key);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(PropertyManager.getString("queueurl"));
    }
}
