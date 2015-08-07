package net.sourceforge.ganttproject.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 描述：缓存某天是否为非工作时间(TODO 需考虑工程日历的问题)
 * @author zhiqiao_yang date：2010-8-20<p>
 * @version <EAS600>
 */
public class NonWorkingDayCache {
	private boolean enableCache;
	private Map cache;
	private static NonWorkingDayCache instance = new NonWorkingDayCache();
	public static NonWorkingDayCache getInstance(){
		return instance;
	}
	private NonWorkingDayCache(){
		cache = new HashMap(3000);
		enableCache = true;
	}
	public Boolean get(Date date){
		return (Boolean) cache.get(date);
	}
	
	public void put(Date date, boolean b){
		if(enableCache){
			cache.put(date, Boolean.valueOf(b));
		}
	}
	public int size(){
		return cache.size();
	}
	
	public void clear(){
		cache.clear();
	}
	
	public void setEnableCache(boolean enableCache){
		this.enableCache = enableCache;
	}
}
