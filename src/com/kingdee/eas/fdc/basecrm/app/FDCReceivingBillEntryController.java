package com.kingdee.eas.fdc.basecrm.app;

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
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCReceivingBillEntryController extends CoreBillEntryBaseController
{
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillEntryInfo getFDCReceivingBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public FDCReceivingBillEntryCollection getFDCReceivingBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}