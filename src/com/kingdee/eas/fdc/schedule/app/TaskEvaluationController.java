package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.schedule.TaskEvaluationCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TaskEvaluationController extends FDCDataBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationInfo getTaskEvaluationInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationInfo getTaskEvaluationInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationInfo getTaskEvaluationInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationCollection getTaskEvaluationCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public TaskEvaluationCollection getTaskEvaluationCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TaskEvaluationCollection getTaskEvaluationCollection(Context ctx) throws BOSException, RemoteException;
    public Set getDataBase(Context ctx, String type) throws BOSException, EASBizException, RemoteException;
    public boolean quoteDelete(Context ctx, String taskId) throws BOSException, EASBizException, RemoteException;
    public void updateSchedule(Context ctx, String srcId, BigDecimal complete, boolean isComplete) throws BOSException, EASBizException, RemoteException;
}