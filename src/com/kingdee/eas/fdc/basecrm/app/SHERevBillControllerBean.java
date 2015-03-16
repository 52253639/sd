package com.kingdee.eas.fdc.basecrm.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.ISHERevBillEntry;
import com.kingdee.eas.fdc.basecrm.ISHERevMap;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevMapCollection;
import com.kingdee.eas.fdc.basecrm.SHERevMapFactory;
import com.kingdee.eas.fdc.basecrm.SHERevMapInfo;
import com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ITranCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.ITranPayListEntry;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class SHERevBillControllerBean extends AbstractSHERevBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.SHERevBillControllerBean");
    
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	if(billId==null) return;
    	SHERevBillInfo model = new SHERevBillInfo();
    	model.setId(billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
    	model.setState(FDCBillStateEnum.AUDITTED);    	
    	model.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	model.setAuditTime(new Date());
    	this._updatePartial(ctx, model, selector);
    	//throw new EASBizException(new NumericExceptionSubItem("100","审批成功！"));
    }
    
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	if(billId==null) return;
    	if(SHERevBillEntryFactory.getLocalInstance(ctx).exists("where parent.id = '"+billId.toString()+"' " +
    					"		and parent.state = '"+FDCBillStateEnum.AUDITTED_VALUE+"' and parent.isGathered = 1 and parent.revAmount!=0 and parent.revAmount is not null")){
    		throw new EASBizException(new NumericExceptionSubItem("100","已生成出纳汇总单，禁止反审批！"));
    	}
    	
    	SHERevBillInfo model = new SHERevBillInfo();
    	model.setId(billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
    	model.setState(FDCBillStateEnum.SUBMITTED);    	
    	model.setAuditor(null);
    	model.setAuditTime(null);
    	this._updatePartial(ctx, model, selector);
    	//throw new EASBizException(new NumericExceptionSubItem("100","反审批审批成功！"));
    }


	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {
		for (int i = 0; i < idList.size(); i++) {
			String idStr = (String)idList.get(i);
			this._audit(ctx, BOSUuid.read(idStr));
		}
	}


	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		for (int i = 0; i < idList.size(); i++) {
			String idStr = (String)idList.get(i);
			this._unAudit(ctx, BOSUuid.read(idStr));
		}	
	}
	
	
	protected void addSysCustomer(Context ctx,SHERevBillInfo revBillInfo) throws EASBizException, BOSException{
		revBillInfo = (SHERevBillInfo)this._getValue(ctx, "select relateBizType,relateBizBillId,sysCustomer,customerEntry.*,customerEntry.sheCustomer.*,customerEntry.sheCustomer.mainCustomer.* where id = '"+revBillInfo.getId().toString()+"'");
		SelectorItemCollection update=new SelectorItemCollection();		
		update.add("sysCustomer");
		if(revBillInfo.getRelateBizBillId()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			ObjectUuidPK pk=new ObjectUuidPK(revBillInfo.getRelateBizBillId());
			IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
			
			if(objectValue instanceof SincerityPurchaseInfo){
				sel.add("customer.customer.mainCustomer.*");
				sel.add("customer.customer.mainCustomer.sysCustomer.*");
				sel.add("customer.isMain");
				SincerityPurchaseInfo info=SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(revBillInfo.getRelateBizBillId()),sel);
				for(int i=0;i<info.getCustomer().size();i++){
					if(info.getCustomer().get(i).isIsMain()&&info.getCustomer().get(i).getCustomer().getMainCustomer()!=null){
						CustomerInfo customer=info.getCustomer().get(i).getCustomer().getMainCustomer().getSysCustomer();
						if(customer!=null){
							revBillInfo.setSysCustomer(customer);
							SHERevBillFactory.getLocalInstance(ctx).updatePartial(revBillInfo, update);
							
							if(customer.getSimpleName()==null){
								customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());
							}else{
								if(customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
									customer.setSimpleName(customer.getSimpleName()+";"+ContextUtil.getCurrentFIUnit(ctx).getName());
								}
							}
							SelectorItemCollection selCol = new SelectorItemCollection();
							selCol.add("simpleName");
							CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
						}else{
							FDCMainCustomerFactory.getLocalInstance(ctx).addToSysCustomer(info.getCustomer().get(i).getCustomer().getMainCustomer());
							customer=info.getCustomer().get(i).getCustomer().getMainCustomer().getSysCustomer();
						}
						CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(customer.getAdminCU().getId()), new ObjectUuidPK(customer.getId()), new ObjectUuidPK(ContextUtil.getCurrentFIUnit(ctx).getId()));
					}
				}
			}else if(objectValue instanceof PrePurchaseManageInfo){
				sel.add("prePurchaseCustomerEntry.customer.mainCustomer.*");
				sel.add("prePurchaseCustomerEntry.customer.mainCustomer.sysCustomer.*");
				sel.add("prePurchaseCustomerEntry.isMain");
				PrePurchaseManageInfo info=PrePurchaseManageFactory.getLocalInstance(ctx).getPrePurchaseManageInfo(new ObjectUuidPK(revBillInfo.getRelateBizBillId()),sel);
				for(int i=0;i<info.getPrePurchaseCustomerEntry().size();i++){
					if(info.getPrePurchaseCustomerEntry().get(i).isIsMain()&&info.getPrePurchaseCustomerEntry().get(i).getCustomer().getMainCustomer()!=null){
						CustomerInfo customer=info.getPrePurchaseCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						if(customer!=null){
							revBillInfo.setSysCustomer(customer);
							SHERevBillFactory.getLocalInstance(ctx).updatePartial(revBillInfo, update);
							
							if(customer.getSimpleName()==null){
								customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());
							}else{
								if(customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
									customer.setSimpleName(customer.getSimpleName()+";"+ContextUtil.getCurrentFIUnit(ctx).getName());
								}
							}
							SelectorItemCollection selCol = new SelectorItemCollection();
							selCol.add("simpleName");
							CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
						}else{
							FDCMainCustomerFactory.getLocalInstance(ctx).addToSysCustomer(info.getPrePurchaseCustomerEntry().get(i).getCustomer().getMainCustomer());
							customer=info.getPrePurchaseCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						}
						CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(customer.getAdminCU().getId()), new ObjectUuidPK(customer.getId()), new ObjectUuidPK(ContextUtil.getCurrentFIUnit(ctx).getId()));
					}
				}
			}else if(objectValue instanceof PurchaseManageInfo){
				sel.add("purCustomerEntry.customer.mainCustomer.*");
				sel.add("purCustomerEntry.customer.mainCustomer.sysCustomer.*");
				sel.add("purCustomerEntry.isMain");
				PurchaseManageInfo info=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo(new ObjectUuidPK(revBillInfo.getRelateBizBillId()),sel);
				for(int i=0;i<info.getPurCustomerEntry().size();i++){
					if(info.getPurCustomerEntry().get(i).isIsMain()&&info.getPurCustomerEntry().get(i).getCustomer().getMainCustomer()!=null){
						CustomerInfo customer=info.getPurCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						if(customer!=null){
							revBillInfo.setSysCustomer(customer);
							SHERevBillFactory.getLocalInstance(ctx).updatePartial(revBillInfo, update);
							
							if(customer.getSimpleName()==null){
								customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());
							}else{
								if(customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
									customer.setSimpleName(customer.getSimpleName()+";"+ContextUtil.getCurrentFIUnit(ctx).getName());
								}
							}
							SelectorItemCollection selCol = new SelectorItemCollection();
							selCol.add("simpleName");
							CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
						}else{
							FDCMainCustomerFactory.getLocalInstance(ctx).addToSysCustomer(info.getPurCustomerEntry().get(i).getCustomer().getMainCustomer());
							customer=info.getPurCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						}
						CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(customer.getAdminCU().getId()), new ObjectUuidPK(customer.getId()), new ObjectUuidPK(ContextUtil.getCurrentFIUnit(ctx).getId()));
					}
				}
			}else if(objectValue instanceof SignManageInfo){
				sel.add("signCustomerEntry.customer.mainCustomer.*");
				sel.add("signCustomerEntry.customer.mainCustomer.sysCustomer.*");
				sel.add("signCustomerEntry.isMain");
				SignManageInfo info=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(revBillInfo.getRelateBizBillId()),sel);
				for(int i=0;i<info.getSignCustomerEntry().size();i++){
					if(info.getSignCustomerEntry().get(i).isIsMain()&&info.getSignCustomerEntry().get(i).getCustomer().getMainCustomer()!=null){
						CustomerInfo customer=info.getSignCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						if(customer!=null){
							revBillInfo.setSysCustomer(customer);
							SHERevBillFactory.getLocalInstance(ctx).updatePartial(revBillInfo, update);
							
							if(customer.getSimpleName()==null){
								customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());
							}else{
								if(customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
									customer.setSimpleName(customer.getSimpleName()+";"+ContextUtil.getCurrentFIUnit(ctx).getName());
								}
							}
							SelectorItemCollection selCol = new SelectorItemCollection();
							selCol.add("simpleName");
							CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
						}else{
							FDCMainCustomerFactory.getLocalInstance(ctx).addToSysCustomer(info.getSignCustomerEntry().get(i).getCustomer().getMainCustomer());
							customer=info.getSignCustomerEntry().get(i).getCustomer().getMainCustomer().getSysCustomer();
						}
						CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(customer.getAdminCU().getId()), new ObjectUuidPK(customer.getId()), new ObjectUuidPK(ContextUtil.getCurrentFIUnit(ctx).getId()));
					}
				}
			}
		}
	}
    
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {	
		SHERevBillInfo revBillInfo = (SHERevBillInfo)model;
		if(revBillInfo.getState()==null || revBillInfo.getState().equals(FDCBillStateEnum.SAVED))
				revBillInfo.setState(FDCBillStateEnum.SUBMITTED);
		
		// 处理断号
		if (revBillInfo.getId() == null	|| !this._exists(ctx, new ObjectUuidPK(revBillInfo.getId()))) {
			handleIntermitNumber(ctx, revBillInfo);
		}
		
		if(revBillInfo.get("SubmitAndAudit")!=null) {
			revBillInfo.setState(FDCBillStateEnum.AUDITTED);
			revBillInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			revBillInfo.setAuditTime(new Date());
		}
		
		if(revBillInfo.getRelateBizType()!=null && revBillInfo.getRelateBizType().equals(RelatBizType.Change)){
			return super._submit(ctx, model);	//如果是变更生成的，则直接提交
		}
		
		SHERevBillInfo oldBillInfo = null;
		if(revBillInfo.getId()!=null) //修改时
			 oldBillInfo = (SHERevBillInfo)this._getValue(ctx, "select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name,entrys.revEntry.* where id = '"+revBillInfo.getId().toString()+"'");

		Map newEntryInfoMap = new HashMap();				//新提交的收款明细的id对应映射
		for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
			SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
			if(thisEntryInfo.getId()!=null)
				newEntryInfoMap.put(thisEntryInfo.getId().toString(), thisEntryInfo);
		}
				
		IObjectPK pk = null;		
		if(RevBillTypeEnum.gathering.equals(revBillInfo.getRevBillType())){ //收款情况
			if(oldBillInfo!=null) {  //修改
				//摧毁历史实收明细的对冲关系
				if(oldBillInfo.getRelateTransId()!=null){
					for (int i = 0; i < oldBillInfo.getEntrys().size(); i++) {
						SHERevBillEntryInfo oldEntryInfo = oldBillInfo.getEntrys().get(i);
						deleteRevMapRelation(ctx, null, oldBillInfo.getRelateBizType(), oldEntryInfo);
						//校验是否符合条件  如果是删除这条明细，则已对冲金额必须为0,且无已退已转金额
						if(newEntryInfoMap.get(oldEntryInfo.getId().toString())==null){
							if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
								throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’无法恢复对冲关系，禁止删除，请检查！"));
						}
					}
				}
			}
						
			//此处为了防止收款提交前，其它地方已经再次做收款或退款等，以至于已退、已转等金额发生变化  (理论上多人同时操作时可能会出现)
			if(oldBillInfo!=null) {
				for (int i = 0; i < oldBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = oldBillInfo.getEntrys().get(i);
					SHERevBillEntryInfo newEntryInfo = (SHERevBillEntryInfo)newEntryInfoMap.get(oldEntryInfo.getId().toString());
					if(newEntryInfo!=null) {//这几个值不能因为新单据的提交而被修改
						newEntryInfo.setHasRefundmentAmount(oldEntryInfo.getHasRefundmentAmount());
						newEntryInfo.setHasTransferAmount(oldEntryInfo.getHasTransferAmount());
						newEntryInfo.setHasMakeInvoiceAmount(oldEntryInfo.getHasMakeInvoiceAmount());
						newEntryInfo.setHasMapedAmount(oldEntryInfo.getHasMapedAmount());  //旧的已对冲金额已经发生变化，此处要跟着变化
					}
				}
			}

			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasMapedAmount");
			if(oldBillInfo!=null) {
				for(int i=0;i<oldBillInfo.getEntrys().size();i++){
					for(int j=0;j<oldBillInfo.getEntrys().get(i).getRevEntry().size();j++){
						SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(oldBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
						revbill.setHasMapedAmount(revbill.getHasMapedAmount().subtract(oldBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
						SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
					}
				}
			}
			
			pk = super._submit(ctx, revBillInfo);
			
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				for(int j=0;j<revBillInfo.getEntrys().get(i).getRevEntry().size();j++){
					SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
					revbill.setHasMapedAmount(revbill.getHasMapedAmount().add(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
					SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
				}
			}
			revBillInfo = (SHERevBillInfo)this._getValue(ctx, "select *,room.*,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name " +
									" ,customerEntry.*,customerEntry.sheCustomer.name,customerEntry.sheCustomer.mainCustomer.* where id = '"+revBillInfo.getId().toString()+"'");
			//对当前的实收明细创建对冲关系
			if(revBillInfo.getRelateTransId()!=null){
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
					this.createRevMapRelation(ctx, revBillInfo.getRelateTransId(),null, revBillInfo.getRelateBizType(), thisEntryInfo);	
				}
			}
			
			//收款单提交后，要考虑开票问题
			ChequeDetailEntryFactory.getLocalInstance(ctx).dealForSheRevBillCheque(revBillInfo);
			
//			for(int i=0;i<revBillInfo.getCustomerEntry().size();i++){
//				if(revBillInfo.getCustomerEntry().get(i).getSheCustomer().getMainCustomer()!=null){
//					if(revBillInfo.getCustomerEntry().get(i).getSheCustomer().getMainCustomer().getSysCustomer()==null){
//						FDCMainCustomerFactory.getLocalInstance(ctx).addToSysCustomer(revBillInfo.getCustomerEntry().get(i).getSheCustomer().getMainCustomer());
//					}else{
//						CustomerInfo customer=revBillInfo.getCustomerEntry().get(i).getSheCustomer().getMainCustomer().getSysCustomer();
//						if(customer.getSimpleName()==null){
//							customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());
//						}else{
//							if(customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
//								customer.setSimpleName(customer.getSimpleName()+";"+ContextUtil.getCurrentFIUnit(ctx).getName());
//							}
//						}
//						SelectorItemCollection selCol = new SelectorItemCollection();
//						selCol.add("simpleName");
//						CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
//					}
//				}
//			}
			addSysCustomer(ctx,revBillInfo);
			boolean isAddRoom=false;
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_ROOMTRANSFER", revBillInfo.getSaleOrgUnit().getId().toString());
			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
			if(hmAllParam.get("CIFI_ROOMTRANSFER")!=null){
				isAddRoom=Boolean.parseBoolean(hmAllParam.get("CIFI_ROOMTRANSFER").toString());
			}
			if(isAddRoom&&revBillInfo.getRoom()!=null&&revBillInfo.getRoom().getName()!=null&&!"".equals(revBillInfo.getRoom().getName().trim())){
				GeneralAsstActTypeCollection aaCol=GeneralAsstActTypeFactory.getLocalInstance(ctx).getGeneralAsstActTypeCollection("select * from where name='"+revBillInfo.getRoom().getName()+"' or description='"+revBillInfo.getRoom().getName()+"'");
				if(aaCol.size()==0){
					GeneralAsstActTypeInfo room=new GeneralAsstActTypeInfo();
					room.setNumber(revBillInfo.getRoom().getNumber());
					room.setName(revBillInfo.getRoom().getName());
					room.setDescription(revBillInfo.getRoom().getName());
					
					GeneralAsstActTypeGroupCollection group=GeneralAsstActTypeGroupFactory.getLocalInstance(ctx).getGeneralAsstActTypeGroupCollection("select * from where number='Z00001'");
					if(group.size()>0){
						room.setGroup(group.get(0));
					}
					room.setIsLeaf(true);
					room.setCreatorCompany(ContextUtil.getCurrentFIUnit(ctx));
					room.setIsEnabled(true);
					GeneralAsstActTypeFactory.getLocalInstance(ctx).addnew(room);
				}
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillInfo.getRevBillType())){ //退款情况
			
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasRefundmentAmount");
			
			SelectorItemCollection transel=new SelectorItemCollection();
			transel.add("actRevAmount");
			
			if(oldBillInfo!=null) {
				for(int i=0;i<oldBillInfo.getEntrys().size();i++){
					for(int j=0;j<oldBillInfo.getEntrys().get(i).getRevEntry().size();j++){
						SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(oldBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
						
						SHERevMapCollection mapInfo =SHERevMapFactory.getLocalInstance(ctx).getSHERevMapCollection("select payListEntryId from where revBillEntryId.id='"+revbill.getId().toString()+"'");
						if(mapInfo.size()!=0){
							TranBusinessOverViewInfo tranInfo=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(mapInfo.get(0).getPayListEntryId()));
							tranInfo.setActRevAmount(tranInfo.getActRevAmount().add(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
							TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tranInfo, transel);
						}
						
						revbill.setHasRefundmentAmount(revbill.getHasRefundmentAmount().subtract(oldBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
						SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
					}
				}
			}
			
			pk = super._submit(ctx, revBillInfo);
			
			SelectorItemCollection psel=new SelectorItemCollection();
    		psel.add("isTansCreate");
    		psel.add("isGathered");
    		
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				for(int j=0;j<revBillInfo.getEntrys().get(i).getRevEntry().size();j++){
					SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
					
//					SHERevMapCollection mapInfo =SHERevMapFactory.getLocalInstance(ctx).getSHERevMapCollection("select payListEntryId from where revBillEntryId.id='"+revbill.getId().toString()+"'");
//					if(mapInfo.size()!=0){
//						TranBusinessOverViewInfo tranInfo=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(mapInfo.get(0).getPayListEntryId()));
//						tranInfo.setActRevAmount(tranInfo.getActRevAmount().subtract(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
//						TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tranInfo, transel);
//					}
					
					revbill.setHasRefundmentAmount(revbill.getHasRefundmentAmount().add(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
					SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
					
					if(revBillInfo.isIsGathered()&&revBillInfo.isIsTansCreate()){
						revbill.getParent().setIsTansCreate(true);
						revbill.getParent().setIsGathered(true);
	    				SHERevBillFactory.getLocalInstance(ctx).updatePartial(revbill.getParent(), psel);
					}
				}
			}
			//对当前的实收明细创建对冲关系
			if(revBillInfo.getRelateTransId()!=null){
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
					this.createRevMapRelation(ctx, revBillInfo.getRelateTransId(),null, revBillInfo.getRelateBizType(), thisEntryInfo);	
				}
			}
			addSysCustomer(ctx,revBillInfo);
			
		}else if(RevBillTypeEnum.transfer.equals(revBillInfo.getRevBillType())){ //转款情况
			
		}
		return pk;
	}
	
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SHERevBillInfo revBillInfo = (SHERevBillInfo)this._getValue(ctx, "select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name,entrys.revEntry.* where id = '"+pk.toString()+"'");
		if(revBillInfo.getRelateBizType()!=null && revBillInfo.getRelateBizType().equals(RelatBizType.Change)){
			super._delete(ctx, pk);		//如果是变更产生的，可直接删除
			return;
		}		
		if(revBillInfo.getState().equals(FDCBillStateEnum.SAVED)){
			super._delete(ctx, pk);		
			return;
		}
		if(revBillInfo.getState()!=null && !revBillInfo.getState().equals(FDCBillStateEnum.SAVED) 
				&& !revBillInfo.getState().equals(FDCBillStateEnum.SUBMITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","非保存或提交状态的单据禁止删除！"));
		}		
		
		if(RevBillTypeEnum.gathering.equals(revBillInfo.getRevBillType())){ //收款情况
			if(revBillInfo.getRelateTransId()!=null) {
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = revBillInfo.getEntrys().get(i);
					deleteRevMapRelation(ctx, null, revBillInfo.getRelateBizType(), oldEntryInfo);
					//校验是否符合条件  如果是删除这条明细，则已对冲金额必须为0,且无已退已转金额
//						if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
//							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’无法恢复对冲关系，禁止删除，请检查！"));
						if(oldEntryInfo.getHasRefundmentAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’存在已退款，禁止删除，请检查！"));
						if(oldEntryInfo.getHasTransferAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’存在已转款，禁止删除，请检查！"));							
				}				
			}	
			SelectorItemCollection bpsel=new SelectorItemCollection();
			bpsel.add("sheRevBill");
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				BankPaymentEntryCollection bpentry=	BankPaymentEntryFactory.getLocalInstance(ctx).getBankPaymentEntryCollection("select * from where sheRevBill='"+revBillInfo.getEntrys().get(i).getId()+"'");
				
				for(int j=0;j<bpentry.size();j++){
					bpentry.get(j).setSheRevBill(null);
					BankPaymentEntryFactory.getLocalInstance(ctx).updatePartial(bpentry.get(j), bpsel);
				}
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sheRevBillEntryId", revBillInfo.getEntrys().get(i).getId()));
				if(SHERevbillTwoEntryFactory.getLocalInstance(ctx).exists(filter)){
					throw new EASBizException(new NumericExceptionSubItem("100","单据明细"+revBillInfo.getEntrys().get(i).getMoneyDefine().getName()+"已经参与冲抵，不能进行删除操作！"));
				}
			}
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasMapedAmount");
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				for(int j=0;j<revBillInfo.getEntrys().get(i).getRevEntry().size();j++){
					SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
					revbill.setHasMapedAmount(revbill.getHasMapedAmount().subtract(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
					SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
				}
			}
		
			
		}else if(RevBillTypeEnum.refundment.equals(revBillInfo.getRevBillType())){ //退款情况
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasRefundmentAmount");
			
			SelectorItemCollection transel=new SelectorItemCollection();
			transel.add("actRevAmount");
			
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				for(int j=0;j<revBillInfo.getEntrys().get(i).getRevEntry().size();j++){
					SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
					
					SHERevMapCollection mapInfo =SHERevMapFactory.getLocalInstance(ctx).getSHERevMapCollection("select payListEntryId from where revBillEntryId.id='"+revbill.getId().toString()+"'");
					if(mapInfo.size()!=0){
						TranBusinessOverViewInfo tranInfo=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(mapInfo.get(0).getPayListEntryId()));
						tranInfo.setActRevAmount(tranInfo.getActRevAmount().add(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
						TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tranInfo, transel);
					}
					revbill.setHasRefundmentAmount(revbill.getHasRefundmentAmount().subtract(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
					SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
				}
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillInfo.getRevBillType())){ //转款情况
//			//1.删除生成的收款单	2.删除生成的退款单
//			if(revBillInfo.getTrsToGatherId()==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","转款单对应的生成收款单id为空，请检查！"));
//			if(revBillInfo.getTrsToRefundId()==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","转款单对应的生成退款单id为空，请检查！"));
//			this._delete(ctx, new ObjectUuidPK(BOSUuid.read(revBillInfo.getTrsToGatherId())));
//			this._delete(ctx, new ObjectUuidPK(BOSUuid.read(revBillInfo.getTrsToRefundId())));
			
			if(revBillInfo.getRelateTransId()!=null) {
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = revBillInfo.getEntrys().get(i);
					deleteRevMapRelation(ctx, null, revBillInfo.getRelateBizType(), oldEntryInfo);
					//校验是否符合条件  如果是删除这条明细，则已对冲金额必须为0,且无已退已转金额
//						if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
//							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’无法恢复对冲关系，禁止删除，请检查！"));
						if(oldEntryInfo.getHasRefundmentAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’存在已退款，禁止删除，请检查！"));
						if(oldEntryInfo.getHasTransferAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","收款明细中‘"+oldEntryInfo.getMoneyDefine().getName()+"’存在已转款，禁止删除，请检查！"));							
				}				
			}	
			
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sheRevBillEntryId", revBillInfo.getEntrys().get(i).getId()));
				if(SHERevbillTwoEntryFactory.getLocalInstance(ctx).exists(filter)){
					throw new EASBizException(new NumericExceptionSubItem("100","单据明细"+revBillInfo.getEntrys().get(i).getMoneyDefine().getName()+"已经参与冲抵，不能进行删除操作！"));
				}
			}
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasTransferAmount");
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				for(int j=0;j<revBillInfo.getEntrys().get(i).getRevEntry().size();j++){
					SHERevBillEntryInfo revbill=SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getSheRevBillEntryId()));
					revbill.setHasTransferAmount(revbill.getHasTransferAmount().subtract(revBillInfo.getEntrys().get(i).getRevEntry().get(j).getAmount()));
					SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revbill, sel);
				}
			}
		}
		super._delete(ctx, pk);
	}
	
	
	/**处理删除退款单时的操作 *
	 * 对所有退款明细的对应收款明细恢复已退金额即可 */
	private void restoreHasRefundmentAmount(Context ctx,SHERevBillInfo revBillInfo) throws BOSException, EASBizException {
		SHERevBillEntryCollection revEntryColl =  revBillInfo.getEntrys();
		if(!revBillInfo.getRevBillType().equals(RevBillTypeEnum.refundment))
			throw new EASBizException(new NumericExceptionSubItem("100","单据"+revBillInfo.getNumber()+"非退款单，请检查！"));
			
		String transEntryIdStr = "";		//收款明细的ids
		Map transEntryMap = new HashMap();
		for (int i = 0; i < revEntryColl.size(); i++) {
			SHERevBillEntryInfo quitEntryInfo = revEntryColl.get(i);   //退款明细
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(quitEntryInfo.getTransferFromEntryId()==null) continue; 
				
			if(quitEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'金额不应该有正数，请检查！"));
			String transEntryId = quitEntryInfo.getTransferFromEntryId();
			if(transEntryIdStr.indexOf(transEntryId)>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细Id重复，请检查！"));
			transEntryIdStr += ",'"+transEntryId+"'";
			transEntryMap.put(transEntryId, quitEntryInfo);
		}
		
		if(transEntryMap.size()==0) return; 
			
		ISHERevBillEntry revEntryFactory = SHERevBillEntryFactory.getLocalInstance(ctx);
		SHERevBillEntryCollection thisRevEntryColl = revEntryFactory.getSHERevBillEntryCollection(
				"select *,parent.relateBizType,parent.relateTransId,moneyDefine.moneyType,moneyDefine.name where id in ("+transEntryIdStr.substring(1)+")");

		if(thisRevEntryColl.size()==0)
			throw new EASBizException(new NumericExceptionSubItem("100","查询不到退款明细对应的收款明细，无法退款，请检查！"));
		
		CoreBaseCollection toUpdateRevEntryColl = new CoreBaseCollection();
		for (int j = 0; j < thisRevEntryColl.size(); j++) {
			SHERevBillEntryInfo transEntryInfo = thisRevEntryColl.get(j);
			SHERevBillEntryInfo quitEntryInfo = (SHERevBillEntryInfo)transEntryMap.get(transEntryInfo.getId().toString());
			if(quitEntryInfo!=null) {
				if(revBillInfo.isIsTansCreate())	//如果是转款单生成的退款单，则要更新已转金额
					transEntryInfo.setHasTransferAmount(transEntryInfo.getHasTransferAmount().add(quitEntryInfo.getRevAmount()));
				else
					transEntryInfo.setHasRefundmentAmount(transEntryInfo.getHasRefundmentAmount().add(quitEntryInfo.getRevAmount()));
				toUpdateRevEntryColl.add(transEntryInfo);
			}
		}
		revEntryFactory.update(toUpdateRevEntryColl);
		
		for (int i = 0; i < toUpdateRevEntryColl.size(); i++) {
			SHERevBillEntryInfo sheRevBillInfo = (SHERevBillEntryInfo)toUpdateRevEntryColl.get(i);
			this.createRevMapRelation(ctx, sheRevBillInfo.getParent().getRelateTransId(),null, sheRevBillInfo.getParent().getRelateBizType(), sheRevBillInfo);
		}
		
	}
	
	
	
	/**处理退款时的对冲关系   ----  只适用于新增退款单时的操作 。修改退款单的情况比较特殊，
		1、更新对应的收款实收明细的已退金额（要先校验）
		2. 若实收明细的部分要退金额已经对冲，则要先销毁对冲关系
	 * */
	private void dealRefundRevMapRelation(Context ctx,SHERevBillInfo revBillInfo) throws BOSException, EASBizException {
	
		SHERevBillEntryCollection revEntryColl =  revBillInfo.getEntrys();
		String transEntryIdStr = "";
		Map transEntryMap = new HashMap();			//实收明细id 与  退款明细的映射关系
		for (int i = 0; i < revEntryColl.size(); i++) {
			SHERevBillEntryInfo quitEntryInfo = revEntryColl.get(i);
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(quitEntryInfo.getTransferFromEntryId()==null) 
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细Id为空，请检查！"));
			if(quitEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'金额不应该有正数，请检查！"));
			String transEntryId = quitEntryInfo.getTransferFromEntryId();
			if(transEntryIdStr.indexOf(transEntryId)>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细Id重复，请检查！"));
			transEntryIdStr += ",'"+transEntryId+"'";
			transEntryMap.put(transEntryId, quitEntryInfo);
  logger.info("-----------------退款-"+quitEntryInfo.getMoneyDefine().getName()+"--退款金额= "+quitEntryInfo.getRevAmount() + "----begin");					
		}
		
		SHERevBillEntryCollection transEntryColl = SHERevBillEntryFactory.getLocalInstance(ctx) //退款明细对应的收款明细
							.getSHERevBillEntryCollection("select parent.relateTransId,parent.relateBizType,*,moneyDefine.* " +
									"where id in ("+ transEntryIdStr.substring(1) +")");
		if(transEntryColl.size()==0)
			throw new EASBizException(new NumericExceptionSubItem("100","查找不到退款明细对应的收款明细id="+transEntryIdStr.substring(1)+"，请检查！"));
		
		
		for (int i = 0; i < transEntryColl.size(); i++) {
			SHERevBillEntryInfo transEntryInfo = transEntryColl.get(i);  //收款明细
  logger.info("-----------------收款明细-"+transEntryInfo.getMoneyDefine().getName()+"--已退金额="+transEntryInfo.getHasRefundmentAmount()+"--已对冲金额= "+transEntryInfo.getHasMapedAmount() + "----");				
			String gatherMoneyName = transEntryInfo.getMoneyDefine().getName();   
			SHERevBillEntryInfo quitEntryInfo = (SHERevBillEntryInfo)transEntryMap.get(transEntryInfo.getId().toString());  //退款明细
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(transEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))<=0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细'"+gatherMoneyName+"'收款金额不应该有负数，请检查！"));
			if(transEntryInfo.getRemainAmount().compareTo(quitEntryInfo.getRevAmount().negate())<0)
				throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细'"+gatherMoneyName+"'可退金额不足，无法退款请检查！"));
			//如果可退金额-已对冲金额还足够退款，则直接反写对应的收款明细的已退金额即可
			BigDecimal freeAmount = transEntryInfo.getRemainAmount();
			BigDecimal lessAmount = freeAmount.subtract(quitEntryInfo.getRevAmount().negate());
			if(lessAmount.doubleValue()<0) {
				BigDecimal toCancelAmount = lessAmount.negate();  //要取消的对冲金额   
				SHERevMapCollection revMapColl = SHERevMapFactory.getLocalInstance(ctx)
									.getSHERevMapCollection("select * where revBillEntryId.id = '"+transEntryInfo.getId().toString()+"' order by seq "); 
				//CRMHelper.sortCollection(revMapColl, "seq", true); //按应收明细的seq排序
				for (int j = 0; j < revMapColl.size(); j++) {
					//1.更改对冲金额 （可能要删除）   2.应收明细的实收金额     3、实收明细的已对冲金额和已退金额   
					SHERevMapInfo revMapInfo = revMapColl.get(j);
					
					ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
					TranPayListEntryInfo entryInfo = null;	//应收明细可能已经删除，但对冲关系没有被销毁！（要杜绝此类现象）
					TranPayListEntryCollection tempPayListColl = entryFactory.getTranPayListEntryCollection("select actRevAmount where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"' ");
					if(tempPayListColl!=null && tempPayListColl.size()>0)
						entryInfo = tempPayListColl.get(0);
					
					if(toCancelAmount.compareTo(new BigDecimal("0"))>0) {
						if(toCancelAmount.compareTo(revMapInfo.getAmount())>=0){  //删除对冲关系
							SHERevMapFactory.getLocalInstance(ctx).delete("where id= '" + revMapInfo.getId().toString()+"'");
							if(entryInfo!=null)
								entryInfo.setActRevAmount(entryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
							transEntryInfo.setHasMapedAmount(transEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
							toCancelAmount = toCancelAmount.subtract(revMapInfo.getAmount());
		logger.info("-----------------删除对冲金额"+revMapInfo.getAmount() + "------");							
						}else{														//修改对冲关系
							SelectorItemCollection selectorMap = new SelectorItemCollection();
							selectorMap = new SelectorItemCollection();
							selectorMap.add(new SelectorItemInfo("amount"));
		logger.info("-----------------修改对冲金额 ---从"+revMapInfo.getAmount() + "--改为"+revMapInfo.getAmount().subtract(toCancelAmount)+"-----");	
							revMapInfo.setAmount(revMapInfo.getAmount().subtract(toCancelAmount));
							SHERevMapFactory.getLocalInstance(ctx).updatePartial(revMapInfo, selectorMap);
							if(entryInfo!=null)
								entryInfo.setActRevAmount(entryInfo.getActRevAmount().subtract(toCancelAmount));
							transEntryInfo.setHasMapedAmount(transEntryInfo.getHasMapedAmount().subtract(toCancelAmount));
							toCancelAmount = new BigDecimal("0");
		logger.info("-----------------修改对冲金额"+revMapInfo.getAmount() + "------");								
						}
						
						SelectorItemCollection selector = new SelectorItemCollection();											
						selector.add(new SelectorItemInfo("actRevAmount"));
						if(entryInfo!=null)
							entryFactory.updatePartial(entryInfo, selector);
					}
				}	
				
				if(toCancelAmount.compareTo(new BigDecimal("0"))>0)
					throw new EASBizException(new NumericExceptionSubItem("100","退款明细'"+quitMoneyName+"'对应的收款明细已对冲，且无法取消对冲金额"+toCancelAmount+"，无法退款请检查！"));
			}

logger.info("-----------------收款明细-"+transEntryInfo.getMoneyDefine().getName()+"--已退金额"+transEntryInfo.getHasRefundmentAmount()+"--已对冲金额= "+transEntryInfo.getHasMapedAmount() + "----end");			
			SelectorItemCollection selector = new SelectorItemCollection();			
			if(revBillInfo.isIsTansCreate()) {	//转款单生成的退款单
				transEntryInfo.setHasTransferAmount(transEntryInfo.getHasTransferAmount().add(quitEntryInfo.getRevAmount().negate()));
				selector.add(new SelectorItemInfo("hasTransferAmount"));
			}else{
				transEntryInfo.setHasRefundmentAmount(transEntryInfo.getHasRefundmentAmount().add(quitEntryInfo.getRevAmount().negate()));
				selector.add(new SelectorItemInfo("hasRefundmentAmount"));
			}
			selector.add(new SelectorItemInfo("hasMapedAmount"));
			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(transEntryInfo, selector);
			
/*			//创建该收款单的对冲关系
			String tempRevBillId = transEntryInfo.getParent().getId().toString();
			if(idToReCreateMapRevBillIdsStr.indexOf(tempRevBillId)<0)
				idToReCreateMapRevBillIdsStr += ",'"+transEntryInfo.getParent().getId().toString()+"'";*/
		}
		
/*	//String idToReCreateMapRevBillIdsStr = "";	//需要重新创建对冲关系的收款单Id	
 * 	if(!idToReCreateMapRevBillIdsStr.equals("")) {
			idToReCreateMapRevBillIdsStr = idToReCreateMapRevBillIdsStr.substring(1);
			SHERevBillCollection toReCreateColl = SHERevBillFactory.getLocalInstance(ctx) 
					.getSHERevBillCollection("select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name where id in ("+idToReCreateMapRevBillIdsStr+")  ");
			for (int i = 0; i < toReCreateColl.size(); i++) {
				this._submit(ctx, toReCreateColl.get(i));
			}
		}*/
		
	}
	
	
	/**为某一收款明细创建对冲关系
	 * relateBizBillId 关联交易单据的id
	 * relateBizType 关联的业务单据的类型
	 * revEntryInfo  收款明细 （id必须存在，款型类型类别必须存在）
	 * **/
	private void createRevMapRelation(Context ctx,String relateTransId,TransactionInfo transInfo,RelatBizType relateBizType,SHERevBillEntryInfo revEntryInfo) throws BOSException, EASBizException {
		if(transInfo==null && (relateTransId==null || relateTransId.trim().equals(""))) return;
		//if(relateBizType==null) return;
		if(revEntryInfo.getId()==null) return;
		
//		MoneyTypeEnum moneyType = revEntryInfo.getMoneyDefine().getMoneyType();
//		if(moneyType==null)  {
//			MoneyDefineInfo tempMonInfo =  MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo("select * where id = '"+revEntryInfo.getMoneyDefine().getId()+"' ");
//			if(tempMonInfo!=null) {
//				revEntryInfo.setMoneyDefine(tempMonInfo);
//				moneyType = tempMonInfo.getMoneyType();
//			}
//		}
//		
//		String moneyTypeSort = (String)CRMHelper.SHERevMoenyTpeMap().get(moneyType.getValue());
//		if(moneyTypeSort==null)
//			throw new EASBizException(new NumericExceptionSubItem("100","收款明细中款项类别'"+moneyType+"'找不到对于的分类，请检查！"));		
	
//	logger.info("---实收明细-"+moneyType+"-------收款金额"+revEntryInfo.getRevAmount()+"--已退金额"+revEntryInfo.getHasRefundmentAmount()
//			+"--已转金额"+revEntryInfo.getHasTransferAmount()+"--已对冲金额"+revEntryInfo.getHasMapedAmount()+"--------create begin");
		
		CoreBaseCollection payListEntryToUpdate = new CoreBaseCollection();	//待更新的应收明细
		CoreBaseCollection revMapToAdd = new CoreBaseCollection();	//待增加的对冲关系
		ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		TranPayListEntryCollection tranEntryColl = new TranPayListEntryCollection();
		if(transInfo!=null){
			TranBusinessOverViewCollection transBizViewColl = transInfo.getTranBusinessOverView();
			for (int i = 0; i < transBizViewColl.size(); i++) {
				if(transBizViewColl.get(i).getType().equals(BusTypeEnum.PAY))
					tranEntryColl.add(transBizViewColl.get(i));
			}
		}else{
			tranEntryColl = entryFactory.getTranPayListEntryCollection( //交易单据的应收明细
					"select *,moneyDefine.moneyType where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' order by seq");
		}
							
		BigDecimal allRemainAmount = revEntryInfo.getRemainAmount();  
		
		TranPayListEntryInfo lasttranEntryInfo=null; 
		for (int j = 0; j < tranEntryColl.size(); j++) {
			TranPayListEntryInfo tranEntryInfo = tranEntryColl.get(j);
			if(tranEntryInfo.getMoneyDefine()==null)
				throw new EASBizException(new NumericExceptionSubItem("100","应收明细中款项类型为空，请检查！"));
			
//			MoneyTypeEnum thisMoneyType = tranEntryInfo.getMoneyDefine().getMoneyType();
//			if(thisMoneyType==null){
//				MoneyDefineInfo tempDefInfo = MoneyDefineFactory.getLocalInstance(ctx)
//							.getMoneyDefineInfo("select moneyType where id = '"+tranEntryInfo.getMoneyDefine().getId()+"' "); 
//				thisMoneyType = tempDefInfo.getMoneyType();
//			}
//			String thisTypeSort = (String)CRMHelper.SHERevMoenyTpeMap().get(thisMoneyType.getValue());
//	logger.info("---应收明细-"+thisTypeSort+"---应收金额"+tranEntryInfo.getAppAmount()+"---实收金额"+tranEntryInfo.getActRevAmount());
//			if(thisTypeSort==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","应收明细中款项类别"+moneyType+"找不到对于的分类，请检查！"));
			
			if(!tranEntryInfo.getMoneyDefine().getId().equals(revEntryInfo.getMoneyDefine().getId()))  //应收明细的款项和实收明细的款项属于同一大类时，才能对冲
				continue;

//			if(allRemainAmount.compareTo(new BigDecimal("0"))<0) //当前还能对冲的金额=收款金额-已退金额-已转金额-已对冲金额
//					throw new EASBizException(new NumericExceptionSubItem("100","收款时明细中不应该出现负金额的情况！"));
			
			BigDecimal tpRevAmount = tranEntryInfo.getActRevAmount();
			if(tpRevAmount==null) tpRevAmount = new BigDecimal("0");
			
			BigDecimal unPayAmount = tranEntryInfo.getAppAmount().subtract(tpRevAmount);	//未收=应收-已收
				
			BigDecimal toMapAmount = new BigDecimal("0");
			if(revEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.ReplaceFee)){
				toMapAmount = allRemainAmount;
			}else{
				if(allRemainAmount.compareTo(unPayAmount)>=0){ //冲应收的全部未收
					toMapAmount = unPayAmount;
				}else{//只能冲部分
					toMapAmount = allRemainAmount;
				}
			}
			if(toMapAmount.compareTo(new BigDecimal("0"))!=0){
				allRemainAmount=allRemainAmount.subtract(toMapAmount);
				
				SHERevMapInfo newMapInfo = new SHERevMapInfo();
				newMapInfo.setRelatBizType(relateBizType);
				newMapInfo.setRevBillEntryId(revEntryInfo);
				newMapInfo.setPayListEntryId(tranEntryInfo.getId().toString());
				newMapInfo.setSeq(tranEntryInfo.getSeq());	
				newMapInfo.setAmount(toMapAmount);		  //对冲关系
				revMapToAdd.add(newMapInfo);
				logger.info("-------------------------------------------对冲金额"+newMapInfo.getAmount());					
				
				tranEntryInfo.setActRevAmount(tpRevAmount.add(toMapAmount)); //应收明细的实收金额
//				revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().add(toMapAmount)); //实收明细的对冲金额
				
				payListEntryToUpdate.add(tranEntryInfo);
			}
			lasttranEntryInfo=tranEntryInfo;
//			break;
//	logger.info("---应收明细-"+thisTypeSort+"---应收金额"+tranEntryInfo.getAppAmount()+"---实收金额"+tranEntryInfo.getActRevAmount());
		}		
		if(lasttranEntryInfo!=null&&allRemainAmount.compareTo(FDCHelper.ZERO)>0){
			SHERevMapInfo newMapInfo = new SHERevMapInfo();
			newMapInfo.setRelatBizType(relateBizType);
			newMapInfo.setRevBillEntryId(revEntryInfo);
			newMapInfo.setPayListEntryId(lasttranEntryInfo.getId().toString());
			newMapInfo.setSeq(lasttranEntryInfo.getSeq());	
			newMapInfo.setAmount(allRemainAmount);		  //对冲关系
			revMapToAdd.add(newMapInfo);
			
			if(!payListEntryToUpdate.contains(lasttranEntryInfo)){
				payListEntryToUpdate.add(lasttranEntryInfo);
			}
			lasttranEntryInfo.setActRevAmount(lasttranEntryInfo.getActRevAmount().add(allRemainAmount));
		}
//	logger.info("---实收明细-"+moneyType+"-------收款金额"+revEntryInfo.getRevAmount()+"--已退金额"+revEntryInfo.getHasRefundmentAmount()
//				+"--已转金额"+revEntryInfo.getHasTransferAmount()+"--已对冲金额"+revEntryInfo.getHasMapedAmount()+"----------create end");
		
		//变化的内容包含：
		//1.应收明细的实收金额   2、实收明细的已对冲金额   3、对冲关系表
		entryFactory.update(payListEntryToUpdate);

//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add(new SelectorItemInfo("hasMapedAmount"));
//		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revEntryInfo, selector);
		
		ISHERevMap revMapFactory = SHERevMapFactory.getLocalInstance(ctx);
		revMapFactory.addnew(revMapToAdd);
	}
	
	
	
	
	/**为某一收款明细删除对冲关系
	 * relateBizBillId 关联的业务单据的id
	 * relateBizType 关联的业务单据的类型
	 * revEntryInfo  收款明细 （id必须存在，款型类型类别必须存在）  后续需要校验结果是否符合条件，可能并不符合删除对冲关系的条件
	 * **/
	private void deleteRevMapRelation(Context ctx,String relateBizBillId,RelatBizType relateBizType,SHERevBillEntryInfo revEntryInfo) throws BOSException, EASBizException {
		//if(relateBizBillId==null || relateBizBillId.trim().equals("")) return;
		//if(relateBizType==null) return;		
		if(revEntryInfo.getId()==null) return;
	
		logger.info("---实收明细-"+revEntryInfo.getMoneyDefine().getMoneyType()+"-------收款金额"+revEntryInfo.getRevAmount()+"--已退金额"+revEntryInfo.getHasRefundmentAmount()
			+"--已转金额"+revEntryInfo.getHasTransferAmount()+"--已对冲金额"+revEntryInfo.getHasMapedAmount()+"--------delete begin");
		
		
		ISHERevMap revMapFactory = SHERevMapFactory.getLocalInstance(ctx);
		SHERevMapCollection existMapColl = 	revMapFactory //当前实收明细已存在的对冲关系
						.getSHERevMapCollection("select * where revBillEntryId.id = '"+revEntryInfo.getId().toString()+"' order by seq");
		if(existMapColl.size()==0) return;		//若没有对冲明细则不用处理
		ITranPayListEntry payEntryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		for(int k=0;k<existMapColl.size();k++){
			SHERevMapInfo revMapInfo = existMapColl.get(k); 
			TranPayListEntryInfo payEntryInfo = payEntryFactory.getTranPayListEntryInfo("select *,moneyDefine.moneyType where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"'  ");
			if(payEntryInfo!=null) {					
				payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selectorPay = new SelectorItemCollection();
				selectorPay.add(new SelectorItemInfo("actRevAmount"));
				payEntryFactory.updatePartial(payEntryInfo, selectorPay);//更新应收明细的实收金额			
				
				logger.info("-----------------------------------------删除对冲金额"+revMapInfo.getAmount());		
				logger.info("---应收明细-"+payEntryInfo.getMoneyDefine().getMoneyType()+"---应收金额"+payEntryInfo.getAppAmount()+"---实收金额"+payEntryInfo.getActRevAmount());
			}

//			revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
		}		
		logger.info("---实收明细-"+revEntryInfo.getMoneyDefine().getMoneyType()+"-------收款金额"+revEntryInfo.getRevAmount()+"--已退金额"+revEntryInfo.getHasRefundmentAmount()
						+"--已转金额"+revEntryInfo.getHasTransferAmount()+"--已对冲金额"+revEntryInfo.getHasMapedAmount()+"----------delete end");
		
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add(new SelectorItemInfo("hasMapedAmount"));
//		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revEntryInfo, selector);  //更新实收明细的已对冲金额
		revMapFactory.delete("where revBillEntryId.id = '"+revEntryInfo.getId().toString()+"' ");  //删除对冲关系
	}


	/**
	 * 设置审批中状态
	 */
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
	    SHERevBillInfo rev = new SHERevBillInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);
	}

    /**
	 * 设置提交状态
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
    	SHERevBillInfo rev = new SHERevBillInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);		
	}


	/**
	 * 交易业务总览信息变化时，需要重新建立对冲关系
	 * oldTransInfo 旧的交易，不可为空
	 * newTransInfo 新的交易，不可为空
	 * */
	protected void _whenTransEntryChange(Context ctx, TransactionInfo oldTransInfo, TransactionInfo newTransInfo)
			throws BOSException, EASBizException {
		if(oldTransInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","旧的交易不能为空，请检查！"));
		if(newTransInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","新的交易不能为空，请检查！"));	
		if(!oldTransInfo.getId().equals(newTransInfo.getId()))
				throw new EASBizException(new NumericExceptionSubItem("100","新旧交易非同一个交易，请检查！"));

		//旧交易必须是收过款的才需要处理
		boolean hasRev = false;
		String oldBizEntryIdsStr = "";
		TranBusinessOverViewCollection oldBizEntryColl = oldTransInfo.getTranBusinessOverView();
		for (int i = 0; i < oldBizEntryColl.size(); i++) {
			TranBusinessOverViewInfo oldBizEntryInfo = oldBizEntryColl.get(i);
			if(oldBizEntryInfo.getType()==null || !oldBizEntryInfo.getType().equals(BusTypeEnum.PAY)) continue;
			if(oldBizEntryInfo.getActRevAmount()==null) oldBizEntryInfo.setActRevAmount(new BigDecimal("0"));
			if(oldBizEntryInfo.getActRevAmount().compareTo(new BigDecimal("0"))>0)	hasRev = true;
			oldBizEntryIdsStr += ",'"+oldBizEntryInfo.getId().toString()+"'";
		}
//		if(!hasRev) return;
		if(!oldBizEntryIdsStr.equals("")) oldBizEntryIdsStr = oldBizEntryIdsStr.substring(1);
		//旧交易对应的对冲关系
//		SHERevMapCollection revMapColl = SHERevMapFactory.getLocalInstance(ctx)
//					.getSHERevMapCollection("select *,revBillEntryId.*,revBillEntryId.parent.id where payListEntryId in ("+oldBizEntryIdsStr+")");
//		CoreBaseCollection toUpdateRevEntryColl = new CoreBaseCollection();
//		String revBillIdsStr = "";			//旧交易所涉及到的收款单的id
//		for(int i=0;i<revMapColl.size();i++) {
//			SHERevMapInfo revMapInfo = revMapColl.get(i);			
//			SHERevBillEntryInfo revBillEntryInfo = revMapInfo.getRevBillEntryId();
//			revBillEntryInfo.setHasMapedAmount(revBillEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
//			toUpdateRevEntryColl.add(revBillEntryInfo);
//			String revBillId = revBillEntryInfo.getParent().getId().toString();
//			if(revBillIdsStr.indexOf(revBillId)<0)
//				revBillIdsStr += ",'"+revBillId+"'";
//		}
		if(oldBizEntryColl.size()>0){
//			SHERevBillEntryFactory.getLocalInstance(ctx).update(toUpdateRevEntryColl);						//恢复旧交易的对冲关系涉及到的实收明细
			SHERevMapFactory.getLocalInstance(ctx).delete("where payListEntryId in ("+oldBizEntryIdsStr+")");//删除旧交易的对冲关系
		}

//		if(!revBillIdsStr.equals("")) {
			//新的交易的明细的实收金额应该为0, --- 在交易提交环节已处理			
/*			TranBusinessOverViewCollection transBizViewColl = newTransInfo.getTranBusinessOverView();
			for (int i = 0; i < transBizViewColl.size(); i++) {
				if(transBizViewColl.get(i).getType().equals(BusTypeEnum.PAY))
					transBizViewColl.get(i).setActRevAmount(new BigDecimal(0));
			}*/
			
//			revBillIdsStr = revBillIdsStr.substring(1);
			SHERevBillEntryCollection sheRevBillEntryColl = SHERevBillEntryFactory.getLocalInstance(ctx)
						.getSHERevBillEntryCollection("select *,parent.relateBizType,moneyDefine.moneyType,moneyDefine.name where parent.relateTransId='"+newTransInfo.getId().toString()+"') order by bizDate");
			for (int i = 0; i < sheRevBillEntryColl.size(); i++) {
				SHERevBillEntryInfo sheRevBillEntryInfo = sheRevBillEntryColl.get(i);
				this.createRevMapRelation(ctx, null,newTransInfo, sheRevBillEntryInfo.getParent().getRelateBizType(), sheRevBillEntryInfo);
			}
//		}
		
	}
	
	
	/**
		当交易客户完全变化时， 
		1.删除本交易的全部对冲关系 
		2.更新本交易的所有收款单为预收款(也就是不对应交易的收款)
	 * */
	protected void _whenTransCustChange(Context ctx, TransactionInfo transInfo)	throws BOSException, EASBizException {
		if(transInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","交易不能为空，请检查！"));
		
		//旧交易必须是收过款的才需要处理
		boolean hasRev = false;
		String transfEntryIdsStr = "";
		ITranPayListEntry payEntryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		ISHERevBillEntry revEntryFactory = SHERevBillEntryFactory.getLocalInstance(ctx);
		TranPayListEntryCollection transfEntryColl = payEntryFactory.getTranPayListEntryCollection("select * where head.id = '"+transInfo.getId()+"' ");
		for (int i = 0; i < transfEntryColl.size(); i++) {
			TranBusinessOverViewInfo transfEntryInfo = (TranBusinessOverViewInfo)transfEntryColl.get(i); 
			if(transfEntryInfo.getType()==null || !transfEntryInfo.getType().equals(BusTypeEnum.PAY)) continue;
			if(transfEntryInfo.getActRevAmount()==null) transfEntryInfo.setActRevAmount(new BigDecimal("0"));
			if(transfEntryInfo.getActRevAmount().compareTo(new BigDecimal("0"))>0)	hasRev = true;
			transfEntryIdsStr += ",'"+transfEntryInfo.getId().toString()+"'";
		}
		if(!hasRev) return;
		if(!transfEntryIdsStr.equals("")) transfEntryIdsStr = transfEntryIdsStr.substring(1);
		

		SHERevMapCollection revMapColl = SHERevMapFactory.getLocalInstance(ctx)
					.getSHERevMapCollection("select *,revBillEntryId.*,revBillEntryId.parent.id where payListEntryId in ("+transfEntryIdsStr+")");
		if(revMapColl.size()==0) return;		//若没有对冲明细则不用处理
		
		for(int k=0;k<revMapColl.size();k++){
			SHERevMapInfo revMapInfo = revMapColl.get(k); 
			TranPayListEntryInfo payEntryInfo = payEntryFactory.getTranPayListEntryInfo("select *,moneyDefine.moneyType where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"'  ");
			if(payEntryInfo!=null) {					
				payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selectorPay = new SelectorItemCollection();
				selectorPay.add(new SelectorItemInfo("actRevAmount"));
				if(payEntryInfo.getActRevAmount().compareTo(new BigDecimal("0"))<0){
					throw new EASBizException(new NumericExceptionSubItem("100","交易明细删除对冲关系异常,明细id='"+revMapInfo.getPayListEntryId()+"'！"));
				}
				
				payEntryFactory.updatePartial(payEntryInfo, selectorPay);//更新应收明细的实收金额			
			}

			SHERevBillEntryInfo revEntryInfo = revEntryFactory.getSHERevBillEntryInfo("select hasMapedAmount where id = '"+ revMapInfo.getRevBillEntryId().getId()  +"' ");
			if(revEntryInfo!=null) {
//				revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("hasMapedAmount"));
				revEntryFactory.updatePartial(revEntryInfo, selector);  //更新实收明细的已对冲金额	
			}
			
			
		}		
		
		//删除交易明细对应的对冲关系
		SHERevMapFactory.getLocalInstance(ctx).delete("where payListEntryId in ("+transfEntryIdsStr+")");
		
		CoreBaseCollection toUpdateColl = new CoreBaseCollection();
		SHERevBillCollection revBillColl = 	SHERevBillFactory.getLocalInstance(ctx)
						.getSHERevBillCollection("select * where relateTransId = '"+transInfo.getId()+"' and revBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' ");
		for (int i = 0; i < revBillColl.size(); i++) {
			SHERevBillInfo revBillInfo = revBillColl.get(i);
			revBillInfo.setRelateTransId(null);
			revBillInfo.setRelateBizBillId(null);
			revBillInfo.setRelateBizBillNumber(null);
			revBillInfo.setRelateBizType(null);
			toUpdateColl.add(revBillInfo);
		}
		SHERevBillFactory.getLocalInstance(ctx).update(toUpdateColl);
		
		
		
	}

	
	
	protected void handleIntermitNumber(Context ctx, CoreBillBaseInfo info)	throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		boolean isExist = true;
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
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
	
	
	
	
	/**检查退款金额是否合理
	 * 退款时校验逻辑：交易单据对应的所有实收金额不能小于0（也就是收的钱还不够退的）  -- （只有交易中有退款时才需要校验，比如凭空退补差款的）
	 *@param relateTransId 交易id
	 *@param quitAmount 退款金额
	 **/
	public static void checkQuitMoneyAmount(Context ctx,String relateTransId,BigDecimal quitAmount) throws BOSException, EASBizException{
		if(relateTransId==null || quitAmount==null || quitAmount.compareTo(new BigDecimal("0"))>=0) return;
		
		ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		boolean exitNegateAppAmount = entryFactory.exists("where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' and appAmount < 0 "); 	
		if(exitNegateAppAmount){ //只有存在负的应收时才需要考虑
			TranPayListEntryCollection tranEntryColl = entryFactory.getTranPayListEntryCollection( //交易单据的应收明细
					"select actRevAmount where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' order by seq");
			BigDecimal allActRevAmount = new BigDecimal("0");
			for (int i = 0; i < tranEntryColl.size(); i++) {
				allActRevAmount = allActRevAmount.add(tranEntryColl.get(i).getActRevAmount());
			}
			if(allActRevAmount.add(quitAmount).compareTo(new BigDecimal("0"))<0) {
				throw new EASBizException(new NumericExceptionSubItem("100","退款对应交易的剩余收款合计不足以退此款，请检查！"));
			}
		}
		

		
		
	}
	
	
	
    
}