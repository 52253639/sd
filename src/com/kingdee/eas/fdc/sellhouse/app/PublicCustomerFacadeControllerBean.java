package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class PublicCustomerFacadeControllerBean extends AbstractPublicCustomerFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PublicCustomerFacadeControllerBean");

	protected void _changeToPublicCustomer(Context ctx) throws BOSException {
		try {
			//获取公司id
			String companyID=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			//获取参数
			int day=Integer.parseInt(FDCUtils.getFDCParamByKey(ctx, companyID, "FDCSHE_120__PUBLICCOUSTERMER_TRANSFORM"));
			java.util.Date date = new java.util.Date();
		    Calendar calendar=Calendar.getInstance();
		    calendar.setTime(date);
		    calendar.add(Calendar.DAY_OF_MONTH, -day);
		    date=calendar.getTime();
		    java.sql.Date passed = new java.sql.Date(date.getTime());
//		    // 查找出n天未跟进且n天未大定的客户
		    String sql = 
//			"select FID from  T_SHE_SHECustomer where fid not in(select FCustomerID from T_SHE_SignCustomerEntry as sce,T_SHE_SignManage as sm where sce.FHeadID=sm.FID and sm.FBizState  in('SignApple','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignAudit','SignAuditing')  ) "+
//			" and  fid not in (select FCustomerID from T_SHE_PrePurchaseCustomerEntry as ppce,T_SHE_PrePurchaseManage as ppm where ppce.FHeadID=ppm.FID and ppm.FBizState  in('PreApple','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreAudit','PreAuditing')   ) "+
//			" and  fid not in (select FCustomerID from T_SHE_SinPurCustomerEntry as spce,T_SHE_NewSincerityPurchase as nsp where spce.FHeadID=nsp.FID and nsp.FBizState  in('BayNumber','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing') ) "+
//			" and  fid not in (select FCustomerID from T_SHE_PurCustomerEntry as pce,T_SHE_PurchaseManage as pm where pce.FHeadID=pm.FID and pm.FBizState  in('PurApple','PurAudit','PurAuditing','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing')) "+
//			" and  ("+
//			" fid in (select fid from t_she_shecustomer a where fid not in (select FCustomerID from T_SHE_CommerceChanceTrack cct"+
//			"	inner join T_SHE_CommerceChance cc on cc.fid=cct.FCommerceChanceID) and a.fcreatetime<={ts '"+passed+"'}) "+
//			" or fid in(select FCustomerID from T_SHE_CommerceChanceTrack cct"+
//			"	inner join T_SHE_CommerceChance cc on cc.fid=cct.FCommerceChanceID and cct.FTrackDate<={ts '"+passed+"'} )"+
//			")";

		    //查询出没有任何交易的客户
		    "select FID from  T_SHE_SHECustomer where " +
		    " fid not in(select FCustomerID from T_SHE_SignCustomerEntry) "+
			" and  fid not in (select FCustomerID from T_SHE_PrePurchaseCustomerEntry) "+
			" and  fid not in (select FCustomerID from T_SHE_SinPurCustomerEntry) "+
			" and  fid not in (select FCustomerID from T_SHE_PurCustomerEntry) ";
		    	
		    ResultSet rs=DbUtil.executeQuery(ctx, sql);
		    while (rs.next()) {
		    	String FID = rs.getString("FID");
		    	//根据客户查询商机
		    	CommerceChanceCollection ccc = CommerceChanceFactory.getLocalInstance(ctx).getCommerceChanceCollection("where shecustomer.id = '"+FID+"'");
		    	if(ccc.size() > 0){
		    		//根据商机查询跟进
		    		CommerceChanceInfo cci = ccc.get(0);
		    		String cciID = cci.getId().toString();
		    		
		    	}
		    	SHECustomerInfo cusInfo = new SHECustomerInfo();
		    	cusInfo.setId(BOSUuid.read(FID));
		    	cusInfo.setIsPublic(true);
		    	SelectorItemCollection sels = new SelectorItemCollection();
		    	sels.add("isPublic");
		    	SHECustomerFactory.getLocalInstance(ctx).updatePartial(cusInfo, sels);
		    }
		} catch (SQLException e) {
			throw new BOSException(e);
		} catch (EASBizException e) {
			throw new BOSException(e);
		}
	}

	protected void _callChangeToPublicProcedure(Context ctx) throws BOSException, SQLException {
		FDCSQLBuilder build = new FDCSQLBuilder(ctx);
		build.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		StringBuffer sql = new StringBuffer();
		sql.append("update t_she_commercechance set ftrackDate=now() where ftrackDate is null"); 
		build.addBatch(sql.toString());
		
		sql = new StringBuffer();
		sql.append("update t_she_commercechanceTrack set ftrackContent=flogContent where ftrackContent is null"); 
		build.addBatch(sql.toString());
		
		sql = new StringBuffer();
		sql.append("update T_SHE_SHECustomer customer set Fispublic=1 where NOT EXISTS ( ");
		sql.append("select distinct customerid from (select customer.fid customerid from t_she_commercechance cc left join t_she_shecustomer customer on customer.fid=cc.fcustomerid left join t_she_sellProject sp on sp.fid=customer.fsellprojectid left join  t_bas_paramItem pitem on pitem.FOrgUnitID = sp.forgUnitid ");
		sql.append("left join T_BAS_Param param on param.fid = pitem.fkeyid where param.FNUMBER = 'FDCSHE_120__PUBLICCOUSTERMER_TRANSFORM' and DATEDIFF (d, cc.ftrackDate , now())<=pItem.Fvalue_L2 ");
		sql.append("union all select change.fshecustomerid customerid from T_SHE_SHECustomerChangeDetail change left join t_bas_paramItem pitem on pitem.FOrgUnitID = change.fcontrolunitid ");
		sql.append("left join T_BAS_Param param on param.fid = pitem.fkeyid where change.freason ='05' and param.FNUMBER = 'FDCSHE_120__PUBLICCOUSTERMER_TRANSFORM' and DATEDIFF (d, change.fcreateTime , now())<=pItem.Fvalue_L2 ");
		sql.append("union all select preCustomer.fcustomerid customerid from t_she_prePurchaseCustomerEntry preCustomer left join t_she_prePurchasemanage pre on pre.fid=preCustomer.fheadid where pre.fbizState not in('PreNullify','QRNullify') ");
		sql.append("union all select purCustomer.fcustomerid customerid from t_she_purCustomerEntry purCustomer left join t_she_purchasemanage pur on pur.fid=purCustomer.fheadid where pur.fbizState not in('PurNullify','QRNullify') ");
		sql.append("union all select signCustomer.fcustomerid customerid from t_she_signCustomerEntry signCustomer left join t_she_signmanage sign on sign.fid=signCustomer.fheadid where sign.fbizState not in('SignNullify','QRNullify'))nocustomer where nocustomer.customerid =customer.fid) ");
		build.addBatch(sql.toString());
		
		build.executeBatch();
	}
}