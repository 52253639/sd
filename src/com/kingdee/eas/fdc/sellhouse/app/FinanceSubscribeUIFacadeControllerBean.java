package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FinanceSubscribeUIFacadeControllerBean extends AbstractFinanceSubscribeUIFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.FinanceSubscribeUIFacadeControllerBean");
   
    protected IRowSet _getSubscribe(Context ctx, String saleLongNumber,
			String sellProjectID, String beginQueryDate, String endQueryDate)
			throws BOSException, EASBizException {
    	StringBuffer sb=new StringBuffer();
    	String sql="select rec.FRoomID as roomID,moneyDef.FMoneyType as moneyType,moneyDef.fnumber as "+
					"moneyDefNumber,moneyDef.fname_l2 as moneyDefName,subarea.fname_l2 as "+
					"subareaName,proType.fname_l2 as proTypeName,builProperty.fname_l2 as "+
					"builProName,purchase.FCustomerNames as customerNames,sellProject.fname_l2 as "+
					"sellProject,building.fname_l2 as building,unit.fname_l2 as buildingUnit,room.fnumber as "+
					"roomNumber,settlementType.fnumber as settTypeNumber,settlementType.fname_l2 as "+
					"settTypeName,sum(recEntry.FRevAmount) as amount "+
//					"settTypeName,transfer.FAmount as tranAmount,tranMoneyDef.fnumber as tranMoneyDefNumber," +
//					"tranMoneyDef.FMoneyType as tranMoneyType,sum(recEntry.FRevAmount) as amount "+
					"from T_BDC_FDCReceivingBill as rec "+
					"left join T_BDC_FDCReceivingBillEntry as recEntry on rec.fid=recEntry.FHeadID "+
					"left join T_BD_SettlementType as settlementType on settlementType.fid=recEntry.FSettlementTypeID "+
					"left join T_SHE_MoneyDefine as moneyDef on recEntry.FMoneyDefineID=moneyDef.fid "+
					"left join T_SHE_Purchase as purchase on rec.FPurchaseObjID=purchase.fid "+
					"left join T_SHE_SellProject as sellProject on sellProject.fid=rec.FSellProjectID "+
					"left join T_ORG_BaseUnit as baseUnit on baseUnit.fid=rec.FOrgUnitID "+
					"left join T_SHE_Room as room on room.fid=purchase.froomid "+
					"left join T_SHE_Building as building on room.FBuildingID=building.fid "+
					"left join T_SHE_Subarea as subarea on building.FSubareaID=subarea.fid "+
					"left join T_FDC_ProductType as proType on room.FProductTypeID=proType.fid "+
					"left join T_SHE_BuildingProperty as builProperty on builProperty.fid=room.FBuildingPropertyID "+
					"left join T_SHE_BuildingUnit as unit on unit.fid=room.FBuildUnitID " +
					"where (rec.FRevBillType ='gathering' or rec.FRevBillType ='refundment') ";
//    				"left join T_BDC_TransferSourceEntry as transfer on transfer.FHeadId=recEntry.fid "+
//    				"left join T_SHE_MoneyDefine as tranMoneyDef on transfer.FFromMoneyDefineID=tranMoneyDef.fid ";
		sb.append(sql);
		if(saleLongNumber!=null){
			sb.append("and baseUnit.flongnumber like '"+saleLongNumber+"%' ");
		}else if(sellProjectID!=null){
			sb.append("and rec.FSellProjectID = '"+sellProjectID+"' ");
		}
	   	if(beginQueryDate!=null && !beginQueryDate.trim().equals("")){
			sb.append(" and rec.fcreatetime >= {ts '"+beginQueryDate+"'}");
		}
		if(beginQueryDate!=null && !beginQueryDate.trim().equals("")){
			sb.append(" and rec.fcreatetime < {ts '"+endQueryDate+"'}");
		}	
	   	String sql2=" group by "+
					"rec.FRoomID,moneyDef.FMoneyType,moneyDef.fnumber,moneyDef.fname_l2,subarea.fname_l2 " +
					",proType.fname_l2,builProperty.fname_l2,purchase.FCustomerNames,sellProject.fname_l2 " +
					",building.fname_l2,unit.fname_l2,room.fnumber,settlementType.fnumber,settlementType.fname_l2 " +
//					",transfer.FAmount,tranMoneyDef.fnumber,tranMoneyDef.FMoneyType "+
					"order by rec.FRoomID";
		sb.append(sql2);
		return DbUtil.executeQuery(ctx, sb.toString());
    }
}