package com.kingdee.eas.fdc.finance.app;

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
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CostClosePeriodFacadeController extends BizController
{
    public void tracePayment(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void checkCostSplit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public String traceCostClose(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void frozenProject(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public String antiCostClose(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, String contractId, String periodId) throws BOSException, EASBizException, RemoteException;
}