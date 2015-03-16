package com.kingdee.eas.fdc.sellhouse.app;

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
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InsteadCollectOutBillEntryController extends CoreBillEntryBaseController
{
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}