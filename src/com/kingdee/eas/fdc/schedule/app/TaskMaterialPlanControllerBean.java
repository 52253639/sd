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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo;
import com.kingdee.eas.fdc.schedule.SchedulePropBaseExtCollection;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanCollection;

public class TaskMaterialPlanControllerBean extends AbstractTaskMaterialPlanControllerBean
{
  	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskMaterialPlanControllerBean");


	protected Map _saveTaskExt(Context ctx, Map model) throws BOSException,
			EASBizException {
		Map map = new HashMap();
		TaskMaterialPlanInfo materialPlanInfo = null;
		if(model.get("materialPlan")!= null){
			materialPlanInfo= (TaskMaterialPlanInfo) model.get("materialPlan");
		}
		FDCScheduleTaskExtInfo taskExtInfo = new FDCScheduleTaskExtInfo();
		String wbsID;
		if(model.get("wbsID")!=null){
			wbsID = (String) model.get("wbsID"); 
			FDCWBSInfo wbsInfo = new FDCWBSInfo();
			wbsInfo.setId(BOSUuid.read(wbsID));
			taskExtInfo.setWbs(wbsInfo);
			taskExtInfo.setId(BOSUuid.create(taskExtInfo.getBOSType()));
			FDCScheduleTaskExtFactory.getLocalInstance(ctx).save(taskExtInfo);
			if(materialPlanInfo != null){
				materialPlanInfo.setTaskExt(taskExtInfo);
				materialPlanInfo.setId(BOSUuid.create(materialPlanInfo.getBOSType()));
				save(ctx, materialPlanInfo);
				map.put("materialPlan",materialPlanInfo );
				map.put("taskExtInfo", taskExtInfo);
			}
		}
		return map;
	}
}