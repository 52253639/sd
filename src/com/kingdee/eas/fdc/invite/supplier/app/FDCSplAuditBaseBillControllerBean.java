package com.kingdee.eas.fdc.invite.supplier.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.KAClassficationInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.INewOrgUnitFacade;
import com.kingdee.eas.basedata.org.NewOrgUnitFacadeFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fi.gl.common.toolkit.sqlbuilder.SqlBuilder;
import com.kingdee.eas.fm.common.EJBAccessFactory;
import com.kingdee.eas.framework.util.TimeStampUtility;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public abstract class FDCSplAuditBaseBillControllerBean extends AbstractFDCSplAuditBaseBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditBaseBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
    	writeBackToSupplier(ctx, billId);
    }
	/**
     * 供应商主数据回写处理
     * @param ctx
     * @param billId
     * @throws BOSException
     * @throws EASBizException
     */
    private void writeBackToSupplier(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	FDCSplAuditBaseBillInfo info = getFDCSplAuditBaseBillInfo(ctx, billId);
    	if (isNeedWriteBack(ctx, info)) {      		
    		createSupplierBill(ctx, info);
    	}
    }
    
    /**
     * 将房地产供应商的数据回写到主数据的供应商中
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    private void createSupplierBill(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	SupplierInfo supplier = new SupplierInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
    	selector.add("id");
    	selector.add("number");
    	selector.add("name");
    	selector.add("supplierType.id");
    	selector.add("supplierType.number");
    	selector.add("supplierType.name");
    	selector.add("supplierType.groupStandard.id");
    	selector.add("supplierType.groupStandard.name");
    	selector.add("businessNum");
    	selector.add("province.id");
    	selector.add("province.number");
    	selector.add("province.name");
    	selector.add("city.id");
    	selector.add("city.number");
    	selector.add("city.name");
    	selector.add("address");
    	selector.add("bankName");
    	selector.add("bankCount");
    	selector.add("hasCreatedSupplierBill");
    	SupplierStockInfo supplierStock = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(info.getSupplier().getId()), selector);
    	
    	//如果已经生成过主数据的供应商了，就不需要再处理
    	if (supplierStock.isHasCreatedSupplierBill()) {
    		return;
    	}
    	/*
    	序号	房地产供应商字段	主数据供应商字段
    	1	基本分类			分类
    	2	编码				编码
    	3	公司名称			名称
    	4	营业执照			营业执照
    	5	所在省份			省份
    	6	所在城市			城市
    	7	公司地址			地址
    	8	开户银行			开户银行
    	9	银行帐号			银行帐号
    	*/
    	supplier.setBrowseGroup(supplierStock.getSupplierType());
    	supplier.setNumber(supplierStock.getNumber());
    	supplier.setName(supplierStock.getName());
    	supplier.setBusiLicence(supplierStock.getBusinessNum());
    	supplier.setProvince(supplierStock.getProvince());
    	supplier.setCity(supplierStock.getCity());
    	supplier.setAddress(supplierStock.getAddress());
    	
    	supplier.setIsInternalCompany(false);
    	supplier.setCreator(ContextUtil.getCurrentUserInfo(ctx)); // 当前用户
    	supplier.setCreateTime(TimeStampUtility.getTime());
    	supplier.setCU(ContextUtil.getCurrentCtrlUnit(ctx)); // 创建公司
    	supplier.setAdminCU(ContextUtil.getCurrentCtrlUnit(ctx));
    	supplier.setVersion(1);// 版本
    	supplier.setUsedStatus(UsedStatusEnum.UNAPPROVE);// 供应商状态
    	supplier.setTaxRate(new BigDecimal("0.00"));
    	supplier.setCreateTime(TimeStampUtility.getTime());
    	supplier.setCreator(ContextUtil.getCurrentUserInfo(ctx));
    	supplier.setLastUpdateTime(null);
    	supplier.setLastUpdateUser(null);
    	
    	//供应商分组明细
    	SupplierGroupDetailInfo groupDetailInfo = new SupplierGroupDetailInfo();
    	groupDetailInfo.setSupplierGroup(supplierStock.getSupplierType());
    	groupDetailInfo.setSupplierGroupStandard(supplierStock.getSupplierType().getGroupStandard());
    	groupDetailInfo.setSupplierGroupFullName(supplierStock.getSupplierType().getGroupStandard().getName() + supplierStock.getSupplierType().getName());
    	supplier.getSupplierGroupDetails().add(groupDetailInfo);
		
    	IObjectPK supplierPK = SupplierFactory.getLocalInstance(ctx).submit(supplier);
    	supplier.setId(BOSUuid.read(supplierPK.toString()));
    	
    	updateHasSupplierBill(ctx, supplierStock);
    	
    	createDefaultOtherInfo(ctx, supplier, supplierStock);
    }
    
    
    /**
     * 保存基本页签得信息同时保存其他也页签得默认信息
     * @param ctx
     * @param info
     * @param supplierStock
     * @throws BOSException
     * @throws EASBizException
     */
    private void createDefaultOtherInfo(Context ctx, SupplierInfo info, SupplierStockInfo supplierStock) throws BOSException, EASBizException {
    	//财务信息
		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
    	INewOrgUnitFacade iNewOrgUnit = null;
		iNewOrgUnit = NewOrgUnitFacadeFactory.getLocalInstance(ctx);
		if (company != null) {// 默认当前财务组织并且没有封存
			SupplierCompanyInfoInfo vo = new SupplierCompanyInfoInfo();
			vo.setCompanyOrgUnit(company);
			vo.setSupplier(info);
			vo.setCU(company.getCU());
			vo.setIsFreezePayment(false);
			vo.setCreateTime(TimeStampUtility.getTime());
			vo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			vo.setLastUpdateTime(null);
			vo.setLastUpdateUser(null);
			
			// 当前财务组织是否被封存
			iNewOrgUnit.checkIsOUSealUp(company.getId().toString(), OrgType.COMPANY_VALUE);
			
			vo.setSettlementCurrency(company.getBaseCurrency());
			//供应商银行
			if (supplierStock.getBankCount() != null) {
				SupplierCompanyBankInfo supplierBank = new SupplierCompanyBankInfo();
				if (supplierStock.getBankName() != null) {
					supplierBank.setBank(supplierStock.getBankName());
				}
				supplierBank.setBankAccount(supplierStock.getBankCount());
				vo.getSupplierBank().add(supplierBank);
			}
			
			SupplierCompanyInfoFactory.getLocalInstance(ctx).submit(vo);
		}
		
		// 采购信息
		PurchaseOrgUnitInfo purOrg = ContextUtil.getCurrentPurchaseUnit(ctx);
		if (purOrg!= null) {// 默认当前采购组织
			SupplierPurchaseInfoInfo purchaseInfo = new SupplierPurchaseInfoInfo();
			purchaseInfo.setPurchaseOrgUnit(purOrg);
			purchaseInfo.setDeliverOrgUnit(info);
			purchaseInfo.setBillingOrgUnit(info);
			purchaseInfo.setSupplier(info);
			purchaseInfo.setGrade("0");
			purchaseInfo.setCU(purOrg.getCU());
			purchaseInfo.setIsFreezeMakeOrder(false);
			purchaseInfo.setCreateTime(TimeStampUtility.getTime());
			purchaseInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			purchaseInfo.setLastUpdateTime(null);
			purchaseInfo.setLastUpdateUser(null);
				// 当前财务组织是否被封存
				iNewOrgUnit.checkIsOUSealUp(purOrg.getId().toString(),OrgType.PURCHASE_VALUE);
				SupplierPurchaseInfoFactory.getLocalInstance(ctx).submit(purchaseInfo);
		}
    }
    
    
    /**
     * 根据单据ID，来获取Info
     * @param ctx
     * @param billId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	EntityObjectInfo info = EJBAccessFactory.createLocalInstance(ctx).getEntityInfo(billId.getType());
		if(info == null) {
			return null;
		}
		//by tim_gao 得到本地供货商信息 2011-04-20
		SelectorItemCollection sic = new SelectorItemCollection();
	    sic.add(new SelectorItemInfo("*"));
	    sic.add(new SelectorItemInfo("supplier.name"));
	    sic.add(new SelectorItemInfo("supplier.number"));
		if("FDCSplKeepContractAuditBill".equals(info.getRealName())){
			return FDCSplKeepContractAuditBillFactory.getLocalInstance(ctx).getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK(billId));
		} else if("FDCSplQualificationAuditBill".equals(info.getRealName())){
			return FDCSplQualificationAuditBillFactory.getLocalInstance(ctx).getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(billId),sic);
		} else if("FDCSplPeriodAuditBill".equals(info.getRealName())){
			return FDCSplPeriodAuditBillFactory.getLocalInstance(ctx).getFDCSplPeriodAuditBillInfo(new ObjectUuidPK(billId));
		}
		return null;
	}
    
    /**
     * 单据审核时是否需要向供应商主数据回写。(子类需要重写此方法)
     * 如果该单据上有对供应商的服务进行评价，并且至少有一项服务是合格的，则需要向供应商主数据回写
     * @param ctx
     * @param info
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	
    	return false;
    }
    
    /**
     * 更新供应商记录的hasCreatedSupplierBill字段为true
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    protected void updateHasSupplierBill(Context ctx, SupplierStockInfo info) throws BOSException, EASBizException {
    	SupplierStockInfo billInfo = new SupplierStockInfo(); 
    	billInfo.setId(info.getId());
    	billInfo.setHasCreatedSupplierBill(true);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasCreatedSupplierBill");
		SupplierStockFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
    }
    
}