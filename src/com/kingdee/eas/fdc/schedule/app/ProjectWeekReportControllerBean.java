package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;

public class ProjectWeekReportControllerBean extends AbstractProjectWeekReportControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectWeekReportControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
    		throws BOSException, EASBizException {
    
    }
    
    public boolean checkNumberDup(Context arg0, IObjectPK arg1,
    		CoreBillBaseInfo arg2) throws BOSException, EASBizException {
    	   return false;
    }
     
    protected IObjectPK _saveRelations(Context arg0, IObjectValue arg1,
    		IObjectCollection arg2) throws BOSException, EASBizException {
    	return null;
    }
    
    protected void checkBill(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
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
		ProjectWeekReportInfo projectWeekReportInfo = getProjectWeekReportInfo(ctx, new ObjectUuidPK(billId), sic);
		updateReportComplete(ctx, true, projectWeekReportInfo.getEntrys());
	}
    
    /**
	 * 更新周报的完成情况
	 */
	protected void updateReportComplete(Context ctx, boolean isWeek, ProjectWeekReportEntryCollection cols) throws BOSException,
			EASBizException {
		//直接从前端页面带过来，不必重新去取
		for (int i = 0; i < cols.size(); i++) {
			OpReportEntryBaseInfo baseInfo = cols.get(i);
			if (Boolean.valueOf(baseInfo.get("isNext").toString()).booleanValue()) {
				// 下周的任务不用反写
				continue;
			}
			processReport(ctx, isWeek, baseInfo);
		}
	}
	
}