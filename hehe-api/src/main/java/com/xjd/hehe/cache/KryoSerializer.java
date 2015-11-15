package com.xjd.hehe.cache;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author elvis.xu
 * @since 2015-11-15 10:20
 */
public class KryoSerializer {
	protected ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>();
	protected Map<Class, Integer> classIdMap = new HashMap<Class, Integer>();

	public Kryo getKryo() {
		Kryo kryo = kryoThreadLocal.get();
		if (kryo == null) {
			kryo = new Kryo();
			registerDefault(kryo);
		}
		return kryo;
	}

	protected void registerDefault(Kryo kryo) {
		for (Map.Entry<Class, Integer> entry : classIdMap.entrySet()) {
			kryo.register(entry.getKey(), entry.getValue());
		}
	}

	public void setClassIdMap(Map<String, Integer> idMap) throws ClassNotFoundException {
		for (Map.Entry<String, Integer> entry : idMap.entrySet()) {
			classIdMap.put(Class.forName(entry.getKey()), entry.getValue());
		}
	}

}
