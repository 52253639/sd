package com.kingdee.eas.fdc.schedule.app;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.util.NumericExceptionSubItem;

public class AchievementManagerControllerBean extends AbstractAchievementManagerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.AchievementManagerControllerBean");
    
     protected boolean _checkNumberDup(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException, BOSException {
		return true;
	}
     
     protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Map initData = super._fetchInitData(ctx, paramMap);
		initData.put("bookedDate", new Date());
		return initData;
		// return super._fetchInitData(ctx, paramMap);
	} 
     
     protected Result _save(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
    	/* TODO 自动生成方法存根 */
    	return super._save(ctx, colls);
    }
     
     protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	/* TODO 自动生成方法存根 */
    	super._save(ctx, pk, model);
    }
     
     protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	/* TODO 自动生成方法存根 */
    	AchievementManagerInfo info = (AchievementManagerInfo) model;
    	  if(info.getRelateTask()!=null){
   		   FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(info.getRelateTask().getSrcID()));
   		   info.setRelateTask(taskInfo);
   	   }
     	checkNameDup(ctx,info);
    	return super._save(ctx, model);
    }
    
    public IObjectPK save(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		// AchievementManagerInfo info = (AchievementManagerInfo) model;
		// String name = info.getName().trim();
		// FDCScheduleTaskInfo task = info.getRelateTask();
		// String taskID = task.getId().toString();
		// FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder.appendSql(
		// "select 1 from t_sch_achievementmanager where fname=? and frelatetaskid=?"
		// );
		// builder.addParam(name);
		// builder.addParam(taskID);
		// IRowSet set = builder.executeQuery();
		// try {
		// if (set.next()) {
		// throw new EASBizException(new NumericExceptionSubItem("100",
		// "同一“工程项目”下“成果名称”不允许重复！"));
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
    	AchievementManagerInfo info = (AchievementManagerInfo) model;
    	checkNameDup(ctx,info);
		return super.save(ctx, info);
	}
    

	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {

		if (!isUseName())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", billInfo.getName()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", ((AchievementManagerInfo) billInfo).getRelateTask().getId().toString()));
		if (billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));
		if (_exists(ctx, filter)) {
			// throw new ContractException(ContractException.NAME_DUP);
			throw new EASBizException(new NumericExceptionSubItem("100", "同一“工程项目”下“成果名称”不允许重复！"));
		} else 
			return;
	 }

	protected void _afterschreportwriteBack(Context ctx, IObjectValue model) throws BOSException {
		// FDCScheduleTaskInfo info = (FDCScheduleTaskInfo)model;
		// FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder.appendSql("update t_sch_");
		//		

	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		AchievementManagerInfo info = (AchievementManagerInfo) model;
  	  if(info.getRelateTask()!=null){
 		   FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(info.getRelateTask().getSrcID()));
 		   info.setRelateTask(taskInfo);
 	   }
		return super._submit(ctx, model);
	}
}