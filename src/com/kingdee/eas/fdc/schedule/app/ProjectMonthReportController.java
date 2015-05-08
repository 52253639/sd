package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectMonthReportController extends OpReportBaseController
{
    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx) throws BOSException, RemoteException;
}