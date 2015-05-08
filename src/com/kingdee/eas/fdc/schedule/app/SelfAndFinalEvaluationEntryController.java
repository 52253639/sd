package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SelfAndFinalEvaluationEntryController extends CoreBillEntryBaseController
{
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationEntryInfo getSelfAndFinalEvaluationEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SelfAndFinalEvaluationEntryCollection getSelfAndFinalEvaluationEntryCollection(Context ctx) throws BOSException, RemoteException;
}