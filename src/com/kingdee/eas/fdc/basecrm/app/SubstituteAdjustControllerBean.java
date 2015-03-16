package com.kingdee.eas.fdc.basecrm.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.ITranPayListEntry;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class SubstituteAdjustControllerBean extends AbstractSubstituteAdjustControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.SubstituteAdjustControllerBean");

	protected void _transfTo(Context ctx,SubstituteAdjustInfo adjustInfo)			throws BOSException, EASBizException {
		if(adjustInfo==null) return;
		if(adjustInfo.getId()==null) return;
		
		adjustInfo = (SubstituteAdjustInfo)this._getValue(ctx, "select *,moneyDefine.name,entrys.* where id = '"+adjustInfo.getId()+"'");
		
		if(adjustInfo.getEntrys()==null || adjustInfo.getEntrys().size()==0) return;
		if(adjustInfo.getTransfAdjustDate()!=null)
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经传递，不能重复传递！"));

		for (int i = 0; i < adjustInfo.getEntrys().size(); i++) {
			SubstituteAdjustEntryInfo adjustEntryInfo = adjustInfo.getEntrys().get(i);
			if(adjustEntryInfo.getRelateBizEntryId()!=null) {	//修改
				TranBusinessOverViewInfo tranViewInfo = new TranBusinessOverViewInfo();
				tranViewInfo.setId(BOSUuid.read(adjustEntryInfo.getRelateBizEntryId()));
				if(adjustEntryInfo.getNewPayAmount().compareTo(adjustEntryInfo.getPayAmount())==0)
					continue; 
					
				tranViewInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
				tranViewInfo.setDesc(tranViewInfo.getDesc()==null?"":tranViewInfo.getDesc() + " ; 由代收费用调整单"+adjustInfo.getNumber()+"修改应收金额");
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("appAmount"));
				selector.add(new SelectorItemInfo("desc"));
				TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tranViewInfo, selector);

				//tranViewInfo = TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo("select where id ='"+tranViewInfo.getId()+"' ");
				ITranPayListEntry payListFactory = null;
				if(RelatBizType.PreOrder.equals(adjustEntryInfo.getRelateBizType())) { //预约单
					payListFactory = SincerReceiveEntryFactory.getLocalInstance(ctx);
				}else if(RelatBizType.PrePur.equals(adjustEntryInfo.getRelateBizType())) { //预订单
					payListFactory = PrePurchasePayListEntryFactory.getLocalInstance(ctx);
				}else if(RelatBizType.Purchase.equals(adjustEntryInfo.getRelateBizType())) { //认购单
					payListFactory = PurPayListEntryFactory.getLocalInstance(ctx);					
				}else if(RelatBizType.Sign.equals(adjustEntryInfo.getRelateBizType())) { //签约单
					payListFactory = SignPayListEntryFactory.getLocalInstance(ctx);			
				}	
				if(payListFactory==null) continue;
				TranPayListEntryCollection tanPayEntryColl = payListFactory.getTranPayListEntryCollection("select * where tanPayListEntryId = '"+tranViewInfo.getId()+"' ");
				if(tanPayEntryColl.size()==0) continue;
				TranPayListEntryInfo tanPayListInfo = tanPayEntryColl.get(0);
				tanPayListInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
				tanPayListInfo.setDescription(tanPayListInfo.getDescription()==null?"":tanPayListInfo.getDescription() + " ; 由代收费用调整单"+adjustInfo.getNumber()+"修改应收金额");
				selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("appAmount"));
				selector.add(new SelectorItemInfo("description"));
				payListFactory.updatePartial(tanPayListInfo, selector);
			}else{	//新增

				BOSUuid billId = BOSUuid.read(adjustEntryInfo.getRelateBizId());
				if(!DynamicObjectFactory.getLocalInstance(ctx).exists(billId.getType(), new ObjectUuidPK(billId)))
					continue;
				
				CompanyOrgUnitInfo tempCompany = ContextUtil.getCurrentFIUnit(ctx);
				CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,tempCompany.getId().toString(),null,false);
				BaseTransactionInfo objectValue = (BaseTransactionInfo)DynamicObjectFactory.getLocalInstance(ctx).getValue(billId.getType(), new ObjectUuidPK(billId));
				
				//2.交易明细上增加此代收明细
				TranBusinessOverViewInfo tranEntryInfo = new TranBusinessOverViewInfo();
				TransactionInfo transInfo = new TransactionInfo();
				transInfo.setId(objectValue.getTransactionID());
				tranEntryInfo.setHead(transInfo);	
				tranEntryInfo.setSeq(99);
				tranEntryInfo.setMoneyDefine(adjustInfo.getMoneyDefine());
				tranEntryInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
				tranEntryInfo.setCurrency(company.getBaseCurrency());
				tranEntryInfo.setAppDate(new Date());
				tranEntryInfo.setBusinessName(adjustInfo.getMoneyDefine().getName());
				tranEntryInfo.setFinishDate(new Date());
				tranEntryInfo.setDesc("由代收费用调整单"+adjustInfo.getNumber()+"生成");
				tranEntryInfo.setType(BusTypeEnum.PAY);
				IObjectPK tanEntryPk = TranBusinessOverViewFactory.getLocalInstance(ctx).addnew(tranEntryInfo);
								
				//1.业务单据上增加此代收明细
				ITranPayListEntry payListFactory = null;
				if(RelatBizType.PreOrder.equals(adjustEntryInfo.getRelateBizType())) { //预约单
					payListFactory = SincerReceiveEntryFactory.getLocalInstance(ctx);
					SincerReceiveEntryInfo payListEntryInfo = new SincerReceiveEntryInfo();
					payListEntryInfo.setHead((SincerityPurchaseInfo)objectValue);
					payListEntryInfo.setSeq(99);
					payListEntryInfo.setMoneyDefine(adjustInfo.getMoneyDefine());
					payListEntryInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
					payListEntryInfo.setCurrency(company.getBaseCurrency());
					payListEntryInfo.setAppDate(new Date());
					payListEntryInfo.setTanPayListEntryId(BOSUuid.read(tanEntryPk.toString()));
					payListEntryInfo.setDescription("由代收费用调整单"+adjustInfo.getNumber()+"生成");
					payListFactory.addnew(payListEntryInfo);
				}else if(RelatBizType.PrePur.equals(adjustEntryInfo.getRelateBizType())) { //预订单
					payListFactory = PrePurchasePayListEntryFactory.getLocalInstance(ctx);
					PrePurchasePayListEntryInfo payListEntryInfo = new PrePurchasePayListEntryInfo();
					payListEntryInfo.setHead((PrePurchaseManageInfo)objectValue);
					payListEntryInfo.setSeq(99);
					payListEntryInfo.setMoneyDefine(adjustInfo.getMoneyDefine());
					payListEntryInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
					payListEntryInfo.setCurrency(company.getBaseCurrency());
					payListEntryInfo.setAppDate(new Date());	
					payListEntryInfo.setTanPayListEntryId(BOSUuid.read(tanEntryPk.toString()));
					payListEntryInfo.setDescription("由代收费用调整单"+adjustInfo.getNumber()+"生成");
					payListFactory.addnew(payListEntryInfo);					
				}else if(RelatBizType.Purchase.equals(adjustEntryInfo.getRelateBizType())) { //认购单
					payListFactory = PurPayListEntryFactory.getLocalInstance(ctx);
					PurPayListEntryInfo payListEntryInfo = new PurPayListEntryInfo();
					payListEntryInfo.setHead((PurchaseManageInfo)objectValue);
					payListEntryInfo.setSeq(99);
					payListEntryInfo.setMoneyDefine(adjustInfo.getMoneyDefine());
					payListEntryInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
					payListEntryInfo.setCurrency(company.getBaseCurrency());
					payListEntryInfo.setAppDate(new Date());
					payListEntryInfo.setTanPayListEntryId(BOSUuid.read(tanEntryPk.toString()));
					payListEntryInfo.setDescription("由代收费用调整单"+adjustInfo.getNumber()+"生成");
					payListFactory.addnew(payListEntryInfo);							
				}else if(RelatBizType.Sign.equals(adjustEntryInfo.getRelateBizType())) { //签约单
					payListFactory = SignPayListEntryFactory.getLocalInstance(ctx);
					SignPayListEntryInfo payListEntryInfo = new SignPayListEntryInfo();
					payListEntryInfo.setHead((SignManageInfo)objectValue);
					payListEntryInfo.setSeq(99);
					payListEntryInfo.setMoneyDefine(adjustInfo.getMoneyDefine());
					payListEntryInfo.setAppAmount(adjustEntryInfo.getNewPayAmount());
					payListEntryInfo.setCurrency(company.getBaseCurrency());
					payListEntryInfo.setAppDate(new Date());
					payListEntryInfo.setTanPayListEntryId(BOSUuid.read(tanEntryPk.toString()));
					payListEntryInfo.setDescription("由代收费用调整单"+adjustInfo.getNumber()+"生成");
					payListFactory.addnew(payListEntryInfo);						
				}				
			}
		}
		
		adjustInfo.setTransfAdjustDate(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("transfAdjustDate"));
		this._updatePartial(ctx, adjustInfo, selector);
	}
    
    
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SubstituteAdjustInfo adjustInfo = (SubstituteAdjustInfo)this._getValue(ctx, pk);
		if(adjustInfo.getTransfAdjustDate()!=null)
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经传递，不能删除！"));
			
		super._delete(ctx, pk);
	}

	
	protected SubstituteAdjustEntryCollection _getCalculateResult(Context ctx,
			BOSUuid moneyDefineId, BOSUuid sellPorjectId, BOSUuid buildingId,	CollectionInfo collInfo) throws BOSException, EASBizException {
		collInfo = CollectionFactory.getLocalInstance(ctx).getCollectionInfo(new ObjectUuidPK(collInfo.getId()));
		
    	String filterSql = "";
    	if(buildingId!=null) filterSql += " and room.building.id = '"+buildingId.toString()+"' "; 
    	else if(sellPorjectId!=null){
    		filterSql += " and room.building.sellProject.id = '"+sellPorjectId.toString()+"' ";
    	}   	
    	
    	TransactionCollection transactionColl = TransactionFactory.getLocalInstance(ctx)
    				.getTransactionCollection("select billId ,billNumber,billTotalAmount,currentLink, customerNames ,tranDate" +
    						" ,room.name,room.building.name ,tranBusinessOverView.* where  " +
    						"  (isValid =0 or isValid is null ) "+filterSql );
    	SubstituteAdjustEntryCollection adjustColl = new SubstituteAdjustEntryCollection(); 
    	String prePurIdsStr = "";
    	String purchaIdsStr = "";		//and moneyDefine.id = '"+moneyDefineId.toString()+"'
    	String signIdsTtr = "";			//(isSubstitute = 0 or isSubstitute is null)
    	for (int i = 0; i < transactionColl.size(); i++) {
    		TransactionInfo transactionInfo = transactionColl.get(i);
    		BOSUuid tranViewBillId = transactionInfo.getBillId();
    		if(tranViewBillId!=null) {
    	    	SubstituteAdjustEntryInfo newEntryInfo = new SubstituteAdjustEntryInfo();
    			newEntryInfo.setRoom(transactionInfo.getRoom());
    			newEntryInfo.setBuilding(transactionInfo.getRoom().getBuilding());
    			newEntryInfo.setCustomer(transactionInfo.getCustomerNames());
    			newEntryInfo.setRelateBizId(transactionInfo.getBillId().toString());
    			newEntryInfo.setRelateBizType(CRMHelper.retRelateBizTypeById(transactionInfo.getBillId()));
    			newEntryInfo.setBizDate(transactionInfo.getTranDate());
    			//查看分录里是否有此款项的代收费用，若没有则要新增；若已存在，则要看是否已经代收，否则不显示
    			boolean isExist = false;
    			TranBusinessOverViewCollection viewColl = transactionInfo.getTranBusinessOverView();
    			for (int j = 0; j < viewColl.size(); j++) {
    				TranBusinessOverViewInfo tranVieInfo = viewColl.get(j);
    				if(tranVieInfo.getType()==null || !BusTypeEnum.PAY.equals(tranVieInfo.getType())) continue;
					if(tranVieInfo.getMoneyDefine()!=null && tranVieInfo.getMoneyDefine().getId().equals(moneyDefineId)){
						isExist = true;
						if(!tranVieInfo.isIsSubstitute()) {
							SubstituteAdjustEntryInfo cloneEntryInfo = (SubstituteAdjustEntryInfo)newEntryInfo.clone();
							cloneEntryInfo.setPayAmount(tranVieInfo.getAppAmount());
							cloneEntryInfo.setRelateBizEntryId(tranVieInfo.getId().toString());
							adjustColl.add(cloneEntryInfo);
						}
					}	
				}
    			
    			if(!isExist){
        			newEntryInfo.setPayAmount(new BigDecimal("0"));
        			newEntryInfo.setRelateBizEntryId(null);
        			adjustColl.add(newEntryInfo);
    			}
    		
    			BOSObjectType bosType = tranViewBillId.getType();
    			if(bosType.equals(new PrePurchaseManageInfo().getBOSType())){
    				prePurIdsStr += ",'"+ tranViewBillId.toString() +"'";
    			}else if(bosType.equals(new PurchaseManageInfo().getBOSType())){
    				purchaIdsStr += ",'"+ tranViewBillId.toString() +"'";
    			}else if(bosType.equals(new SignManageInfo().getBOSType())){
    				signIdsTtr += ",'"+ tranViewBillId.toString() +"'";
    			}
    		}
		}
    	
    	
    	
    	Map allBillIdMap = new HashMap();
    	String selectSql = "select number,contractTotalAmount,dealTotalAmount,strdTotalAmount,bulidingArea,roomArea,room.*,room.buildingProperty.* where id in ";
    	if(!prePurIdsStr.equals("")) {
    		prePurIdsStr = prePurIdsStr.substring(1);
    		CoreBaseCollection preColl = PrePurchaseManageFactory.getLocalInstance(ctx)
    															.getCollection(selectSql + " ("+prePurIdsStr+") ");
    		for (int i = 0; i < preColl.size(); i++) {
    			BaseTransactionInfo transInfo = (BaseTransactionInfo)preColl.get(i);
    			allBillIdMap.put(transInfo.getId().toString(),transInfo);
			}
    	}
    	
    	if(!purchaIdsStr.equals("")) {
    		purchaIdsStr = purchaIdsStr.substring(1);
    		CoreBaseCollection transColl = PurchaseManageFactory.getLocalInstance(ctx)
    															.getCollection(selectSql + " ("+purchaIdsStr+") ");
    		for (int i = 0; i < transColl.size(); i++) {
    			BaseTransactionInfo transInfo = (BaseTransactionInfo)transColl.get(i);
    			allBillIdMap.put(transInfo.getId().toString(),transInfo);
			}
    	}    	
    	
    	if(!signIdsTtr.equals("")) {
    		signIdsTtr = signIdsTtr.substring(1);
    		CoreBaseCollection transColl = SignManageFactory.getLocalInstance(ctx)
    															.getCollection(selectSql + " ("+signIdsTtr+") ");
    		for (int i = 0; i < transColl.size(); i++) {
    			BaseTransactionInfo transInfo = (BaseTransactionInfo)transColl.get(i);
    			allBillIdMap.put(transInfo.getId().toString(),transInfo);
			}
    	}    	
    	//DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
    	SubstituteAdjustEntryCollection toReturnColl = new SubstituteAdjustEntryCollection();
    	for (int i = 0; i < adjustColl.size(); i++) {
    		SubstituteAdjustEntryInfo adjustInfo = adjustColl.get(i);
    		String billId = adjustInfo.getRelateBizId();
    		BaseTransactionInfo transInfo = (BaseTransactionInfo)allBillIdMap.get(billId);
    		if(transInfo!=null) {
	    		adjustInfo.setRelateBizNumber(transInfo.getNumber());
	    		adjustInfo.setContactAmount(transInfo.getContractTotalAmount());
	    		adjustInfo.setRoom(transInfo.getRoom());
	    		BigDecimal retAmount = CRMHelper.getSubstituteAmountByCollection(ctx, collInfo, transInfo.getDealTotalAmount(),
	    				transInfo.getStrdTotalAmount(),	transInfo.getBulidingArea(), transInfo.getRoomArea(),transInfo.getRoom());
	    		adjustInfo.setNewPayAmount(retAmount);
	    		if(retAmount.compareTo(new BigDecimal("0"))>0)
	    			toReturnColl.add(adjustInfo);
    		}
		}
    	
		return toReturnColl;
	}


	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SubstituteAdjustInfo adjustInfo = (SubstituteAdjustInfo)model;
		// 处理断号
		if (adjustInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(adjustInfo.getId()))) {
			handleIntermitNumber(ctx, adjustInfo);
		}
		
		return super._submit(ctx, model);
	}
	
	
	protected void handleIntermitNumber(Context ctx, CoreBillBaseInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		
		boolean isExist = true;
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId()
				.toString();
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		
		if (!iCodingRuleManager.isExist(info, costUnitId)) {
			isExist = false;
		}
		
		if (isExist) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
					|| !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}		
	
    
    
}