package com.xjd.hehe.dal.mongodb;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * @author elvis.xu
 * @since 2015-10-12 23:54
 */
public class MorphiaIniter {
	MorphiaIniter(Morphia morphia, Datastore datastore, List<String> packageList) {
		for (String pack : packageList) {
			morphia.mapPackage(pack);
		}
		datastore.ensureCaps();
		datastore.ensureIndexes();
	}
}
