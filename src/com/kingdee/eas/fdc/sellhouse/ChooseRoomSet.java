package com.kingdee.eas.fdc.sellhouse;

import java.util.Date;
import java.util.Map;

import com.kingdee.util.SortedStringObjectMap;

public class ChooseRoomSet extends MyPropertyContainer implements SHEParamConstant{
	public ChooseRoomSet(){
		this.values = new SortedStringObjectMap();
		
		for(int i=0; i<t3.length; i++){
			put((String) t3[i][0], t3[i][1]);	
		}
		put(T3_BEGIN_DATE, new Date());
	}
	public ChooseRoomSet(Map map){
		this.values = map;
	}
}