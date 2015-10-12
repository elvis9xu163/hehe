package com.xjd.hehe.dal.biz.service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	
	public static String CACHE_ENTITY = "entity";
	public static String CACHE_TEMP = "temp";
	public static String CACHE_STATIC = "static";

	@Autowired
	CacheManager cacheManager;

	@Profiled
	public <T, K> GetResult<T, K> get(String cacheName, Collection<K> idList) {
		GetResult<T, K> rt = new GetResult<T, K>();

		if (CollectionUtils.isEmpty(idList)) {
			rt.setResultList(Collections.<T> emptyList());
			rt.setNoHitIdList(Collections.<K> emptyList());
		} else {
			Cache cache = cacheManager.getCache(cacheName);
			List<K> noHitIdList = new LinkedList<K>();
			List<T> resultList = new LinkedList<T>();
			for (K k : idList) {
				ValueWrapper vw = cache.get(k);
				if (vw == null) {
					noHitIdList.add(k);
				} else {
					resultList.add((T) vw.get());
				}
			}
			rt.setResultList(resultList);
			rt.setNoHitIdList(noHitIdList);
		}

		return rt;
	}

	@Profiled
	public <K, T> void put(String cacheName, Map<K, T> map) {
		if (map == null || map.isEmpty()) return;
		Cache cache = cacheManager.getCache(cacheName);
		for (Entry<K, T> entry : map.entrySet()) {
			cache.put(entry.getKey(), entry.getValue());
		}
	}
	
	@Profiled
	public <K, T> void put(String cacheName, K key, T val) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.put(key, val);
	}

	public static class GetResult<T, K> {
		private List<T> resultList;
		private List<K> noHitIdList;

		public List<T> getResultList() {
			return resultList;
		}

		public void setResultList(List<T> resultList) {
			this.resultList = resultList;
		}

		public List<K> getNoHitIdList() {
			return noHitIdList;
		}

		public void setNoHitIdList(List<K> noHitIdList) {
			this.noHitIdList = noHitIdList;
		}

	}
}
