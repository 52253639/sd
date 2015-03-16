package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.FDCCostLogCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogProductEntryInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 成本数据中间表服务器端帮助类
 * @author xiaohong_shi
 * 2008-04-10 17:17:07
 *
 */
public class FDCCostDataAppHelper {
	public static Map getPrjCostData(Context ctx,String prjId,boolean isLeaf) throws BOSException{
		if(prjId==null){
			return null;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(isLeaf){
			builder.appendSql("");
			builder.addParam(prjId);
//			IRowSet rowSet=builder.executeQuery();
			
		}else{
			//汇总
		}
		return null;
	}
	public static Map getOrgCostData(Context ctx,String orgId){
		return null;
	}
	
	public static void merge(Context ctx){
		
	}
	
	
	public static boolean appendLog(Context ctx,Map map){
		return true;
	}
	
	
	public static boolean deleteLog(Map map){
		return true;
	}
	
	public static boolean appendSplitLog(Context ctx,Map map){
		return SplitLogStrategy.appendSplitLog(ctx, map);
	}

}
