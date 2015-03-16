/**
 * 
 */
package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		朱俊
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class PaymentSplitWithoutTxtConTransmission extends AbstractDataTransmission {
	
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";

	// 引入工具类
	
	/** 无文本合同付款拆分 */
	private PaymentSplitInfo info = null;
	/** 无文本合同付款拆分分录 */
	private PaymentSplitEntryInfo entryInfo = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 合同 */
	private ContractWithoutTextInfo contractWithoutText = null;
	/** 付款单 */
	private PaymentBillInfo paymentBill = null;
	/** 拆分人 */
	private UserInfo createUser = null;
	/** 审批人 */
	private UserInfo auditUser = null;
	/** 成本科目 */
	private CostAccountInfo costAccount = null;
	/** 归属产品<直接归属> */
	private ProductTypeInfo productType = null;
	
	/**
	 * @description		
	 * @author			朱俊		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PaymentSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			朱俊		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			info = transmitHead(hashtable, context);

			if (info == null) {
				return null;
			}
			PaymentSplitEntryInfo entry = transmitEntry(hashtable, context);
			entry.setParent(info);
			int seq = info.getEntrys().size() + 1;
			entry.setSeq(seq);
			entry.setIndex(seq);
			info.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}

	/**
	 * 
	 * @description		无文本合同付款拆分单头 
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {

		// 1.组织长编码
		String fCostCenterNumber  = ((String) ((DataToken)hashtable.get("FCostCenter_number")).data).trim();
		// 2.工程项目编码
		String fCurProjectLongNumber  = ((String) ((DataToken)hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.工程项目名称
		String fCurProjectNameL2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
		// 4.合同编码
		String fConWithoutTextNumber = ((String) ((DataToken)hashtable.get("FConWithoutText_number")).data).trim();
		// 5.合同名称
		String fConWithoutTextName  = ((String) ((DataToken)hashtable.get("FConWithoutText_name")).data).trim();
		// 6.付款单编码
		String fPaymentBillNumber  = ((String) ((DataToken)hashtable.get("FPaymentBill_number")).data).trim();
		// 7.付款金额
		String fPaymentBillAmount = ((String) ((DataToken)hashtable.get("FPaymentBill_amount")).data).trim();
		// 12.归属成本金额
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrys_payedAmt")).data).trim();
		// 14.拆分人
		String fCreatorNameL2  = ((String) ((DataToken)hashtable.get("FCreator_name_l2")).data).trim();
		// 15.拆分时间
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 16.审批人
		String fAuditorNameL2 = ((String) ((DataToken)hashtable.get("FAuditor_name_l2")).data).trim();
		// 17.审批时间
		String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		
		/**
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			// "工程项目编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCurProjectLongNumber"));
		}
		if (StringUtils.isEmpty(fConWithoutTextNumber)) {
			// "合同编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fConWithoutTextNumber"));
		}
		if (StringUtils.isEmpty(fPaymentBillNumber)) {
			// "付款单编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fPaymentBillNumber"));
		}
		if (!StringUtils.isEmpty(fPaymentBillAmount)) {
			if (!fPaymentBillAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "付款金额金额应该录入数字型！"
				throw new TaskExternalException(getResource(context, "fkjeyglrszx"));
			}
		}
		if (!StringUtils.isEmpty(fEntrysPayedAmt)) {
			if(!fEntrysPayedAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")){
				// "归属成本金额应该录入数字型！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt"));
			}
		} else {
			// "归属成本金额不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmtNotNull"));
		}
		if (StringUtils.isEmpty(fCreatorNameL2)) {
			// "拆分人编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreatorNameL2"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "拆分时间不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreateTime"));
		}
		if (StringUtils.isEmpty(fAuditorNameL2)) {
			// "审批人编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditorNameL2"));
		}
		if (StringUtils.isEmpty(fAuditTime)) {
			// "审批时间不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditTime"));
		}
		
		/**
		 * 将值存入对象中 
		 */
		try {  
			// 付款单编码
			FilterInfo filterpaymentBill = new FilterInfo();
			filterpaymentBill.getFilterItems().add(new FilterItemInfo("number", fPaymentBillNumber));
			EntityViewInfo viewpaymentBill = new EntityViewInfo();
			viewpaymentBill.setFilter(filterpaymentBill);
			PaymentBillCollection paymentBillColl = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(viewpaymentBill);
			if (paymentBillColl.size() > 0){
				paymentBill = paymentBillColl.get(0);
				// 判断挡墙的付款单编码是否为已审批状态
				if (!paymentBill.getBillStatus().getName().equals("AUDITED")){
					// 当前所选付款单编码对象未审批
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fkdbmwsp"));
				}
				/**
				 *判断单头是否存在使用付款单编码，因为一个付款单编码只能对应一个无文本合同付款拆分
				 */
				FilterInfo filterinfo = new FilterInfo();
				filterinfo.getFilterItems().add(new FilterItemInfo("paymentBill", paymentBill.getId()));
				EntityViewInfo viewinfo = new EntityViewInfo();
				viewinfo.setFilter(filterinfo);
				PaymentSplitCollection paymentSplitColl = PaymentSplitFactory.getLocalInstance(context).getPaymentSplitCollection(viewinfo);
				if (paymentSplitColl.size() > 0) {
					info = paymentSplitColl.get(0);
					return info;
				} else {
					info = new PaymentSplitInfo();
				}
			}else{
				// 1 "付款单编码不存在,或者该付款单编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fPaymentBillNumber1") + fPaymentBillNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setPaymentBill(paymentBill);
			// 工程项目编码
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// 组织长编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); // 工程项目对应成本中心组织
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (ccouc.size() > 0) {
					costCenterOrgUnit = ccouc.get(0);
					if (!StringUtils.isEmpty(fCostCenterNumber) && !fCostCenterNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
						// "组织长编码在工程项目所对应的成本中心不存在!"
	 					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_cbzxbdy"));
					}
					if (!StringUtils.isEmpty(fCurProjectNameL2) && !curProject.getName().equals(fCurProjectNameL2)) {
						// 工程项目名称与工程项目编码所对应的名称不相同！
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmccw"));
					}
				} else {
					// "组织长编码在工程项目所对应的成本中心不存在!"
 					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_cbzxbdy"));
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "Import_NOTNULL"));
			}
			// 合同编码
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fConWithoutTextNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractWithoutTextCollection cwtc = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewcontractBill);
			if (cwtc.size() > 0) {
				contractWithoutText = cwtc.get(0);
				if (!StringUtils.isEmpty(fConWithoutTextName) && !fConWithoutTextName.equals(contractWithoutText.getName())) {
					// 合同名称对应合同编码中的名称不相同！
					throw new TaskExternalException(getResource(context, "htmcbxt"));
				}
				info.setConWithoutText(contractWithoutText);// 无文本合同
			} else {
				// 1 "合同编码不存在,或者该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fConWithoutTextNumber1") + fConWithoutTextNumber + getResource(context, "Import_NOTNULL"));
			}
			if (!StringUtils.isEmpty(fPaymentBillAmount) && new BigDecimal(fPaymentBillAmount).compareTo(paymentBill.getActPayAmt()) != 0) {
				// 付款金额与付款单编码中对应的付款金额不相等！
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			BigDecimal bigD1 = new BigDecimal(fEntrysPayedAmt);
			BigDecimal bigD2 = paymentBill.getActPayAmt();
			if (bigD1.compareTo(bigD2) != 0) {
				// "付款金额不等于归属成本金额,不能导入数据!"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt1"));
			}
			// 拆分人编码
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
			}else{
				// 1 "拆分人编码为空,或者该拆分人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			info.setCreator(createUser);
			// 拆分时间 // 拆分时间录入不正确 格式为：YYYY-MM-DD
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_cfsjcw")).getTime()));
			// 审批人编码
			FilterInfo filterauditUser = new FilterInfo();
			filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
			EntityViewInfo viewauditUser = new EntityViewInfo();
			viewauditUser.setFilter(filterauditUser);
			UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
			if (auditUserColl.size() > 0){
				auditUser = auditUserColl.get(0);
				info.setAuditor(auditUser);
			} else {
				// 1 "审批人编码为空,或者该审批人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditorNameL21") + fAuditorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			// 审批时间 // 审批时间录入不正确 格式为：YYYY-MM-DD
			info.setAuditTime(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")));
			info.setAmount(new BigDecimal(fEntrysPayedAmt));
			info.setCompletePrjAmt(new BigDecimal(fEntrysPayedAmt));
			info.setPayAmount(new BigDecimal(fEntrysPayedAmt));
			info.setOriginalAmount(new BigDecimal(fEntrysPayedAmt));
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("contractId", contractWithoutText.getId()));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			ContractBaseDataCollection cbdc = ContractBaseDataFactory.getLocalInstance(context).getContractBaseDataCollection(view1);
			if (cbdc.size() > 0) {
				info.setContractBaseData(cbdc.get(0));
			}
			info.setState(FDCBillStateEnum.AUDITTED);	     // 单据状态：枚举值，保存、已审批。默认为已审批。
			info.setSplitState(CostSplitStateEnum.ALLSPLIT); // 拆分状态：枚举值，未拆分、完全拆分。默认为完全拆分。
			info.setHasEffected(false);
			info.setIsNeedTransit(true);
			info.setIslastVerThisPeriod(true);
			info.setHasInitIdx(true);  // HasInitIdx  HasInitIdx
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	/**
	 * 
	 * @description		无文本合同付款拆分分录 
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo transmitEntry(Hashtable hashtable, Context context) throws TaskExternalException {

		// 7.付款金额
		String fPaymentBillAmount = ((String) ((DataToken)hashtable.get("FPaymentBill_amount")).data).trim();
		// 8.归属财务核算对象代码
		String fEntrysCurProjectNumber = ((String) ((DataToken)hashtable.get("FEntrys_curProject_number")).data).trim();
		// 9.归属财务核算对象
		String fEntrysCurProjectNameL2  = ((String) ((DataToken)hashtable.get("FEntrys_curProject_name_l2")).data).trim();
		// 10.归属成本科目代码
		String fEntrysCostAccountNumber  = ((String) ((DataToken)hashtable.get("FEntrys_costAccount_number")).data).trim();
		// 11.归属成本科目
		String fEntrysCostAccountNameL2 = ((String) ((DataToken)hashtable.get("FEntrys_costAccount_name_l2")).data).trim();
		// 12.归属成本金额
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrys_payedAmt")).data).trim();
		// 13.归属产品<直接归属>
		String fEntrysProductNameL2 = ((String) ((DataToken)hashtable.get("FEntrys_product_name_l2")).data).trim();
		
		/**
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fEntrysCurProjectNumber)) {
			// "归属财务核算对象代码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCurProjectNumber"));
		}
		if (StringUtils.isEmpty(fEntrysCostAccountNumber)) {
			// "归属成本科目代码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCostAccountNumber"));
		}
		if (StringUtils.isEmpty(fEntrysProductNameL2)) {
			// 直接归属不能为空！
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysProductNameL2"));
		}
		
		/**
		 * 分录信息设值
		 */
		try {
			// 归属财务核算对象  fCurProjectNumber
			CurProjectInfo curProjectInfo = null;
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCurProjectNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// 归属财务核算对象代码在系统中不存在
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_bcz"));
			}
			// 查询归属成本科目代码  
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCostAccountNumber.replace('.', '!')));
			filtercostAccount.getFilterItems().add(new FilterItemInfo("curproject", curProjectInfo.getId().toString()));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
				costAccount.setCurProject(curProjectInfo);
			} else {
				// 1 "归属成本科目代码 "
				// 2 " 在系统中不存在"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCostAccountNumber1") + fEntrysCostAccountNumber + getResource(context, "Import_NOTNULL"));
			}
			// 查询归属产品<直接归属> 需求不明,不能理解(字段说明及约束：归属产品，各产品的拆分之和必须等于付款金额)
//			if (!StringUtils.isEmpty(fEntrysProductNameL2)) {
				FilterInfo filterproductType = new FilterInfo();
				filterproductType.getFilterItems().add(new FilterItemInfo("name", fEntrysProductNameL2));
				EntityViewInfo viewproductType = new EntityViewInfo();
				viewproductType.setFilter(filterproductType);
				ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(viewproductType);
				if (productTypeColl.size() > 0){
					productType = productTypeColl.get(0);
				} else {
					// 1 "直接归属 "
					// 2 " 在系统中不存在"
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysProductNameL21") + fEntrysProductNameL2 + getResource(context, "Import_NOTNULL"));
				}
//			}
			// 得到分录		<无需判断分录重复>
//			PaymentSplitEntryCollection entryColl = info.getEntrys();
//			if (entryColl.size() > 0) {
//				entryInfo = null;
//				for (int i = 0; i < entryColl.size(); i++) {
//					entryInfo = entryColl.get(i);
//					// 根据分录中的成本科目代码，归属产品以及归属金额来判断是否重复分录
//					// 根据成本科目代码ID查询成本科目对象
//					CostAccountCollection cac = CostAccountFactory.getLocalInstance(context).getCostAccountCollection("where id = '" + entryInfo.getCostAccount().getId().toString() + "'");
//					if (!StringUtils.isEmpty(fEntrysProductNameL2)) {
//						// 根据归属产品ID查询归属产品对象
//						ProductTypeCollection ptc = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection("where id = '" + entryInfo.getProduct().getId().toString() + "'");
//						if (cac.get(0).getLongNumber().equals(fEntrysCostAccountNumber) && ptc.get(0).getNumber().equals(fEntrysProductNameL2) && entryInfo.getAmount().compareTo(FDCTransmissionHelper.strToBigDecimal(fEntrysPayedAmt))==0) {
//							// 分录重复
//							throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
//						}
//					}
//					if (cac.get(0).getLongNumber().equals(fEntrysCostAccountNumber)  && entryInfo.getAmount().compareTo(FDCTransmissionHelper.strToBigDecimal(fEntrysPayedAmt))==0) {
//						// 分录重复
//						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
//					}
//					
//				}
//			}
			// 如果单头存在的情况下： 需要判断单头的付款金额是否等于归属成本金额，如果不等，则不允许录入！
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("id", info.getPaymentBill().getId()));
			EntityViewInfo view2 = new EntityViewInfo();
			view2.setFilter(filter2);
			PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view2);
			if (!(pbc.size() > 0)) {
				throw new TaskExternalException("");
			}
			if (!StringUtils.isEmpty(fPaymentBillAmount) && new BigDecimal(fPaymentBillAmount).compareTo(pbc.get(0).getActPayAmt()) != 0) {
				// 付款金额与付款单编码中对应的付款金额不相等！
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			BigDecimal bigD1 = new BigDecimal(fEntrysPayedAmt);
			BigDecimal bigD2 = pbc.get(0).getActPayAmt();
			if (bigD1.compareTo(bigD2) != 0) {
				// "付款金额不等于归属成本金额,不能导入数据!"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt1"));
			}
			entryInfo = new PaymentSplitEntryInfo();
			if (!StringUtils.isEmpty(fEntrysCurProjectNameL2)) {
				if (!curProjectInfo.getName().equals(fEntrysCurProjectNameL2)) {
					// 归属财务核算对象与归属财务核算对象代码中存在的归属财务核算对象不相同！
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmcbxt"));
				}
			}
			if (!StringUtils.isEmpty(fEntrysCostAccountNameL2)) {
				if (!costAccount.getName().equals(fEntrysCostAccountNameL2)) {
					// 归属成本科目与归属成本科目代码中对应的名称不相同！
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gscbkmbxt"));
				}
			}
			entryInfo.setCostAccount(costAccount);					// 设置归属成本科目代码
			entryInfo.setAmount(new BigDecimal(fEntrysPayedAmt));	// 归属成本金额
			entryInfo.setProduct(productType);						// 归属产品<直接归属>
			entryInfo.setIsLeaf(true);
			entryInfo.setLevel(0);
			entryInfo.setPayedAmt(new BigDecimal(fEntrysPayedAmt)); // 归属付款金额
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return entryInfo;
	}

	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
