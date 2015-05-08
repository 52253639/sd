package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SelfAndFinalEvaluationController extends FDCBillController
{
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(Context ctx) throws BOSException, RemoteException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(Context ctx, String oql) throws BOSException, RemoteException;
}