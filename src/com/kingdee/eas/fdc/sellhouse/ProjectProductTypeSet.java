package com.kingdee.eas.fdc.sellhouse;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.util.Null;
import com.kingdee.util.PropertyContainer;

public class ProjectProductTypeSet extends MyPropertyContainer implements SHEParamConstant{
	public ProjectProductTypeSet(){
		this.values = new HashMap();
		
		for(int i=0; i<t1.length; i++){
			put((String) t1[i][0], t1[i][1]);
		}
	}
	
	public ProjectProductTypeSet(Map map){
		this.values = map;
	}
}

//key不要转为小写
//abstract class MyPropertyContainer extends PropertyContainer{
//	public Map getValues(){
//		return values;
//	}
//	
//	public Object get(String key) {
//		//全部以小写方式存储
//		Object obj = values.get(key);
//		if (obj instanceof Null)
//			return null;
//		else
//			return obj;
//	}
//	
//	public Object get(String key, Object defaultValue) {
//		Object obj = values.get(key);
//		if (obj == null)
//			return defaultValue;
//		else {
//			if (obj instanceof Null)
//				return null;
//			else
//				return obj;
//		}
//	}
//
//	public Object put(String key, Object value) {
//		if (value == null) {
//			values.put(key, Null.NULL);
//			return null;
//		} else
//			return values.put(key, value);
//	}
//
//	public Object remove(String key) {
//		return values.remove(key);
//	}
//
//	public boolean containsKey(String key) {
//		return values.containsKey(key);
//	}
//}