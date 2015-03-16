package com.kingdee.eas.fdc.sellhouse.formula;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class SellHouseHelper {
	
	//过滤出当前项目所有的置业顾问
	public static Set getPerson(String sellProjectID) throws BOSException, SQLException{
		Set set = new HashSet();
		StringBuffer sql = new StringBuffer();
		sql.append("select T_SHE_MarketingUnitMember.fmemberid from T_SHE_MarketingUnitMember ");
		sql.append("left outer join T_SHE_MarketingUnit on T_SHE_MarketingUnitMember.Fheadid = T_SHE_MarketingUnit.Fid ");
		sql.append("left outer join T_SHE_MarketingUnitSellProject on T_SHE_MarketingUnitMember.Fheadid = ");
		sql.append(" T_SHE_MarketingUnitSellProject.Fheadid left outer join T_SHE_SellProject ");
		sql.append("on T_SHE_MarketingUnitSellProject.Fsellprojectid = T_SHE_SellProject.Fid ");
		sql.append("where T_SHE_SellProject.Fid = '"+sellProjectID+"'");
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql.toString());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			set.add(rowSet.getObject("fmemberid"));
		}
		return set;
	}
	
	//过滤出当前操作人是否是选中项目的负责人
	public static boolean getPersonIsDuty(String sellProjectID,String personId) throws BOSException, SQLException{
		boolean duty = true;
		StringBuffer sql = new StringBuffer();
		sql.append("select T_SHE_MarketingUnitMember.fisduty from T_SHE_MarketingUnitMember ");
		sql.append("left outer join T_SHE_MarketingUnit on T_SHE_MarketingUnitMember.Fheadid = T_SHE_MarketingUnit.Fid ");
		sql.append("left outer join T_SHE_MarketingUnitSellProject on T_SHE_MarketingUnitMember.Fheadid = ");
		sql.append(" T_SHE_MarketingUnitSellProject.Fheadid left outer join T_SHE_SellProject ");
		sql.append("on T_SHE_MarketingUnitSellProject.Fsellprojectid = T_SHE_SellProject.Fid ");
		sql.append("where T_SHE_SellProject.Fid = '"+sellProjectID+"' and T_SHE_MarketingUnitMember.fmemberid = '"+personId+"'");
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql.toString());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			String isDuty = rowSet.getObject("fisduty").toString();
			if(isDuty.equals("0")){
				duty = false;
			}
		}
		return duty;
	}
}
