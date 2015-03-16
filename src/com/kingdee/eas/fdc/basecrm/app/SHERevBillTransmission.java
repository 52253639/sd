package com.kingdee.eas.fdc.basecrm.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.app.ContextUtil;

public class SHERevBillTransmission extends AbstractDataTransmission{
	private static final Logger logger=CoreUIObject.getLogger(com.kingdee.eas.fdc.basecrm.app.SHERevBillTransmission.class);

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try
        {
            return SHERevBillFactory.getLocalInstance(ctx);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try
        {
            check(hsData, ctx);
        }
        catch(EASBizException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        return null;
	}
	private void check(Hashtable hsData, Context ctx) throws TaskExternalException, EASBizException, BOSException{
		 if(hsData.get("FNumber") == null || hsData.get("FNumber") != null && hsData.get("FNumber").toString().trim().length() == 0)
	         throw new TaskExternalException("单据编号不能为空！");
	     if(hsData.get("FRoom_name_l2") == null || hsData.get("FRoom_name_l2") != null && hsData.get("FRoom_name_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("房间不能为空！");
	     if(hsData.get("FRevBillType") == null || hsData.get("FRevBillType") != null && hsData.get("FRevBillType").toString().trim().length() == 0)
	         throw new TaskExternalException("收款单单据类型！");
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		SHERevBillInfo info = (SHERevBillInfo)coreBaseInfo;
		try {
			if(info.getRoom()!=null&&info.getRelateTransId()==null){
			
				info.setIsTansCreate(false);
				info.setExchangeRate(FDCHelper.ONE);
				info.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				info.setState(FDCBillStateEnum.SAVED);
				
				CompanyOrgUnitInfo tempCompany = ContextUtil.getCurrentFIUnit(ctx);
				CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,tempCompany.getId().toString(),null,false);
				info.setCurrency(company.getBaseCurrency());
				
				if(info.getRevAccount()!=null){
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("companyID.id", ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
					if (ContextUtil.getCurrentFIUnit(ctx).getAccountTable() != null)
					{
						filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", ContextUtil.getCurrentFIUnit(ctx).getAccountTable().getId().toString()));
					}
					filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
					filter.getFilterItems().add(new FilterItemInfo("name",info.getRevAccount().getName()));
					evi.setFilter(filter);
					AccountViewCollection col=AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(evi);
					if(col.size()>0){
						info.setRevAccount(col.get(0));
					}
				}
				info.setRoom(RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(info.getRoom().getId())));
				
				IObjectValue objectValue=null;
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id", info.getRoom().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));
				view.setFilter(filter);
				
				TransactionCollection col=TransactionFactory.getLocalInstance(ctx).getTransactionCollection(view);
				if(col.size()>0){
					ObjectUuidPK pk=new ObjectUuidPK(col.get(0).getBillId());
					objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
				}
				if(objectValue!=null){
					String custIdsStr = "";
					if(objectValue instanceof PrePurchaseManageInfo){
						PrePurchaseManageInfo prePur=(PrePurchaseManageInfo)objectValue;
						if(!TransactionStateEnum.PREAPPLE.equals(prePur.getBizState())&&!TransactionStateEnum.PREAUDIT.equals(prePur.getBizState())){
							throw new TaskExternalException("房间"+info.getRoom().getName()+"对应交易单据业务状态不能进行收款操作！");
						}
						info.setRelateBizBillId(prePur.getId().toString());
						info.setRelateBizBillNumber(prePur.getNumber());
						info.setSellProject(prePur.getSellProject());
						info.setRelateBizType(RelatBizType.PrePur);
						info.setRelateTransId(prePur.getTransactionID().toString());
						info.setPayCustomerName(prePur.getCustomerNames());
						info.setCustomerNames(prePur.getCustomerNames());
						
						for(int i=0;i<prePur.getPrePurchaseCustomerEntry().size();i++){
							SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
							entry.setSheCustomer(prePur.getPrePurchaseCustomerEntry().get(i).getCustomer());
							info.getCustomerEntry().add(entry);
							
							custIdsStr += ","+prePur.getPrePurchaseCustomerEntry().get(i).getCustomer().getId().toString();
						}
						if(!custIdsStr.equals("")) {
							info.setCustomerIds(custIdsStr.substring(1));
						}
					}else if(objectValue instanceof PurchaseManageInfo){
						PurchaseManageInfo prePur=(PurchaseManageInfo)objectValue;
						if(!TransactionStateEnum.PURAPPLE.equals(prePur.getBizState())&&!TransactionStateEnum.PURAUDIT.equals(prePur.getBizState())){
							throw new TaskExternalException("房间"+info.getRoom().getName()+"对应交易单据业务状态不能进行收款操作！");
						}
						info.setRelateBizBillId(prePur.getId().toString());
						info.setRelateBizBillNumber(prePur.getNumber());
						info.setSellProject(prePur.getSellProject());
						info.setRelateBizType(RelatBizType.PreOrder);
						info.setRelateTransId(prePur.getTransactionID().toString());
						info.setPayCustomerName(prePur.getCustomerNames());
						info.setCustomerNames(prePur.getCustomerNames());
						
						for(int i=0;i<prePur.getPurCustomerEntry().size();i++){
							SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
							entry.setSheCustomer(prePur.getPurCustomerEntry().get(i).getCustomer());
							info.getCustomerEntry().add(entry);
							
							custIdsStr += ","+prePur.getPurCustomerEntry().get(i).getCustomer().getId().toString();
						}
						if(!custIdsStr.equals("")) {
							info.setCustomerIds(custIdsStr.substring(1));
						}
					}else if(objectValue instanceof SignManageInfo){
						SignManageInfo prePur=(SignManageInfo)objectValue;
						if(!TransactionStateEnum.SIGNAPPLE.equals(prePur.getBizState())&&!TransactionStateEnum.SIGNAUDIT.equals(prePur.getBizState())){
							throw new TaskExternalException("房间"+info.getRoom().getName()+"对应交易单据业务状态不能进行收款操作！");
						}
						info.setRelateBizBillId(prePur.getId().toString());
						info.setRelateBizBillNumber(prePur.getNumber());
						info.setSellProject(prePur.getSellProject());
						info.setRelateBizType(RelatBizType.Sign);
						info.setRelateTransId(prePur.getTransactionID().toString());
						info.setPayCustomerName(prePur.getCustomerNames());
						info.setCustomerNames(prePur.getCustomerNames());
						
						for(int i=0;i<prePur.getSignCustomerEntry().size();i++){
							SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
							entry.setSheCustomer(prePur.getSignCustomerEntry().get(i).getCustomer());
							info.getCustomerEntry().add(entry);
							
							custIdsStr += ","+prePur.getSignCustomerEntry().get(i).getCustomer().getId().toString();
						}
						if(!custIdsStr.equals("")) {
							info.setCustomerIds(custIdsStr.substring(1));
						}
					}
				}else{
					throw new TaskExternalException("房间"+info.getRoom().getName()+"无关联交易单据！");
				}
			}
			BigDecimal amont=FDCHelper.ZERO;
			for(int i=0;i<info.getEntrys().size();i++){
				if(info.getEntrys().get(i).getId()!=null){
					amont=amont.add(SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(info.getEntrys().get(i).getId())).getRevAmount());
				}else if(info.getEntrys().get(i).getRevAmount()!=null){
					amont=amont.add(info.getEntrys().get(i).getRevAmount());
				}
				info.getEntrys().get(i).setInvoiceType(InvoiceTypeEnum.OTHER);
				info.getEntrys().get(i).setAmount(FDCHelper.ZERO);
			}
			info.setRevAmount(amont);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		super.submit(info, ctx);
	}
}
