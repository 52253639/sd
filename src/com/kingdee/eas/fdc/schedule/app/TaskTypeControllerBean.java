package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.DataBaseInfo;

public class TaskTypeControllerBean extends AbstractTaskTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskTypeControllerBean");
    protected void _enabled(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException
    {
    }
    protected void _disEnabled(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException
    {
    }
    public void checkNumberDup(Context ctx, DataBaseInfo dataBaseInfo)
    		throws BOSException, EASBizException {
    	TaskTypeInfo taskTypeInfo = (TaskTypeInfo) dataBaseInfo; 
    	FilterInfo filter = new FilterInfo();
    	if(taskTypeInfo.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",taskTypeInfo.getId(),CompareType.NOTEQUALS));
    	}
    	filter.getFilterItems().add(new FilterItemInfo("number",taskTypeInfo.getNumber()));
    	filter.getFilterItems().add(new FilterItemInfo("parent.id",taskTypeInfo.getParent().getId()));
    	if(_exists(ctx,filter)){
    		throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { taskTypeInfo.getNumber() });
    	}
    }
    public void checkNameDup(Context ctx, DataBaseInfo dataBaseInfo)
    		throws BOSException, EASBizException {
    	TaskTypeInfo taskTypeInfo = (TaskTypeInfo) dataBaseInfo; 
    	FilterInfo filter = new FilterInfo();
    	if(taskTypeInfo.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",taskTypeInfo.getId(),CompareType.NOTEQUALS));
    	}
    	filter.getFilterItems().add(new FilterItemInfo("name",taskTypeInfo.getName()));
    	if(_exists(ctx,filter)){
    		throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { taskTypeInfo.getName()});
    	}
    }
}