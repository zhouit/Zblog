package com.zblog.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程工具类
 *
 * @author zhou
 *
 */
public class Threads {
	private Threads() {
	}

	/**
	 * 获取当前调用方法调用栈
	 *
	 * @return
	 */
	public static List<String> getCallStack() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		List<String> result = new ArrayList<>(ste.length);
		for (int i = 0; i < ste.length; i++) {
			result.add(ste[i].getClassName() + "." + ste[i].getMethodName() + "(" + ste[i].getLineNumber() + ")");
		}

		return result;
	}

	/**
	 * 执行obj的wait方法
	 *
	 * @param obj
	 */
	public static void waitQuietly(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleepQuietly(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Throwable getRootCause(Throwable t) {
		Throwable cause = null;
		while ((cause = t.getCause()) != null) {
			t = cause;
		}
		return t;
	}

}
