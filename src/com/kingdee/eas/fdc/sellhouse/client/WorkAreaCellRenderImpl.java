package com.kingdee.eas.fdc.sellhouse.client;

import java.util.Map;

import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.eas.fdc.sellhouse.WorkAreaInfo;
import com.kingdee.eas.fdc.tenancy.BusinessScopeInfo;

public class WorkAreaCellRenderImpl  extends CellTextRender {
	
	/**
	 * 
	 */
	public String getText(Object obj) {
		if(obj == null)
			return null;
		
		
		if(obj instanceof String){
			return String.valueOf(obj);
		}
		else if(obj instanceof Object []){
			Object [] workareas = (Object [])obj;
			StringBuffer workareaString = new StringBuffer();
			for(int i=0;i<workareas.length;i++){
				if(workareas[i] instanceof WorkAreaInfo){
					WorkAreaInfo thisInfo = (WorkAreaInfo)workareas[i] ;
					workareaString.append(i==0?"":",");
					workareaString.append(thisInfo.getName());
				}
				
			}
			return workareaString.toString();
		}
		else
			return String.valueOf(obj);
			
			
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public String getBusText(Object obj) {
		if(obj == null)
			return null;
		
		
		if(obj instanceof String){
			return String.valueOf(obj);
		}
		else if(obj instanceof Object []){
			Object [] BusinessScopeInfos = (Object [])obj;
			StringBuffer BusinessScopeInfoString = new StringBuffer();
			for(int i=0;i<BusinessScopeInfos.length;i++){
				if(BusinessScopeInfos[i] instanceof BusinessScopeInfo){
					BusinessScopeInfo thisInfo = (BusinessScopeInfo)BusinessScopeInfos[i] ;
					BusinessScopeInfoString.append(i==0?"":",");
					BusinessScopeInfoString.append(thisInfo.getName());
				}
				
			}
			return BusinessScopeInfoString.toString();
		}
		else
			return String.valueOf(obj);
			
			
	}
}

