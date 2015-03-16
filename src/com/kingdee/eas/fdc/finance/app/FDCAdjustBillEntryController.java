package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCAdjustBillEntryController extends CoreBillEntryBaseController
{
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}