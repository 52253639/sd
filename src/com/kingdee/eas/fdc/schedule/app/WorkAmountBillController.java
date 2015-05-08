package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.WorkAmountBillCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.WorkAmountBillInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WorkAmountBillController extends FDCBillController
{
    public WorkAmountBillInfo getWorkAmountBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WorkAmountBillInfo getWorkAmountBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WorkAmountBillInfo getWorkAmountBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WorkAmountBillCollection getWorkAmountBillCollection(Context ctx) throws BOSException, RemoteException;
    public WorkAmountBillCollection getWorkAmountBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public WorkAmountBillCollection getWorkAmountBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public Map initTaskInfo(Context ctx, String projectId) throws BOSException, RemoteException;
    public Map getSelectedTask(Context ctx, Map param) throws BOSException, RemoteException;
}