/**
 * 
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-9-2
 * @see
 */

public class FDCScheduleNumberHandler {

	public static void execute(Context ctx, FDCScheduleInfo info) throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号

		// add by david_yang R110411-511 2011.04.20
		if (info.getProject() != null) {
			info.setProject(CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(info.getProject().getId())));
		}

		String bindingProperty = "scheduleCodingType.number";
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		// 对成本中心进行处理
		String orgId = info.getAdminDept().getId().toString();
		if (StringUtils.isEmpty(orgId))
			return;
		boolean isExist = true;
		if (!iCodingRuleManager.isExist(info, orgId, bindingProperty)) {
			orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			if (!iCodingRuleManager.isExist(info, orgId, bindingProperty)) {
				isExist = false;
			}
		}
		if (isExist) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty) || !iCodingRuleManager.isAddView(info, orgId, bindingProperty)) { // 此处的orgId与步骤1
				// ）
				// 的orgId时一致的
				// ，
				// 判断用户是否启用断号支持功能
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
				info.setNumber(number);
			}
		}
	}

	public static void recycleNumber(Context ctx, IObjectPK pk, IObjectValue objectValue) throws BOSException, EASBizException, CodingRuleException {
		String bindingProperty = "scheduleCodingType.number";
		FDCScheduleInfo info = (FDCScheduleInfo) objectValue;
		ScheduleCodingTypeInfo scheduleType = info.getScheduleCodingType();
		if (scheduleType == null)
			return;
		if (ScheduleCodingTypeInfo.MAINTASK_CODINGID.equals(info.getScheduleCodingType().getId().toString())) {
			scheduleType.setNumber(ScheduleCodingTypeInfo.MAINTASK_CODINGNUM);
		} else if (ScheduleCodingTypeInfo.SPECIALTASK_CODINGID.equals(info.getScheduleCodingType().getId().toString())) {
			scheduleType.setNumber(ScheduleCodingTypeInfo.SPECIALTASK_CODINGNUM);
		}
		OrgUnitInfo orgInfo = ContextUtil.getCurrentOrgUnit(ctx);
		String curOrgId = orgInfo.getId().toString();
		if (info.getNumber() != null && info.getNumber().length() > 0) {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if (iCodingRuleManager.isExist(info, curOrgId, bindingProperty)
					&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId, bindingProperty)) {
				iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty, "", info.getNumber());
			}
		}
	}
}
