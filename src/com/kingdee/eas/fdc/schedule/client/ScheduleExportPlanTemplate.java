/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 导出计划模板
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleExportPlanTemplate {
	private FDCScheduleInfo editData;

	public ScheduleExportPlanTemplate(FDCScheduleInfo editData) {
		this.editData = editData;
	}

	public void exportPlanTemplate(RESchTemplateInfo schTemplateInfo) {
		if (null == schTemplateInfo.getName() || "".equals(schTemplateInfo.getName().trim())) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", this.editData.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		try {
			FDCScheduleTaskCollection fDCScheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);

			Map ttMap = new HashMap();
			Map tpMap = new HashMap();
			RESchTemplateTaskCollection rESchTemplateTaskCollection = new RESchTemplateTaskCollection();

			/* 取数据 */
			for (int k = 0; k < fDCScheduleTaskCollection.size(); k++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = new RESchTemplateTaskInfo();
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(k);
				/* 设置主键 */
				rESchTemplateTaskInfo.setId(BOSUuid.create((new RESchTemplateTaskInfo()).getBOSType()));
				/* 设置任务名称 */
				rESchTemplateTaskInfo.setName(fDCScheduleTaskInfo.getName());
				/* 设置前置任务 */

				/* 设置 任务类别 */
				rESchTemplateTaskInfo.setTaskType(fDCScheduleTaskInfo.getTaskType());
				/* 设置业务类型 */
				for (int s = 0; s < fDCScheduleTaskInfo.getBizType().size(); s++) {
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = fDCScheduleTaskInfo.getBizType().get(s);
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
					rESchTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					rESchTemplateTaskBizTypeInfo.setBizType(scheduleTaskBizTypeInfo.getBizType());
					rESchTemplateTaskInfo.getBusinessType().add(rESchTemplateTaskBizTypeInfo);
				}
				/* 设置工期 */
				rESchTemplateTaskInfo.setReferenceDay(fDCScheduleTaskInfo.getDuration());
				/* 设置成果类别 */
				rESchTemplateTaskInfo.setAchievementType(fDCScheduleTaskInfo.getAchievementType());
				/* 设置标准任务 */
				rESchTemplateTaskInfo.setStandardTask(fDCScheduleTaskInfo.getStandardTask());
				/* 设置备注 */
				rESchTemplateTaskInfo.setDescription(fDCScheduleTaskInfo.getDescription());
				/* 设置模板 */
				rESchTemplateTaskInfo.setTemplate(schTemplateInfo);
				/* 设置编码 */
				rESchTemplateTaskInfo.setNumber(fDCScheduleTaskInfo.getNumber());
				rESchTemplateTaskCollection.add(rESchTemplateTaskInfo);
				ttMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo);
				if (null != fDCScheduleTaskInfo.getParent() && null != fDCScheduleTaskInfo.getParent().getId()
						&& !"".equals(fDCScheduleTaskInfo.getParent().getId().toString().trim())) {
					tpMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo.getParent().getId());
				}
				/* 设置长编码 */
				String longNumber = fDCScheduleTaskInfo.getLongNumber();
				rESchTemplateTaskInfo.setLongNumber(longNumber.replaceAll("\\.", "!"));
				/* 设置级次 */
				rESchTemplateTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel());
				/* 设置是否是子节点 */
				rESchTemplateTaskInfo.setIsLeaf(fDCScheduleTaskInfo.isIsLeaf());
			}

			CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
			/* 存数据 */
			for (int j = 0; j < rESchTemplateTaskCollection.size(); j++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(j);

				Object pObject = tpMap.get(rESchTemplateTaskInfo.getId().toString());

				/* 判断是否存在父节点 */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = ttMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof RESchTemplateTaskInfo) {
						RESchTemplateTaskInfo chTemplateTaskInfo = (RESchTemplateTaskInfo) sObject;
						rESchTemplateTaskInfo.setParent(chTemplateTaskInfo);
						coreBaseCollection.add(chTemplateTaskInfo);
					}
				}
				RESchTemplateTaskFactory.getRemoteInstance().addnew(rESchTemplateTaskInfo);
			}
			// RESchTemplateTaskFactory.getRemoteInstance().updateBatchData(
			// coreBaseCollection);
		} catch (BOSException e) {
			// logger.error(e.getMessage());
		} catch (Exception e) {
			// logger.error(e.getMessage());
		}
	}
}
