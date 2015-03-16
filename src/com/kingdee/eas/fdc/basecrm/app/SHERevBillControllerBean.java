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
    	//throw new EASBizException(new NumericExceptionSubItem("100","�����ɹ���"));
    }
    
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	if(billId==null) return;
    	if(SHERevBillEntryFactory.getLocalInstance(ctx).exists("where parent.id = '"+billId.toString()+"' " +
    					"		and parent.state = '"+FDCBillStateEnum.AUDITTED_VALUE+"' and parent.isGathered = 1 and parent.revAmount!=0 and parent.revAmount is not null")){
    		throw new EASBizException(new NumericExceptionSubItem("100","�����ɳ��ɻ��ܵ�����ֹ��������"));
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
    	//throw new EASBizException(new NumericExceptionSubItem("100","�����������ɹ���"));
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
		
		// ����Ϻ�
		if (revBillInfo.getId() == null	|| !this._exists(ctx, new ObjectUuidPK(revBillInfo.getId()))) {
			handleIntermitNumber(ctx, revBillInfo);
		}
		
		if(revBillInfo.get("SubmitAndAudit")!=null) {
			revBillInfo.setState(FDCBillStateEnum.AUDITTED);
			revBillInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			revBillInfo.setAuditTime(new Date());
		}
		
		if(revBillInfo.getRelateBizType()!=null && revBillInfo.getRelateBizType().equals(RelatBizType.Change)){
			return super._submit(ctx, model);	//����Ǳ�����ɵģ���ֱ���ύ
		}
		
		SHERevBillInfo oldBillInfo = null;
		if(revBillInfo.getId()!=null) //�޸�ʱ
			 oldBillInfo = (SHERevBillInfo)this._getValue(ctx, "select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name,entrys.revEntry.* where id = '"+revBillInfo.getId().toString()+"'");

		Map newEntryInfoMap = new HashMap();				//���ύ���տ���ϸ��id��Ӧӳ��
		for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
			SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
			if(thisEntryInfo.getId()!=null)
				newEntryInfoMap.put(thisEntryInfo.getId().toString(), thisEntryInfo);
		}
				
		IObjectPK pk = null;		
		if(RevBillTypeEnum.gathering.equals(revBillInfo.getRevBillType())){ //�տ����
			if(oldBillInfo!=null) {  //�޸�
				//�ݻ���ʷʵ����ϸ�ĶԳ��ϵ
				if(oldBillInfo.getRelateTransId()!=null){
					for (int i = 0; i < oldBillInfo.getEntrys().size(); i++) {
						SHERevBillEntryInfo oldEntryInfo = oldBillInfo.getEntrys().get(i);
						deleteRevMapRelation(ctx, null, oldBillInfo.getRelateBizType(), oldEntryInfo);
						//У���Ƿ��������  �����ɾ��������ϸ�����ѶԳ������Ϊ0,����������ת���
						if(newEntryInfoMap.get(oldEntryInfo.getId().toString())==null){
							if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
								throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"���޷��ָ��Գ��ϵ����ֹɾ�������飡"));
						}
					}
				}
			}
						
			//�˴�Ϊ�˷�ֹ�տ��ύǰ�������ط��Ѿ��ٴ����տ���˿�ȣ����������ˡ���ת�Ƚ����仯  (�����϶���ͬʱ����ʱ���ܻ����)
			if(oldBillInfo!=null) {
				for (int i = 0; i < oldBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = oldBillInfo.getEntrys().get(i);
					SHERevBillEntryInfo newEntryInfo = (SHERevBillEntryInfo)newEntryInfoMap.get(oldEntryInfo.getId().toString());
					if(newEntryInfo!=null) {//�⼸��ֵ������Ϊ�µ��ݵ��ύ�����޸�
						newEntryInfo.setHasRefundmentAmount(oldEntryInfo.getHasRefundmentAmount());
						newEntryInfo.setHasTransferAmount(oldEntryInfo.getHasTransferAmount());
						newEntryInfo.setHasMakeInvoiceAmount(oldEntryInfo.getHasMakeInvoiceAmount());
						newEntryInfo.setHasMapedAmount(oldEntryInfo.getHasMapedAmount());  //�ɵ��ѶԳ����Ѿ������仯���˴�Ҫ���ű仯
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
			//�Ե�ǰ��ʵ����ϸ�����Գ��ϵ
			if(revBillInfo.getRelateTransId()!=null){
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
					this.createRevMapRelation(ctx, revBillInfo.getRelateTransId(),null, revBillInfo.getRelateBizType(), thisEntryInfo);	
				}
			}
			
			//�տ�ύ��Ҫ���ǿ�Ʊ����
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
		}else if(RevBillTypeEnum.refundment.equals(revBillInfo.getRevBillType())){ //�˿����
			
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
			//�Ե�ǰ��ʵ����ϸ�����Գ��ϵ
			if(revBillInfo.getRelateTransId()!=null){
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo thisEntryInfo = revBillInfo.getEntrys().get(i);
					this.createRevMapRelation(ctx, revBillInfo.getRelateTransId(),null, revBillInfo.getRelateBizType(), thisEntryInfo);	
				}
			}
			addSysCustomer(ctx,revBillInfo);
			
		}else if(RevBillTypeEnum.transfer.equals(revBillInfo.getRevBillType())){ //ת�����
			
		}
		return pk;
	}
	
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SHERevBillInfo revBillInfo = (SHERevBillInfo)this._getValue(ctx, "select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name,entrys.revEntry.* where id = '"+pk.toString()+"'");
		if(revBillInfo.getRelateBizType()!=null && revBillInfo.getRelateBizType().equals(RelatBizType.Change)){
			super._delete(ctx, pk);		//����Ǳ�������ģ���ֱ��ɾ��
			return;
		}		
		if(revBillInfo.getState().equals(FDCBillStateEnum.SAVED)){
			super._delete(ctx, pk);		
			return;
		}
		if(revBillInfo.getState()!=null && !revBillInfo.getState().equals(FDCBillStateEnum.SAVED) 
				&& !revBillInfo.getState().equals(FDCBillStateEnum.SUBMITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","�Ǳ�����ύ״̬�ĵ��ݽ�ֹɾ����"));
		}		
		
		if(RevBillTypeEnum.gathering.equals(revBillInfo.getRevBillType())){ //�տ����
			if(revBillInfo.getRelateTransId()!=null) {
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = revBillInfo.getEntrys().get(i);
					deleteRevMapRelation(ctx, null, revBillInfo.getRelateBizType(), oldEntryInfo);
					//У���Ƿ��������  �����ɾ��������ϸ�����ѶԳ������Ϊ0,����������ת���
//						if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
//							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"���޷��ָ��Գ��ϵ����ֹɾ�������飡"));
						if(oldEntryInfo.getHasRefundmentAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"���������˿��ֹɾ�������飡"));
						if(oldEntryInfo.getHasTransferAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"��������ת���ֹɾ�������飡"));							
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
					throw new EASBizException(new NumericExceptionSubItem("100","������ϸ"+revBillInfo.getEntrys().get(i).getMoneyDefine().getName()+"�Ѿ������֣����ܽ���ɾ��������"));
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
		
			
		}else if(RevBillTypeEnum.refundment.equals(revBillInfo.getRevBillType())){ //�˿����
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
		}else if(RevBillTypeEnum.transfer.equals(revBillInfo.getRevBillType())){ //ת�����
//			//1.ɾ�����ɵ��տ	2.ɾ�����ɵ��˿
//			if(revBillInfo.getTrsToGatherId()==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","ת���Ӧ�������տidΪ�գ����飡"));
//			if(revBillInfo.getTrsToRefundId()==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","ת���Ӧ�������˿idΪ�գ����飡"));
//			this._delete(ctx, new ObjectUuidPK(BOSUuid.read(revBillInfo.getTrsToGatherId())));
//			this._delete(ctx, new ObjectUuidPK(BOSUuid.read(revBillInfo.getTrsToRefundId())));
			
			if(revBillInfo.getRelateTransId()!=null) {
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo oldEntryInfo = revBillInfo.getEntrys().get(i);
					deleteRevMapRelation(ctx, null, revBillInfo.getRelateBizType(), oldEntryInfo);
					//У���Ƿ��������  �����ɾ��������ϸ�����ѶԳ������Ϊ0,����������ת���
//						if(oldEntryInfo.getHasMapedAmount().compareTo(new BigDecimal("0"))!=0)
//							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"���޷��ָ��Գ��ϵ����ֹɾ�������飡"));
						if(oldEntryInfo.getHasRefundmentAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"���������˿��ֹɾ�������飡"));
						if(oldEntryInfo.getHasTransferAmount().compareTo(new BigDecimal("0"))!=0)
							throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�С�"+oldEntryInfo.getMoneyDefine().getName()+"��������ת���ֹɾ�������飡"));							
				}				
			}	
			
			for(int i=0;i<revBillInfo.getEntrys().size();i++){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sheRevBillEntryId", revBillInfo.getEntrys().get(i).getId()));
				if(SHERevbillTwoEntryFactory.getLocalInstance(ctx).exists(filter)){
					throw new EASBizException(new NumericExceptionSubItem("100","������ϸ"+revBillInfo.getEntrys().get(i).getMoneyDefine().getName()+"�Ѿ������֣����ܽ���ɾ��������"));
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
	
	
	/**����ɾ���˿ʱ�Ĳ��� *
	 * �������˿���ϸ�Ķ�Ӧ�տ���ϸ�ָ����˽��� */
	private void restoreHasRefundmentAmount(Context ctx,SHERevBillInfo revBillInfo) throws BOSException, EASBizException {
		SHERevBillEntryCollection revEntryColl =  revBillInfo.getEntrys();
		if(!revBillInfo.getRevBillType().equals(RevBillTypeEnum.refundment))
			throw new EASBizException(new NumericExceptionSubItem("100","����"+revBillInfo.getNumber()+"���˿�����飡"));
			
		String transEntryIdStr = "";		//�տ���ϸ��ids
		Map transEntryMap = new HashMap();
		for (int i = 0; i < revEntryColl.size(); i++) {
			SHERevBillEntryInfo quitEntryInfo = revEntryColl.get(i);   //�˿���ϸ
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(quitEntryInfo.getTransferFromEntryId()==null) continue; 
				
			if(quitEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ�������������飡"));
			String transEntryId = quitEntryInfo.getTransferFromEntryId();
			if(transEntryIdStr.indexOf(transEntryId)>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸId�ظ������飡"));
			transEntryIdStr += ",'"+transEntryId+"'";
			transEntryMap.put(transEntryId, quitEntryInfo);
		}
		
		if(transEntryMap.size()==0) return; 
			
		ISHERevBillEntry revEntryFactory = SHERevBillEntryFactory.getLocalInstance(ctx);
		SHERevBillEntryCollection thisRevEntryColl = revEntryFactory.getSHERevBillEntryCollection(
				"select *,parent.relateBizType,parent.relateTransId,moneyDefine.moneyType,moneyDefine.name where id in ("+transEntryIdStr.substring(1)+")");

		if(thisRevEntryColl.size()==0)
			throw new EASBizException(new NumericExceptionSubItem("100","��ѯ�����˿���ϸ��Ӧ���տ���ϸ���޷��˿���飡"));
		
		CoreBaseCollection toUpdateRevEntryColl = new CoreBaseCollection();
		for (int j = 0; j < thisRevEntryColl.size(); j++) {
			SHERevBillEntryInfo transEntryInfo = thisRevEntryColl.get(j);
			SHERevBillEntryInfo quitEntryInfo = (SHERevBillEntryInfo)transEntryMap.get(transEntryInfo.getId().toString());
			if(quitEntryInfo!=null) {
				if(revBillInfo.isIsTansCreate())	//�����ת����ɵ��˿����Ҫ������ת���
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
	
	
	
	/**�����˿�ʱ�ĶԳ��ϵ   ----  ֻ�����������˿ʱ�Ĳ��� ���޸��˿������Ƚ����⣬
		1�����¶�Ӧ���տ�ʵ����ϸ�����˽�Ҫ��У�飩
		2. ��ʵ����ϸ�Ĳ���Ҫ�˽���Ѿ��Գ壬��Ҫ�����ٶԳ��ϵ
	 * */
	private void dealRefundRevMapRelation(Context ctx,SHERevBillInfo revBillInfo) throws BOSException, EASBizException {
	
		SHERevBillEntryCollection revEntryColl =  revBillInfo.getEntrys();
		String transEntryIdStr = "";
		Map transEntryMap = new HashMap();			//ʵ����ϸid ��  �˿���ϸ��ӳ���ϵ
		for (int i = 0; i < revEntryColl.size(); i++) {
			SHERevBillEntryInfo quitEntryInfo = revEntryColl.get(i);
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(quitEntryInfo.getTransferFromEntryId()==null) 
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸIdΪ�գ����飡"));
			if(quitEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ�������������飡"));
			String transEntryId = quitEntryInfo.getTransferFromEntryId();
			if(transEntryIdStr.indexOf(transEntryId)>=0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸId�ظ������飡"));
			transEntryIdStr += ",'"+transEntryId+"'";
			transEntryMap.put(transEntryId, quitEntryInfo);
  logger.info("-----------------�˿�-"+quitEntryInfo.getMoneyDefine().getName()+"--�˿���= "+quitEntryInfo.getRevAmount() + "----begin");					
		}
		
		SHERevBillEntryCollection transEntryColl = SHERevBillEntryFactory.getLocalInstance(ctx) //�˿���ϸ��Ӧ���տ���ϸ
							.getSHERevBillEntryCollection("select parent.relateTransId,parent.relateBizType,*,moneyDefine.* " +
									"where id in ("+ transEntryIdStr.substring(1) +")");
		if(transEntryColl.size()==0)
			throw new EASBizException(new NumericExceptionSubItem("100","���Ҳ����˿���ϸ��Ӧ���տ���ϸid="+transEntryIdStr.substring(1)+"�����飡"));
		
		
		for (int i = 0; i < transEntryColl.size(); i++) {
			SHERevBillEntryInfo transEntryInfo = transEntryColl.get(i);  //�տ���ϸ
  logger.info("-----------------�տ���ϸ-"+transEntryInfo.getMoneyDefine().getName()+"--���˽��="+transEntryInfo.getHasRefundmentAmount()+"--�ѶԳ���= "+transEntryInfo.getHasMapedAmount() + "----");				
			String gatherMoneyName = transEntryInfo.getMoneyDefine().getName();   
			SHERevBillEntryInfo quitEntryInfo = (SHERevBillEntryInfo)transEntryMap.get(transEntryInfo.getId().toString());  //�˿���ϸ
			String quitMoneyName = quitEntryInfo.getMoneyDefine().getName();
			if(transEntryInfo.getRevAmount().compareTo(new BigDecimal("0"))<=0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸ'"+gatherMoneyName+"'�տ��Ӧ���и��������飡"));
			if(transEntryInfo.getRemainAmount().compareTo(quitEntryInfo.getRevAmount().negate())<0)
				throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸ'"+gatherMoneyName+"'���˽��㣬�޷��˿����飡"));
			//������˽��-�ѶԳ���㹻�˿��ֱ�ӷ�д��Ӧ���տ���ϸ�����˽���
			BigDecimal freeAmount = transEntryInfo.getRemainAmount();
			BigDecimal lessAmount = freeAmount.subtract(quitEntryInfo.getRevAmount().negate());
			if(lessAmount.doubleValue()<0) {
				BigDecimal toCancelAmount = lessAmount.negate();  //Ҫȡ���ĶԳ���   
				SHERevMapCollection revMapColl = SHERevMapFactory.getLocalInstance(ctx)
									.getSHERevMapCollection("select * where revBillEntryId.id = '"+transEntryInfo.getId().toString()+"' order by seq "); 
				//CRMHelper.sortCollection(revMapColl, "seq", true); //��Ӧ����ϸ��seq����
				for (int j = 0; j < revMapColl.size(); j++) {
					//1.���ĶԳ��� ������Ҫɾ����   2.Ӧ����ϸ��ʵ�ս��     3��ʵ����ϸ���ѶԳ�������˽��   
					SHERevMapInfo revMapInfo = revMapColl.get(j);
					
					ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
					TranPayListEntryInfo entryInfo = null;	//Ӧ����ϸ�����Ѿ�ɾ�������Գ��ϵû�б����٣���Ҫ�ž���������
					TranPayListEntryCollection tempPayListColl = entryFactory.getTranPayListEntryCollection("select actRevAmount where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"' ");
					if(tempPayListColl!=null && tempPayListColl.size()>0)
						entryInfo = tempPayListColl.get(0);
					
					if(toCancelAmount.compareTo(new BigDecimal("0"))>0) {
						if(toCancelAmount.compareTo(revMapInfo.getAmount())>=0){  //ɾ���Գ��ϵ
							SHERevMapFactory.getLocalInstance(ctx).delete("where id= '" + revMapInfo.getId().toString()+"'");
							if(entryInfo!=null)
								entryInfo.setActRevAmount(entryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
							transEntryInfo.setHasMapedAmount(transEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
							toCancelAmount = toCancelAmount.subtract(revMapInfo.getAmount());
		logger.info("-----------------ɾ���Գ���"+revMapInfo.getAmount() + "------");							
						}else{														//�޸ĶԳ��ϵ
							SelectorItemCollection selectorMap = new SelectorItemCollection();
							selectorMap = new SelectorItemCollection();
							selectorMap.add(new SelectorItemInfo("amount"));
		logger.info("-----------------�޸ĶԳ��� ---��"+revMapInfo.getAmount() + "--��Ϊ"+revMapInfo.getAmount().subtract(toCancelAmount)+"-----");	
							revMapInfo.setAmount(revMapInfo.getAmount().subtract(toCancelAmount));
							SHERevMapFactory.getLocalInstance(ctx).updatePartial(revMapInfo, selectorMap);
							if(entryInfo!=null)
								entryInfo.setActRevAmount(entryInfo.getActRevAmount().subtract(toCancelAmount));
							transEntryInfo.setHasMapedAmount(transEntryInfo.getHasMapedAmount().subtract(toCancelAmount));
							toCancelAmount = new BigDecimal("0");
		logger.info("-----------------�޸ĶԳ���"+revMapInfo.getAmount() + "------");								
						}
						
						SelectorItemCollection selector = new SelectorItemCollection();											
						selector.add(new SelectorItemInfo("actRevAmount"));
						if(entryInfo!=null)
							entryFactory.updatePartial(entryInfo, selector);
					}
				}	
				
				if(toCancelAmount.compareTo(new BigDecimal("0"))>0)
					throw new EASBizException(new NumericExceptionSubItem("100","�˿���ϸ'"+quitMoneyName+"'��Ӧ���տ���ϸ�ѶԳ壬���޷�ȡ���Գ���"+toCancelAmount+"���޷��˿����飡"));
			}

logger.info("-----------------�տ���ϸ-"+transEntryInfo.getMoneyDefine().getName()+"--���˽��"+transEntryInfo.getHasRefundmentAmount()+"--�ѶԳ���= "+transEntryInfo.getHasMapedAmount() + "----end");			
			SelectorItemCollection selector = new SelectorItemCollection();			
			if(revBillInfo.isIsTansCreate()) {	//ת����ɵ��˿
				transEntryInfo.setHasTransferAmount(transEntryInfo.getHasTransferAmount().add(quitEntryInfo.getRevAmount().negate()));
				selector.add(new SelectorItemInfo("hasTransferAmount"));
			}else{
				transEntryInfo.setHasRefundmentAmount(transEntryInfo.getHasRefundmentAmount().add(quitEntryInfo.getRevAmount().negate()));
				selector.add(new SelectorItemInfo("hasRefundmentAmount"));
			}
			selector.add(new SelectorItemInfo("hasMapedAmount"));
			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(transEntryInfo, selector);
			
/*			//�������տ�ĶԳ��ϵ
			String tempRevBillId = transEntryInfo.getParent().getId().toString();
			if(idToReCreateMapRevBillIdsStr.indexOf(tempRevBillId)<0)
				idToReCreateMapRevBillIdsStr += ",'"+transEntryInfo.getParent().getId().toString()+"'";*/
		}
		
/*	//String idToReCreateMapRevBillIdsStr = "";	//��Ҫ���´����Գ��ϵ���տId	
 * 	if(!idToReCreateMapRevBillIdsStr.equals("")) {
			idToReCreateMapRevBillIdsStr = idToReCreateMapRevBillIdsStr.substring(1);
			SHERevBillCollection toReCreateColl = SHERevBillFactory.getLocalInstance(ctx) 
					.getSHERevBillCollection("select *,entrys.*,entrys.moneyDefine.moneyType,entrys.moneyDefine.name where id in ("+idToReCreateMapRevBillIdsStr+")  ");
			for (int i = 0; i < toReCreateColl.size(); i++) {
				this._submit(ctx, toReCreateColl.get(i));
			}
		}*/
		
	}
	
	
	/**Ϊĳһ�տ���ϸ�����Գ��ϵ
	 * relateBizBillId �������׵��ݵ�id
	 * relateBizType ������ҵ�񵥾ݵ�����
	 * revEntryInfo  �տ���ϸ ��id������ڣ�����������������ڣ�
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
//			throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸ�п������'"+moneyType+"'�Ҳ������ڵķ��࣬���飡"));		
	
//	logger.info("---ʵ����ϸ-"+moneyType+"-------�տ���"+revEntryInfo.getRevAmount()+"--���˽��"+revEntryInfo.getHasRefundmentAmount()
//			+"--��ת���"+revEntryInfo.getHasTransferAmount()+"--�ѶԳ���"+revEntryInfo.getHasMapedAmount()+"--------create begin");
		
		CoreBaseCollection payListEntryToUpdate = new CoreBaseCollection();	//�����µ�Ӧ����ϸ
		CoreBaseCollection revMapToAdd = new CoreBaseCollection();	//�����ӵĶԳ��ϵ
		ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		TranPayListEntryCollection tranEntryColl = new TranPayListEntryCollection();
		if(transInfo!=null){
			TranBusinessOverViewCollection transBizViewColl = transInfo.getTranBusinessOverView();
			for (int i = 0; i < transBizViewColl.size(); i++) {
				if(transBizViewColl.get(i).getType().equals(BusTypeEnum.PAY))
					tranEntryColl.add(transBizViewColl.get(i));
			}
		}else{
			tranEntryColl = entryFactory.getTranPayListEntryCollection( //���׵��ݵ�Ӧ����ϸ
					"select *,moneyDefine.moneyType where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' order by seq");
		}
							
		BigDecimal allRemainAmount = revEntryInfo.getRemainAmount();  
		
		TranPayListEntryInfo lasttranEntryInfo=null; 
		for (int j = 0; j < tranEntryColl.size(); j++) {
			TranPayListEntryInfo tranEntryInfo = tranEntryColl.get(j);
			if(tranEntryInfo.getMoneyDefine()==null)
				throw new EASBizException(new NumericExceptionSubItem("100","Ӧ����ϸ�п�������Ϊ�գ����飡"));
			
//			MoneyTypeEnum thisMoneyType = tranEntryInfo.getMoneyDefine().getMoneyType();
//			if(thisMoneyType==null){
//				MoneyDefineInfo tempDefInfo = MoneyDefineFactory.getLocalInstance(ctx)
//							.getMoneyDefineInfo("select moneyType where id = '"+tranEntryInfo.getMoneyDefine().getId()+"' "); 
//				thisMoneyType = tempDefInfo.getMoneyType();
//			}
//			String thisTypeSort = (String)CRMHelper.SHERevMoenyTpeMap().get(thisMoneyType.getValue());
//	logger.info("---Ӧ����ϸ-"+thisTypeSort+"---Ӧ�ս��"+tranEntryInfo.getAppAmount()+"---ʵ�ս��"+tranEntryInfo.getActRevAmount());
//			if(thisTypeSort==null)
//				throw new EASBizException(new NumericExceptionSubItem("100","Ӧ����ϸ�п������"+moneyType+"�Ҳ������ڵķ��࣬���飡"));
			
			if(!tranEntryInfo.getMoneyDefine().getId().equals(revEntryInfo.getMoneyDefine().getId()))  //Ӧ����ϸ�Ŀ����ʵ����ϸ�Ŀ�������ͬһ����ʱ�����ܶԳ�
				continue;

//			if(allRemainAmount.compareTo(new BigDecimal("0"))<0) //��ǰ���ܶԳ�Ľ��=�տ���-���˽��-��ת���-�ѶԳ���
//					throw new EASBizException(new NumericExceptionSubItem("100","�տ�ʱ��ϸ�в�Ӧ�ó��ָ����������"));
			
			BigDecimal tpRevAmount = tranEntryInfo.getActRevAmount();
			if(tpRevAmount==null) tpRevAmount = new BigDecimal("0");
			
			BigDecimal unPayAmount = tranEntryInfo.getAppAmount().subtract(tpRevAmount);	//δ��=Ӧ��-����
				
			BigDecimal toMapAmount = new BigDecimal("0");
			if(revEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.ReplaceFee)){
				toMapAmount = allRemainAmount;
			}else{
				if(allRemainAmount.compareTo(unPayAmount)>=0){ //��Ӧ�յ�ȫ��δ��
					toMapAmount = unPayAmount;
				}else{//ֻ�ܳ岿��
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
				newMapInfo.setAmount(toMapAmount);		  //�Գ��ϵ
				revMapToAdd.add(newMapInfo);
				logger.info("-------------------------------------------�Գ���"+newMapInfo.getAmount());					
				
				tranEntryInfo.setActRevAmount(tpRevAmount.add(toMapAmount)); //Ӧ����ϸ��ʵ�ս��
//				revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().add(toMapAmount)); //ʵ����ϸ�ĶԳ���
				
				payListEntryToUpdate.add(tranEntryInfo);
			}
			lasttranEntryInfo=tranEntryInfo;
//			break;
//	logger.info("---Ӧ����ϸ-"+thisTypeSort+"---Ӧ�ս��"+tranEntryInfo.getAppAmount()+"---ʵ�ս��"+tranEntryInfo.getActRevAmount());
		}		
		if(lasttranEntryInfo!=null&&allRemainAmount.compareTo(FDCHelper.ZERO)>0){
			SHERevMapInfo newMapInfo = new SHERevMapInfo();
			newMapInfo.setRelatBizType(relateBizType);
			newMapInfo.setRevBillEntryId(revEntryInfo);
			newMapInfo.setPayListEntryId(lasttranEntryInfo.getId().toString());
			newMapInfo.setSeq(lasttranEntryInfo.getSeq());	
			newMapInfo.setAmount(allRemainAmount);		  //�Գ��ϵ
			revMapToAdd.add(newMapInfo);
			
			if(!payListEntryToUpdate.contains(lasttranEntryInfo)){
				payListEntryToUpdate.add(lasttranEntryInfo);
			}
			lasttranEntryInfo.setActRevAmount(lasttranEntryInfo.getActRevAmount().add(allRemainAmount));
		}
//	logger.info("---ʵ����ϸ-"+moneyType+"-------�տ���"+revEntryInfo.getRevAmount()+"--���˽��"+revEntryInfo.getHasRefundmentAmount()
//				+"--��ת���"+revEntryInfo.getHasTransferAmount()+"--�ѶԳ���"+revEntryInfo.getHasMapedAmount()+"----------create end");
		
		//�仯�����ݰ�����
		//1.Ӧ����ϸ��ʵ�ս��   2��ʵ����ϸ���ѶԳ���   3���Գ��ϵ��
		entryFactory.update(payListEntryToUpdate);

//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add(new SelectorItemInfo("hasMapedAmount"));
//		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revEntryInfo, selector);
		
		ISHERevMap revMapFactory = SHERevMapFactory.getLocalInstance(ctx);
		revMapFactory.addnew(revMapToAdd);
	}
	
	
	
	
	/**Ϊĳһ�տ���ϸɾ���Գ��ϵ
	 * relateBizBillId ������ҵ�񵥾ݵ�id
	 * relateBizType ������ҵ�񵥾ݵ�����
	 * revEntryInfo  �տ���ϸ ��id������ڣ�����������������ڣ�  ������ҪУ�����Ƿ�������������ܲ�������ɾ���Գ��ϵ������
	 * **/
	private void deleteRevMapRelation(Context ctx,String relateBizBillId,RelatBizType relateBizType,SHERevBillEntryInfo revEntryInfo) throws BOSException, EASBizException {
		//if(relateBizBillId==null || relateBizBillId.trim().equals("")) return;
		//if(relateBizType==null) return;		
		if(revEntryInfo.getId()==null) return;
	
		logger.info("---ʵ����ϸ-"+revEntryInfo.getMoneyDefine().getMoneyType()+"-------�տ���"+revEntryInfo.getRevAmount()+"--���˽��"+revEntryInfo.getHasRefundmentAmount()
			+"--��ת���"+revEntryInfo.getHasTransferAmount()+"--�ѶԳ���"+revEntryInfo.getHasMapedAmount()+"--------delete begin");
		
		
		ISHERevMap revMapFactory = SHERevMapFactory.getLocalInstance(ctx);
		SHERevMapCollection existMapColl = 	revMapFactory //��ǰʵ����ϸ�Ѵ��ڵĶԳ��ϵ
						.getSHERevMapCollection("select * where revBillEntryId.id = '"+revEntryInfo.getId().toString()+"' order by seq");
		if(existMapColl.size()==0) return;		//��û�жԳ���ϸ���ô���
		ITranPayListEntry payEntryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		for(int k=0;k<existMapColl.size();k++){
			SHERevMapInfo revMapInfo = existMapColl.get(k); 
			TranPayListEntryInfo payEntryInfo = payEntryFactory.getTranPayListEntryInfo("select *,moneyDefine.moneyType where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"'  ");
			if(payEntryInfo!=null) {					
				payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selectorPay = new SelectorItemCollection();
				selectorPay.add(new SelectorItemInfo("actRevAmount"));
				payEntryFactory.updatePartial(payEntryInfo, selectorPay);//����Ӧ����ϸ��ʵ�ս��			
				
				logger.info("-----------------------------------------ɾ���Գ���"+revMapInfo.getAmount());		
				logger.info("---Ӧ����ϸ-"+payEntryInfo.getMoneyDefine().getMoneyType()+"---Ӧ�ս��"+payEntryInfo.getAppAmount()+"---ʵ�ս��"+payEntryInfo.getActRevAmount());
			}

//			revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
		}		
		logger.info("---ʵ����ϸ-"+revEntryInfo.getMoneyDefine().getMoneyType()+"-------�տ���"+revEntryInfo.getRevAmount()+"--���˽��"+revEntryInfo.getHasRefundmentAmount()
						+"--��ת���"+revEntryInfo.getHasTransferAmount()+"--�ѶԳ���"+revEntryInfo.getHasMapedAmount()+"----------delete end");
		
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add(new SelectorItemInfo("hasMapedAmount"));
//		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revEntryInfo, selector);  //����ʵ����ϸ���ѶԳ���
		revMapFactory.delete("where revBillEntryId.id = '"+revEntryInfo.getId().toString()+"' ");  //ɾ���Գ��ϵ
	}


	/**
	 * ����������״̬
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
	 * �����ύ״̬
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
	 * ����ҵ��������Ϣ�仯ʱ����Ҫ���½����Գ��ϵ
	 * oldTransInfo �ɵĽ��ף�����Ϊ��
	 * newTransInfo �µĽ��ף�����Ϊ��
	 * */
	protected void _whenTransEntryChange(Context ctx, TransactionInfo oldTransInfo, TransactionInfo newTransInfo)
			throws BOSException, EASBizException {
		if(oldTransInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","�ɵĽ��ײ���Ϊ�գ����飡"));
		if(newTransInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","�µĽ��ײ���Ϊ�գ����飡"));	
		if(!oldTransInfo.getId().equals(newTransInfo.getId()))
				throw new EASBizException(new NumericExceptionSubItem("100","�¾ɽ��׷�ͬһ�����ף����飡"));

		//�ɽ��ױ������չ���Ĳ���Ҫ����
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
		//�ɽ��׶�Ӧ�ĶԳ��ϵ
//		SHERevMapCollection revMapColl = SHERevMapFactory.getLocalInstance(ctx)
//					.getSHERevMapCollection("select *,revBillEntryId.*,revBillEntryId.parent.id where payListEntryId in ("+oldBizEntryIdsStr+")");
//		CoreBaseCollection toUpdateRevEntryColl = new CoreBaseCollection();
//		String revBillIdsStr = "";			//�ɽ������漰�����տ��id
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
//			SHERevBillEntryFactory.getLocalInstance(ctx).update(toUpdateRevEntryColl);						//�ָ��ɽ��׵ĶԳ��ϵ�漰����ʵ����ϸ
			SHERevMapFactory.getLocalInstance(ctx).delete("where payListEntryId in ("+oldBizEntryIdsStr+")");//ɾ���ɽ��׵ĶԳ��ϵ
		}

//		if(!revBillIdsStr.equals("")) {
			//�µĽ��׵���ϸ��ʵ�ս��Ӧ��Ϊ0, --- �ڽ����ύ�����Ѵ���			
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
		�����׿ͻ���ȫ�仯ʱ�� 
		1.ɾ�������׵�ȫ���Գ��ϵ 
		2.���±����׵������տΪԤ�տ�(Ҳ���ǲ���Ӧ���׵��տ�)
	 * */
	protected void _whenTransCustChange(Context ctx, TransactionInfo transInfo)	throws BOSException, EASBizException {
		if(transInfo==null)
			throw new EASBizException(new NumericExceptionSubItem("100","���ײ���Ϊ�գ����飡"));
		
		//�ɽ��ױ������չ���Ĳ���Ҫ����
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
		if(revMapColl.size()==0) return;		//��û�жԳ���ϸ���ô���
		
		for(int k=0;k<revMapColl.size();k++){
			SHERevMapInfo revMapInfo = revMapColl.get(k); 
			TranPayListEntryInfo payEntryInfo = payEntryFactory.getTranPayListEntryInfo("select *,moneyDefine.moneyType where id = '"+revMapInfo.getPayListEntryId()+"' and type = '"+BusTypeEnum.PAY_VALUE+"'  ");
			if(payEntryInfo!=null) {					
				payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selectorPay = new SelectorItemCollection();
				selectorPay.add(new SelectorItemInfo("actRevAmount"));
				if(payEntryInfo.getActRevAmount().compareTo(new BigDecimal("0"))<0){
					throw new EASBizException(new NumericExceptionSubItem("100","������ϸɾ���Գ��ϵ�쳣,��ϸid='"+revMapInfo.getPayListEntryId()+"'��"));
				}
				
				payEntryFactory.updatePartial(payEntryInfo, selectorPay);//����Ӧ����ϸ��ʵ�ս��			
			}

			SHERevBillEntryInfo revEntryInfo = revEntryFactory.getSHERevBillEntryInfo("select hasMapedAmount where id = '"+ revMapInfo.getRevBillEntryId().getId()  +"' ");
			if(revEntryInfo!=null) {
//				revEntryInfo.setHasMapedAmount(revEntryInfo.getHasMapedAmount().subtract(revMapInfo.getAmount()));
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("hasMapedAmount"));
				revEntryFactory.updatePartial(revEntryInfo, selector);  //����ʵ����ϸ���ѶԳ���	
			}
			
			
		}		
		
		//ɾ��������ϸ��Ӧ�ĶԳ��ϵ
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
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
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
			// ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
					|| !iCodingRuleManager.isAddView(info, costUnitId))
			// �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
			{
				// �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}
	
	
	
	
	/**����˿����Ƿ����
	 * �˿�ʱУ���߼������׵��ݶ�Ӧ������ʵ�ս���С��0��Ҳ�����յ�Ǯ�������˵ģ�  -- ��ֻ�н��������˿�ʱ����ҪУ�飬����ƾ���˲����ģ�
	 *@param relateTransId ����id
	 *@param quitAmount �˿���
	 **/
	public static void checkQuitMoneyAmount(Context ctx,String relateTransId,BigDecimal quitAmount) throws BOSException, EASBizException{
		if(relateTransId==null || quitAmount==null || quitAmount.compareTo(new BigDecimal("0"))>=0) return;
		
		ITranPayListEntry entryFactory = TranBusinessOverViewFactory.getLocalInstance(ctx);
		boolean exitNegateAppAmount = entryFactory.exists("where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' and appAmount < 0 "); 	
		if(exitNegateAppAmount){ //ֻ�д��ڸ���Ӧ��ʱ����Ҫ����
			TranPayListEntryCollection tranEntryColl = entryFactory.getTranPayListEntryCollection( //���׵��ݵ�Ӧ����ϸ
					"select actRevAmount where head.id = '"+relateTransId+"' and type = '"+BusTypeEnum.PAY_VALUE+"' order by seq");
			BigDecimal allActRevAmount = new BigDecimal("0");
			for (int i = 0; i < tranEntryColl.size(); i++) {
				allActRevAmount = allActRevAmount.add(tranEntryColl.get(i).getActRevAmount());
			}
			if(allActRevAmount.add(quitAmount).compareTo(new BigDecimal("0"))<0) {
				throw new EASBizException(new NumericExceptionSubItem("100","�˿��Ӧ���׵�ʣ���տ�ϼƲ������˴˿���飡"));
			}
		}
		

		
		
	}
	
	
	
    
}