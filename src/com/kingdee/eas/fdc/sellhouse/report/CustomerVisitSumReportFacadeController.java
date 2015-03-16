package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.report.util.RptParams;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CustomerVisitSumReportFacadeController extends BizController
{
    public List getTableList(Context ctx, RptParams params) throws BOSException, RemoteException;
}