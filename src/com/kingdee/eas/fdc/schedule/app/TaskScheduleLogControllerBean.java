package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.fdc.schedule.framework.SchedulePropBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogCollection;
import com.kingdee.eas.fdc.schedule.SchedulePropBaseExtCollection;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogInfo;
import com.kingdee.eas.framework.CoreBaseInfo;

public class TaskScheduleLogControllerBean extends AbstractTaskScheduleLogControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskScheduleLogControllerBean");

	protected Map _saveTaskExt(Context ctx, Map model) throws BOSException,
			EASBizException {
		Map map = new HashMap();
		TaskScheduleLogInfo info = null;
		if(model.get("taskScheduleLog" )!= null){
			info = (TaskScheduleLogInfo) model.get("taskScheduleLog");
		}
		FDCScheduleTaskExtInfo taskExtInfo = new FDCScheduleTaskExtInfo();
		String wbsId;
		if(model.get("wbsID") != null){
			wbsId = (String) model.get("wbsID");
			FDCWBSInfo wbsInfo = new FDCWBSInfo();
			wbsInfo.setId(BOSUuid.read(wbsId));
			taskExtInfo.setWbs(wbsInfo);
			taskExtInfo.setId(BOSUuid.create(taskExtInfo.getBOSType()));
			FDCScheduleTaskExtFactory.getLocalInstance(ctx).save(taskExtInfo);
			if(info != null){
				info.setTaskExt(taskExtInfo);
				info.setId(BOSUuid.create(info.getBOSType()));
				save(ctx, info);
				map.put("taskScheduleLog", info);
				map.put("taskExtInfo", taskExtInfo);
			}
		}
		return map;
	}
}