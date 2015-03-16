package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SignCustomerEntryController extends TranCustomerEntryController
{
    public SignCustomerEntryCollection getSignCustomerEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SignCustomerEntryCollection getSignCustomerEntryCollection(Context ctx) throws BOSException, RemoteException;
    public SignCustomerEntryCollection getSignCustomerEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SignCustomerEntryInfo getSignCustomerEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}