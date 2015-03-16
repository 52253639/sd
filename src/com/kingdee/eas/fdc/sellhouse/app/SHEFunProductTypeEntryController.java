package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHEFunProductTypeEntryController extends BizController
{
    public SHEFunProductTypeEntryInfo getValue(Context ctx, IObjectPK pk) throws BOSException, RemoteException;
    public SHEFunProductTypeEntryInfo getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, RemoteException;
    public SHEFunProductTypeEntryInfo getValue(Context ctx, String oql) throws BOSException, RemoteException;
    public SHEFunProductTypeEntryCollection getCollection(Context ctx) throws BOSException, RemoteException;
    public SHEFunProductTypeEntryCollection getCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHEFunProductTypeEntryCollection getCollection(Context ctx, String oql) throws BOSException, RemoteException;
}