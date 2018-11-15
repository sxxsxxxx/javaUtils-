package com.sunUtils.commos.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

import static com.sunUtils.commos.system.PropertyManager.getString;

/**
 * @author 9f
 */
public class JedisUtil {

    public static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private static JedisPool pool = null;

    private static String REDIS_HOST = "redis_host";

    private static String REDIS_PASSWORD = "redis_paswd";

    private static String REDIS_MAXIDLE = "redis_maxIdle";

    private static String REDIS_MAXTOTAL = "redis_maxtotal";

    private static String REDIS_TIMEOUT = "redis_timeout";

    private static String REDIS_PORT = "redis_port";

    private static String REDIS_PREFIX = "redis_prefix";

    private static String REDIS_DATABASE = "redis_database";

    public static String prefix = getString(REDIS_PREFIX);
    public static int database = Integer.valueOf(getString(REDIS_DATABASE));

    static {

        init();

    }

    private static int maxTotal = Integer.parseInt(getString(REDIS_MAXTOTAL));

    private static void init() {
        try {
            prefix = getString(REDIS_PREFIX);
            String password = getString(REDIS_PASSWORD);

            String host = getString(REDIS_HOST);

            int port = Integer.parseInt(getString(REDIS_PORT));

            int timeout = Integer.parseInt(getString(REDIS_TIMEOUT));
            int maxIdle = Integer.parseInt(getString(REDIS_MAXIDLE));
            int maxTotal = Integer.parseInt(getString(REDIS_MAXTOTAL));
            logger.info("redis 启动参数 前缀 :{},IP:{},port:{},timeout:{},maxidle:{},maxtotal:{}", prefix, host, port, timeout, maxIdle, maxTotal);
            JedisPoolConfig config = new JedisPoolConfig();

            try {
                config.setMaxIdle(maxIdle);
                // 最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
                config.setMaxTotal(maxTotal);
                // config.setMaxWaitMillis(maxWaitMillis); //在连接池取连接等待时间
            } catch (Exception e) {
                config.setMaxIdle(200);
                // 最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
                config.setMaxTotal(300);
            }

            // 最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数

            config.setTestOnBorrow(false);
            config.setTestOnReturn(false);

            // String host = "*.aliyuncs.com";
            // String password = "实例id:密码";

            //
            // String password = "";
            pool = new JedisPool(config, host, port, timeout, password);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static Jedis getResource() {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis != null) {
                jedis.select(database);
            }
            return jedis;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    public static void hSet(String key, String field, String value) {

        Jedis redis = getResource();
        try {
            redis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 为key设置过期时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {

        Jedis redis = getResource();
        try {
            redis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 获取一个map
     *
     * @param key
     * @return
     */
    public static Map<String, String> hgetAll(String key) {

        Jedis redis = getResource();
        Map<String, String> map = null;
        try {
            map = redis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        if (map != null) {
            return map;
        } else {
            return new LinkedHashMap<String, String>();
        }
    }

    /**
     * 判断是否存在field
     *
     * @param key
     * @param field
     * @return
     */
    public static boolean hexists(String key, String field) {

        Jedis redis = getResource();
        boolean b = false;
        try {
            b = redis.hexists(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return b;
    }

    public static Set<String> hKeys(String key) {

        Jedis redis = getResource();
        Set<String> set = null;
        try {
            set = redis.hkeys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        if (set != null) {
            return set;
        } else {
            return new LinkedHashSet<String>();
        }
    }

    /**
     * 向redis中赋值
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {

        Jedis redis = getResource();
        try {
            redis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 向redis中赋值 并设置过期时间
     *
     * @param key
     * @param value
     * @param nxxx
     * @param exxx
     * @param time
     */
    public static void set(String key, String value, String nxxx, String exxx, int time) {

        Jedis redis = getResource();
        try {
            redis.set(key, value, nxxx, exxx, time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 获取key的过期时间
     *
     * @param key
     * @return
     */
    public static Long pttl(String key) {
        Jedis redis = getResource();
        try {
            return redis.pttl(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return 0L;
    }

    /**
     * 向set集合中添加原始
     *
     * @param key
     * @param members
     */
    public static void sadd(String key, String... members) {

        Jedis redis = getResource();
        try {
            redis.sadd(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 取出set集合的元素
     *
     * @param key
     * @return
     */
    public static Set<String> smembers(String key) {

        Set<String> set = null;
        Jedis redis = getResource();
        try {
            set = redis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return set;
    }

    /**
     * set and expire
     *
     * @param key
     * @param value
     * @param seconds
     */
    public static void setExpire(String key, String value, int seconds) {

        Jedis redis = getResource();
        try {
            redis.set(key, value);
            redis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 在redis里取值
     *
     * @param key
     * @return
     */
    public static String get(String key) {

        Jedis redis = getResource();
        String value = null;
        try {
            value = redis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return value;
    }

    /**
     * 将任务放到队列尾
     *
     * @param key
     * @param value
     */
    public static void rpush(String key, String value) {
        Jedis redis = getResource();
        try {
            redis.rpush(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 将任务放到队列头
     *
     * @param key
     * @param value
     */
    public static void lpush(String key, String value) {
        Jedis redis = getResource();
        try {
            redis.lpush(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 将任务放到队列中
     *
     * @param key
     */
    public static String lpop(String key) {
        Jedis redis = getResource();
        String value = null;
        try {
            value = redis.lpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return value;
    }

    /**
     * 从队列左边取指定个数的元素
     */
    public static List<String> lrange(final String key, final long start, final long end) {
        Jedis redis = getResource();
        List<String> list = null;
        try {
            list = redis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return list;
    }

    /**
     * 获取hash的一根field的value
     *
     * @param key
     * @param field
     * @return
     */
    public static String hGet(String key, String field) {

        Jedis redis = getResource();
        String value = null;
        try {
            value = redis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return value;
    }

    /**
     * 删除hash的一根field
     *
     * @param key
     * @param field
     */
    public static void hDel(String key, String field) {

        Jedis redis = getResource();
        try {
            redis.hdel(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key) {

        boolean isexists = false;
        Jedis redis = getResource();
        try {
            isexists = redis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return isexists;
    }

    /**
     * 自增
     *
     * @param key
     * @return
     */
    public static long incr(String key) {

        long value = 0;
        Jedis redis = getResource();
        try {
            value = redis.incr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return value;
    }

    /**
     * 带有过期时间
     *
     * @param key
     * @param map
     */
    public static final void hmsetExpire(String key, Map<String, String> map) {
        hmsetExpire(key, map, 7 * 24 * 3600);
    }

    public static final void hmsetExpire(String key, Map<String, String> map, int seconds) {

        Jedis redis = null;
        try {
            redis = getResource();
            redis.del(key);
            redis.hmset(key, map);
            redis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("redis error(hmset):" + e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 自减
     *
     * @param key
     * @return
     */
    public static long decr(String key) {

        long value = 0;
        Jedis redis = getResource();
        try {
            value = redis.decr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return value;
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public static long del(String key) {

        long value = 0;
        Jedis redis = getResource();
        try {
            value = redis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            redis.close();
        }
        return value;
    }

    /**
     * 获取redis线程池使用状态，大于1则认为出现问题
     *
     * @return
     */
    public static double getRateOfPool() {
        return (pool.getNumActive() + pool.getNumWaiters()) * 1.00d / maxTotal;
    }


    public static void hmset(String taskKey, Map<String, String> map) {
        Jedis redis = null;
        try {
            redis = getResource();
            redis.hmset(taskKey, map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 增加存储在字段中存储由增量键哈希的数量<br>
     * 如果键不存在，新的key被哈希创建<br>
     * 如果字段不存在，值被设置为0之前进行操作。
     *
     * @param key
     * @param field
     * @param value
     * @return 回复整数，字段的增值操作后的值。
     */
    public static int hincrBy(String key, String field, long value) {
        Long result = 0L;
        Jedis redis = null;
        try {
            redis = getResource();
            result = redis.hincrBy(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
        return result.intValue();
    }

    public static void main(String[] args) {

        try {

            System.out.println("start setting 。。。。");
            Jedis jedis = JedisUtil.getResource();
            jedis.sadd("match_lid", "1001", "1002");

            // jedis.s
            // jedis.hset("match_lid", field, value);
            // jedis.hkeys(key);
            jedis.set("11", "22");
            // SSDBProxy db = SSDBProxyFactory.getInstance();
            // db.set("11", "11");
            System.out.println(" setting  end。。。。");
            System.out.println(pool.getNumWaiters());
            System.out.println(pool.getNumActive());
            System.out.println(pool.getNumIdle());
            jedis.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
