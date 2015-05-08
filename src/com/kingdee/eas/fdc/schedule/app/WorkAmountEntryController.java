package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WorkAmountEntryController extends CoreBillEntryBaseController
{
    public WorkAmountEntryCollection getWorkAmountEntryCollection(Context ctx) throws BOSException, RemoteException;
    public WorkAmountEntryCollection getWorkAmountEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WorkAmountEntryCollection getWorkAmountEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}