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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
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
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-14	 
 * @see						
 */
public class DeductBillTransmission extends AbstractDataTransmission {
	
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";
	
	/** 扣款单 */
	private DeductBillInfo info = null;
	/** 扣款单分录 */
	private DeductBillEntryInfo entryInfo = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 制单人 */
	private UserInfo createUser = null;
	/** 审批人 */
	private UserInfo auditUser = null;
	/** 合同 */
	private ContractBillInfo contractBill = null;
	/** 扣款单位 */
	private SupplierInfo supplier = null;
	/** 扣款类型 */
	private DeductTypeInfo deductType = null;
	/** 币别 */
	private CurrencyInfo currency = null;
	private CurrencyCollection currencyCollection = null;
	
	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = DeductBillFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			info = transmitHead(hashtable, context);
			if (info == null) {
				return null;
			}
			DeductBillEntryInfo entry = transmitEntry(hashtable, context);
            entry.setParent(info);
            int seq = info.getEntrys().size() + 1;
            entry.setSeq(seq);
            info.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}
	
	/**
	 * 
	 * @description		扣款单单头
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException DeductBillInfo
	 * @version			EAS1.0
	 * @see
	 */
	private DeductBillInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {
		/**
		 * 获取Excel文本中对应的值
		 */
		// 1.组织编码 
		String fOrgUnitNumber = ((String) ((DataToken) hashtable.get("FOrgUnit_number")).data).trim();
		// 2.工程项目编码
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.编码
		String fNumber = ((String) ((DataToken) hashtable.get("FNumber")).data).trim();
		// 4.名称
		String fName = ((String) ((DataToken) hashtable.get("FName")).data).trim();
		// 5.状态
		String fState = ((String) ((DataToken) hashtable.get("FState")).data).trim();
		// 6.制单人编码
		String fCreatorNameL2 = ((String) ((DataToken) hashtable.get("FCreator_name_l2")).data).trim();
		// 7.制单时间
		String fCreateTime = ((String) ((DataToken) hashtable.get("FCreateTime")).data).trim();
		// 8.审批人编码
		String fAuditorNameL2 = ((String) ((DataToken) hashtable.get("FAuditor_name_l2")).data).trim();
		// 9.审核时间
		String fAuditTime = ((String) ((DataToken) hashtable.get("FAuditTime")).data).trim();

		/**
		 * 判断部分字段是否为空
		 */
		if (!StringUtils.isEmpty(fOrgUnitNumber)) {
			if (fOrgUnitNumber.length() > 40) {
				// "组织编码字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fOrgUnitNumber"));
			}
		}
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 40) {
				// "工程项目编码字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber"));
			}
		} else {
			// "工程项目编码不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "编码字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fNumber"));
			}
		} else {
			// "编码不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "名称字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fName"));
			}
		} else {
			// "名称不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fNameNotNull"));
		}
		
		/**
		 * 将值存入对象中
		 */
		try {
			info = this.getDeductBillInfoFromNumber(fNumber,context);
			if (info != null) {
				return info;
			} else {
				info = new DeductBillInfo();
			}
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
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "组织长编码在工程项目所对应的成本中心不存在!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					// 原转换组织对象方法 <暂不推荐使用> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 需要判断编码和名称不能重复
			FilterInfo filter5 = new FilterInfo();
			filter5.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo view5 = new EntityViewInfo();
			view5.setFilter(filter5);
			DeductBillCollection dbc = DeductBillFactory.getLocalInstance(context).getDeductBillCollection(view5);
			if (dbc.size() > 0) {
				// 扣款单编码重复！
				throw new TaskExternalException(getResource(context, "DeductBill_Import_bmcf"));
			}
			filter5 = new FilterInfo();
			filter5.getFilterItems().add(new FilterItemInfo("name", fName));
			view5 = new EntityViewInfo();
			view5.setFilter(filter5);
			dbc = DeductBillFactory.getLocalInstance(context).getDeductBillCollection(view5);
			if (dbc.size() > 0) {
				// 扣款单名称重复！
				throw new TaskExternalException(getResource(context, "DeductBill_Import_mccf"));
			}
			// 编码  
			info.setNumber(fNumber);
			// 名称
			info.setName(fName);
			// 状态
			if (fState.equals(getResource(context, "SAVE"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// 审批人 <"状态"字段需求文档中不存在,需要修改需求文档添加该字段>
				if (!StringUtils.isEmpty(fAuditorNameL2)) {
					FilterInfo filterauditUser = new FilterInfo();
					filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
					EntityViewInfo viewauditUser = new EntityViewInfo();
					viewauditUser.setFilter(filterauditUser);
					UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
					if (auditUserColl.size() > 0){
						auditUser = auditUserColl.get(0);
						info.setAuditor(auditUser);
					} else {
						// 1 "审批人编码 "
						// 2 " 在系统中不存在！"
						throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditorNameL2") + fAuditorNameL2 + getResource(context, "DeductBill_Import_NOTNULL"));
					}
				} else {
					// "在已审批状态下的审批人字段不能为空！"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditorNameL2NotNull"));
				}
				//审批时间
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")).getTime()));
				} else {
					// "在已审批状态下的审批日期字段不能为空！"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditTime"));
				}
			} else {
				// 1 "状态 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fState") + fState + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 制单人编码
			if (!StringUtils.isEmpty(fCreatorNameL2)) {
				FilterInfo filtercreateUser = new FilterInfo();
				filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
				EntityViewInfo viewcreateUser = new EntityViewInfo();
				viewcreateUser.setFilter(filtercreateUser);
				UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
				if (createUserColl.size() > 0){
					createUser = createUserColl.get(0);
					info.setCreator(createUser);
				}else{
					// 1 "制单人编码为空,或者制制单人编码 "
					// 2 " 在系统中不存在！"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "DeductBill_Import_NOTNULL"));
				}
			}
			// 制单时间 // 制单时间录入不正确 格式为：YYYY-MM-DD
			if (!StringUtils.isEmpty(fCreateTime)) {
				info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_zdsjcw")).getTime()));
			}
			info.setConTypeBefContr(false);
			info.setSourceType(SourceTypeEnum.IMP);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	/**
	 * 
	 * @description		扣款单分录
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException DeductBillEntryInfo
	 * @version			EAS1.0
	 * @see
	 */
	private DeductBillEntryInfo transmitEntry(Hashtable hashtable, Context context) throws TaskExternalException {
		
		/**
		 * 得到Excel中分录信息
		 */
		// 10.合同编码
		String fEntrysContractId = ((String) ((DataToken) hashtable.get("FEntrys_contractId")).data).trim();
		// 11.扣款单位
		String fDeductUnitNameL2 = ((String) ((DataToken) hashtable.get("FDeductUnit_name_l2")).data).trim();
		// 12.扣款类型
		String fDeductTypeNameL2 = ((String) ((DataToken) hashtable.get("FDeductType_name_l2")).data).trim();
		// 13.扣款事项
		String fEntrysDeductItem = ((String) ((DataToken) hashtable.get("FEntrys_deductItem")).data).trim();
		// 14.币别编码
		String fCurrencyIDNumber = ((String) ((DataToken) hashtable.get("FCurrencyID_number")).data).trim();
		// 15.扣款金额
		String fEntrysDeductAmt = ((String) ((DataToken) hashtable.get("FEntrys_deductAmt")).data).trim();
		// 16.扣款日期
		String fEntrysDeductDate = ((String) ((DataToken) hashtable.get("FEntrys_deductDate")).data).trim();
		// 17.备注
		String fEntrysRemark = ((String) ((DataToken) hashtable.get("FEntrys_remark")).data).trim();
		
		/**
		 * 判断部分字段不能为空
		 */
		if (!StringUtils.isEmpty(fEntrysContractId)) {
			if (fEntrysContractId.length() > 40) {
				// "合同编码编码字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractId"));
			}
		} else {
			// "合同编码不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractIdNotNull"));
		}
		if (!StringUtils.isEmpty(fDeductUnitNameL2)) {
			if (fDeductUnitNameL2.length() > 40) {
				// "扣款单位字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL2"));
			}
		} else {
			// "扣款单位不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fDeductTypeNameL2)) {
			if (fDeductTypeNameL2.length() > 40) {
				// "扣款类型字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL2"));
			}
		} else {
			// "扣款类型不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysDeductItem) && fEntrysDeductItem.length() > 40) {
			// "扣款事项字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductItem"));
		}
		if (!StringUtils.isEmpty(fCurrencyIDNumber) && fCurrencyIDNumber.length() > 40) {
			// "币别编码字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurrencyIDNumber"));
		}
		if (!StringUtils.isEmpty(fEntrysDeductAmt)) {
			if (!fEntrysDeductAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "扣款金额录入不合法！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductAmt"));
			}
		} else {
			// "扣款金额不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductAmtNotNull"));
		}
		if (StringUtils.isEmpty(fEntrysDeductDate)) {
			// "扣款日期不能为空！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductDate"));
		}
		if (!StringUtils.isEmpty(fEntrysRemark) && fEntrysRemark.length() > 40) {
			// "备注字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysRemark"));
		}
		
		/**
		 * 分录信息设值
		 */
		try {
			// 已经确认分录可以重复输入，因此不用判断分录重复
			entryInfo = new DeductBillEntryInfo();
			// 合同编码
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fEntrysContractId));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				entryInfo.setContractId(contractBill.getId().toString());  // 设置合同编码
			}else{
				// 1 "合同编码为空,或者该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractId1") + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 扣款单位
			FilterInfo filtersupplier = new FilterInfo();
			filtersupplier.getFilterItems().add(new FilterItemInfo("name", fDeductUnitNameL2));
			EntityViewInfo viewsupplier = new EntityViewInfo();
			viewsupplier.setFilter(filtersupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewsupplier);
			if (supplierColl.size() > 0){
				supplier = supplierColl.get(0);
				entryInfo.setDeductUnit(supplier);	// 设置扣款单位
			}else{
				// 1 "该扣款单位 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL21")  + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 扣款类型
			FilterInfo filterdeductType = new FilterInfo();
			filterdeductType.getFilterItems().add(new FilterItemInfo("name", fDeductTypeNameL2));
			EntityViewInfo viewdeductType = new EntityViewInfo();
			viewdeductType.setFilter(filterdeductType);
			DeductTypeCollection deductTypeColl = DeductTypeFactory.getLocalInstance(context).getDeductTypeCollection(viewdeductType);
			if (deductTypeColl.size() > 0){
				deductType = deductTypeColl.get(0);
				entryInfo.setDeductType(deductType);	// 设置扣款类型
			}else{
				// 1 "该扣款类型 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL21")+ getResource(context, "DeductBill_Import_NOTNULL"));
			}
			
			// 扣款事项
			entryInfo.setDeductItem(fEntrysDeductItem);
			// 币别编码  扣款单中美有直接存放币种的字段。因此只需要判断币种编码是否存在
//			contractBill.getCurrency();	// 币别来源于合同
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			if (StringUtils.isEmpty(fCurrencyIDNumber)) {
				// 如果币别编码输入为空,则设置默认值
				filter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("number", fCurrencyIDNumber));
			}
			view.setFilter(filter);
			currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(view);
			if (!(currencyCollection.size() > 0)) {
				// 1 "该币别编码"
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurrencyIDNumber1") + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// 扣款金额
			entryInfo.setDeductAmt(new BigDecimal(fEntrysDeductAmt));
			// 扣款日期 // 扣款日期录入不正确 格式为：YYYY-MM-DD
			entryInfo.setDeductDate(new Timestamp(FDCTransmissionHelper.checkDateFormat(fEntrysDeductDate, getResource(context, "DeductBill_Import_kkrqcw")).getTime()));
			// 备注
			entryInfo.setRemark(fEntrysRemark);
			entryInfo.setHasApplied(false);
			entryInfo.setDeductOriginalAmt(new BigDecimal(fEntrysDeductAmt));
			entryInfo.setExRate(contractBill.getExRate()); // 汇率在模板中没有给出字段，但汇率源于合同，因此设置汇率为合同汇率
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return entryInfo;
	}
	
	
	/**
	 * 根据number获取id，如果没有则返回null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author 
	 * @throws EASBizException 
	 */
	private DeductBillInfo getDeductBillInfoFromNumber(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number,CompareType.EQUALS));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			DeductBillCollection deductBillCollection = DeductBillFactory.getLocalInstance(ctx).getDeductBillCollection(view);

			if (deductBillCollection.size() > 0){
				DeductBillInfo info = deductBillCollection.get(0);
			
				if (info != null) {
					return info;
				}
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
