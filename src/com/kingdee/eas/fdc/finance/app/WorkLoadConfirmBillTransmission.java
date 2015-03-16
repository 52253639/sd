package com.kingdee.eas.fdc.finance.app;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 
 * 描述： 		工程量确认单引入引出数据转换类
 * 
 * @author 王川
 * @createDate 2011-6-13
 */
public class WorkLoadConfirmBillTransmission extends AbstractDataTransmission {

	WorkLoadConfirmBillInfo info = null;

	WorkLoadConfirmBillRelTaskInfo infoEntry = null;

	ContractBillInfo contractBillInfo = null;

	private static String resource = "com.kingdee.eas.fdc.finance.FinanceTransmissionResource";
	
//	static Map stateMap = new HashedMap();
//	
//	static {
//		stateMap.put("保存", "1SAVED");
//		stateMap.put("已提交", "2SUBMITTED");
//		stateMap.put("已审批", "4AUDITTED");
//	}

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			infoEntry = transmitEntry(hsData, ctx);
			if(infoEntry != null) {				
				int seq = info.getReftaskentry().size() + 1;
				infoEntry.setSeq(seq);
				infoEntry.setParent(info);
				info.getReftaskentry().add(infoEntry);
			}
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}

		return info;
	}

	/**
	 * 根据number获取WorkLoadConfirmBillInfo ，如果没有则返回null
	 * 
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException
	 */
	private WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(String number, String contractBillId, 
			Context ctx) throws TaskExternalException {
		try {
			FilterInfo workLoadConfirmBillFilter = new FilterInfo();
			workLoadConfirmBillFilter.getFilterItems().add(new FilterItemInfo("number", number));
			workLoadConfirmBillFilter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBillId));
			workLoadConfirmBillFilter.setMaskString("#0 and #1");
			EntityViewInfo workLoadConfirmBillView = new EntityViewInfo();
			workLoadConfirmBillView.setFilter(workLoadConfirmBillFilter);
			WorkLoadConfirmBillCollection info = WorkLoadConfirmBillFactory
					.getLocalInstance(ctx).getWorkLoadConfirmBillCollection(
							workLoadConfirmBillView);
			if (info.get(0) != null) {
				return info.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}

	private WorkLoadConfirmBillInfo transmitHead(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// 组织编码
		String costOrg_longNumber = FDCTransmissionHelper.getFieldValue(hsData,
				"FCurProject$costOrg_longNumber");
		// 工程项目编码
		String fcurProjectLongNumber = FDCTransmissionHelper.getFieldValue(
				hsData, "FCurProject_longNumber");
		// 合同编码
		String fcontractNumber = FDCTransmissionHelper.getFieldValue(hsData,
				"FContractBill_number");
		// 乙方
		String partBname = FDCTransmissionHelper.getFieldValue(hsData,
				"FContractBill$partB_name_l2");
		// 业务日期
		String fbizDate = FDCTransmissionHelper.getFieldValue(hsData,
				"FBizDate");
		// 工程量确认单编码
		String fnumber = FDCTransmissionHelper.getFieldValue(hsData, "FNumber");
		// 确认工程量
		String fworkLoad = FDCTransmissionHelper.getFieldValue(hsData,
				"FWorkLoad");
		// 确认日期
		String fconfirmDate = FDCTransmissionHelper.getFieldValue(hsData,
				"FConfirmDate");
		// 状态
		String fstate = FDCTransmissionHelper.getFieldValue(hsData, "FState");
		// 备注
		String fdescription = FDCTransmissionHelper.getFieldValue(hsData,
				"FDescription");
		// 制单人编码
		String fcreatorNumber = FDCTransmissionHelper.getFieldValue(hsData,
				"FCreator_number");
		// 制单日期
		String fcreateTime = FDCTransmissionHelper.getFieldValue(hsData,
				"FCreateTime");
		// 审批人编码
		String fauditorNumber = FDCTransmissionHelper.getFieldValue(hsData,
				"FAuditor_number");
		// 审批日期
		String fauditTime = FDCTransmissionHelper.getFieldValue(hsData,
				"FAuditTime");

		String stateStr = this.getStateStr(fstate, ctx);

		/**
		 * 必录项校验
		 */
		if (StringUtils.isEmpty(fcurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"CurProjectLongNumberNotNull"));
		}
		if (StringUtils.isEmpty(fcontractNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"ContractBillCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fbizDate)) {
			throw new TaskExternalException(getResource(ctx, "BizDateNotNull"));
		}
		if (StringUtils.isEmpty(fnumber)) {
			throw new TaskExternalException(getResource(ctx, "NumberNotNull"));
		}
		if (StringUtils.isEmpty(fworkLoad)) {
			throw new TaskExternalException(getResource(ctx, "WorkLoadNotNull"));
		}
		if (StringUtils.isEmpty(fconfirmDate)) {
			throw new TaskExternalException(getResource(ctx,
					"ConfirmDateNotNull"));
		}
		if (StringUtils.isEmpty(fcreatorNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"CreatorNumberNotNull"));
		}
		if (StringUtils.isEmpty(fcreateTime)) {
			throw new TaskExternalException(getResource(ctx,
					"CreateTimeNotNull"));
		}
		// 判断单据状态,AUDITTED 表示已审批
		if (StringUtils.isEmpty(fstate)) {
			fstate = getResource(ctx, "AUDITTED");
		}
		if(stateStr == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		} 
		if(FDCBillStateEnum.getEnum(stateStr) == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		}
		
		if(!FDCTransmissionHelper.isNumber(fworkLoad)) {
			throw new TaskExternalException(getResource(ctx, "WorkLoadNotNumber"));
		}
		Date bizDate = FDCTransmissionHelper.checkDateFormat(fbizDate, getResource(ctx, "BizDateFormatError"));
		Date confirmDate = FDCTransmissionHelper.checkDateFormat(fconfirmDate, getResource(ctx, "ConfirmDateFormatError"));
		FDCTransmissionHelper.checkDateFormat(fcreateTime, getResource(ctx, "CreateDateFormatError"));

		/**
		 * 字符长度校验
		 */
		if (fcurProjectLongNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,
					"CurProjectLongNumberIsOver80"));
		}
		if (fdescription != null && fdescription.length() > 80) {
			throw new TaskExternalException(getResource(ctx,
					"DescriptionIsOver80"));
		}
		if (fstate != null && fstate.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "StateIsOver40"));
		}

		/**
		 * 数据库取数校验
		 */
		CurProjectInfo curProjectInfo = null;
		UserInfo creatot = null;
		UserInfo auditor = null;
		Date auditDate = null;
		CostCenterOrgUnitInfo costCenter = null;
		try {
			// 工程项目
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(
					new FilterItemInfo("longnumber", fcurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory
					.getLocalInstance(ctx).getCurProjectCollection(
							curProjectView);
			if (curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"CurProjectInfoNotFound"));
			}

			// 组织编码
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if (costCenter == null) {
				throw new TaskExternalException(getResource(ctx,
						"CostCenterNotFound"));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory
					.getLocalInstance(ctx).getCostCenterOrgUnitCollection(
							costCenterView);
			if (costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if (!StringUtils.isEmpty(costOrg_longNumber)
						&& !costCenterLongNumber.equals(costOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx,
							"CostOrgLongNumberNotFound"));
				}
			}

			// 合同
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(
					new FilterItemInfo("number", fcontractNumber));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory
					.getLocalInstance(ctx).getContractBillCollection(
							contractBillView);
			if (contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"ContractBillInfoNotFound"));
			}
			
			// 判断该合同是否在输入的工程项目下面
			if(!contractBillInfo.getCurProject().getId().toString().equals(
					curProjectInfo.getId().toString())) {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberIsNotExist"));
			}
			
			// 判断单头是否存在
			info = this.getWorkLoadConfirmBillInfo(fnumber, contractBillInfo.getId().toString(), ctx);
			if (info != null) {
				return info;
			} else {
				info = new WorkLoadConfirmBillInfo();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		// 制单人
		creatot = isUserNumber(fcreatorNumber, ctx);
		if (creatot == null) {
			throw new TaskExternalException(getResource(ctx,
					"CreatotNotFound"));
		}

		if (fstate.trim().equals(getResource(ctx, "AUDITTED"))) {
			if (StringUtils.isEmpty(fauditorNumber)) {
				throw new TaskExternalException(getResource(ctx,
						"AuditorNumberNotNull"));
			}
			if (StringUtils.isEmpty(fauditTime)) {
				throw new TaskExternalException(getResource(ctx,
						"AuditTimeNotNull"));
			}
		}
		if (!StringUtils.isEmpty(fauditTime)) {
			auditDate = FDCTransmissionHelper.checkDateFormat(fauditTime, getResource(ctx,"AuditTimeFormatError"));
		}
		if (!StringUtils.isEmpty(fauditorNumber)) {
			auditor = isUserNumber(fauditorNumber, ctx);
			if (auditor == null) {
				throw new TaskExternalException(getResource(ctx,
						"AuditorNumberNotFound"));
			}
		}

		/**
		 * 填充值
		 */
		info.setOrgUnit(costCenter.castToFullOrgUnitInfo());
		info.setCurProject(curProjectInfo);
		info.setContractBill(contractBillInfo); // 乙方从合同中带出
		info.setBizDate(bizDate);
		info.setNumber(fnumber);
		info.setWorkLoad(FDCTransmissionHelper.strToBigDecimal(fworkLoad));
		info.setConfirmDate(confirmDate);
		info.setDescription(fdescription);
		info.setState(FDCBillStateEnum.getEnum(stateStr));
		info.setCreator(creatot);
		info.setCreateTime(Timestamp.valueOf(fcreateTime + " 00:00:00"));
		info.setAuditor(auditor);
		info.setAuditTime(auditDate);

		return info;
	}

	private WorkLoadConfirmBillRelTaskInfo transmitEntry(Hashtable lineData,
			Context ctx) throws TaskExternalException {
		// 任务编码
		String taskNumber = FDCTransmissionHelper.getFieldValue(lineData,
				"FReftaskentry$workamountEntry$task_number");
		// 任务名称
		String taskName = FDCTransmissionHelper.getFieldValue(lineData,
				"FReftaskentry$workamountEntry$task_name_l2");

		if (taskNumber != null && taskNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,
					"TaskNumberIsOver80"));
		}
		if (taskName != null && taskName.length() > 80) {
			throw new TaskExternalException(
					getResource(ctx, "TaskNameIsOver80"));
		}

		infoEntry = new WorkLoadConfirmBillRelTaskInfo();

		FDCScheduleTaskInfo scheduleTaskInfo = null;
		WorkAmountEntryInfo workAmountEntryInfo = null;
		try {
			if(!StringUtils.isEmpty(taskNumber) || !StringUtils.isEmpty(taskName)) {
				// 判断任务是否存在
				FilterInfo scheduleTaskFilter = new FilterInfo();
				scheduleTaskFilter.getFilterItems().add(
						new FilterItemInfo("number", taskNumber));
				scheduleTaskFilter.getFilterItems().add(
						new FilterItemInfo("name", taskName.trim()));
				scheduleTaskFilter.setMaskString("#0 and #1");
				EntityViewInfo scheduleTaskView = new EntityViewInfo();
				scheduleTaskView.setFilter(scheduleTaskFilter);
				FDCScheduleTaskCollection scheduleTaskCollection = FDCScheduleTaskFactory
						.getLocalInstance(ctx).getFDCScheduleTaskCollection(
								scheduleTaskView);
				
				if (scheduleTaskCollection.size() > 0) {
					for (int i = 0; i < scheduleTaskCollection.size(); i++) {
						scheduleTaskInfo = scheduleTaskCollection.get(i);
						// 如果任务存在，则根据任务查询工程量确认单的分录是否存在
						FilterInfo workAmountEntryFilter = new FilterInfo();
						workAmountEntryFilter.getFilterItems().add(
								new FilterItemInfo("task.id", scheduleTaskInfo
										.getId().toString()));
						EntityViewInfo workAmountEntryView = new EntityViewInfo();
						workAmountEntryView.setFilter(workAmountEntryFilter);
						SorterItemInfo sorter = new SorterItemInfo("percent");
						sorter.setSortType(SortType.DESCEND);
						workAmountEntryView.getSorter().add(sorter);
						WorkAmountEntryCollection workAmountEntryCollection = WorkAmountEntryFactory
								.getLocalInstance(ctx)
								.getWorkAmountEntryCollection(workAmountEntryView);
						if (workAmountEntryCollection.size() > 0) {
							workAmountEntryInfo = workAmountEntryCollection.get(0);
							break;
						}
					}
					
					if (workAmountEntryInfo == null) {
						throw new TaskExternalException(getResource(ctx,
								"WorkAmountEntryInfoNotNumber"));
					}
					
					// 判断分录是否存在
					WorkLoadConfirmBillRelTaskCollection coll = info.getReftaskentry();
					if(coll!=null&&coll.size()>0)
					{
						WorkLoadConfirmBillRelTaskInfo tempInfo = null;
						for(int i=0;i<coll.size();i++)
						{
							tempInfo = coll.get(i);
							String id = tempInfo.getWorkamountEntry().getId().toString();
							if(id.equals(workAmountEntryInfo.getId().toString())) {
								throw new TaskExternalException(getResource(ctx,"EntryIsRepaet"));
							}
						}
					}
				} else {
					throw new TaskExternalException(getResource(ctx,
							"ScheduleTaskInfoNotFount"));
				}
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		if(workAmountEntryInfo != null) {
			workAmountEntryInfo.setTask(scheduleTaskInfo);
			infoEntry.setWorkamountEntry(workAmountEntryInfo);
			infoEntry.setContractBill(contractBillInfo);
			return infoEntry;
		} else {
			return null;
		}
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param number
	 *            用户编码
	 * @param ctx
	 * @return true/false 存在/不存在
	 */
	private UserInfo isUserNumber(String number, Context ctx) {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(
					new FilterItemInfo("number", number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx)
					.getUserCollection(userView);
			if (userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return user;
	}

	private String  getStateStr(String fState, Context ctx) {
		String stateStr = null;
		if (fState.equals(getResource(ctx, "SAVED"))) {
			// 保存
			stateStr = FDCBillStateEnum.SAVED_VALUE;
		} else if (fState.equals(getResource(ctx, "SUBMITTED"))) {
			// 已提交
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		} else if (fState.equals(getResource(ctx, "AUDITTED"))) {
			// 已审批
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}
		return stateStr;
	}

	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
