package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.util.Map;

import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;

public class CostDataGetter implements Serializable {
	protected void clearMap(Map map){
		if(map!=null){
			map.clear();
		}
	}
	
	private static final BOSObjectType prjType=new CurProjectInfo().getBOSType();
	public static boolean isCurProject(String objectId){
		if(objectId==null) throw new IllegalArgumentException("objectId can't be Null");
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		if(prjType.equals(bosType)){
			return true;
		}else{
			return false;
		}

	}
}
