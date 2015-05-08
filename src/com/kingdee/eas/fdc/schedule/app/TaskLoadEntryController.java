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
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TaskLoadEntryController extends CoreBillEntryBaseController
{
    public TaskLoadEntryInfo getTaskLoadEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TaskLoadEntryInfo getTaskLoadEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TaskLoadEntryInfo getTaskLoadEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateConfirmStatus(Context ctx, Set idSet, boolean isConfirm) throws BOSException, EASBizException, RemoteException;
}