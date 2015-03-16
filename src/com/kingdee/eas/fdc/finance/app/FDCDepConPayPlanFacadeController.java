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
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCDepConPayPlanFacadeController extends BizController
{
    public Map getProjectPayPlanExeData(Context ctx, Set prjIds, Map param) throws BOSException, EASBizException, RemoteException;
    public void autoUpdateConPayPlan(Context ctx, String id, boolean isAudit) throws BOSException, EASBizException, RemoteException;
}