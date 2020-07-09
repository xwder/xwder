package com.xwder.app.config.cache;

import com.xwder.app.attribute.SysConfigAttribute;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * ehcache service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@Component
public class EhCacheService {

    private CacheManager cacheManager;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    // 默认的缓存存在时间（秒）
    private static final int DEFAULT_LIVE_SECOND = 20 * 60;

    private static final String path = "/ehcache.xml";

    private URL url;

    /**
     * 获取缓存管理器
     *
     * @return
     */
    @Bean
    public CacheManager getCacheManager() {
        url = getClass().getResource(path);
        cacheManager = CacheManager.create(url);
        // 将CacheManager注册为bean，供缓存工具类使用
        return cacheManager;
    }

    /**
     * 添加缓存 默认项目名作为cacheName
     *
     * @param key
     * @param value
     * @param timeToLiveSeconds 缓存生存时间（秒）
     */
    public void putCache(String key, Object value, int timeToLiveSeconds) {
        Cache cache = getCacheByCacheName(sysConfigAttribute.getSystemName());
        Element element = new Element(
                key, value,
                0,
                timeToLiveSeconds);
        cache.put(element);
    }

    /**
     * 添加缓存
     *
     * @param cacheName         缓存名称
     * @param key
     * @param value
     * @param timeToLiveSeconds 缓存生存时间（秒）
     */
    public void putCache(String cacheName, String key, Object value, int timeToLiveSeconds) {
        Cache cache = getCacheByCacheName(cacheName);
        Element element = new Element(
                key, value,
                0,
                timeToLiveSeconds);
        cache.put(element);
    }

    /**
     * 添加缓存 默认项目名作为cacheName
     * 使用默认生存时间
     *
     * @param key
     * @param value
     */
    public void setCache(String key, Object value) {
        Cache cache = getCacheByCacheName(sysConfigAttribute.getSystemName());
        Element element = new Element(
                key, value,
                0,
                DEFAULT_LIVE_SECOND);
        cache.put(element);
    }

    /**
     * 添加缓存 默认项目名作为cacheName
     *
     * @param key
     * @param value
     * @param timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。
     *                          只对eternal为false的有效。传入0，表示一直可以访问。以秒为单位。
     * @param timeToLiveSeconds 缓存生存时间（秒）
     *                          只对eternal为false的有效
     */
    public void setCache(String key, Object value, int timeToIdleSeconds, int timeToLiveSeconds) {
        Cache cache = getCacheByCacheName(sysConfigAttribute.getSystemName());
        Element element = new Element(
                key, value,
                timeToIdleSeconds,
                timeToLiveSeconds);
        cache.put(element);
    }

    /**
     * 添加缓存
     *
     * @param cacheName
     * @param key
     * @param value
     * @param timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。
     *                          只对eternal为false的有效。传入0，表示一直可以访问。以秒为单位。
     * @param timeToLiveSeconds 缓存生存时间（秒）
     *                          只对eternal为false的有效
     */
    public void setCache(String cacheName, String key, Object value, int timeToIdleSeconds, int timeToLiveSeconds) {
        Cache cache = getCacheByCacheName(cacheName);
        Element element = new Element(
                key, value,
                timeToIdleSeconds,
                timeToLiveSeconds);
        cache.put(element);
    }

    /**
     * 获取缓存 默认项目名作为cacheName
     *
     * @param key
     * @return
     */
    public Object getCache(String key) {
        Cache cache = getCacheByCacheName(sysConfigAttribute.getSystemName());
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
    }


    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public Object getCache(String cacheName, String key) {
        Cache cache = getCacheByCacheName(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public void removeCache(String key) {
        Cache cache = getCacheByCacheName(sysConfigAttribute.getSystemName());
        cache.remove(key);
    }

    /**
     * 移除缓存
     * @param cacheName
     * @param key
     */
    public void removeCache(String cacheName, String key) {
        Cache cache = getCacheByCacheName(cacheName);
        cache.remove(key);
    }

    /**
     * 获取cache 没有的话 添加
     *
     * @param cacheName
     * @return
     */
    private Cache getCacheByCacheName(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized (EhCacheService.class) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    cacheManager.addCache(cacheName);
                    cache = cacheManager.getCache(cacheName);
                    return cache;
                }
            }
        }
        return cache;
    }


}
