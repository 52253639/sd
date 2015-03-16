package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusCollection;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectPeriodStatusController extends CoreBaseController
{
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map closeInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException, RemoteException;
    public void closeProject(Context ctx, List projectIds) throws BOSException, EASBizException, RemoteException;
    public Map closeAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException, RemoteException;
    public Map closeUnInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException, RemoteException;
    public void closeUnProject(Context ctx, List projectIds) throws BOSException, EASBizException, RemoteException;
    public Map closeUnAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException, RemoteException;
    public PeriodInfo fetchPeriod(Context ctx, String projectId, Date bookedDate, boolean isCost) throws BOSException, EASBizException, RemoteException;
    public Map fetchInitData(Context ctx, String curOrgId, boolean isCompany) throws BOSException, EASBizException, RemoteException;
    public void paramCheck(Context ctx, String comId) throws BOSException, EASBizException, RemoteException;
}