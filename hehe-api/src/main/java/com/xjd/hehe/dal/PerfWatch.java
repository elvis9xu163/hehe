package com.xjd.hehe.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfWatch {
	private static Logger log = LoggerFactory.getLogger(PerfWatch.class);
	public static ThreadLocal<PerfPoint> tl = new ThreadLocal<PerfWatch.PerfPoint>();

	public static void start(String msg) {
		if (log.isInfoEnabled()) {
			PerfPoint pp = new PerfPoint();
			tl.set(pp);
			pp.start = System.currentTimeMillis();
			pp.last = pp.start;
			log.info("[0, 0] {}", msg);
		}
	}

	public static void point(String msg) {
		if (log.isInfoEnabled()) {
			long cur = System.currentTimeMillis();
			PerfPoint pp = tl.get();
			log.info("[{}, {}] {}", cur - pp.start, cur - pp.last, msg);
			pp.last = cur;
		}
	}

	public static class PerfPoint {
		long start = -1;
		long last = -1;
	}
}
