package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.HashMap;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MonthFactValueFacadeController extends BizController
{
    public IRowSet getFactValue(Context ctx, HashMap map) throws BOSException, EASBizException, RemoteException;
    public IRowSet getYearValue(Context ctx, String ProjectId, String year) throws BOSException, EASBizException, RemoteException;
    public IRowSet getLastValue(Context ctx, String projectId) throws BOSException, EASBizException, RemoteException;
}