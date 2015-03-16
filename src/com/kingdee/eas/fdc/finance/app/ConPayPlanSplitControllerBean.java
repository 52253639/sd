package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitEntryCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitEntryInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.PayRequestSplitCollection;
import com.kingdee.eas.fdc.finance.PayRequestSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PayRequestSplitFactory;
import com.kingdee.eas.fdc.finance.PayRequestSplitInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillFacade;
import com.kingdee.eas.fdc.basedata.FDCBillFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillControllerBean;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class ConPayPlanSplitControllerBean extends AbstractConPayPlanSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ConPayPlanSplitControllerBean");
    protected void _autoSplit(Context ctx, String billId) throws BOSException, EASBizException {
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("payAmount");
    	selector.add("contractId.id");
    	final ContractPayPlanInfo contractPayPlanInfo = ContractPayPlanFactory.getLocalInstance(ctx).getContractPayPlanInfo(new ObjectUuidPK(billId), selector);
    	ConPayPlanSplitInfo info=new ConPayPlanSplitInfo();
    	Map dataMap=new HashMap();
    	dataMap.put("amount", contractPayPlanInfo.getPayAmount());
    	dataMap.put("contractId", contractPayPlanInfo.getContractId().getId().toString());
    	FDCSplitBillInfo fdcSplitInfo=FDCBillFacadeFactory.getLocalInstance(ctx).autoPropSplit("contract", dataMap);
    	if(fdcSplitInfo==null){
    		return;
    	}
    	info.putAll(fdcSplitInfo);
    	info.setBill(contractPayPlanInfo);
    	ConPayPlanSplitEntryCollection entrys =new ConPayPlanSplitEntryCollection();
    	for(Iterator iter=((AbstractObjectCollection)fdcSplitInfo.get("entrys")).iterator();iter.hasNext();){
    		FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
    		ConPayPlanSplitEntryInfo planEntry=new ConPayPlanSplitEntryInfo();
    		planEntry.putAll(entry);
    		planEntry.setParent(info);
    		planEntry.setCostBillId(contractPayPlanInfo.getContractId().getId());
    		planEntry.setSplitBillId(planEntry.getCostBillId());
			entrys.add(planEntry);
    	}
    	info.put("entrys", entrys);
    	_addnew(ctx, info);
    }
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	return super._save(ctx, model);
    }
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	super._delete(ctx, pk);
    }
	protected Map _getCostAcctPlan(Context ctx, String prjId, java.util.Date date) throws BOSException, EASBizException {
		EntityViewInfo view=new  EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("bill.contractId.id");
		view.getSelector().add("bill.payDate");
		view.getSelector().add("entrys.isLeaf");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.amount");
		
		String sql="select distinct entry.fparentid from T_FNC_ConPayPlanSplitEntry entry inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid where acct.fcurproject='"+prjId+"'";
		view.getFilter().getFilterItems().add(new  FilterItemInfo("id",sql,CompareType.INNER));
		Date startDate=FDCDateHelper.getSqlDate(FDCDateHelper.getFirstDayOfMonth(date));
		Date endDate=FDCDateHelper.getSqlDate(FDCDateHelper.getLastDayOfMonth(date));
//		view.getFilter().getFilterItems().add(new  FilterItemInfo("bill.payDate",startDate,CompareType.GREATER_EQUALS));
		view.getFilter().getFilterItems().add(new  FilterItemInfo("bill.payDate",endDate,CompareType.LESS_EQUALS));
		final ConPayPlanSplitCollection splits = ConPayPlanSplitFactory.getLocalInstance(ctx).getConPayPlanSplitCollection(view);
		//key=acctId+contractId
		Map planSplitMap=new HashMap();
		Map allPlanSplitMap=new  HashMap();
		for(int i=0;i<splits.size();i++){
			final ConPayPlanSplitInfo splitInfo = splits.get(i);
			String contractId=splitInfo.getBill().getContractId().getId().toString();
			boolean isThisMonth=splitInfo.getBill().getPayDate().compareTo(startDate)>=0;
			for(Iterator iter=splitInfo.getEntrys().iterator();iter.hasNext();){
				ConPayPlanSplitEntryInfo entry=(ConPayPlanSplitEntryInfo)iter.next();
				if((entry.isIsLeaf()&&entry.getSplitType()!=CostSplitTypeEnum.PRODSPLIT)||(!entry.isIsLeaf()&&entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT)){
					String key=entry.getCostAccount().getId().toString()+contractId;
					BigDecimal amount=entry.getAmount();
					if(isThisMonth){
						planSplitMap.put(key, FDCHelper.add((BigDecimal)planSplitMap.get(key), amount));
					}
					allPlanSplitMap.put(key, FDCHelper.add((BigDecimal)allPlanSplitMap.get(key), amount));
				}
			}
		}
		//ÒÑ¸¶¿î
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endDateString=format.format(endDate);
		sql="select distinct entry.fparentid From T_FNC_PayReqestSplitEntry entry " +
		"inner join t_fdc_costaccount acct on entry.fcostaccountid=acct.fid "+
		"inner join t_cas_paymentbill payment on entry.fcostbillid=payment.ffdcpayreqid "+ 
		"where payment.fbillStatus>=15 and payment.fbillStatus<=16 and payment.fpaydate<={" +
			endDateString+
		"} and acct.fcurproject='"+prjId+"'";
		view=new EntityViewInfo();
		view.getSelector().add("bill.contractId");
		view.getSelector().add("bill.payDate");
		view.getSelector().add("entrys.isLeaf");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.amount");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new  FilterItemInfo("id",sql,CompareType.INNER));
//		view.getFilter().getFilterItems().add(new  FilterItemInfo("bill.payDate",endDate,CompareType.LESS_EQUALS));

		Map reqSplitMap=new HashMap();
		Map allReqSplitMap=new HashMap();
		final PayRequestSplitCollection payRequestSplitCollection = PayRequestSplitFactory.getLocalInstance(ctx).getPayRequestSplitCollection(view);
		for(int i=0;i<payRequestSplitCollection.size();i++){
			final PayRequestSplitInfo splitInfo = payRequestSplitCollection.get(i);
			boolean isThisMonth=splitInfo.getBill().getPayDate().compareTo(startDate)>=0;
			String contractId=splitInfo.getBill().getContractId();
			for(Iterator iter=splitInfo.getEntrys().iterator();iter.hasNext();){
				PayRequestSplitEntryInfo entry=(PayRequestSplitEntryInfo)iter.next();
				if((entry.isIsLeaf()&&entry.getSplitType()!=CostSplitTypeEnum.PRODSPLIT)||(!entry.isIsLeaf()&&entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT)){
					String key=entry.getCostAccount().getId().toString()+contractId;
					BigDecimal amount=entry.getAmount();
					if(isThisMonth){
						reqSplitMap.put(key, FDCHelper.add((BigDecimal)reqSplitMap.get(key), amount));
					}
					allReqSplitMap.put(key, FDCHelper.add((BigDecimal)allReqSplitMap.get(key), amount));
				}
			}
		}
		
		Map map=new HashMap();
		map.put("planSplitMap", planSplitMap);
		map.put("reqSplitMap", reqSplitMap);
		map.put("allPlanSplitMap", allPlanSplitMap);
		map.put("allReqSplitMap", allReqSplitMap);
		return map;
	}
    
}