package com.xjd.hehe.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.nio.charset.Charset;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author elvis.xu
 * @since 2015-11-15 09:41
 */
public class JedisCache implements Cache {
	private static Logger log = LoggerFactory.getLogger(JedisCache.class);

	protected Charset defaultCharset = Charset.forName("UTF-8");
	protected String name;
	protected KryoSerializer kryoSerializer;
	protected Integer expireSeconds;
	protected JedisPool jedisPool;

	public void setName(String name) {
		this.name = name;
	}

	public KryoSerializer getKryoSerializer() {
		return kryoSerializer;
	}

	public void setKryoSerializer(KryoSerializer kryoSerializer) {
		this.kryoSerializer = kryoSerializer;
	}

	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JedisPool getNativeCache() {
		return jedisPool;
	}

	@Override
	@Profiled
	public ValueWrapper get(Object o) {
		Object v = getObject(o);
		return v == null ? null : new SimpleValueWrapper(v);
	}

	@Override
	@Profiled
	public <T> T get(Object o, Class<T> aClass) {
		return aClass.cast(getObject(o));
	}

	@Profiled
	protected Object getObject(Object key) {
		while (true) {
			try (Jedis jedis = jedisPool.getResource()) {
				byte[] bytes = jedis.get(key.toString().getBytes(defaultCharset));
				if (bytes == null) {
					return null;
				}
				Object val = kryoSerializer.getKryo().readClassAndObject(new Input(new ByteArrayInputStream(bytes)));
				return val;
			} catch (JedisConnectionException e) {
				if (e.getCause() != null && e.getCause() instanceof SocketException
						&& e.getCause().getMessage().indexOf("Broken pipe") != -1) {
					log.warn("jedis broken pipe occured.");
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	@Override
	@Profiled
	public void put(Object o, Object o1) {
		while (true) {
			try (Jedis jedis = jedisPool.getResource()) {
				if (o1 == null) {
					jedis.del(o.toString().getBytes(defaultCharset));
				} else {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					Output out = new Output(outputStream);
					kryoSerializer.getKryo().writeClassAndObject(out, o1);
					out.close();
					;
					byte[] bytes = outputStream.toByteArray();
					byte[] key = o.toString().getBytes(defaultCharset);
					jedis.set(key, bytes);
					jedis.expire(key, expireSeconds);
				}
			} catch (JedisConnectionException e) {
				if (e.getCause() != null && e.getCause() instanceof SocketException
						&& e.getCause().getMessage().indexOf("Broken pipe") != -1) {
					log.warn("jedis broken pipe occured.");
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	@Override
	@Profiled
	public ValueWrapper putIfAbsent(Object o, Object o1) {
		throw new RuntimeException("Not support method!");
	}

	@Override
	@Profiled
	public void evict(Object o) {
		put(o, null);
	}

	@Override
	public void clear() {
		throw new RuntimeException("Not support method!");
	}
}
