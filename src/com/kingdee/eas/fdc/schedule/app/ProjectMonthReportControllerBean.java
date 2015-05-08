package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;

public class ProjectMonthReportControllerBean extends AbstractProjectMonthReportControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectMonthReportControllerBean");

	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
	}

	/**
	 * 更新月报的完成情况
	 * @param isWeek true为周报，false为月报
	 */
	protected void updateReportComplete(Context ctx, boolean isWeek, ProjectMonthReportEntryCollection cols) throws BOSException,
			EASBizException {
		for (int i = 0; i < cols.size(); i++) {
			OpReportEntryBaseInfo baseInfo = cols.get(i);
			if (Boolean.valueOf(baseInfo.get("isNext").toString()).booleanValue()) {
				// 下月的任务不用反写
				continue;
			}
			processReport(ctx, isWeek, baseInfo);
		}
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("projectSpecial.id");
		sic.add("entrys.*");
		sic.add("entrys.head.projectSpecial.id");		
		sic.add("entrys.relateTask.*");
		sic.add("entrys.respPerson.*");
		sic.add("entrys.respDept.*");
		ProjectMonthReportInfo projectMonthReportInfo = getProjectMonthReportInfo(ctx, new ObjectUuidPK(billId), sic);
		updateReportComplete(ctx, false, projectMonthReportInfo.getEntrys());
	}
}