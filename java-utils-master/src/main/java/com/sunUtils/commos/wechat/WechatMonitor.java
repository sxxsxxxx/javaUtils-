package com.sunUtils.commos.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunUtils.commos.database.JedisUtil;
import com.sunUtils.commos.http.sync.HttpSyncClient;
import com.sunUtils.commos.system.PropertyManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 需要：redis用来保存实时获取的token
 *
 * @author dengtongcai
 * @date 2018/2/5
 */
public class WechatMonitor {
    private static Logger logger = LoggerFactory.getLogger(WechatMonitor.class);

    /**
     * access_token在redis保存key
     */
    private static final String ACCESS_TOKEN_KEY = JedisUtil.prefix + "WechatMonitor_access_token";
    /**
     * 企业号
     */
    private static final String CORPID = PropertyManager.getString("corpid");
    /**
     * 应用密钥
     */
    private static final String CORPSECRET = PropertyManager.getString("corpsecret");
    /**
     * 获取access_token的api
     */
    private static final String ACCESS_TOKEN_URL = PropertyManager.getString("access_token_url");
    /**
     * 发送监控消息的api
     */
    private static final String SEND_URL = PropertyManager.getString("send_url");
    /**
     * 发送应用编号
     */
    private static final String AGENTID = PropertyManager.getString("agentid");


    /**
     * 简单使用：向所有人发送TXT消息
     * <p>文本消息支持换行、以及A标签，即可打开自定义的网页（可参考以上示例代码）(注意：换行符请用转义过的\n)<p/>
     *
     * @param msg 消息内容，最长不超过2048个字节
     * @return
     */
    public static String sendTxt(String msg) {
        //消息内容
        HashMap<String, String> text = new HashMap<>(1);
        text.put("content", msg);

        //发送实体
        HashMap<String, Object> body = new HashMap<>(7);
        body.put("touser", "@all");
        body.put("toparty", "");
        body.put("totag", "");
        body.put("msgtype", "text");
        body.put("agentid", Integer.parseInt(AGENTID));
        body.put("text", text);
        body.put("safe", 0);

        return send2Server(body);
    }


    /**
     * 常规使用：发送TXT消息,自定义成员列表、部门、标签、是否加密
     * <p>文本消息支持换行、以及A标签，即可打开自定义的网页（可参考以上示例代码）(注意：换行符请用转义过的\n)<p/>
     *
     * @param msg    消息内容，最长不超过2048个字节
     * @param config party 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当user为@all时忽略本参数
     *               tag   标签ID列表，多个接收者用‘|’分隔，最多支持100个。当user为@all时忽略本参数
     *               user  成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
     *               safe  表示是否是保密消息，0表示否，1表示是，默认0
     * @return
     */
    public static String sendTxt(String msg, Map<String, String> config) {
        String party = (String) config.get("party");
        String tag = (String) config.get("tag");
        String user = (String) config.get("user");
        int safe = Integer.parseInt(config.get("safe"));
        //消息内容
        HashMap<String, String> text = new HashMap<>(1);
        text.put("content", msg);

        //发送实体
        HashMap<String, Object> body = new HashMap<>(7);
        body.put("touser", user);
        body.put("toparty", party);
        body.put("totag", tag);
        body.put("msgtype", "text");
        body.put("agentid", Integer.parseInt(AGENTID));
        body.put("text", text);
        body.put("safe", safe);

        return send2Server(body);
    }

    /**
     * 卡片消息：给指定成员发送
     * 卡片消息的展现形式非常灵活，支持使用br标签或者空格来进行换行处理，
     * 也支持使用div标签来使用不同的字体颜色，
     * 目前内置了3种文字颜色：灰色(gray)、高亮(highlight)、默认黑色(normal)，
     * 将其作为div标签的class属性即可，具体用法请参考上面的示例。
     *
     * @param msg    title       标题，不超过128个字节，超过会自动截断
     *               description 描述，不超过512个字节，超过会自动截断
     *               url         点击后跳转的链接。
     *               btntxt      按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。
     * @param config party 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当user为@all时忽略本参数
     *               tag   标签ID列表，多个接收者用‘|’分隔，最多支持100个。当user为@all时忽略本参数
     *               user  成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
     *               safe  表示是否是保密消息，0表示否，1表示是，默认0
     * @return
     */
    public static String sendTxtCard(Map<String, String> msg, Map<String, String> config) {
        String party = (String) config.get("party");
        String tag = (String) config.get("tag");
        String user = (String) config.get("user");
        int safe = Integer.parseInt(config.get("safe"));
        //消息内容
        HashMap<String, String> text = new HashMap<>(4);
        text.put("title", msg.get("title"));
        text.put("description", msg.get("description"));
        text.put("btntxt", msg.get("btntxt"));
        text.put("url", msg.get("url"));

        //发送实体
        HashMap<String, Object> body = new HashMap<>(7);
        body.put("touser", user);
        body.put("toparty", party);
        body.put("totag", tag);
        body.put("msgtype", "textcard");
        body.put("agentid", Integer.parseInt(AGENTID));
        body.put("text", text);
        body.put("safe", safe);

        return send2Server(body);
    }

    public static String sendTxtCard(Map<String, String> msg) {
        //消息内容
        HashMap<String, String> text = new HashMap<>(4);
        text.put("title", msg.get("title"));
        text.put("description", msg.get("description"));
        text.put("btntxt", msg.get("btntxt"));
        text.put("url", msg.get("url"));

        //发送实体
        HashMap<String, Object> body = new HashMap<>(7);
        body.put("touser", "@all");
        body.put("toparty", "");
        body.put("totag", "");
        body.put("msgtype", "textcard");
        body.put("agentid", Integer.parseInt(AGENTID));
        body.put("text", text);
        body.put("safe", 0);

        return send2Server(body);
    }

    /**
     * 发送消息
     *
     * @param body 文本消息内容
     * @return
     */
    private static String send2Server(HashMap<String, Object> body) {
        String accessToken = ensureAccesstoken();
        String sendUrl = String.format(SEND_URL, accessToken);
        String response = "";
        HttpSyncClient httpClient = null;
        try {
            httpClient = new HttpSyncClient();
            response = httpClient.httpSyncPost(sendUrl, null, JSON.toJSONString(body));

            //打印错误信息
            JSONObject jsonObject = JSON.parseObject(response);
            int errcode = jsonObject.getIntValue("errcode");
            if (errcode != 0) {
                logger.error("WechatMonitor 发送消息 失败:【{}】", jsonObject.getString("errmsg"));
            }
        } catch (Exception e) {
            logger.error("WechatMonitor 发送消息 出现异常:【{}】", e);
        } finally {
            httpClient.close();
            return response;
        }
    }

    /**
     * 获取更新access_token
     *
     * @return accessToken
     */
    private static String ensureAccesstoken() {
        String accessToken = null;
        HttpSyncClient httpClient = null;
        try {
            accessToken = JedisUtil.get(ACCESS_TOKEN_KEY);

            //如果缓存中没有权限信息,重新生成
            if (StringUtils.isBlank(accessToken)) {
                String accessTokenUrl = String.format(ACCESS_TOKEN_URL, CORPID, CORPSECRET);

                httpClient = new HttpSyncClient();
                String s = httpClient.httpSyncGet(accessTokenUrl);

                JSONObject json = JSON.parseObject(s);
                int errcode = json.getIntValue("errcode");
                if (errcode == 0) {

                    accessToken = json.getString("access_token");
                    int expires = json.getIntValue("expires_in");

                    //保存redis
                    JedisUtil.setExpire(ACCESS_TOKEN_KEY, accessToken, (expires - 300));
                } else {
                    logger.error("WechatMonitor 获取 access_token 失败:【{}】", json.getString("errmsg"));
                }
            }
        } catch (Exception e) {
            logger.error("WechatMonitor 获取 access_token 异常:【{}】", e);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            return accessToken;
        }
    }
}
