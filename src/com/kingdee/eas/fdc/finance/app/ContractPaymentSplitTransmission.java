/**
 * 
 */
package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
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
public class ContractPaymentSplitTransmission extends AbstractDataTransmission {
	
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";

	// 引入工具类
	private FDCTransmissionHelper help = new FDCTransmissionHelper();
	
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
			for (int i = 0; i < hashtable.size(); i++) {
				Hashtable lineData = (Hashtable) hashtable.get(new Integer(i));
				// 当游标为0，表示第一条记录，此时要拼凑一个表头来
				if (i == 0) {
					info = transmitHead(lineData, context);
				}
				if (info == null) {
					return null;
				}
				PaymentSplitEntryInfo entry = transmitEntry(lineData, context);
	            entry.setParent(info);
	            int seq = info.getEntrys().size() + 1;
	            entry.setSeq(seq);
	            info.getEntrys().add(entry);
			}
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}
	
	/**
	 * 
	 * @description		合同付款拆分单头 
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
			if (!fPaymentBillActPayAmt.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
				// "付款金额录入不合法！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillActPayAmt"));
			}
		} else {
			// "付款金额不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillActPayAmtNOTNULL"));
		}
		if (!StringUtils.isEmpty(fEntrysAmount)) {
			if (!fPaymentBillActPayAmt.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
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
		
		/**
		 * 将值存入对象中 
		 */
		try { 
			// 工程项目编码
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// 组织长编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); // 工程项目对应成本中心组织
				if (!StringUtils.isEmpty(fOrgUnitNumber) && fOrgUnitNumber.equals(costCenterOrgUnit.getLongNumber())) {
//					throw new TaskExternalException("组织长编码在工程项目所对应的成本中心不存在!");
					// 原转换组织对象方法 <暂不推荐使用> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
					info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
				}
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCurProjectCodingNumber1") + fCurProjectCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			// 合同编码
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fContractBillCodingNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
			}else{
				// 1 "合同编码不存在,或者该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fContractBillCodingNumber1") + fContractBillCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setContractBill(contractBill);
			// 付款单编码 
			FilterInfo filterpaymentBill = new FilterInfo();
			filterpaymentBill.getFilterItems().add(new FilterItemInfo("number", fPaymentBillNumber));
			EntityViewInfo viewpaymentBill = new EntityViewInfo();
			viewpaymentBill.setFilter(filterpaymentBill);
			PaymentBillCollection paymentBillColl = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(viewpaymentBill);
			if (paymentBillColl.size() > 0){
				paymentBill = paymentBillColl.get(0);
			}else{
				// 1 "付款单编码不存在,或者该付款单编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillNumber1") + fPaymentBillNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setPaymentBill(paymentBill);
			if (!help.strToBigDecimal(paymentBill.getActPayAmt().toString()).equals(help.strToBigDecimal(fEntrysAmount))) {
				// "付款金额不等于归属成本金额,不能导入数据!"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount1"));
			}
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
			info.setCreateTime(new Timestamp(help.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_cfsjcw")).getTime()));
			// 审批人编码
			FilterInfo filterauditUser = new FilterInfo();
			filterauditUser.getFilterItems().add(new FilterItemInfo("number", fPersonNumber));
			EntityViewInfo viewauditUser = new EntityViewInfo();
			viewauditUser.setFilter(filterauditUser);
			UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
			if (auditUserColl.size() > 0){
				createUser = auditUserColl.get(0);
			} else {
				// 1 "审批人编码为空,或者该审批人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPersonNumber1") + fPersonNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setCreator(auditUser);
			// 审批时间
			info.setCreateTime(new Timestamp(help.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")).getTime()));
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}

	/**
	 * 
	 * @description		合同付款拆分分录 
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException PaymentSplitEntryInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo transmitEntry (Hashtable hashtable, Context context) throws TaskExternalException {
		entryInfo = new PaymentSplitEntryInfo();
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
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrys_payedAmt")).data).trim();
		
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
		if (!StringUtils.isEmpty(fEntrysPayedAmt)) {
			if (!fEntrysPayedAmt.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
				// "归属付款金额录入不合法！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysPayedAmt"));
			}
		} else {
			// "归属付款金额不能为空！"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysPayedAmtNOTNULL"));
		}
		
		/**
		 * 分录信息设值
		 */
		try {
			// 归属财务核算对象代码   <工程项目>在单头中已经设置了参数
			// 归属成本科目代码  
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fCostAccountLongNumber.replace('.', '!')));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
				entryInfo.setCostAccount(costAccount);
			} else {
				// 1 "归属成本科目编码为空,或者该归属成本科目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber1") + fCostAccountLongNumber + getResource(context, "Import_NOTNULL"));
			}
			// 归属成本金额
			entryInfo.setAmount(new BigDecimal(fEntrysAmount));
			// 归属付款金额
			entryInfo.setPayedAmt(new BigDecimal(fEntrysPayedAmt));
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return entryInfo;
	}
	
	//设置一次性加载数据
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof PaymentSplitInfo == false) {
			return;
		}
		try {
			PaymentSplitInfo billBase = (PaymentSplitInfo) coreBaseInfo;
			String id = this.getIdFromNumber(billBase.getNumber(), ctx);
			if (StringUtil.isEmptyString(id)) {
				getController(ctx).addnew(coreBaseInfo);
			} else {
				coreBaseInfo.setId(BOSUuid.read(id));
				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
			}

		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
	
	/**
	 * 根据number获取id，如果没有则返回null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException 
	 */
	private String getIdFromNumber(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number,CompareType.IS));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(view);

			if (paymentSplitCollection.size() > 0){
				PaymentSplitInfo info = paymentSplitCollection.get(0);
			
				if (info != null) {
					return info.getId().toString();
				}
			}else{
				// "对象找不到！"
				throw new TaskExternalException(getResource(ctx, "Import_getIdFromNumber"));
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
