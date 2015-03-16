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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.permission.ContextUtils;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.CRMChequeCollection;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CRMChequeControllerBean extends AbstractCRMChequeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.CRMChequeControllerBean");
    protected void _addBatch(Context ctx, IObjectCollection model)throws BOSException, EASBizException
    {
    }
    protected void _abandon(Context ctx, List ids)throws BOSException, EASBizException
    {
    }
    protected void _distribute(Context ctx, String[] ids, String newKeepOrgUnitId, String newKeeperId)throws BOSException, EASBizException
    {
    }
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	return super._submit(ctx, model);
    }
	protected void _pickCheque(Context ctx, Map dataMap) throws BOSException {
		Boolean radpart = (Boolean)dataMap.get("radpart");
		Boolean radAll = (Boolean)dataMap.get("radAll");
		UserInfo user = (UserInfo)dataMap.get("picker");
		CRMChequeInfo cheque =(CRMChequeInfo)dataMap.get(("cheque"));
		Date date = (Date)dataMap.get("pickDate");
		String numberString = (String)dataMap.get("numberString");
		numberString = addRefSign(numberString);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(radAll.booleanValue()){//更新整个账本的领用人，领用日期，票据状态 //登记的才能领用
			String sql ="update T_SHE_ChequeDetailEntry set FStatus = 4,FPickerID = '"+user.getId().toString()+"', FPikeDate = " +
			"{ts '"+FDCDateHelper.DateToString(date)+"'}"+" where fchequeid = '"+cheque.getId().toString()+"' and fstatus = 0";
			builder.appendSql(sql);
			builder.executeUpdate();
		}else if(radpart.booleanValue()){
			String sql = "update T_SHE_ChequeDetailEntry set FStatus = 4,FPickerID = '"+user.getId().toString()+"', FPikeDate = " +
			"{ts '"+FDCDateHelper.DateToString(date)+"'}"+" where fchequeid = '"+cheque.getId().toString()+"' and fnumber in ("+ numberString+")";
			builder.appendSql(sql);
			builder.executeUpdate();
		}
	}
	private String addRefSign(String numberString) {
		StringBuffer sb = new StringBuffer();
		if(numberString!=null){
			String[] numberArray = numberString.split(",");
			for(int i = 0; i <numberArray.length ;i++){
				if(i>0)sb.append(",");
				sb.append("'");
				sb.append(numberArray[i]);
				sb.append("'");
			}
		}
		return sb.toString();
	}
	protected void _vc(Context ctx, Map ids) throws BOSException, EASBizException {
		Boolean radpart = (Boolean)ids.get("radpart");
		Boolean radAll = (Boolean)ids.get("radAll");
		UserInfo user = (UserInfo)ids.get("F7VCer");
		CRMChequeInfo cheque =(CRMChequeInfo)ids.get(("cheque"));
		Date date = (Date)ids.get("VCDate");
		String numberString = (String)ids.get("numberString");
		String orgId = ContextUtil.getCurrentSaleUnit(ctx).getId().toString();
		numberString = addRefSign(numberString);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(radAll.booleanValue()){//更新整个账本的核销人，核销日期，票据状态 //已填开的才能核销		
			builder.appendSql("delete from T_SHE_ChequeRevListEntry where FChequeDetailID in (select fid from T_SHE_ChequeDetailEntry " +
					" where fchequeid = '"+cheque.getId().toString()+"' and fstatus = 1 ) ");
			builder.execute();
			
			builder.clear();
			builder.appendSql("delete from T_SHE_ChequeCustomerEntry where FChequeID in (select fid from T_SHE_ChequeDetailEntry " +
					" where fchequeid = '"+cheque.getId().toString()+"' and fstatus = 1 ) ");
			builder.execute();
			
			builder.clear();
			String sql ="update T_SHE_ChequeDetailEntry set FStatus = 5 ,FVerifierID = '"+user.getId().toString()+"', FVerifyTime = " +
			"{ts '"+FDCDateHelper.DateToString(date)+"'}"+" ,FVerifyOrgUnitID ='"+orgId +"' " +
					",FChequeCustomer=null,Famount = null,fCapitalization=null,FroomID=null,FWrittenOfferID=null,FWrittenOffTime=null,Fdes=null " +
					" where fchequeid = '"+cheque.getId().toString()+"' and fstatus = 1";
			builder.appendSql(sql);
			builder.executeUpdate();
		}else if(radpart.booleanValue()){
			builder.appendSql("delete from T_SHE_ChequeRevListEntry where FChequeDetailID in (select fid from T_SHE_ChequeDetailEntry " +
					" where fchequeid = '"+cheque.getId().toString()+"' and fnumber in ("+ numberString+") and fstatus = 1 ) ");
			builder.execute();
			
			builder.clear();
			builder.appendSql("delete from T_SHE_ChequeCustomerEntry where FChequeID in (select fid from T_SHE_ChequeDetailEntry " +
					" where fchequeid = '"+cheque.getId().toString()+"' and fnumber in ("+ numberString+") and fstatus = 1 ) ");
			builder.execute();
			
			builder.clear();
			String sql = "update T_SHE_ChequeDetailEntry set FStatus = 5,FVerifierID = '"+user.getId().toString()+"', FVerifyTime = " +
			"{ts '"+FDCDateHelper.DateToString(date)+"'}"+" ,FVerifyOrgUnitID ='"+orgId+"' " +
					",FChequeCustomer=null,Famount = null,fCapitalization=null,FroomID=null,FWrittenOfferID=null,FWrittenOffTime=null,Fdes=null " +
					"where fchequeid = '"+cheque.getId().toString()+"' and fnumber in ("+ numberString+") and fstatus = 1";
			builder.appendSql(sql);
			builder.executeUpdate();	
		}
	}
	
	
}