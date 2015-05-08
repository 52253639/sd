package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TaskEvaluationBillController extends FDCBillController
{
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(Context ctx) throws BOSException, RemoteException;
}