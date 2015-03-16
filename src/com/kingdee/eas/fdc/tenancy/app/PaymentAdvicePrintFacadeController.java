package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PaymentAdvicePrintFacadeController extends BizController
{
    public Map getValue(Context ctx, Map param) throws BOSException, RemoteException;
    public IRowSet getPrintData(Context ctx, Set idSet) throws BOSException, RemoteException;
    public IRowSet getComment(Context ctx) throws BOSException, RemoteException;
}