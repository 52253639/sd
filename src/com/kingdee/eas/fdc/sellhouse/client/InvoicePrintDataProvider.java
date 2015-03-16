package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class InvoicePrintDataProvider implements BOSQueryDelegate{
	
	private IMetaDataPK qpk = null;
	private String id = null;
	private Map map =null;
	protected boolean ishasNext = true;
	private static String RECEIVEINVOICEPRINTQUERY = "ReceiveInvoicePrintQuery";
	private static String SHERECEIVEBILLENTRYTDQUERY = "SHEReceivingBillEntryTDQuery";
	
	public InvoicePrintDataProvider(Map map,String id,IMetaDataPK qpk)
	{
		this.map = map;
		this.id = id;
		this.qpk = qpk;
	}
	
	public boolean hasNext() {
		if (ishasNext) {
			ishasNext = false;
			return true;
		} else {
			return false;
		}
	}
	public IRowSet execute(BOSQueryDataSource ds) {
		String dsId = ds.getID();
		IRowSet iRowSet = null;
		int inx = dsId.indexOf(".");
		
		if(inx>=0){
			dsId = dsId.substring(0, inx);
		}
		
		// 主数据源
		if (RECEIVEINVOICEPRINTQUERY.equals(dsId)) {
			iRowSet = getReceiveInvoiceRowSet(ds);
		} else if (SHERECEIVEBILLENTRYTDQUERY.equals(ds.getID())) {
			 //收款单分录信息
			iRowSet = getReceivingBillEntryRowSet(ds);
		}
		return iRowSet;
	}
	public IRowSet getReceiveInvoiceRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {            
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum= true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			ev.setFilter(filter);            
			exec.setObjectView(ev);
			//System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
		}catch (Exception e) {
			ExceptionHandler.handle((CoreUI) null,e);
		}
		return iRowSet;
	}
	//查询收款单分录数据
	public IRowSet getReceivingBillEntryRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
		Set receivingBillID =null;
		receivingBillID=getReceivingBillIDSet();
		IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillEntryTDQuery"));
		exec.option().isAutoTranslateEnum = true;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", receivingBillID, CompareType.INCLUDE));
		ev.setFilter(filter);
		exec.setObjectView(ev);
		iRowSet = exec.executeQuery();
		while (iRowSet.next()) {
			String revListId = iRowSet.getString("revListId");
			BizEnumValueInfo ob= (BizEnumValueInfo)iRowSet.getObject("revListType");
			String stra = (String)ob.getValue();
			RevListTypeEnum revListType = RevListTypeEnum.getEnum(stra);
			ICoreBase iface = SHEComHelper.getRevListBizInterface(null, revListType);
			IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
			BigDecimal appAmount = revList.getAppAmount();
			BigDecimal actRevAmount = revList.getActRevAmount();
			BigDecimal hasTransferredAmount = revList.getHasTransferredAmount();
			BigDecimal hasRefundmentAmount = revList.getHasRefundmentAmount();
			BigDecimal hasAdjustedAmount = revList.getHasAdjustedAmount();
			BigDecimal revAmount = actRevAmount.subtract(hasTransferredAmount).subtract(hasRefundmentAmount).subtract(hasAdjustedAmount);
			iRowSet.updateBigDecimal("appAmount", appAmount);
			iRowSet.updateBigDecimal("actRevAmount", revAmount);
			iRowSet.updateBigDecimal("hasTransferredAmount", hasTransferredAmount);
			iRowSet.updateBigDecimal("hasRefundmentAmount", hasRefundmentAmount);
			iRowSet.updateBigDecimal("hasAdjustedAmount", hasAdjustedAmount);
		}
		iRowSet.beforeFirst();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return iRowSet;
	}
	private Set getReceivingBillIDSet(){
		Set receivingBillID =null;
		try {
			MoneySysTypeEnum sysType = null;
			String roomId = null;
			String sysCustId = null;
			if(this.map !=null){
				sysType = (MoneySysTypeEnum)map.get("sysType");
				roomId = (String) map.get("roomId");
				sysCustId = (String)map.get("sysCustId");
			}
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("number");
			coll.add("bizDate");
			coll.add("revBillType");
			coll.add("amount");
			coll.add("invoice.number");
			coll.add("receipt.number");
			coll.add("receipt.amount");
			coll.add("receiptState");
			coll.add("receiptNumber");
			coll.add("entries.*");
			coll.add("entries.moneyDefine.name");
			coll.add("entries.moneyDefine.number");
			coll.add("customer.name");
			FilterInfo filter = new FilterInfo();
			if(roomId!=null && !"".equals(roomId)){
				filter.getFilterItems().add(new FilterItemInfo("room.id",roomId));
			}
			filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(RevBillStatusEnum.AUDITED_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(RevBillStatusEnum.RECED_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("revBillType",RevBillTypeEnum.GATHERING_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("revBillType",RevBillTypeEnum.TRANSFER_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("customer.id",sysCustId));
			/*需要加入系统过滤
			 * 售楼系统  收款业务类型  revBizType 客户收款，认购收款，诚意认购收款，面积补差
			 * 租赁系统 	收款业务类型  revBizType 诚意预留收款，租赁合同收款
			 * 物业系统    收款业务类型  revBizType 物业收费
			 */
			if(MoneySysTypeEnum.SalehouseSys.equals(sysType)){//去售楼收款单
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.CUSTOMER_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.PURCHASE_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.SINCERITY_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.AREACOMPENSATE_VALUE));
				
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and (#6 or #7 or #8 or #9)");	
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and (#5 or #6 or #7 or #8)");
				}
			}else if (MoneySysTypeEnum.TenancySys.equals(sysType)){
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.OBLIGATE_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.TENANCY_VALUE));
				
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and (#6 or #7)");
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and (#5 or #6)");
				}
			}else if(MoneySysTypeEnum.ManageSys.equals(sysType)){
				filter.getFilterItems().add(new FilterItemInfo("revBizType",RevBizTypeEnum.MANAGE_VALUE));
			
				if(roomId!=null && !"".equals(roomId)){
					filter.setMaskString("#0 and  (#1 or #2) and (#3 or #4) and #5 and #6");
				}else{
					filter.setMaskString("(#0 or #1) and (#2 or #3) and #4 and #5");
				}
			}
			
			view.setSelector(coll);
			view.setFilter(filter);
			FDCReceivingBillCollection revColl = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(view);
			for (int i = 0; i < revColl.size(); i++) {
				FDCReceivingBillInfo revInfo = revColl.get(i);
				if(receivingBillID == null){
					receivingBillID = new HashSet();
				}
				receivingBillID.add(revInfo.getId().toString());
			}
		}catch (BOSException e) {
			e.printStackTrace();
			return null;
		}
		return receivingBillID;
	}
//	public IRowSet execute(BOSQueryDataSource ds) {
//		IRowSet iRowSet = null;
//		try {            
//			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
//			exec.option().isAutoTranslateEnum= true;
//			EntityViewInfo ev = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
//			ev.setFilter(filter);            
//			exec.setObjectView(ev);
//			//System.out.println(exec.getSQL());
//			iRowSet = exec.executeQuery();
//		}catch (Exception e) {
//			ExceptionHandler.handle((CoreUI) null,e);
//		}
//		return iRowSet;
//	}

}
