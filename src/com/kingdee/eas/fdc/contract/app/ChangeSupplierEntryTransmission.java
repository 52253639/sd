/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.CopySupplierEntryInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 朱俊
 * @version EAS7.0
 * @createDate 2011-6-13
 * @see
 */
public class ChangeSupplierEntryTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ChangeSupplierEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {

		ChangeSupplierEntryInfo info = new ChangeSupplierEntryInfo();
		ChangeAuditBillCollection coll = null;
		ContractBillCollection contractBillCollection = null;
		UserCollection userCollection = null;
		AdminOrgUnitCollection adminOrgUnitCollection = null;
		// String fSign = null;
		// ChangeAuditEntryInfo changeAuditEntryInfo = null;

		// 变更单编码
		String fChangeauditNumber = (String) ((DataToken) hsData.get("FParentID_number")).data;
		// 序列号
		String fSeq = (String) ((DataToken) hsData.get("FSeq")).data;
		// 合同编码
		String fContractBillCodingNumber = (String) ((DataToken) hsData.get("FContractBill_codingNumber")).data;
		// 主送单位
		String fMainSuppName = (String) ((DataToken) hsData.get("FMainSupp_name_l2")).data;
		// 抄送单位
		String fCopySuppName = (String) ((DataToken) hsData.get("FCopySupp$copySupp_name_l2")).data;
//		// 原始联系单号
//		String fOriginalContactNum = (String) ((DataToken) hsData.get("FOriginalContactNum")).data;
		// 执行内容序列号
		String fContentSeq = (String) ((DataToken) hsData.get("FEntrys$content_seq")).data;
		// 测算金额
		String fCostAmount = (String) ((DataToken) hsData.get("FCostAmount")).data;
//		// 是否确认变更金额
//		String fIsSureChangeAmt = (String) ((DataToken) hsData.get("FIsSureChangeAmt")).data;
//		// 施工方报审金额
//		String fConstructPrice = (String) ((DataToken) hsData.get("FConstructPrice")).data;
		// 测算说明
		String fCostDescription = (String) ((DataToken) hsData.get("FCostDescription")).data;
//		// 是否责任扣款单位
//		String fIsDeduct = (String) ((DataToken) hsData.get("FIsDeduct")).data;
		// 责任扣款金额
//		String fDeductAmount = (String) ((DataToken) hsData.get("FDeductAmount")).data;
//		// 结算方式
//		String fBalanceType = (String) ((DataToken) hsData.get("FBalanceType")).data;
		// 测算人名称
		String fReckonorName = (String) ((DataToken) hsData.get("FReckonor_name_l2")).data;
		// 责任归属部门
		String fDutyOrgName = (String) ((DataToken) hsData.get("FDutyOrg_name_l2")).data;

		String FCurrency_name_l2 = (String) ((DataToken) hsData.get("FCurrency_name_l2")).data;
		String FExRate = (String) ((DataToken) hsData.get("FExRate")).data;
		String FOriCostAmount = (String) ((DataToken) hsData.get("FOriCostAmount")).data;
		// 变更单编码
		if (StringUtils.isEmpty(fChangeauditNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_ChangeAuditNumberIsNull"));
		}
		if (fChangeauditNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,
					"Import_ChangeAuditNumberIsTooLong"));
		}
		try {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",
						"select fid from t_con_changeauditbill where fnumber='"
								+ fChangeauditNumber + "'", CompareType.INNER));
		view.setFilter(filter);

			coll = ChangeAuditBillFactory.getLocalInstance(ctx)
					.getChangeAuditBillCollection(view);
			if (coll.size() > 0) {
				ChangeAuditBillInfo changeAuditBill = coll.get(0);
				// changeAuditEntryInfo = changeAuditBill.getEntrys().get(0);
				info.setParent(changeAuditBill);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_ChangeAuditNumberIsNotExsit"));
			}
		} catch (BOSException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}

		// 序列号
		if (StringUtils.isEmpty(fSeq)) {
			throw new TaskExternalException(
					getResource(ctx, "Import_SeqIsNull"));
		}
		if (fSeq.length() > 4) {
			throw new TaskExternalException(getResource(ctx,
					"Import_SeqIsTooLong"));
		}
		info.setSeq(Integer.parseInt(fSeq));

		// 主送单位
		if (StringUtils.isEmpty(fMainSuppName)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fMainSuppNameIsNull"));
		}
		if (fMainSuppName.length() > 40) {
			fMainSuppName = (fMainSuppName.substring(0, 40));
		}
		try {
			FilterInfo supplierFilter = new FilterInfo();
			supplierFilter.getFilterItems().add(
					new FilterItemInfo("name", fMainSuppName));
			EntityViewInfo supplierView = new EntityViewInfo();
			supplierView.setFilter(supplierFilter);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(
					ctx).getSupplierCollection(supplierView);
			if (supplierColl != null && supplierColl.size() > 0) {
				SupplierInfo supplierInfo = supplierColl.get(0);
				info.setMainSupp(supplierInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_fMainSuppNameIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		// 抄送单位 TODO
		try {
//			String fCopySuppNames = fCopySuppName.replaceAll(",", "','") ;
//			String queryStr = "('"+fCopySuppNames+ "')";
			FilterInfo supplierFilter = new FilterInfo();
			supplierFilter.getFilterItems().add(new FilterItemInfo("name",fCopySuppName,CompareType.INCLUDE));
			EntityViewInfo supplierView = new EntityViewInfo();
			supplierView.setFilter(supplierFilter);
			SupplierCollection csec = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(supplierView);
			if (csec.size() < 0) {
				throw new TaskExternalException(getResource(ctx,
				"fCopySuppNameIsNotExsit"));
			}
			for(int i=0;i<csec.size()-1;i++) {
				SupplierInfo csdwInfo = csec.get(i);
				CopySupplierEntryInfo cseInfo = new CopySupplierEntryInfo();
				cseInfo.setCopySupp(csdwInfo);
				int seq = info.getCopySupp().size()+1;
				cseInfo.setSeq(seq);
				cseInfo.setParent(info);
				info.getCopySupp().add(cseInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

//		// 原始联系单号
//		info.setOriginalContactNum(fOriginalContactNum);

		// 执行内容序列号 TODO:输入的是序列号，而显示的是变更内容
		if (StringUtils.isEmpty(fContentSeq)) {
			throw new TaskExternalException(getResource(ctx,
					"fContentSeqIsNull"));
		}
		
//		 String a [] =
//		 {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
//		 ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//		 for(int i=1; i<27;i++) {
//		 if(Integer.parseInt(fContentSeq) == i) {
//		 fSign = a[i-1];
//		 }
//		 }
//		 String changeContent = changeAuditEntryInfo.getChangeContent();

		// 测算金额
		if (StringUtils.isEmpty(fCostAmount)) {
			throw new TaskExternalException(getResource(ctx,
					"fCostAmountIsNull"));
		}
		if (fCostAmount.matches("^\\d+(\\.\\d+)?$")) {
			if (!fCostAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
				// 测算金额录入不合法
				throw new TaskExternalException(getResource(ctx, "Import_fCostAmountIsWrong"));
			}
			info.setCostAmount(new BigDecimal(fCostAmount));
		}else {
			throw new TaskExternalException(getResource(ctx, "Import_fCostAmountIsNotNumber"));
		}
		

//		// 责任扣款金额
//		if(!StringUtils.isEmpty(fDeductAmount)) {
//			if(fDeductAmount.matches("^\\d+(\\.\\d+)?$")) {
//				if (!fDeductAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
//					// 责任扣款金额录入不合法
//					throw new TaskExternalException(getResource(ctx, "Import_fDeductAmountIsWrong"));
//				}
//				info.setDeductAmount(new BigDecimal(fDeductAmount));
//			}else {
//				throw new TaskExternalException(getResource(ctx, "Import_fDeductAmountIsNotNumber"));
//			}
//		}
		

		// 施工方报审金额
//		if (fConstructPrice.trim().length() > 19) {
//			throw new TaskExternalException(getResource(ctx,
//					"fConstructPriceIsOverLength"));
//		}
//		if(!StringUtils.isEmpty(fConstructPrice)) {
//			if (fConstructPrice.matches("^\\d+(\\.\\d+)?$")) {
//				if (!fConstructPrice.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
//					// 施工方报审金额录入不合法
//					throw new TaskExternalException(getResource(ctx, "Import_fConstructPriceIsWrong"));
//				}
//				info.setConstructPrice(new BigDecimal(fConstructPrice));
//			}else {
//				throw new TaskExternalException(getResource(ctx, "Import_fConstructPriceIsNotNumber"));
//			}
//		}
		

//		// 是否确认变更金额
//		if (fIsSureChangeAmt.equals(getResource(ctx, "Yes"))) {
//			info.setIsSureChangeAmt(true);
//		} else {
//			info.setIsSureChangeAmt(false);
//		}

		// 测算说明
		if (fCostDescription.length() > 40) {
			throw new TaskExternalException(getResource(ctx,
					"fCostDescriptionIsTooLong"));
		}
		info.setCostDescription(fCostDescription);

		// 是否责任扣款单位
//		if (fIsDeduct.trim().equals(getResource(ctx, "Yes"))) {
//			info.setIsDeduct(true);
//		} else {
//			info.setIsDeduct(false);
//		}

		// 结算方式
//		if (fBalanceType.length() > 40) {
//			throw new TaskExternalException(getResource(ctx,
//					"fBalanceTypeIsTooLong"));
//		}
//		info.setBalanceType(fBalanceType);

		// 测算人名称
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(
					new FilterItemInfo("name", fReckonorName));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			userCollection = UserFactory.getLocalInstance(ctx)
					.getUserCollection(userView);
			if (userCollection.size() > 0) {
				UserInfo userInfo = userCollection.get(0);
				info.setReckonor(userInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		// 责任归属部门
		if (StringUtils.isEmpty(fDutyOrgName)) {
			throw new TaskExternalException(getResource(ctx,
					"fDutyOrgNameIsNull"));
		}
		try {
			FilterInfo adminOrgUnitFilter = new FilterInfo();
			EntityViewInfo adminOrgUnitView = new EntityViewInfo();
			adminOrgUnitFilter.getFilterItems().add(
					new FilterItemInfo("id",
							"select fid from T_ORG_Admin where fname_l2='"
									+ fDutyOrgName + "'", CompareType.INNER));
			adminOrgUnitView.setFilter(adminOrgUnitFilter);
			adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx)
					.getAdminOrgUnitCollection(adminOrgUnitView);
			if (adminOrgUnitCollection.size() > 0) {
				AdminOrgUnitInfo adminOrgUnitInfo = adminOrgUnitCollection
						.get(0);
				info.setDutyOrg(adminOrgUnitInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"fDutyOrgNameIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		// 合同编码
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fContractBillCodingNumberIsNull"));
		}
		if (fContractBillCodingNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fContractBillCodingNumberIsTooLong"));
		}
		try {
			FilterInfo contractBillFilter = new FilterInfo();
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillFilter.getFilterItems().add(
					new FilterItemInfo("id",
							"select fid from t_con_contractbill where fnumber='"
									+ fContractBillCodingNumber + "'",
							CompareType.INNER));
			contractBillView.setFilter(contractBillFilter);
			contractBillCollection = ContractBillFactory.getLocalInstance(ctx)
					.getContractBillCollection(contractBillView);
			if (contractBillCollection.size() > 0) {
				ContractBillInfo contractBillInfo = contractBillCollection.get(0);
//				String a = contractBillInfo.getExRate().toString();
//				info.setExRate(FDCNumberHelper.toBigDecimal(a, 2));
				info.setContractBill(contractBillInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_fContractBillCodingNumberIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		if (StringUtils.isEmpty(FOriCostAmount)) {
			throw new TaskExternalException("测试金额原币不能为空！");
		}
		if (StringUtils.isEmpty(FExRate)) {
			throw new TaskExternalException("汇率不能为空！");
		}
		if (StringUtils.isEmpty(FCurrency_name_l2)) {
			throw new TaskExternalException("币别不能为空！");
		}
		if(!StringUtils.isEmpty(FOriCostAmount)) {
			if(FOriCostAmount.matches("^\\d+(\\.\\d+)?$")) {
				if (!FOriCostAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
					// 责任扣款金额录入不合法
					throw new TaskExternalException("测试金额原币录入不合法！");
				}
				info.setOriCostAmount(new BigDecimal(FOriCostAmount));
			}else {
				throw new TaskExternalException("测算金额原币不是数字！");
			}
		}
		if(!StringUtils.isEmpty(FExRate)) {
			if(FExRate.matches("^\\d+(\\.\\d+)?$")) {
				if (!FExRate.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
					// 责任扣款金额录入不合法
					throw new TaskExternalException("汇率录入不合法！");
				}
				info.setExRate(new BigDecimal(FExRate));
			}else {
				throw new TaskExternalException("汇率不是数字！");
			}
		}
		try {
			CurrencyCollection col = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='"+FCurrency_name_l2+"'");
			if (col.size() > 0) {
				info.setCurrency(col.get(0));
			} else {
				throw new TaskExternalException("币别在系统中不存在！");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			ChangeAuditEntryCollection col = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryCollection("select * from where parent.id='"+info.getParent().getId().toString()+"' and seq="+fContentSeq);
			if(col.size()>0){
				SupplierContentEntryInfo entry=new SupplierContentEntryInfo();
				entry.setContent(col.get(0));
				info.getEntrys().add(entry);
			}else{
				throw new TaskExternalException("执行内容序列号在系统中不存在！");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 得到资源文件
	 * 
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ChangeSupplierEntryInfo == false) {
			return;
		}	
		try {
			getController(ctx).save(coreBaseInfo);
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
}
