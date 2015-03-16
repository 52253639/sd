package com.kingdee.eas.fdc.sellhouse;

import java.util.HashMap;
import java.util.Map;

public class ProjectSet extends MyPropertyContainer implements SHEParamConstant{
	public ProjectSet(){
		this.values = new HashMap();
		
		for(int i=0; i<t2.length; i++){
			put((String) t2[i][0], t2[i][1]);	
		}
	}
	public ProjectSet(Map map){
		this.values = map;
	}
}