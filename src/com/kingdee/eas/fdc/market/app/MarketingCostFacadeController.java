package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.base.param.ParamException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.app.VirtualRptBaseFacadeController;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketingCostFacadeController extends VirtualRptBaseFacadeController
{
    public RptParams getTableList(Context ctx, RptParams params, String ids) throws BOSException, ParamException, RemoteException;
}