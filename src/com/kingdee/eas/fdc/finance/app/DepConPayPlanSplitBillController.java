package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillController;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillCollection;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DepConPayPlanSplitBillController extends FDCSplitBillController
{
    public Map fetchData(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}