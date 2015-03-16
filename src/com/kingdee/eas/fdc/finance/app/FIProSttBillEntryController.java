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
import com.kingdee.eas.fdc.finance.FIProSttBillEntryCollection;
import com.kingdee.eas.fdc.finance.FIProSttBillEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.BillEntryBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FIProSttBillEntryController extends BillEntryBaseController
{
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FIProSttBillEntryInfo getFIProSttBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FIProSttBillEntryCollection getFIProSttBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}