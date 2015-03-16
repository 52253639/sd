package com.kingdee.eas.fdc.aimcost.app;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		目标成本引入引出转换类
 *		
 * @author		王川	
 * @createDate	2011-6-16		
 */
public class AimCostTransmission extends AbstractDataTransmission {

	AimCostInfo info = null;
	
	CostEntryInfo infoEntry = null;
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostTransmissionResource";
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = AimCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			infoEntry = transmitEntry(hsData, ctx);
			infoEntry.setHead(info);
			info.getCostEntry().add(infoEntry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		
		return info;
	}
	
	
	public AimCostInfo transmitHead(Hashtable hsData, Context ctx) throws TaskExternalException {
		// 工程项目编码
		String curProjectLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_longNumber");
		// 工程项目名称
		String curProjectName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_name_l2");	
		// 版本号
		String fVersionNumber = FDCTransmissionHelper.getFieldValue(hsData, "FVersionNumber");		
		// 版本名称
		String fVersionName = FDCTransmissionHelper.getFieldValue(hsData, "FVersionName");		
		// 制单人编码
		String fCreatorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_number");		
		// 制单日期
		String fCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");		
		// 审批人编码
		String fAuditorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_number");		
		// 审批日期
		String fAuditDate = FDCTransmissionHelper.getFieldValue(hsData, "FAuditDate");	
		// 单据状态
		String fState = FDCTransmissionHelper.getFieldValue(hsData, "FState");

		/**
		 * 必录项校验
		 */
		if(StringUtils.isEmpty(curProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotNull"));
		}
		if(StringUtils.isEmpty(fVersionNumber)) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberNotNull"));
		}
		if(StringUtils.isEmpty(fVersionName)) {
			throw new TaskExternalException(getResource(ctx, "VersionNameNotNull"));
		}
		if(StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTimeNotNull"));
		}
		if(StringUtils.isEmpty(fAuditorNumber)) {
			throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fAuditDate)) {
			throw new TaskExternalException(getResource(ctx, "AuditorDate"));
		}
	
		/**
		 * 字符长度校验
		 */
		if(fVersionNumber.length() > 10) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberIsOver10"));
		}
		if(fVersionName.length() > 20) {
			throw new TaskExternalException(getResource(ctx, "VersionNameIsOver20"));
		}
	
		/**
		 * 数字判断
		 */
		if(!FDCTransmissionHelper.isNumber(fVersionNumber)) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberNotNumber"));
		}
		if(fVersionNumber.indexOf(".") == -1) {
			fVersionNumber += ".0";
		}
		
		/**
		 * 日期格式校验
		 */
		FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(ctx, "CreateDateFormatError"));
		Date auditDate = FDCTransmissionHelper.checkDateFormat(fAuditDate, getResource(ctx, "AuditDateFormatError"));
		
		/**
		 * 查询数据校验
		 */
		CurProjectInfo curProjectInfo = null;		
		UserInfo creatot = null;
		UserInfo auditor = null;
		try {
			// 工程项目编码
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
			}
			
			
			// 判断单头是否已经存在
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProjectInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("versionNumber", fVersionNumber));
			filter.getFilterItems().add(new FilterItemInfo("versionName", fVersionName));
			filter.setMaskString("#0 and #1 and #2");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
			if(aimCostCollection.size() > 0) {
				info = aimCostCollection.get(0);
				return info;
			} else {
				info = new AimCostInfo();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 制单人
		creatot = isUserNumber(fCreatorNumber, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotFound"));
		}
			
		// 审批人
		auditor = isUserNumber(fAuditorNumber, ctx);
		if(auditor == null){
			throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound"));
		}
		
		/**
		 * 填充值
		 */
		info.setVersionNumber(fVersionNumber);
		info.setVersionName(fVersionName);
		info.setOrgOrProId(curProjectInfo.getId().toString());
		// 单据状态:枚举值，保存、已审批，默认为已审批。
		if (fState.trim().equals(getResource(ctx,"Saved"))) {
			info.setState(FDCBillStateEnum.SAVED);
		} else {
			info.setState(FDCBillStateEnum.AUDITTED);
		}
		info.setCreator(creatot);
		info.setCreateTime(Timestamp.valueOf(fCreateTime + " 00:00:00"));
		info.setAuditor(auditor);
		info.setAuditDate(auditDate);
		
		return info;
	}
	
	private CostEntryInfo transmitEntry(Hashtable hsData, Context ctx) throws TaskExternalException {
		// 组织编码
		String costOrg_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject$costOrg_longNumber");
		// 工程项目编码
		String curProjectLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_longNumber");
		// 工程项目名称
		String curProjectName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_name_l2");	
		// 成本科目编码
		String costAccountNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount_number");
		// 成本科目名称
		String costAccountName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount_name_l2");
		// 工作量
		String fCostEntryWorkload = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_workload");
		// 单位
		String fCostEntryUnit = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_unit");
		// 单价
		String fCostEntrPrice = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_price");
		// 目标成本(元)
		String fCostEntryCostAmount = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_costAmount");		
		// 归属产品
		String productNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$product_number");		
		// 备注
		String fCostEntryDesc = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_desc");		
		// 变化原因
		String fCostEntryChangeReason = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_changeReason");	
		
		if(StringUtils.isEmpty(fCostEntryCostAmount)) {
			throw new TaskExternalException(getResource(ctx, "CostAmountNotNull"));
		}
		if(StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotNull"));
		}
		
		if(!StringUtils.isEmpty(fCostEntryDesc) && fCostEntryDesc.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "DescIsOver200"));
		}
		if(!StringUtils.isEmpty(fCostEntryChangeReason) && fCostEntryChangeReason.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "ChangeReasonIsOver200"));
		}
		
		if(!StringUtils.isEmpty(fCostEntryWorkload) && !FDCTransmissionHelper.isNumber(fCostEntryWorkload)) {
			throw new TaskExternalException(getResource(ctx, "CostEntryWorkloadNotNumber"));
		}
		if(!StringUtils.isEmpty(fCostEntrPrice) && !FDCTransmissionHelper.isNumber(fCostEntrPrice)) {
			throw new TaskExternalException(getResource(ctx, "CostEntrPriceNotNuber"));
		}
		if(!FDCTransmissionHelper.isNumber(fCostEntryCostAmount)) {
			throw new TaskExternalException(getResource(ctx, "CostEntryCostAmountNotNumber"));
		}
		// 判断小数位
		
		
		MeasureUnitCollection measureCollection = null;
		ProductTypeCollection productTypeCollection = null;
		ProductTypeInfo productTypeInfo = null;
		CostAccountInfo costAccount = null;
		CostCenterOrgUnitInfo costCenter = null;
		CurProjectInfo curProjectInfo = null;
		try {
			// 工程项目编码
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
			}
			
			// 组织编码
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenter == null) {
				throw new TaskExternalException(getResource(ctx, "CostCenterNotFound"));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if(!StringUtils.isEmpty(costOrg_longNumber) && !costCenterLongNumber.equals(costOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound"));
				}
			}
			
			// 成本科目(父节点不能导入，直接判断isLeaf，暂时留着！)
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", costAccountNumber.replace('!', '.')));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if(costAccountCollection.size() > 0) {
				costAccount = costAccountCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotFound"));
			}
			
			// 单位
			if(!StringUtils.isEmpty(fCostEntryUnit)) {
				FilterInfo measureUnitFilter = new FilterInfo();
				measureUnitFilter.getFilterItems().add(new FilterItemInfo("name", fCostEntryUnit));
				EntityViewInfo materialView = new EntityViewInfo();
				materialView.setFilter(measureUnitFilter);
				measureCollection  = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(materialView);
				if(measureCollection.size() == 0) {
					throw new TaskExternalException(getResource(ctx, "MeasureUnitNotFound"));
				}
			}
			
			// 归属产品
			if(!StringUtils.isEmpty(productNumber)) {
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", productNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				productTypeCollection  = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound"));
				}
			}
		} catch (BOSException e) {
				e.printStackTrace();
		}
		
		infoEntry = new CostEntryInfo();
		
		// 组织
		costAccount.setFullOrgUnit(costCenter.castToFullOrgUnitInfo());
		// 工程项目
		costAccount.setCurProject(curProjectInfo);
		// 成本科目
		infoEntry.setCostAccount(costAccount);
		// 工作量
		infoEntry.setWorkload(FDCTransmissionHelper.strToBigDecimal(fCostEntryWorkload));
		// 单位
		infoEntry.setUnit(fCostEntryUnit);
		// 单价
		infoEntry.setPrice(FDCTransmissionHelper.strToBigDecimal(fCostEntrPrice));
		// 目标成本（元）
		infoEntry.setCostAmount(FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount));
		// 归属产品
		infoEntry.setProduct(productTypeInfo);
//		try {
//			// 建筑单方?
//			BigDecimal buildArea = FDCHelper.getApportionValue(curProjectInfo.getId().toString(),
//					ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
//			BigDecimal build = FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount).divide(buildArea, 2, BigDecimal.ROUND_HALF_UP);
//			
//			// 可售单方?
//			BigDecimal sellArea = FDCHelper.getApportionValue(curProjectInfo.getId().toString(),
//					ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
//			BigDecimal sell = FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount).divide(sellArea, 2, BigDecimal.ROUND_HALF_UP);
//			
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		// 备注
		infoEntry.setDesc(fCostEntryDesc);
		// 变化原因
		infoEntry.setChangeReason(fCostEntryChangeReason);
		
		return infoEntry;
	}
	
	/**
	 * 	判断用户是否存在
	 * @param number 用户编码
	 * @param ctx
	 * @return	true/false  存在/不存在
	 */
	private UserInfo  isUserNumber(String number, Context ctx) {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(new FilterItemInfo("number",number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(userView);
			if(userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

}