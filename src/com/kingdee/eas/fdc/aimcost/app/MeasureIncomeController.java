package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MeasureIncomeController extends FDCBillController
{
    public MeasureIncomeInfo getMeasureIncomeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MeasureIncomeInfo getMeasureIncomeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MeasureIncomeCollection getMeasureIncomeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MeasureIncomeCollection getMeasureIncomeCollection(Context ctx) throws BOSException, RemoteException;
    public MeasureIncomeCollection getMeasureIncomeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map getImportData(Context ctx, Map params) throws BOSException, EASBizException, RemoteException;
}