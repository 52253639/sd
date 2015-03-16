/**
 * 
 */
package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
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
 * @description	合同付款拆分
 * @author			yongdingwen
 * @version		EAS7.0		
 * @createDate		2011-6-9	 
 * @see						
 */
public class PaymentSplitTransmission extends AbstractDataTransmission {
	
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";
	
	/** 单据与单据对应的所有分录归属成本金额集合的映射 */
	private Map arrMap = new HashMap();
	/** 加载的单头映射 */
	private Map infoMap = new HashMap();
	/** 分录归属成本科目集合*验证唯一性 */
	private Set costAccountSet = new HashSet();

	/** 合同付款拆分 */
	private PaymentSplitInfo info = new PaymentSplitInfo();
	/** 合同付款拆分分录 */
	private PaymentSplitEntryInfo entryInfo = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 合同 */
	private ContractBillInfo contractBill = null;
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
	/** 付款单金额 */
	private BigDecimal paymentBillAmount = null;
	/** 合同拆分科目映射 */
	private Map contractSplitAccountMap = new HashMap();
	
	/**
	 * 
	 * @description		
	 * @author			雍定文		
	 * @createDate		2011-7-12
	 * @param			context
	 * @return			ICoreBase		
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PaymentSplitFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * 
	 * @description		
	 * @author			雍定文		
	 * @createDate		2011-7-12
	 * @param			hastable
	 * @param			context
	 * @return			CoreBaseInfo
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
//		try {
//			info = transmitHead(hashtable, context);
//
//			if (info == null) {
//				return null;
//			}
//			PaymentSplitEntryInfo entry = transmitEntry(hashtable, context);
//			entry.setParent(info);
//			int seq = info.getEntrys().size() + 1;
//			entry.setSeq(seq);
//			entry.setIndex(seq);
//			info.getEntrys().add(entry);
//		} catch (TaskExternalException e) {
//			info = null;
//			throw e;
//		}
//		return info;

		setArrMap(hashtable, context);
		innerTransform(hashtable, context);
		return null;
	}
	
	/**
	 * 
	 * @description		合同付款拆分单头 
	 * @author				dingwen_yong		
	 * @createDate			2011-6-14
	 * @param 				hashtable
	 * @param 				context
	 * @return				PaymentSplitInfo
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {
		
		// 1.组织长编码
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// 2.工程项目编码
		String fCurProjectCodingNumber  = ((String) ((DataToken)hashtable.get("FCurProject_codingNumber")).data).trim();
		// 3.工程项目名称
		String fCurProjectNameL2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
		// 4.合同编码
		String fContractBillCodingNumber = ((String) ((DataToken)hashtable.get("FContractBill_codingNumber")).data).trim();
		// 5.合同名称
		String fContractBillName  = ((String) ((DataToken)hashtable.get("FContractBill_name")).data).trim();
		// 6.付款单编码
		String fPaymentBillNumber  = ((String) ((DataToken)hashtable.get("FPaymentBill_number")).data).trim();
		// 7.付款金额
		String fPaymentBillActPayAmt = ((String) ((DataToken)hashtable.get("FPaymentBill_actPayAmt")).data).trim();
		// 12.归属成本金额
		String fEntrysAmount = ((String) ((DataToken)hashtable.get("FEntrys_amount")).data).trim();
		// 14.拆分人编码
		String fCreateNumber  = ((String) ((DataToken)hashtable.get("FCreate_number")).data).trim();
		// 15.拆分时间
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 16.审批人编码
		String fPersonNumber = ((String) ((DataToken)hashtable.get("FPerson_number")).data).trim();
		// 17.审批时间
		String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		// 18.单据状态
		String fState = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		
		/**
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectCodingNumber)) {
			// "工程项目编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCurProjectCodingNumber"));
		}
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			// "合同编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fContractBillCodingNumber"));
		}
		if (StringUtils.isEmpty(fPaymentBillNumber)) {
			// "付款单编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillNumber"));
		}
		if (!StringUtils.isEmpty(fPaymentBillActPayAmt)) {
			if (!fPaymentBillActPayAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "付款金额录入不合法！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillActPayAmt"));
			}
		}
		if (!StringUtils.isEmpty(fEntrysAmount)) {
			if (!fEntrysAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "归属成本金额录入不合法！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount"));
			}
		} else {
			// "归属成本金额不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmountNOTNULL"));
		}
		if (StringUtils.isEmpty(fCreateNumber)) {
			// "拆分人编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateNumber"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "拆分时间不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateTime"));
		}
		if (StringUtils.isEmpty(fPersonNumber)) {
			// "审批人编码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPersonNumber"));
		}
		if (StringUtils.isEmpty(fAuditTime)) {
			// "审批时间不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fAuditTime"));
		}
		
		/** 如果已经构造了单头，则直接返回 */
		if (infoMap.get(fPaymentBillNumber) != null) {
			return (PaymentSplitInfo) infoMap.get(fPaymentBillNumber);
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
				// 判断当前的付款单编码是否为已审批状态或已付款状态
				if (!(paymentBill.getBillStatus().getName().equals("AUDITED") || paymentBill.getBillStatus().getName().equals("PAYED"))){
					// 当前所选付款单编码对象未审批或未付款
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
					if (info.getSplitState().equals("3ALLSPLIT")) {
						// 该合同对应的付款单已完全拆分！不能导入。
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_ghtbndr"));
					}
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
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// 组织长编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (ccouc.size() > 0) {
					costCenterOrgUnit = ccouc.get(0);
					if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
						// "组织长编码在工程项目所对应的成本中心不存在!"
	 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					}
					if (!StringUtils.isEmpty(fCurProjectNameL2) && !curProject.getName().equals(fCurProjectNameL2)) {
						// 工程项目名称与工程项目编码所对应的名称不相同！
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmccw"));
					}
				} else {
					// "组织长编码在工程项目所对应的成本中心不存在!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber1") + fCurProjectCodingNumber + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 获得合同拆分科目映射
			getContractSplitAccountMap(curProject.getId().toString(), context, contractSplitAccountMap);
			// 合同编码
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fContractBillCodingNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				if (!StringUtils.isEmpty(fContractBillName) && !fContractBillName.equals(contractBill.getName())) {
					// 合同名称对应合同编码中的名称不相同！
					throw new TaskExternalException(getResource(context, "htmcbxt"));
				}
				// if (contractBill.getSplitState().equals("3ALLSPLIT")) {
				// if (CostSplitStateEnum.NOSPLIT_VALUE.equals(contractBill.getSplitState().getValue())) {
				if (!CostSplitStateEnum.ALLSPLIT_VALUE.equals(contractBill.getSplitState().getValue())) {
					// 该合同未完全拆分，不能导入！
					throw new TaskExternalException(getResource(context, "PaymentSplit_Import_ghtwwqcf"));
				}
			}else{
				// 1 "合同编码不存在,或者该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fContractBillCodingNumber1") + fContractBillCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setContractBill(contractBill);
			if (!StringUtils.isEmpty(fPaymentBillActPayAmt) && new BigDecimal(fPaymentBillActPayAmt).compareTo(paymentBill.getActPayAmt()) != 0) {
				// 付款金额与付款单编码所对应的付款金额不相等！
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			ArrayList list=(ArrayList)arrMap.get(fPaymentBillNumber);
			BigDecimal bd = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				bd = bd.add((BigDecimal)list.get(i));
			}
			paymentBillAmount = paymentBill.getActPayAmt();
			if (bd.compareTo(paymentBillAmount) != 0) {
				// "付款金额不等于归属成本金额,不能导入数据!"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount1"));
			}
			info.setAmount(paymentBill.getActPayAmt());
			info.setCompletePrjAmt(paymentBill.getActPayAmt());
			info.setPayAmount(paymentBill.getActPayAmt());
			info.setOriginalAmount(paymentBill.getActPayAmt());
			// 拆分人编码
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreateNumber));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
			} else {
				// 1 "拆分人编码为空,或者该拆分人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateNumber1") + fCreateNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setCreator(createUser);
			// 拆分时间
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_cfsjcw")).getTime()));
			// 审批人编码
			FilterInfo filterauditUser = new FilterInfo();
			filterauditUser.getFilterItems().add(new FilterItemInfo("number", fPersonNumber));
			EntityViewInfo viewauditUser = new EntityViewInfo();
			viewauditUser.setFilter(filterauditUser);
			UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
			if (auditUserColl.size() > 0){
				auditUser = auditUserColl.get(0);
			} else {
				// 1 "审批人编码为空,或者该审批人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPersonNumber1") + fPersonNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setAuditor(auditUser);
			// 审批时间
			info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")).getTime()));
			// 单据状态:枚举值，保存、已审批，默认为已审批。
			if (fState.trim().equals(getResource(context,"SAVE"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else {
				info.setState(FDCBillStateEnum.AUDITTED);
			}
			info.setSplitState(CostSplitStateEnum.ALLSPLIT);	// 拆分状态：枚举值，未拆分、完全拆分。默认为完全拆分。
			info.setHasEffected(false);
			info.setIsNeedTransit(true);
			info.setIslastVerThisPeriod(true);
			info.setHasInitIdx(true);  // HasInitIdx  HasInitIdx
			info.setIsColsed(true);    //isclosed  isclosed
			info.setFivouchered(true);
			infoMap.put(fPaymentBillNumber, info);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}

	/**
	 * 
	 * @description		合同付款拆分分录 
	 * @author				dingwen_yong		
	 * @createDate			2011-6-14
	 * @param 				hashtable
	 * @param 				context
	 * @return				PaymentSplitEntryInfo
	 * @throws 			TaskExternalException PaymentSplitEntryInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo transmitEntry (Hashtable hashtable, Context context) throws TaskExternalException {

		// 7.付款金额
		String fPaymentBillActPayAmt = ((String) ((DataToken)hashtable.get("FPaymentBill_actPayAmt")).data).trim();
		// 8.归属财务核算对象代码
		String fEntrysCurProjectCodingNumber = ((String) ((DataToken)hashtable.get("FEntrys_curProject_codingNumber")).data).trim();
		// 9.归属财务核算对象
		String fEntrysCurProjectNameL2  = ((String) ((DataToken)hashtable.get("FEntrys_curProject_name_l2")).data).trim();
		// 10.归属成本科目代码
		String fCostAccountLongNumber  = ((String) ((DataToken)hashtable.get("FCostAccount_longNumber")).data).trim();
		// 11.归属成本科目
		String fCostAccountNameL2 = ((String) ((DataToken)hashtable.get("FCostAccount_name_l2")).data).trim();
		// 12.归属成本金额
		String fEntrysAmount = ((String) ((DataToken)hashtable.get("FEntrys_amount")).data).trim();
		// 13.归属付款金额
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrysPayedAmt")).data).trim();
		
		/**
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fEntrysCurProjectCodingNumber)) {
			// "归属财务核算对象代码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysCurProjectCodingNumber"));
		}
		if (StringUtils.isEmpty(fCostAccountLongNumber)) {
			// "归属成本科目代码不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber"));
		}
		if (StringUtils.isEmpty(fEntrysPayedAmt)) {
			// "归属付款金额不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_gsfkje"));
		} else {
			if (!fEntrysPayedAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "归属付款金额录入不合法！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_gsfkjecw"));
			}
		}
		
		/**
		 * 分录信息设值
		 */
		try {
			// 归属财务核算对象  fCurProjectNumber
			CurProjectInfo curProjectInfo = null;
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// 归属财务核算对象代码在系统中不存在
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_bcz"));
			}
			// 归属成本科目代码  
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fCostAccountLongNumber.replace('.', '!')));
			filtercostAccount.getFilterItems().add(new FilterItemInfo("curproject", curProjectInfo.getId().toString()));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
				costAccount.setCurProject(curProjectInfo);
			} else {
				// 1 "归属成本科目编码为空,或者该归属成本科目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber1") + fCostAccountLongNumber + getResource(context, "Import_NOTNULL"));
			}
			// 得到分录		<不需要判断分录重复>
			PaymentSplitEntryCollection entryColl = info.getEntrys();
			if (entryColl.size() > 0) {
				entryInfo = null;
				for (int i = 0; i < entryColl.size(); i++) {
					entryInfo = entryColl.get(i);
					// 如果分录存在时 比较归属财务核算对象代码与归属成本科目代码是否相同！
					if (entryInfo.getCostAccount().getId().equals(costAccount.getId())) {
						// 归属成本科目代码对象
						FilterInfo filtercostA1 = new FilterInfo();
						filtercostA1.getFilterItems().add(new FilterItemInfo("id", entryInfo.getCostAccount().getId()));
						EntityViewInfo viewcostA1 = new EntityViewInfo();
						viewcostA1.setFilter(filtercostA1);
						CostAccountCollection costA1Coll = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostA1);
						if (costA1Coll.size() > 0){
							// 归属财务核算对象
							FilterInfo curProjectF = new FilterInfo();
							curProjectF.getFilterItems().add(new FilterItemInfo("id", costA1Coll.get(0).getCurProject().getId()));
							EntityViewInfo curProjectE = new EntityViewInfo();
							curProjectE.setFilter(curProjectF);
							CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectE);
							if(curProjectColl.size() > 0) {
								if (curProjectColl.get(0).getId().equals(curProjectInfo.getId())){
									// 分录重复
									throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
								}
							} else {
								// 如果分录存在，则不可能会执行到else中的内容！
							}
						} else {
							// 如果分录存在，则不可能会执行到else中的内容！
						}
					}
				}
			}
//			// 如果单头存在的情况下： 需要判断单头的付款金额是否等于归属成本金额，如果不等，则不允许录入！
//			FilterInfo filter1 = new FilterInfo();
//			filter1.getFilterItems().add(new FilterItemInfo("id", info.getPaymentBill().getId()));
//			EntityViewInfo view1 = new EntityViewInfo();
//			view1.setFilter(filter1);
//			PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view1);
//			if (!(pbc.size() > 0)) {
//				throw new TaskExternalException("");
//			}
//			if (!StringUtils.isEmpty(fPaymentBillActPayAmt) && new BigDecimal(fPaymentBillActPayAmt).compareTo(pbc.get(0).getActPayAmt()) != 0) {
//				// 付款金额与付款单编码中对应的付款金额不相等！
//				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
//			}
			entryInfo = new PaymentSplitEntryInfo();
			if (!StringUtils.isEmpty(fEntrysCurProjectNameL2)) {
				if (!curProjectInfo.getName().equals(fEntrysCurProjectNameL2)) {
					// 归属财务核算对象与归属财务核算对象代码中存在的归属财务核算对象不相同！
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmcbxt"));
				}
			}
			if (!StringUtils.isEmpty(fCostAccountNameL2)) {
				if (!costAccount.getName().equals(fCostAccountNameL2)) {
					// 归属成本科目与归属成本科目代码中对应的名称不相同！
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gscbkmbxt"));
				}
			}
			
			entryInfo.setCostAccount(costAccount);						 // 设置成本科目对象
			// entryInfo.setAmount(new BigDecimal(fEntrysAmount));			 // 归属成本金额
			entryInfo.setAmount(paymentBillAmount);			 	 		 // 付款单金额
			entryInfo.setPayedAmt(new BigDecimal(fEntrysPayedAmt));		 // 归属付款金额
			entryInfo.setIsLeaf(true);
			entryInfo.setLevel(0);
//			entryInfo.setDirectAmount(new BigDecimal(fEntrysPayedAmt));  // 直接付款金额
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return entryInfo;
	}
	
	/**
	 * 
	 * @description	集合映射设置
	 * @author			雍定文	
	 * @createDate		2011-7-12
	 * @param			hashtable
	 * @param 			context
	 * @throws 		TaskExternalException void
	 * @version		EAS1.0
	 * @see
	 */
	private void setArrMap(Hashtable hashtable, Context context) throws TaskExternalException {
		for (int i = 0; i < hashtable.size(); i++) {
			Hashtable lineData = (Hashtable) hashtable.get(new Integer(i));
			// 付款单编码
			String paymentBillNumber = ((String)((DataToken) lineData.get("FPaymentBill_number")).data).trim();
			// 归属成本科目代码
			String costAccountLongNumber = ((String)((DataToken) lineData.get("FCostAccount_longNumber")).data).trim();
			// 归属成本金额
			String entrysAmount = ((String)((DataToken) lineData.get("FEntrys_amount")).data).trim();
			
			/**
			 * 必录，非法录入 <判断>
			 */
			if (StringUtils.isEmpty(paymentBillNumber)) {
				// "付款单编码不能为空！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillNumber"));
			}
			if (StringUtils.isEmpty(costAccountLongNumber)) {
				// "归属成本科目代码不能为空！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber"));
			}
			if (!StringUtils.isEmpty(entrysAmount)) {
				if (!entrysAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
					// "归属成本金额录入不合法！"
					throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount"));
				}
			} else {
				// "归属成本金额不能为空！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmountNOTNULL"));
			}
			
			if(!costAccountSet.add(paymentBillNumber+costAccountLongNumber)){
				// 分录重复！
				FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
			}
			
			if (arrMap.get(paymentBillNumber) == null) {
				arrMap.put(paymentBillNumber, new ArrayList());
			}

			BigDecimal decimal = new BigDecimal(entrysAmount);
			((ArrayList) arrMap.get(paymentBillNumber)).add(decimal);
			
		}
	}
	
	/**
	 * 
	 * @description	内调转换处理方法	
	 * @author			雍定文	
	 * @createDate		2011-7-12
	 * @param			hashtable
	 * @param 		    context
	 * @return			CoreBaseInfo
	 * @throws 		TaskExternalException CoreBaseInfo
	 * @version		EAS1.0
	 * @see
	 */
	protected CoreBaseInfo innerTransform(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			for (int i = 0; i < hashtable.size(); i++) {
				Hashtable lineData = (Hashtable) hashtable.get(new Integer(i));
				info = innerTransformHead(lineData, context);
				PaymentSplitEntryInfo entry = innerTransformEntry(lineData, context);
				int seq = info.getEntrys().size() + 1;
				entry.setSeq(seq);
				entry.setParent(info);
				info.getEntrys().add(entry);
			}
			// 遍历infoMAP
			Collection coll = infoMap.values();
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				PaymentSplitInfo info = (PaymentSplitInfo) it.next();
				// 工程量拆分科目与合同拆分科目一致性校验
				HashMap contractSplitAccountMapTemp = (HashMap)contractSplitAccountMap.get(info.getCurProject().getId().toString() + info.getContractBill().getId().toString());
				if (contractSplitAccountMapTemp == null) {
					FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplit_Import_ContractSplitNotExist"));
				} else {
					PaymentSplitEntryCollection entrys = info.getEntrys();
					for (int i = 0; i < entrys.size(); i++) {
						PaymentSplitEntryInfo entry = entrys.get(i);
						ContractCostSplitEntryInfo  contractCostSplitEntryInfo = (ContractCostSplitEntryInfo)contractSplitAccountMapTemp.get(entry.getCostAccount().getId().toString());
						if (contractCostSplitEntryInfo == null) {
							FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplit_Import_ContractSplitNotExist"));
						}
					}
				}
				if (info.getId() == null || !getController(context).exists(new ObjectUuidPK(info.getId())))
					getController(context).save(info);
				else
					getController(context).update(new ObjectUuidPK(info.getId()), info);
			}
		}catch (TaskExternalException e) {
			info = null;
			throw e;
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	
	/**
	 * 
	 * @description	单据头*转换处理方法
	 * @author			雍定文	
	 * @createDate		2011-7-12
	 * @param 			hashtable
	 * @param 			context
	 * @return			PaymentSplitInfo
	 * @throws 		TaskExternalException PaymentSplitInfo
	 * @version		EAS1.0
	 * @see
	 */
	private PaymentSplitInfo innerTransformHead(Hashtable hashtable, Context context) throws TaskExternalException {
		return this.transmitHead(hashtable, context);
	}
	
	
	/**
	 * 
	 * @description	分录*转换处理方法	
	 * @author			雍定文	
	 * @createDate		2011-7-12
	 * @param 			hashtable
	 * @param 			context
	 * @return			PaymentSplitEntryInfo
	 * @throws 		TaskExternalException PaymentSplitEntryInfo
	 * @version		EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo innerTransformEntry(Hashtable hashtable, Context context) throws TaskExternalException {
		return this.transmitEntry(hashtable, context);
	}
	
	/** 
	 * 覆写父类方法
	 */
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData){
		return firstData != null && currentData != null ;
	}
	
	/**
	 * 设置一次性加载数据
	 */
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
	private void getContractSplitAccountMap(String curProjectID, Context ctx, Map contractSplitAccountMap) throws TaskExternalException{
		try {
			Map contractCostSplitEntryMap = new HashMap();
			ContractCostSplitInfo contractCostSplitInfo = null;
			ContractCostSplitEntryInfo contractCostSplitEntryInfo = null;
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ContractCostSplitCollection info = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
			for (int i = 0; i < info.size(); i++) {
				contractCostSplitInfo = info.get(i);
				ContractCostSplitEntryCollection contractCostSplitEntrys = contractCostSplitInfo.getEntrys();
				for (int j = 0; j < contractCostSplitEntrys.size(); j++) {
					contractCostSplitEntryInfo = contractCostSplitEntrys.get(j);
					contractCostSplitEntryMap.put(contractCostSplitEntryInfo.getCostAccount().getId().toString(), contractCostSplitEntryInfo);
				}
				contractSplitAccountMap.put(curProjectID + contractCostSplitInfo.getContractBill().getId().toString(), contractCostSplitEntryMap);
			}
			return ;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
}
