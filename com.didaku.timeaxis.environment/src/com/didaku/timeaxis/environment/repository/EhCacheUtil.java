package com.didaku.timeaxis.environment.repository;

/*
 * 生成ehcache的实例，获取cache,调用getCrabmanCache就可以获取cache进行使用
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhCacheUtil
{
	private static Cache brandKeywordMonitorCache;
	private static Cache httpStatusCache;
	private static CacheManager manager;
	private static EhCacheUtil instance;
	private static Log log = LogFactory.getLog(EhCacheUtil.class);

	public static Cache getBrandKeywordMonitorCache()
	{
		return brandKeywordMonitorCache;
	}

	public static Cache getHttpStatusCache()
	{
		return httpStatusCache;
	}

	public static CacheManager getManager()
	{
		return manager;
	}

	public static EhCacheUtil init()
	{
		log.debug("[EhcacheUtil.init]");
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		if (instance == null)
		{
			instance = new EhCacheUtil();
			manager = CacheManager.create(EhCacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml"));

			brandKeywordMonitorCache = manager.getCache("BrandKeywordMonitorCache");
			httpStatusCache = manager.getCache("HttpStatusCache");
		}
		return instance;

	}

	public static EhCacheUtil init(final String path)
	{
		log.debug("[EhcacheUtil.init]");
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		if (instance == null)
		{
			instance = new EhCacheUtil();
			manager = CacheManager.create(EhCacheUtil.class.getClassLoader().getResourceAsStream(path));

			brandKeywordMonitorCache = manager.getCache("BrandKeywordMonitorCache");
			httpStatusCache = manager.getCache("HttpStatusCache");
		}
		return instance;

	}

	private static boolean isNull(final Element e)
	{
		return (e == null) || (e.getObjectValue() == null) || (e.getValue() == null);
	}

	/**
	 * 存入
	 * 
	 * @param <T>
	 * @param cache
	 *            缓存库
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static <T extends Serializable> void put(final Cache cache, final String key, final T value)
	{
		Element e = new Element(key, value);
		cache.put(e);
		cache.flush();
	}

	/**
	 * 存入 并设置元素是否永恒保存
	 * 
	 * @param <T>
	 * @param cache
	 *            缓存库
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static <T extends Serializable> void put(final Cache cache, final String key, final T value, final boolean eternal)
	{
		Element element = new Element(key, value);
		element.setEternal(eternal);
		cache.put(element);
		cache.flush();
	}

	/**
	 * 存入
	 * 
	 * @param <T>
	 * @param cache
	 *            缓存库
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param timeToLiveSeconds
	 *            最大存活时间
	 * @param timeToIdleSeconds
	 *            最大访问间隔时间
	 */
	public static <T extends Serializable> void put(final Cache cache, final String key, final T value, final int timeToLiveSeconds, final int timeToIdleSeconds)
	{
		Element element = new Element(key, value);
		element.setTimeToLive(timeToLiveSeconds);
		element.setTimeToIdle(timeToIdleSeconds);
		cache.put(element);
		cache.flush();
		cache.get("").getObjectValue();
	}

	public static Object getCacheElement(final Cache cache, final String key)
	{
		Element e = cache.get(key);
		return e;
	}

	public static Object get(final Cache cache, final String key)
	{
		Element e = cache.get(key);
		if (e != null)
			return e.getObjectValue();
		return null;
	}

	public static void remove(final Cache cache, final String key)
	{
		cache.remove(key);
	}

	public static void removeAll(final Cache cache, final Collection<String> keys)
	{
		cache.removeAll(keys);
	}

	@SuppressWarnings("unchecked")
	public static void addToList(final Cache cache, final String key, final Serializable value)
	{
		// if("dailytaskidpingpailist".equals(key))
		// {
		// log.warn(value);
		// new Exception().printStackTrace();
		// }
		Element e = cache.get(key);
		if (isNull(e))
		{
			List<Serializable> list = Collections.synchronizedList(new LinkedList<Serializable>());
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static void addAllToList(final Cache cache, final String key, final Collection<? extends Serializable> value)
	{
		Element e = cache.get(key);
		if (isNull(e))
		{
			List<Serializable> list = Collections.synchronizedList(new LinkedList<Serializable>());
			list.addAll(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();
			list.addAll(value);
			log.debug(key + "--" + list);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static void addToHashSet(final Cache cache, final String key, final Serializable value)
	{
		Element e = cache.get(key);
		if (isNull(e))
		{
			Set<Serializable> list = Collections.synchronizedSet(new HashSet<Serializable>());
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			Set<Serializable> list = (Set<Serializable>) e.getObjectValue();
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static void addAllToHashSet(final Cache cache, final String key, final Collection<? extends Serializable> value)
	{
		Element e = cache.get(key);
		if (isNull(e))
		{
			Set<Serializable> list = Collections.synchronizedSet(new HashSet<Serializable>());
			list.addAll(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			Set<Serializable> list = (Set<Serializable>) e.getObjectValue();
			list.addAll(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static void addToArrayList(final Cache cache, final String key, final Serializable value)
	{
		Element e = cache.get(key);
		if (isNull(e))
		{
			List<Serializable> list = Collections.synchronizedList(new ArrayList<Serializable>());
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();
			list.add(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static void addAllToArrayList(final Cache cache, final String key, final Collection<? extends Serializable> value)
	{
		Element e = cache.get(key);
		if (isNull(e))
		{
			List<Serializable> list = Collections.synchronizedList(new ArrayList<Serializable>());
			list.addAll(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}
		else
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();
			list.addAll(value);
			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
		}

		cache.flush();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T popFromList(final Cache cache, final String key, final Class<T> T)
	{
		Element e = cache.get(key);
		if (e != null)
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();
			Iterator<Serializable> it = list.iterator();
			if (list.size() > 0)
			{
				Serializable obj = it.next();
				it.remove();
				e = new Element(key, list);
				e.setEternal(true);
				cache.put(e);
				cache.flush();
				return (T) obj;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> popFromList(final Cache cache, final String key, final int count, final Class<T> T)
	{
		Element e = cache.get(key);
		if (e != null)
		{
			List<Serializable> list = (List<Serializable>) e.getObjectValue();

			if (count < 1)
			{
				List<T> result = (List<T>) new ArrayList<Serializable>(list);
				list.clear();
				e = new Element(key, list);
				e.setEternal(true);
				cache.put(e);
				cache.flush();
				return result;
			}

			List<T> result = new ArrayList<T>(count);
			Iterator<Serializable> it = list.iterator();
			for (int i = 0; (i < count) && it.hasNext(); i++)
			{
				Serializable obj = it.next();
				it.remove();
				result.add((T) obj);
			}

			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
			cache.flush();
			return result;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T popFromHashSet(final Cache cache, final String key, final Class<T> T)
	{
		Element e = cache.get(key);
		if (e != null)
		{
			Set<Serializable> list = (Set<Serializable>) e.getObjectValue();
			Iterator<Serializable> it = list.iterator();
			if (list.size() > 0)
			{
				Serializable obj = it.next();
				it.remove();
				e = new Element(key, list);
				e.setEternal(true);
				cache.put(e);
				cache.flush();
				return (T) obj;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> popFromHashSet(final Cache cache, final String key, final int count, final Class<T> T)
	{
		Element e = cache.get(key);
		if (e != null)
		{
			Set<Serializable> list = (Set<Serializable>) e.getObjectValue();

			if (count < 1)
			{
				List<T> result = (List<T>) new ArrayList<Serializable>(list);
				list.clear();
				e = new Element(key, list);
				e.setEternal(true);
				cache.put(e);
				cache.flush();
				return result;
			}

			List<T> result = new ArrayList<T>(count);
			Iterator<Serializable> it = list.iterator();
			for (int i = 0; (i < count) && it.hasNext(); i++)
			{
				Serializable obj = it.next();
				it.remove();
				result.add((T) obj);
			}

			e = new Element(key, list);
			e.setEternal(true);
			cache.put(e);
			cache.flush();
			return result;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static int getCollectionSize(final Cache cache, final String key)
	{
		Element e = cache.get(key);
		if (e != null)
		{
			Collection<Serializable> list = (Collection<Serializable>) e.getObjectValue();
			return list.size();
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public static List getKeys(final Cache cache)
	{
		return cache.getKeys();
	}

	public static List<String> getKeys(final Cache cache, final String start)
	{
		List<?> list = cache.getKeys();
		List<String> result = new ArrayList<String>(list.size());
		for (Object obj : list)
			if ((obj != null) && (obj.getClass() == String.class))
			{
				String s = (String) obj;
				if (s.startsWith(start))
					result.add(s);
			}
		return result;
	}

	public static void main(final String[] args)
	{
		init();
		put(httpStatusCache, "xiaochen", "a");
		List<String> list = new ArrayList<String>();
		list.add("b");
		list.add("c");
		list.add("d");

		put(httpStatusCache, "xiaochen", "b");
		put(httpStatusCache, "xiaochen", "c");

		Element aaaa = new Element("xiaohan", list);
		httpStatusCache.put(aaaa);

		Element element = httpStatusCache.get("xiaochen");

		Element aaaaa = httpStatusCache.get("xiaohan");
		System.out.println("aaaa : " + aaaaa.getValue());

		System.out.println("** : " + element.getValue());

		System.out.println("keys : " + httpStatusCache.getKeys().size());
		System.out.println("keys : " + httpStatusCache.getKeys().get(1));
		int a = 10;
		assert a == 10 : "Out assertion failed!";

		element.getValue();

	}
}