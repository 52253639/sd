package com.kingdee.eas.fdc.schedule.framework.util;

public class StopWatch {
	private long totalStart;
	private long start;
	private long end;
	public void start(){
		totalStart = start = end = System.currentTimeMillis();
	}
	public long getLastTime(){
		end = System.currentTimeMillis();
		long ret = end - start;
		start = end;
		return ret;
	}
	public long getTotalTime(){
		return System.currentTimeMillis() - totalStart;
	}
}
